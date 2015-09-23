package crowsofwar.shogun.common.gui;

import crowsofwar.shogun.common.data.ShogunPlayerDataFetcher;
import crowsofwar.shogun.common.entity.ShogunNPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

/**
 * The container for the conversation GUI.
 * 
 * @author CrowsOfWar
 */
public class ShogunContainerConversation extends Container {
	
	/**
	 * The player talking to the NPC
	 */
	private final EntityPlayer player;
	
	/**
	 * The NPC talking to the player
	 */
	private final ShogunNPC npc;
	
	public ShogunContainerConversation(EntityPlayer player, ShogunNPC npc) {
		this.player = player;
		this.npc = npc;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return player == this.player && player.getDistanceSqToEntity(npc) <= 5*5; // TODO Make the range configurable and stuff
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		npc.talkTo(null);
		ShogunPlayerDataFetcher.FETCHER.getDataPerformance(player).setTalkingTo(-1);
	}
	
}
