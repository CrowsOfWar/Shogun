package crowsofwar.shogun.common.data;

import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;

import crowsofwar.gorecore.data.GoreCoreDataSaver;
import crowsofwar.gorecore.data.GoreCorePlayerData;
import crowsofwar.shogun.common.society.ShogunSocialClass;

public class ShogunPlayerData extends GoreCorePlayerData {
	
	private ShogunSocialClass socialClass;
	
	public ShogunPlayerData(GoreCoreDataSaver dataSaver, UUID playerID) {
		super(dataSaver, playerID);
	}
	
	public ShogunSocialClass getSocialClass() {
		if (socialClass == null) socialClass = ShogunSocialClass.ERROR;
		return socialClass;
	}
	
	public void changeSocialClass(ShogunSocialClass socialClass) {
		this.socialClass = socialClass;
	}
	
	@Override
	protected void readPlayerDataFromNBT(NBTTagCompound nbt) {
		changeSocialClass(ShogunSocialClass.getClassByID(nbt.getInteger("SocialClass")));
	}
	
	@Override
	protected void writePlayerDataToNBT(NBTTagCompound nbt) {
		nbt.setInteger("SocialClass", getSocialClass().id());
	}
	
}
