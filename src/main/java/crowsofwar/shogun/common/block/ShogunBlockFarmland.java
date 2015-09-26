package crowsofwar.shogun.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import crowsofwar.shogun.common.management.ShogunCreativeTabs;
import crowsofwar.shogun.common.management.ShogunRenderIDs;

public class ShogunBlockFarmland extends Block {
	
	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	
	@SideOnly(Side.CLIENT)
	private IIcon iconSide;
	
	public ShogunBlockFarmland() {
		super(Material.ground);
		setBlockName("sfarmland");
		setBlockTextureName("sfarmland");
		setCreativeTab(ShogunCreativeTabs.tabBuilding);
		setHardness(0.5f);
		setResistance(0.5f);
		setStepSound(soundTypeGravel);
		setBlockBounds(0, 0, 0, 1, 0.5f, 1);
	}
	
	@Override
	public int getRenderType() {
		return ShogunRenderIDs.ID_FARMLAND;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		iconTop = register.registerIcon("minecraft:farmland_wet");
		iconSide = register.registerIcon("minecraft:dirt");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == ForgeDirection.UP.ordinal() ? iconTop : iconSide;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean isBlockSolid(IBlockAccess p_149747_1_, int p_149747_2_, int p_149747_3_, int p_149747_4_, int p_149747_5_) {
		return false;
	}
	
//	@Override
//	@SideOnly(Side.CLIENT)
//	public int getRenderBlockPass() {
//		return 1;
//	}
	
}
