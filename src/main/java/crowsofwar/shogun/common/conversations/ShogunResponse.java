package crowsofwar.shogun.common.conversations;

/**
 * Conversations are a series of questions and responses
 * between a NPC and a player. The response enum is a
 * response given by a player. The meaning of the response
 * is interpreted based on the prompt.
 * 
 * @author CrowsOfWar
 */
public enum ShogunResponse implements ShogunConversationStage {
	
	OK("ok"),
	NO("no"),
	NEVER("never"),
	MAYBE_LATER("maybeLater"),
	HELLO("hello"),
	GO_AWAY_RUDE("goAwayRude");
	
	private final String langKey;
	
	private ShogunResponse(String langKey) {
		this.langKey = langKey;
	}
	
	public String getLanguageKey() {
		return langKey;
	}
	
}
