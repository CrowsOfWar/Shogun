package crowsofwar.shogun.common.society;

import java.util.HashMap;
import java.util.Map;

/**
 * The different social classes in Feudal Japan. Some you
 * cannot achieve at first.
 * 
 * @author CrowsOfWar
 */
public enum ShogunSocialClass {
	
	ERROR(false, 0),
	BARBARIAN(true, -300),
	BARBARIAN_LONGER(false, -50),
	PEASANT(false, 100),
	SAMURAI(false, 300),
	RONIN(true, 50),
	DAIMYO(false, 400),
	ROYALTY(true, 500),
	SHOGUN(false, 700),
	EMPEROR(false, 800);
	
	private static final Map<Integer, ShogunSocialClass> classByID;
	private final boolean startingClass;
	private final int defaultHonor;
	
	private ShogunSocialClass(boolean startingClass, int defaultHonor) {
		this.startingClass = startingClass;
		this.defaultHonor = defaultHonor;
	}
	
	static {
		classByID = new HashMap<Integer, ShogunSocialClass>();
		for (ShogunSocialClass socialClass : values()) classByID.put(socialClass.id(), socialClass);
	}
	
	public boolean canStartWith() {
		return startingClass;
	}
	
	public int getDefaultHonor() {
		return defaultHonor;
	}
	
	public int id() {
		return ordinal();
	}
	
	public static ShogunSocialClass getClassByID(int id) {
		return classByID.get(id);
	}
	
}
