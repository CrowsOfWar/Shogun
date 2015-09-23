package crowsofwar.shogun.common.gui;

import static crowsofwar.shogun.common.management.ShogunGuiIDs.ID_CONVERSATION;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.IGuiHandler;
import crowsofwar.shogun.Shogun;
import crowsofwar.shogun.common.conversations.ShogunConversation;
import crowsofwar.shogun.common.data.ShogunPlayerData;
import crowsofwar.shogun.common.data.ShogunPlayerDataFetcher;

/**
 * The GUI handler for the Shogun mod - keeps track of GUI.
 * 
 * @author CrowsOfWar
 */
public class ShogunGuiHandler implements IGuiHandler {
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		ShogunPlayerData data = ShogunPlayerDataFetcher.FETCHER.getDataPerformance(player);
		switch (id) {
			case ID_CONVERSATION: {
				return new ShogunContainerConversation(new ShogunConversation(player, data.getTalkingToNPC(world)));
			}
		}
		
		FMLLog.bigWarning("Shogun> This is a bug! Problem with making a new server-side GUI because the GUI ID was incorrect: " + id);
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		return Shogun.proxy.createClientGUI(id, player, world, x, y, z);
	}
	
}
