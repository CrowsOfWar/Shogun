package crowsofwar.shogun.common.gui;

import crowsofwar.shogun.Shogun;
import crowsofwar.shogun.common.conversations.ShogunConversation;
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
	 * The covversation at hand, it holds the player and the NPC
	 */
	private final ShogunConversation conversation;
	
	public ShogunContainerConversation(ShogunConversation conversation) {
		this.conversation = conversation;
	}
	
	public ShogunConversation getConversation() {
		return conversation;
	}
	
	public EntityPlayer getPlayer() {
		return conversation.getPlayer();
	}
	
	public ShogunNPC getNPC() {
		return conversation.getNPC();
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return player == getPlayer() && player.getDistanceSqToEntity(getNPC()) <= 5*5; // TODO Make the range configurable and stuff
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		getNPC().talkTo(null);
		ShogunPlayerDataFetcher.FETCHER.getDataPerformance(player).setTalkingTo(-1);
		Shogun.proxy.setCurrentConversation(null);
	}
	
}
