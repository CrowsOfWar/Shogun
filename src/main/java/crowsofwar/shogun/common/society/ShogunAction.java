package crowsofwar.shogun.common.society;

import java.util.HashMap;
import java.util.Map;

/**
 * A type of action that the player took. The action includes
 * an honor modifier.
 * 
 * @author CrowsOfWar
 */
public enum ShogunAction {
	
	HIRE_ASSASSINS(-150),
	REFUSE_SEPPUKU(-300),
	/** Defeat a daimyo, either personally or with troops */
	DEFEAT_DAIMYO(250),
	/** Defeat a soldier, either personally or with troops */
	DEFEAT_SOLDIER(3);
	
	private static final Map<Integer, ShogunAction> actionByID;
	private final int honorModifier;
	
	private ShogunAction(int honorModifier) {
		this.honorModifier = honorModifier;
	}
	
	static {
		actionByID = new HashMap<Integer, ShogunAction>();
		for (ShogunAction action : values()) actionByID.put(action.id(), action);
	}
	
	public int id() {
		return ordinal();
	}
	
	public static ShogunAction getActionByID(int id) {
		return actionByID.get(id);
	}
	
}
