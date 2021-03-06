package crowsofwar.shogun.common.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import crowsofwar.gorecore.data.GoreCoreDataSaver;
import crowsofwar.gorecore.data.GoreCorePlayerData;
import crowsofwar.gorecore.util.GoreCoreNBTInterfaces.CreateFromNBT;
import crowsofwar.gorecore.util.GoreCoreNBTInterfaces.WriteToNBT;
import crowsofwar.gorecore.util.GoreCoreNBTUtil;
import crowsofwar.shogun.common.entity.ShogunNPC;
import crowsofwar.shogun.common.society.ShogunAction;
import crowsofwar.shogun.common.society.ShogunSocialClass;

public class ShogunPlayerData extends GoreCorePlayerData {
	
	private ShogunSocialClass socialClass;
	private List<ShogunAction> actionLog;
	
	/**
	 * The ID of the NPC this player is currently talking to. -1 if not talking. This isn't saved because the opened GUI isn't saved.
	 */
	private long talkingTo;
	
	public ShogunPlayerData(GoreCoreDataSaver dataSaver, UUID playerID) {
		super(dataSaver, playerID);
		
		actionLog = new ArrayList<ShogunAction>();
		talkingTo = -1;
		
	}
	
	public ShogunSocialClass getSocialClass() {
		if (socialClass == null) socialClass = ShogunSocialClass.ERROR;
		return socialClass;
	}
	
	public void changeSocialClass(ShogunSocialClass socialClass) {
		this.socialClass = socialClass;
	}
	
	public List<ShogunAction> getActionLog() {
		return actionLog;
	}
	
	public void setActionLog(List<ShogunAction> actionLog) {
		this.actionLog = actionLog;
	}
	
	public long getTalkingTo() {
		return talkingTo;
	}
	
	public ShogunNPC getTalkingToNPC(World world) {
		// TODO Find a better approach. Use maps?
		for (Object obj : world.loadedEntityList) {
			if (obj instanceof ShogunNPC && ((ShogunNPC) obj).getShogunID() == talkingTo) return (ShogunNPC) obj;
		}
		return null;
	}
	
	public void setTalkingTo(long talkingTo) {
		this.talkingTo = talkingTo;
	}
	
	public void setTalkingToNPC(ShogunNPC npc) {
		setTalkingTo(npc.getShogunID());
	}
	
	@Override
	protected void readPlayerDataFromNBT(NBTTagCompound nbt) {
		changeSocialClass(ShogunSocialClass.getClassByID(nbt.getInteger("SocialClass")));
		setActionLog(GoreCoreNBTUtil.readListFromNBT(nbt, "Actions", new CreateFromNBT<ShogunAction>() {
			@Override
			public ShogunAction create(NBTTagCompound nbt, Object[] methodsExtraData, Object[] extraData) {
				return ShogunAction.getActionByID(nbt.getInteger("ID"));
			}
		}));
	}
	
	@Override
	protected void writePlayerDataToNBT(NBTTagCompound nbt) {
		nbt.setInteger("SocialClass", getSocialClass().id());
		GoreCoreNBTUtil.writeListToNBT(nbt, "Actions", new WriteToNBT<ShogunAction>() {
			@Override
			public void write(NBTTagCompound nbt, ShogunAction object, Object[] methodsExtraData, Object[] extraData) {
				nbt.setInteger("ID", object.id());
			}
		}, actionLog);
	}
	
}
