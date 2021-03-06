package crowsofwar.shogun.common.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import crowsofwar.gorecore.util.GoreCoreMathHelper;
import crowsofwar.shogun.Shogun;
import crowsofwar.shogun.common.management.ShogunCreativeTabs;
import crowsofwar.shogun.common.management.ShogunRenderIDs;

public class ShogunBlockFarmland extends Block {
	
	private static final ForgeDirection[] ADJACENT_DIRECTIONS = new ForgeDirection[] {
		ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.NORTH, ForgeDirection.EAST
	};
	private static final int TICK_RATE_SPREAD = 2;
	private static final int TICK_RATE_STATIC = 4;
	
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
		updateTick(world, x, y, z, null);
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random randomizer) {
		int waterLevelThis = world.getBlockMetadata(x, y, z);
		for (int i = 0; i < ADJACENT_DIRECTIONS.length; i++) {
			ForgeDirection dir = ADJACENT_DIRECTIONS[i];
			int modX = x + dir.offsetX;
			int modY = y + dir.offsetY; // technically not necessary
			int modZ = z + dir.offsetZ;
			if (world.getBlock(modX, modY, modZ) == this) {
				// Check water level
				int waterLevelThat = world.getBlockMetadata(modX, modY, modZ);
				// Transfer some? IF this is <1 higher than that
				if (waterLevelThis - 1 > waterLevelThat) {
					world.setBlockMetadataWithNotify(x, y, z, waterLevelThis - 1, 2);
					world.setBlockMetadataWithNotify(modX, modY, modZ, waterLevelThat + 1, 2);
					world.scheduleBlockUpdate(modX, modY, modZ, this, TICK_RATE_SPREAD);
				}
			}
		}
		world.scheduleBlockUpdate(x, y, z, this, TICK_RATE_STATIC);
		
		// Rain add/dry drain
		if (GoreCoreMathHelper.randomInt(0, 100) == 1) { // don't use randomizer because onNeighborBlockChange uses randomizer as null
			if (world.isRaining()) world.setBlockMetadataWithNotify(x, y, z, waterLevelThis + 1, 2);
			if (waterLevelThis > 0 && !world.isRaining() && GoreCoreMathHelper.randomInt(0, 10) == 0)
				world.setBlockMetadataWithNotify(x, y, z, waterLevelThis - 1, 2);
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
			int something1, float something2, float something3, float something4) {
		
		if (player.getHeldItem() != null && player.getHeldItem().getItem() == Items.water_bucket) {
			world.setBlockMetadataWithNotify(x, y, z, 15, 3);
			return true;
		}
		if (player.getHeldItem() != null && player.getHeldItem().getItem() == Items.bucket) {
			world.setBlockMetadataWithNotify(x, y, z, 0, 3);
			for (int i = 0; i < ADJACENT_DIRECTIONS.length; i++) {
				int modX = x + ADJACENT_DIRECTIONS[i].offsetX;
				int modY = y + ADJACENT_DIRECTIONS[i].offsetY;
				int modZ = z + ADJACENT_DIRECTIONS[i].offsetZ;
				if (world.getBlock(modX, modY, modZ) == this) world.setBlockMetadataWithNotify(modX, modY, modZ, 0, 3);
			}
			return true;
		}
		
		return false;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		int metadata = world.getBlockMetadata(x, y, z);
		boolean moving = Math.abs(entity.motionX) > 0.01 || Math.abs(entity.motionY) > 0.01 || Math.abs(entity.motionZ) > 0.01;
		if (moving && metadata > 2 && entity.ticksExisted % 15 == 0 && GoreCoreMathHelper.randomInt(0, 10) == 0) {
			world.playSoundAtEntity(entity, "game.neutral.swim.splash", metadata / 120f + GoreCoreMathHelper.randomFloat(0, 0.1f),
					GoreCoreMathHelper.randomFloat(0f, 0.15f));
		}
	}
	
}
