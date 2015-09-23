package crowsofwar.shogun.common.management;

import cpw.mods.fml.common.registry.GameRegistry;
import crowsofwar.shogun.common.block.ShogunBlockRoofing;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.GoreCoreStairConstructorHack;

/**
 * Similar to the vanilla Blocks class, but for Shogun. This
 * is different because it also manages registering.
 * 
 * @author CrowsOfWar
 */
public class ShogunBlocks {
	
	public static Block blockRoofing;
	public static Block blockRoofingStairs;
	
	/**
	 * Initialize the blocks and then register them - this is
	 * for startup.
	 */
	public static void initAndRegister() {
		blockRoofing = new ShogunBlockRoofing();
		blockRoofingStairs = GoreCoreStairConstructorHack.blockStairs(blockRoofing, 0).setBlockName("roofingStairs").setLightOpacity(0);
		
		GameRegistry.registerBlock(blockRoofing, "roofing");
		GameRegistry.registerBlock(blockRoofingStairs, "roofingStairs");
		
	}
}
