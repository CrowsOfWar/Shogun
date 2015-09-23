package crowsofwar.shogun.common.management;

import cpw.mods.fml.common.registry.GameRegistry;
import crowsofwar.shogun.common.block.ShogunBlockRoofing;
import net.minecraft.block.Block;

/**
 * Similar to the vanilla Blocks class, but for Shogun. This
 * is different because it also manages registering.
 * 
 * @author CrowsOfWar
 */
public class ShogunBlocks {
	
	public static Block blockRoofing;
	
	/**
	 * Initialize the blocks and then register them - this is
	 * for startup.
	 */
	public static void initAndRegister() {
		blockRoofing = new ShogunBlockRoofing();
		
		GameRegistry.registerBlock(blockRoofing, "roofing");
	}
}
