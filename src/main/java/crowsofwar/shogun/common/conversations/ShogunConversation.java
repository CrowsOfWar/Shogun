package crowsofwar.shogun.common.conversations;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;

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
	
	public ShogunConversation() {
		stages = new ArrayList<ShogunConversationStage>();
	}
	
	public void addToHistory(ShogunConversationStage stage) {
		stages.add(stage);
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
	public List<ShogunResponse> getResponsesForCurrentPrompt(EntityPlayer player) {
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
	
}
