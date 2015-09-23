package net.minecraft.block;

/**
 * Allows you to call the constructor for {@link BlockStairs#BlockStairs(Block, int)},
 * which is the only constructor for BlockStairs. You would use this class because
 * the access modifier is "protected" - annoying, right?
 * 
 * @author CrowsOfWar
 */
public final class GoreCoreStairConstructorHack {
	
	/**
	 * Construct a new instance of BlockStairs using
	 * {@link BlockStairs#BlockStairs(Block, int)}. You have
	 * to call setBlockName and optionally setCreativeTab, however.
	 * 
	 * @param block The block to use as the texture, hardness, etc.
	 * @param metadata The metadata of that block, used for rendering
	 * @return A new instance of BlockStairs using its constructor
	 */
	public static BlockStairs blockStairs(Block block, int metadata) {
		return new BlockStairs(block, metadata);
	}
	
}
