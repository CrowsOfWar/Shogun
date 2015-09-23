package crowsofwar.shogun.common.conversations;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.FMLLog;
import crowsofwar.shogun.common.entity.ShogunNPC;

/**
 * <p>By definition, a conversation is an exchange of questions
 * and responses between a NPC and a player. Therefore, this
 * class contains a log of the questions and responses from
 * the NPC and the player.</p>
 * 
 * <p>The conversation object also manages the current prompt
 * and the responses. The possible responses are calculated by
 * first taking into account the prompt's type, e.g. "yes
 * no question". The prompt type has a number of responses which
 * are added to the list. Then, the prompt can have other responses
 * which are added to the list too. Finally, other factors are
 * taken into account, mainly the player's recent actions.</p>
 * 
 * @author CrowsOfWar
 */
public class ShogunConversation {
	
	private final List<ShogunConversationStage> stages;
	
	/**
	 * The responses currently available. This is null when the current stage is a
	 * response.
	 */
	private List<ShogunResponse> currentResponses;
	
	private final EntityPlayer player;
	private final ShogunNPC npc;
	
	public ShogunConversation(EntityPlayer player, ShogunNPC npc) {
		this.player = player;
		this.npc = npc;
		stages = new ArrayList<ShogunConversationStage>();
		currentResponses = null;
	}
	
	public void addToHistory(ShogunConversationStage stage) {
		if (getCurrentStage().getClass() != stage.getClass()) {
			FMLLog.bigWarning("Shogun> Someone is messing around with conversations and is trying to add a conversation stage " +
					"out of order. It's supposed to be prompt -> response, prompt -> response, but the order has been broken.");
			FMLLog.bigWarning("Debug:");
			Thread.dumpStack();
		}
		stages.add(stage);
		if (stage instanceof ShogunPrompt) {
			currentResponses = getResponsesForCurrentPrompt();
		} else {
			currentResponses = null;
			addToHistory(npc.getNextConversationPrompt(this));
		}
	}
	
	/**
	 * Get the latest stage, null if there are no stages
	 * @return
	 */
	public ShogunConversationStage getCurrentStage() {
		return stages.size() == 0 ? null : stages.get(stages.size() - 1);
	}
	
	/**
	 * Get the current prompt, null if the current stage is not a prompt or if there are no stages
	 * @return
	 */
	public ShogunPrompt getCurrentPrompt() {
		ShogunConversationStage stage = getCurrentStage();
		return stage == null ? null : (stage instanceof ShogunPrompt ? (ShogunPrompt) stage : null);
	}
	
	/**
	 * Get the responses for this current prompt. Null if there is no
	 * open prompt or if nothing happened in the conversation.
	 * @return
	 */
	private List<ShogunResponse> getResponsesForCurrentPrompt() {
		ShogunPrompt prompt = getCurrentPrompt();
		if (prompt != null) {
			List<ShogunResponse> res = prompt.getAllResponses();
			// Now: take into account the player's recent actions...
			// TODO Take into account the recent actions
			// (not done because there are virtually no actions currently)
			return res;
		}
		
		return null;
	}
	
	/**
	 * Get a list of responses for the current prompt. This is null if there is not a prompt
	 * right now.
	 * @return
	 */
	public List<ShogunResponse> getCurrentResponses() {
		return currentResponses;
	}
	
	/**
	 * Override the list of current responses to that list. The list of current responses is
	 * automatically calculated, so only do this if you know what you're doing.
	 * @param currentResponses
	 */
	public void overrideCurrentResponses(List<ShogunResponse> currentResponses) {
		this.currentResponses = currentResponses;
	}
	
	public EntityPlayer getPlayer() {
		return player;
	}
	
	public ShogunNPC getNPC() {
		return npc;
	}
	
}