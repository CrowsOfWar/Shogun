package crowsofwar.shogun.client;

import static crowsofwar.shogun.common.management.ShogunGuiIDs.ID_CONVERSATION;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLLog;
import crowsofwar.shogun.client.gui.ShogunGUIConversation;
import crowsofwar.shogun.client.render.ShogunRenderNPC;
import crowsofwar.shogun.common.ShogunCommonProxy;
import crowsofwar.shogun.common.conversations.ShogunConversation;
import crowsofwar.shogun.common.data.ShogunPlayerData;
import crowsofwar.shogun.common.data.ShogunPlayerDataFetcher;
import crowsofwar.shogun.common.entity.ShogunNPCPeasant;
import crowsofwar.shogun.common.entity.ShogunNPCTestBrain;
import crowsofwar.shogun.common.gui.ShogunContainerConversation;

public class ShogunClientProxy extends ShogunCommonProxy {
	
	private ShogunConversation currentConversation;
	
	public ShogunClientProxy() {
		currentConversation = null;
	}
	
	@Override
	public void sideSpecifics() {
		RenderingRegistry.registerEntityRenderingHandler(ShogunNPCPeasant.class, new ShogunRenderNPC("peasant", 2));
		RenderingRegistry.registerEntityRenderingHandler(ShogunNPCTestBrain.class, new ShogunRenderNPC("peasant", 1));
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
	
}
