package crowsofwar.shogun.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ShogunBlockRoofing extends Block {
	
	public ShogunBlockRoofing() {
		super(Material.wood);
		setBlockTextureName("shogun:roofing");
		setBlockName("roofing");
		setHardness(1.5f);
		setResistance(4);
	}
	
}
