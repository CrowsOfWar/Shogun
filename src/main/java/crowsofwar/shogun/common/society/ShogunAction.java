package crowsofwar.shogun.common.society;

import java.util.HashMap;
import java.util.Map;

import crowsofwar.shogun.common.entity.ShogunNPCIntelligent;

/**
 * A type of action that the player took. The action includes
 * an honor modifier.
 * 
 * @author CrowsOfWar
 */
public enum ShogunAction {
	
	HIRE_ASSASSINS(-150),
	REFUSE_SEPPUKU(-300),
	DISRESPECT_CATHOLICS,
	/** Defeat a daimyo, either personally or with troops */
	DEFEAT_DAIMYO(250),
	/** Defeat a soldier, either personally or with troops */
	DEFEAT_SOLDIER(3);
	
	private static final Map<Integer, ShogunAction> actionByID;
	private final boolean shouldBeInterpreted;
	private final int honorModifier;
	
	/**
	 * An action which has a set honor modifier, regardless of what the NPC thinks
	 */
	private ShogunAction(int honorModifier) {
		this(false, honorModifier);
	}
	
	/**
	 * An action which has an honor modifier based off of what the NPC thinks
	 */
	private ShogunAction() {
		this(true, 0);
	}
	
	private ShogunAction(boolean shouldBeInterpreted, int honorModifier) {
		this.shouldBeInterpreted = shouldBeInterpreted;
		this.honorModifier = honorModifier;
	}
	
	static {
		actionByID = new HashMap<Integer, ShogunAction>();
		for (ShogunAction action : values()) actionByID.put(action.id(), action);
	}
	
	/**
	 * Determine the honor modifier, from that NPC's point of view. Some actions
	 * modify honor based on opinions, some actions don't.
	 */
	public int determineHonorModifier(ShogunNPCIntelligent npc) {
		return shouldBeInterpreted ? npc.interpretAction(this) : honorModifier;
	}
	
	public int id() {
		return ordinal();
	}
	
	public static ShogunAction getActionByID(int id) {
		return actionByID.get(id);
	}
	
}
