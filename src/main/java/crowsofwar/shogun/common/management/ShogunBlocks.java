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
	
	public static final String[] colors = { "white", "gray", "black", "red", "green", "brown" };
	public static Block blockRoofing[];
	public static Block blockRoofingStairs[];
	public static BlockSlab blockRoofingSlab[];
	public static BlockSlab blockRoofingSlabDouble[];
	
	/**
	 * Initialize the blocks and then register them - this is
	 * for startup.
	 */
	public static void initAndRegister() {
		blockRoofing = new Block[colors.length];
		blockRoofingStairs = new Block[colors.length];
		blockRoofingSlab = new BlockSlab[colors.length];
		blockRoofingSlabDouble = new BlockSlab[colors.length];
		
		for (int i = 0; i < colors.length; i++) {
			String color = colors[i];
			
			blockRoofing[i] = new ShogunBlockRoofing(color);
			blockRoofingStairs[i] = GoreCoreStairConstructorHack.blockStairs(blockRoofing[i], 0)
					.setBlockName("roofing_stairs").setLightOpacity(0).setStepSound(Block.soundTypeWood);
			blockRoofingSlab[i] = new ShogunBlockRoofingSlab(i, false);
			blockRoofingSlabDouble[i] = new ShogunBlockRoofingSlab(i, true);
			
			GameRegistry.registerBlock(blockRoofing[i], "roofing_" + color);
			GameRegistry.registerBlock(blockRoofingStairs[i], "roofing_stairs_" + color);
			GameRegistry.registerBlock(blockRoofingSlab[i], ShogunItemRoofingSlab.class, "roofing_slab_" + color, blockRoofingSlab[i], blockRoofingSlabDouble[i], false);
			GameRegistry.registerBlock(blockRoofingSlabDouble[i], ShogunItemRoofingSlab.class, "roofing_double_slab_" + color, blockRoofingSlab[i], blockRoofingSlabDouble[i], true);
		}
		
	}
}
