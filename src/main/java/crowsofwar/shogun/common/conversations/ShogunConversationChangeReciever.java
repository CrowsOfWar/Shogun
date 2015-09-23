package crowsofwar.shogun.common.conversations;

/**
 * Hooks on to whenever the conversation gets a new prompt/response.
 * 
 * @author CrowsOfWar
 */
public interface ShogunConversationChangeReciever {
	
	/**
	 * Called whenever the conversation's {@link ShogunConversation#addToHistory(ShogunConversationStage)}
	 * method is called.
	 */
	void onChanged();
	
}
