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
import crowsofwar.gorecore.util.GoreCoreNBTInterfaces.MapUser;
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
	
	private static final MapUser<UUID, Integer> CACHED_HONOR_LEVEL_MAP_USER = new MapUser<UUID, Integer>() {
		@Override
		public UUID createK(NBTTagCompound nbt, Object[] constructArgsK) {
			return GoreCoreNBTUtil.readUUIDFromNBT(nbt, "PlayerUUID");
		}
		
		@Override
		public Integer createV(NBTTagCompound nbt, UUID key, Object[] constructArgsV) {
			return nbt.getInteger("Honor");
		}

		@Override
		public void writeK(NBTTagCompound nbt, UUID obj) {
			GoreCoreNBTUtil.writeUUIDToNBT(nbt, "PlayerUUID", obj);
		}

		@Override
		public void writeV(NBTTagCompound nbt, Integer obj) {
			nbt.setInteger("Honor", obj);
		}
	};
	
	private List<ShogunOpinion> opinions;
	/** The map is playerID -> actions known about. The list is the indices of the player's actions that the NPC knows about. */
	private final Map<UUID, List<Integer>> knowsAboutEvents;
	/** The cached honor calculations, 0 to 1000. -1 means to recalculate the honor level. */
	private final Map<UUID, Integer> cachedHonorLevels;
	
	public ShogunNPCIntelligent(World world) {
		super(world);
		
		opinions = new ArrayList<ShogunOpinion>();
		knowsAboutEvents = new HashMap<UUID, List<Integer>>();
		cachedHonorLevels = new HashMap<UUID, Integer>();
		
	}
	
	public int getHonorLevel(UUID playerID) {
		if (cachedHonorLevels.containsKey(playerID) && cachedHonorLevels.get(playerID) != -1) {
			return cachedHonorLevels.get(playerID);
		} else {
			ShogunPlayerData data = (ShogunPlayerData) ShogunWorldData.getWorldData(worldObj).getPlayerData(playerID);
			
			int honor = data.getSocialClass().getDefaultHonor();
			List<Integer> knowledge = getEventKnowledge(playerID);
			List<ShogunAction> actionLog = data.getActionLog();
			
			for (int i = 0; i < actionLog.size(); i++) {
				ShogunAction action = actionLog.get(i);
				if (knowledge.contains(i)) honor += action.determineHonorModifier(this);
			}
			
			return honor;
		}
	}
	
	/**
	 * <p>Get a list of indices for that player. Each index represents the action
	 * that player performed through his/her action log. So, this effectively
	 * gives a list of actions the NPC knows about for that player.</p>
	 * 
	 * <p><strong>IMPORTANT - If you modify this list</strong>, call
	 * {@link #recalculateHonorLevel(playerID)}. Otherwise the honor level may
	 * not be changed.</p>
	 * 
	 * @param playerID The account UUID of that player
	 * @return A knowledge list for that player
	 */
	public List<Integer> getEventKnowledge(UUID playerID) {
		if (!knowsAboutEvents.containsKey(playerID)) knowsAboutEvents.put(playerID, new ArrayList<Integer>());
		return knowsAboutEvents.get(playerID);
	}
	
	/**
	 * <p>Get a list of indices for that player. Each index represents the action
	 * that player performed through his/her action log. So, this effectively
	 * gives a list of actions the NPC knows about for that player.</p>
	 * 
	 * <p><strong>IMPORTANT - If you modify this list</strong>, call
	 * {@link #recalculateHonorLevel(playerID)}. Otherwise the honor level may
	 * not be changed.</p>
	 * 
	 * @param player The player
	 * @return A knowledge list for that player
	 */
	public List<Integer> getEventKnowledge(EntityPlayer player) {
		return getEventKnowledge(GoreCorePlayerUUIDs.getUUIDPerformance(player.getCommandSenderName()));
	}
	
	/**
	 * Mark that player's honor level for recalculation - this would be called
	 * upon changes that somehow modify the honor level.
	 * 
	 * @param playerID The ID of the player to recalculate
	 */
	public void recalculateHonorLevel(UUID playerID) {
		cachedHonorLevels.put(playerID, -1);
	}
	
	/**
	 * Mark that player's honor level for recalculation - this would be called
	 * upon changes that somehow modify the honor level.
	 * 
	 * @param player The player to recalculate
	 */
	public void recalculateHonorLevel(EntityPlayer player) {
		recalculateHonorLevel(GoreCorePlayerUUIDs.getUUIDPerformance(player.getCommandSenderName()));
	}
	
	/**
	 * Learn about the latest action performed by that player.
	 * 
	 * @param player The player
	 */
	public void learnLatestAction(EntityPlayer player) {
		ShogunPlayerData data = ShogunPlayerDataFetcher.FETCHER.getDataPerformance(player);
		if (data != null) {
			getEventKnowledge(player).add(data.getActionLog().size() - 1);
			recalculateHonorLevel(player);
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
	
	/**
	 * Check if the NPC has that opinion.
	 * 
	 * @param opinion The opinion 
	 * @return Whether the NPC has the opinion or not
	 */
	public boolean hasOpinion(ShogunOpinion opinion) {
		return opinions.contains(opinion);
	}
	
	/**
	 * Set whether the NPC has that opinion.
	 * 
	 * @param opinion The opinion
	 * @param has Whether the NPC should have the opinion or not
	 */
	public void setOpinion(ShogunOpinion opinion, boolean has) {
		if (has && !opinions.contains(opinion)) opinions.add(opinion);
		if (!has && opinions.contains(opinion)) opinions.remove(opinion);
	}
	
	public int interpretAction(ShogunAction action) {
		if (action == ShogunAction.DISRESPECT_CATHOLICS) {
			return hasOpinion(ShogunOpinion.CATHOLIC) ? -250 : 100;
		}
		
		return 0;
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
		
		cachedHonorLevels = GoreCoreNBTUtil.readMapFromNBT(nbt, CACHED_HONOR_LEVEL_MAP_USER, "CachedHonor", new Object[0],
				new Object[0]);
		
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
		
		GoreCoreNBTUtil.writeMapToNBT(nbt, cachedHonorLevels, CACHED_HONOR_LEVEL_MAP_USER, "CachedHonor");
		
	}
	
}