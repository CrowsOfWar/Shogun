package crowsofwar.shogun.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import crowsofwar.shogun.Shogun;
import crowsofwar.shogun.common.management.ShogunCreativeTabs;
import crowsofwar.shogun.common.management.ShogunRenderIDs;

public class ShogunBlockFarmland extends Block {
	
	private static final ForgeDirection[] ADJACENT_DIRECTIONS = new ForgeDirection[] {
		ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.NORTH, ForgeDirection.EAST
	};
	
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
	
	@Override
	public boolean canRenderInPass(int pass) {
		Shogun.proxy.setCurrentRenderPass(pass);
		return true;
	}
	
	@Override
	public int getRenderBlockPass() {
		return 1;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		// BlockLiquid
		System.out.println("On neighbor block change");
		int waterLevelThis = world.getBlockMetadata(x, y, z);
		for (int i = 0; i < ADJACENT_DIRECTIONS.length; i++) {
			ForgeDirection dir = ADJACENT_DIRECTIONS[i];
			int modX = x + dir.offsetX;
			int modY = y + dir.offsetY; // technically not necessary
			int modZ = z + dir.offsetZ;
			if (world.getBlock(modX, modY, modZ) == this) {
				// Check water level
				int waterLevelThat = world.getBlockMetadata(modX, modY, modZ);
				System.out.println("Another thing to the " + dir);
				System.out.println("THIS/THAT || " + waterLevelThis + "/" + waterLevelThat);
				// Transfer some? IF this is <1 higher than that
				if (waterLevelThis - 1 > waterLevelThat) {
					System.out.println("Hahger, lawer dis and raise dat");
					world.setBlockMetadataWithNotify(x, y, z, waterLevelThis - 1, 3);
					world.setBlockMetadataWithNotify(modX, modY, modZ, waterLevelThat + 1, 3);
				}
			}
		}
	}
	
}
