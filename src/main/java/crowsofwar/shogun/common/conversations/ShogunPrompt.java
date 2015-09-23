package crowsofwar.shogun.common.conversations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Conversations are a series of questions and responses
 * between a NPC and a player. The prompt enumeration
 * represents a question asked by a NPC.
 * 
 * @author CrowsOfWar
 */
public enum ShogunPrompt implements ShogunConversationStage {
	
	ERROR("error"), // "BEEP. BEEP. Error imminent."
	WHAT_IS_IT("whatIsIt"), // "What is it?"
	HELLO("hello", ShogunResponse.HELLO, ShogunResponse.GO_AWAY_RUDE), // "Hello"
	ASK_BUY_GOODS("askBuyGoods", PromptType.YES_NO_MAYBE); // "would you like to buy my wonderful wares?"
	
	private final String langKey;
	private final PromptType type;
	private final List<ShogunResponse> moreResponses;
	private final List<ShogunResponse> allResponses;
	
	private ShogunPrompt(String langKey) {
		this(langKey, PromptType.NONE);
	}
	
	private ShogunPrompt(String langKey, ShogunResponse... moreResponses) {
		this(langKey, PromptType.NONE, moreResponses);
	}
	
	private ShogunPrompt(String langKey, PromptType type, ShogunResponse... moreResponses) {
		this.langKey = langKey;
		this.type = type;
		this.moreResponses = Arrays.asList(moreResponses);
		this.allResponses = new ArrayList<ShogunResponse>(this.moreResponses);
		this.allResponses.addAll(type.getResponseList());
	}
	
	public String getLanguageKey() {
		return langKey;
	}
	
	public PromptType getType() {
		return type;
	}
	
	public List<ShogunResponse> getMoreResponses() {
		return moreResponses;
	}
	
	public List<ShogunResponse> getAllResponses() {
		return allResponses;
	}
	
	/**
	 * A prompt type is tagged to each prompt. If the prompt has
	 * a prompt type, responses are AUTOMATICALLY added for that
	 * prompt type. More responses can be added however
	 * 
	 * @author CrowsOfWar
	 */
	public enum PromptType {
		NONE,
		OK(ShogunResponse.OK),
		YES_NO(ShogunResponse.OK, ShogunResponse.NO, ShogunResponse.NEVER),
		YES_NO_MAYBE(ShogunResponse.OK, ShogunResponse.NO, ShogunResponse.NEVER, ShogunResponse.MAYBE_LATER);
		
		private final List<ShogunResponse> predefinedResponses;
		
		private PromptType(ShogunResponse... predefinedResponses) {
			this.predefinedResponses = Arrays.asList(predefinedResponses);
		}
		
		/**
		 * Get the list of responses which should automatically
		 * be added to the possible response list
		 */
		public List<ShogunResponse> getResponseList() {
			return predefinedResponses;
		}
		
	}
	
	public int id() {
		return ordinal();
	}
	
	public static ShogunPrompt getByID(int id) {
		for (ShogunPrompt prompt : values()) if (prompt.id() == id) return prompt;
		return null;
	}
	
}
