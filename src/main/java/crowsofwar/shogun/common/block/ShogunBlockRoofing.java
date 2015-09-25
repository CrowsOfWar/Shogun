package crowsofwar.shogun.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ShogunBlockRoofing extends Block {
	
	public ShogunBlockRoofing(String color) {
		super(Material.wood);
		setBlockTextureName("shogun:roofing_" + color);
		setBlockName("roofing");
		setHardness(1.5f);
		setResistance(4);
		setStepSound(soundTypeWood);
	}
	
}
