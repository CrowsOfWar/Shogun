package crowsofwar.shogun.common.block;

import java.util.Random;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import crowsofwar.shogun.common.management.ShogunBlocks;

public class ShogunBlockRoofingSlab extends BlockSlab {
	
	public ShogunBlockRoofingSlab(boolean doubleSlab) {
		super(doubleSlab, Material.wood);
		setBlockTextureName("shogun:roofing");
		setBlockName("roofing");
		setHardness(1.2f);
		setResistance(3);
		setLightOpacity(0);
	}
	
	/**
	 * Called to get the unlocalized name for tooltips
	 */
	@Override
	public String func_150002_b(int metadata) {
		return ShogunBlocks.blockRoofing.getUnlocalizedName();
	}
	
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Item.getItemFromBlock(ShogunBlocks.blockRoofingSlab);
	}
	
	@Override
	protected ItemStack createStackedBlock(int metadata) {
		return new ItemStack(Item.getItemFromBlock(ShogunBlocks.blockRoofingSlab), 2, metadata & 7);
	}
	
}
