package crowsofwar.shogun.client;

import static crowsofwar.shogun.common.management.ShogunGuiIDs.ID_CONVERSATION;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLLog;
import crowsofwar.shogun.client.gui.ShogunGUIConversation;
import crowsofwar.shogun.client.render.ShogunModelOyoriArmor;
import crowsofwar.shogun.client.render.ShogunRenderFarmland;
import crowsofwar.shogun.client.render.ShogunRenderNPC;
import crowsofwar.shogun.common.ShogunCommonProxy;
import crowsofwar.shogun.common.conversations.ShogunConversation;
import crowsofwar.shogun.common.data.ShogunPlayerData;
import crowsofwar.shogun.common.data.ShogunPlayerDataFetcher;
import crowsofwar.shogun.common.entity.ShogunNPCPeasant;
import crowsofwar.shogun.common.entity.ShogunNPCTestBrain;
import crowsofwar.shogun.common.gui.ShogunContainerConversation;
import crowsofwar.shogun.common.management.ShogunArmorType;
import crowsofwar.shogun.common.management.ShogunRenderIDs;

public class ShogunClientProxy extends ShogunCommonProxy {
	
	private ShogunConversation currentConversation;
	private int currentRenderPass;
	
	private ModelBiped modelOyoriArmor;
	
	public ShogunClientProxy() {
		currentConversation = null;
		
		modelOyoriArmor = new ShogunModelOyoriArmor();
	}
	
	@Override
	public void sideSpecifics() {
		RenderingRegistry.registerEntityRenderingHandler(ShogunNPCPeasant.class, new ShogunRenderNPC("peasant", 2));
		RenderingRegistry.registerEntityRenderingHandler(ShogunNPCTestBrain.class, new ShogunRenderNPC("peasant", 1));
		addISBRH(new ShogunRenderFarmland());
	}
	
	private void addISBRH(ISimpleBlockRenderingHandler handler) {
		RenderingRegistry.registerBlockHandler(ShogunRenderIDs.ID_FARMLAND = RenderingRegistry.getNextAvailableRenderId(), handler);
	}
	
	@Override
	public Object createClientGUI(int id, EntityPlayer player, World world, int x, int y, int z) {
		ShogunPlayerData data = ShogunPlayerDataFetcher.FETCHER.getDataPerformance(player);
		switch (id) {
			case ID_CONVERSATION: {
				if (currentConversation == null)
					setCurrentConversation(new ShogunConversation(player, data.getTalkingToNPC(world)));
				return new ShogunGUIConversation(new ShogunContainerConversation(currentConversation));
			}
		}
		
		FMLLog.bigWarning("Shogun> This is a bug! Problem with making a new server-side GUI because the GUI ID was incorrect: " + id);
		return null;
	}
	
	@Override
	public ShogunConversation getCurrentConversation() {
		return currentConversation;
	}
	
	@Override
	public void setCurrentConversation(ShogunConversation conversation) {
		this.currentConversation = conversation;
	}
	
	@Override
	public int getCurrentRenderPass() {
		return currentRenderPass;
	}
	
	@Override
	public void setCurrentRenderPass(int pass) {
		this.currentRenderPass = pass;
	}
	
	@Override
	public ModelBiped getArmorModel(ShogunArmorType type, int armorSlot, EntityLivingBase entity) {
		
		ModelBiped model = null;
		if (type == ShogunArmorType.OYORI) model = modelOyoriArmor;
		
		if (model != null) {
			model.bipedHead.showModel = armorSlot == 0;
			model.bipedHeadwear.showModel = armorSlot == 0;
			model.bipedBody.showModel = armorSlot == 1 || armorSlot == 2;
			model.bipedRightArm.showModel = armorSlot == 1;
			model.bipedLeftArm.showModel = armorSlot == 1;
			model.bipedRightLeg.showModel = armorSlot == 2
					|| armorSlot == 3;
			model.bipedLeftLeg.showModel = armorSlot == 2
					|| armorSlot == 3;
			model.isSneak = entity.isSneaking();
			model.isRiding = entity.isRiding();
			model.isChild = entity.isChild();
			model.heldItemRight = entity.getEquipmentInSlot(0) != null ? 1 : 0;
			if (entity instanceof EntityPlayer) model.aimedBow = ((EntityPlayer) entity).getItemInUseDuration() > 2;
			
			return model;
		}
		
		return null;
	}
	
}
