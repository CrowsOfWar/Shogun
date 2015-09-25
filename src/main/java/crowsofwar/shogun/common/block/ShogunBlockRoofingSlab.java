package crowsofwar.shogun.common.block;

import java.util.Random;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import crowsofwar.shogun.common.management.ShogunBlocks;
import crowsofwar.shogun.common.management.ShogunCreativeTabs;

public class ShogunBlockRoofingSlab extends BlockSlab {
	
	private final int color;
	
	public ShogunBlockRoofingSlab(int color, boolean doubleSlab) {
		super(doubleSlab, Material.wood);
		setBlockTextureName("shogun:roofing_" + ShogunBlocks.colors[color]);
		setBlockName("roofing");
		setHardness(1.2f);
		setResistance(3);
		setLightOpacity(0);
		setStepSound(soundTypeWood);
		setCreativeTab(ShogunCreativeTabs.tabBuilding);
		this.color = color;
	}
	
	/**
	 * Called to get the unlocalized name for tooltips
	 */
	@Override
	public String func_150002_b(int metadata) {
		return ShogunBlocks.blockRoofing[color].getUnlocalizedName();
	}
	
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Item.getItemFromBlock(ShogunBlocks.blockRoofingSlab[color]);
	}
	
	@Override
	protected ItemStack createStackedBlock(int metadata) {
		return new ItemStack(Item.getItemFromBlock(ShogunBlocks.blockRoofingSlab[color]), 2, metadata & 7);
	}
	
	@Override
	public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
		return Item.getItemFromBlock(this);
	}
	
}
