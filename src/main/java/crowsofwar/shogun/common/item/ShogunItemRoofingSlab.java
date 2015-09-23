package crowsofwar.shogun.common.item;

import crowsofwar.shogun.common.block.ShogunBlockRoofingSlab;
import crowsofwar.shogun.common.management.ShogunBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;

public class ShogunItemRoofingSlab extends ItemSlab {
	
	public ShogunItemRoofingSlab(Block block, ShogunBlockRoofingSlab slabSingle, ShogunBlockRoofingSlab slabDouble,
			Boolean doubleSlab) {
		super(block, slabSingle, slabDouble, doubleSlab);
	}
	
}
