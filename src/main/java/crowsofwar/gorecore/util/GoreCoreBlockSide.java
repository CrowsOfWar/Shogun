package crowsofwar.gorecore.util;

/**
 * Keeps track of block sides for side-related methods.
 * <a href="http://bedrockminer.jimdo.com/modding-tutorials/basic-modding-1-7/multi-texture-blocks/#cc-m-header-9714804223">
 * Thanks to Bedrock Miner for the numbers!</a>
 * 
 * @author CrowsOfWar
 */
public enum GoreCoreBlockSide {
	
	/**
	 * The bottom side of blocks; -y
	 */
	BOTTOM,
	
	/**
	 * The top side of blocks; +y
	 */
	TOP,
	
	/**
	 * The northern side of blocks; -z
	 */
	NORTH,
	
	/**
	 * The southern side of blocks; +z
	 */
	SOUTH,
	
	/**
	 * The western side of blocks; -x
	 */
	WEST,
	
	/**
	 * The eastern side of blocks; +x
	 */
	EAST;
	
	/**
	 * Get the integer for this block side to use in block
	 * methods.
	 */
	public int side() {
		return ordinal();
	}
	
}
