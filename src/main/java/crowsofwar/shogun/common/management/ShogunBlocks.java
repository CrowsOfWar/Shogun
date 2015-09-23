package crowsofwar.shogun.common.management;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.GoreCoreStairConstructorHack;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import crowsofwar.shogun.common.block.ShogunBlockRoofing;
import crowsofwar.shogun.common.block.ShogunBlockRoofingSlab;
import crowsofwar.shogun.common.item.ShogunItemRoofingSlab;

/**
 * Similar to the vanilla Blocks class, but for Shogun. This
 * is different because it also manages registering.
 * 
 * @author CrowsOfWar
 */
public class ShogunBlocks {
	
	public static Block blockRoofing;
	public static Block blockRoofingStairs;
	public static BlockSlab blockRoofingSlab;
	public static BlockSlab blockRoofingSlabDouble;
	
	/**
	 * Initialize the blocks and then register them - this is
	 * for startup.
	 */
	public static void initAndRegister() {
		blockRoofing = new ShogunBlockRoofing();
		blockRoofingStairs = GoreCoreStairConstructorHack.blockStairs(blockRoofing, 0).setBlockName("roofingStairs").setLightOpacity(0);
		blockRoofingSlab = new ShogunBlockRoofingSlab(false);
		blockRoofingSlabDouble = new ShogunBlockRoofingSlab(true);
		
		GameRegistry.registerBlock(blockRoofing, "roofing");
		GameRegistry.registerBlock(blockRoofingStairs, "roofingStairs");
		GameRegistry.registerBlock(blockRoofingSlab, ShogunItemRoofingSlab.class, "roofing_slab", blockRoofingSlab, blockRoofingSlabDouble, false);
		GameRegistry.registerBlock(blockRoofingSlabDouble, ShogunItemRoofingSlab.class, "roofing_double_slab", blockRoofingSlab, blockRoofingSlabDouble, true);
		
	}
}
