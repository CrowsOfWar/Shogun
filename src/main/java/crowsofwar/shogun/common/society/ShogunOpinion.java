package crowsofwar.shogun.common.society;

import java.util.HashMap;
import java.util.Map;

/**
 * Various NPC opinions.
 * 
 * @author CrowsOfWar
 */
public enum ShogunOpinion {
	
	/** The NPC is Catholic - false by default because most Japanese people weren't Christians. No option for other types
	 * of Christians exist because the only Christianity in Japan was Catholicism. */
	CATHOLIC,
	/** The NPC supports usage of guns, most would think this dishonorable */
	GUNS;
	
	private static final Map<Integer, ShogunOpinion> opinionByID;
	
	static {
		opinionByID = new HashMap<Integer, ShogunOpinion>();
		for (ShogunOpinion opinion : values()) opinionByID.put(opinion.id(), opinion);
	}
	
	public int id() {
		return ordinal();
	}
	
	public static ShogunOpinion getOpinionByID(int id) {
		return opinionByID.get(id);
	}
	
}
