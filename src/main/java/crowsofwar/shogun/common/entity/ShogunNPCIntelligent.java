package crowsofwar.shogun.common.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import cpw.mods.fml.common.FMLLog;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import crowsofwar.gorecore.util.GoreCoreNBTInterfaces.CreateFromNBT;
import crowsofwar.gorecore.util.GoreCoreNBTInterfaces.WriteToNBT;
import crowsofwar.gorecore.util.GoreCoreNBTUtil;
import crowsofwar.gorecore.util.GoreCorePlayerUUIDs;
import crowsofwar.shogun.common.data.ShogunPlayerData;
import crowsofwar.shogun.common.data.ShogunPlayerDataFetcher;
import crowsofwar.shogun.common.data.ShogunWorldData;
import crowsofwar.shogun.common.society.ShogunAction;
import crowsofwar.shogun.common.society.ShogunOpinion;

/**
 * Defines NPCs which are "intelligent", that is, they have
 * opinions and keep track of what players are doing. Daimyos
 * will be complex and in-depth characters, but peasants will
 * not.
 * 
 * @author CrowsOfWar
 */
public abstract class ShogunNPCIntelligent extends ShogunNPC {
	
	private List<ShogunOpinion> opinions;
	/** The map is playerID -> actions known about. The list is the indices of the player's actions that the NPC knows about. */
	private final Map<UUID, List<Integer>> knowsAboutEvents;
	
	public ShogunNPCIntelligent(World world) {
		super(world);
		
		opinions = new ArrayList<ShogunOpinion>();
		knowsAboutEvents = new HashMap<UUID, List<Integer>>();
		
	}
	
	public int calculateHonorLevel(UUID playerID) {
		ShogunPlayerData data = (ShogunPlayerData) ShogunWorldData.getWorldData(worldObj).getPlayerData(playerID);
		
		int honor = data.getSocialClass().getDefaultHonor();
		List<Integer> knowledge = getEventKnowledge(playerID);
		List<ShogunAction> actionLog = data.getActionLog();
		
		for (int i = 0; i < actionLog.size(); i++) {
			ShogunAction action = actionLog.get(i);
			if (knowledge.contains(i)) honor += action.getHonorModifier();
		}
		
		return honor;
		
	}
	
	/**
	 * Get a list of indices for that player. Each index represents the action
	 * that player performed through his/her action log. So, this effectively
	 * gives a list of actions the NPC knows about for that player.
	 * 
	 * @param playerID The account UUID of that player
	 * @return A knowledge list for that player
	 */
	private List<Integer> getEventKnowledge(UUID playerID) {
		if (!knowsAboutEvents.containsKey(playerID)) knowsAboutEvents.put(playerID, new ArrayList<Integer>());
		return knowsAboutEvents.get(playerID);
	}
	
	/**
	 * Get a list of indices for that player. Each index represents the action
	 * that player performed through his/her action log. So, this effectively
	 * gives a list of actions the NPC knows about for that player.
	 * 
	 * @param player The player
	 * @return A knowledge list for that player
	 */
	private List<Integer> getEventKnowledge(EntityPlayer player) {
		return getEventKnowledge(GoreCorePlayerUUIDs.getUUIDPerformance(player.getCommandSenderName()));
	}
	
	/**
	 * Learn about the latest action performed by that playe.r
	 * 
	 * @param player The player
	 */
	public void learnLatestAction(EntityPlayer player) {
		ShogunPlayerData data = ShogunPlayerDataFetcher.FETCHER.getDataPerformance(player);
		if (data != null) {
			getEventKnowledge(player).add(data.getActionLog().size() - 1);
		} else {
			FMLLog.warning("Shogun> NPC could not learn the latest action of " + player.getCommandSenderName() + ".");
			Thread.dumpStack();
		}
	}
	
	/**
	 * Completely brainwash the NPC about that player, so that the NPC knows
	 * nothing about what the player has done, whether it be good or bad...
	 * 
	 * @param player The player
	 */
	public void brainwash(EntityPlayer player) {
		getEventKnowledge(player).clear();
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		
		opinions = GoreCoreNBTUtil.readListFromNBT(nbt, "Opinions", new CreateFromNBT<ShogunOpinion>() {
			@Override
			public ShogunOpinion create(NBTTagCompound nbt, Object[] methodsExtraData, Object[] extraData) {
				return ShogunOpinion.getOpinionByID(nbt.getInteger("ID"));
			}
		});
		
		knowsAboutEvents.clear();
		NBTTagList knowledgeList = nbt.getTagList("KnowledgeAboutPlayers", 10);
		for (int i = 0; i < knowledgeList.tagCount(); i++) {
			NBTTagCompound knowledgeNBT = knowledgeList.getCompoundTagAt(i);
			
			knowsAboutEvents.put(GoreCoreNBTUtil.readUUIDFromNBT(knowledgeNBT, "PlayerUUID"),
					GoreCoreNBTUtil.readListFromNBT(knowledgeNBT, "KnowledgeList", new CreateFromNBT<Integer>() {
						@Override
						public Integer create(NBTTagCompound nbt, Object[] methodsExtraData, Object[] extraData) {
							return nbt.getInteger("ActionLogIndex");
						}
			}));
			
		}
		
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		
		GoreCoreNBTUtil.writeListToNBT(nbt, "Opinions", new WriteToNBT<ShogunOpinion>() {
			@Override
			public void write(NBTTagCompound nbt, ShogunOpinion object, Object[] methodsExtraData, Object[] extraData) {
				nbt.setInteger("ID", object.id());
			}
		}, opinions);
		
		NBTTagList knowledgeList = new NBTTagList();
		Set<Map.Entry<UUID, List<Integer>>> entrySet = knowsAboutEvents.entrySet();
		for (Map.Entry<UUID, List<Integer>> entry : entrySet) {
			NBTTagCompound knowledgeNBT = new NBTTagCompound();
			GoreCoreNBTUtil.writeUUIDToNBT(knowledgeNBT, "PlayerUUID", entry.getKey());
			GoreCoreNBTUtil.writeListToNBT(knowledgeNBT, "KnowledgeList", new WriteToNBT<Integer>() {
				@Override
				public void write(NBTTagCompound innerNBT, Integer object, Object[] methodsExtraData, Object[] extraData) {
					innerNBT.setInteger("ActionLogIndex", object);
				}
			}, entry.getValue(), new Object[0]);
			knowledgeList.appendTag(knowledgeNBT);
		}
		nbt.setTag("KnowledgeAboutPlayers", knowledgeList);
		
	}
	
}