package crowsofwar.shogun.common;

import crowsofwar.shogun.common.conversations.ShogunConversation;
import crowsofwar.shogun.common.management.ShogunArmorType;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ShogunCommonProxy {
	
	public void sideSpecifics() {}
	
	/**
	 * Make a client-side GUI element
	 */
	public Object createClientGUI(int id, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	public ShogunConversation getCurrentConversation() {
		return null;
	}

	public void setCurrentConversation(ShogunConversation conversation) {
		
	}
	
	public int getCurrentRenderPass() { return 0; }
	
	public void setCurrentRenderPass(int pass) {}
	
	public ModelBiped getArmorModel(ShogunArmorType type, int armorSlot, EntityLivingBase entity) {
		return null;
	}
	
}
