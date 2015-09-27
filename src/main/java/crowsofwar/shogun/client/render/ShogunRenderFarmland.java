package crowsofwar.shogun.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import crowsofwar.shogun.Shogun;
import crowsofwar.shogun.common.management.ShogunBlocks;
import crowsofwar.shogun.common.management.ShogunRenderIDs;

public class ShogunRenderFarmland implements ISimpleBlockRenderingHandler {
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		if (Shogun.proxy.getCurrentRenderPass() == 0) {
			Block textureBlock = block;
			
			tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
			renderer.setRenderBounds(0, 0, 0, 1, 0.5, 1);
			
			tessellator.setColorOpaque_F(0.6f, 0.6f, 0.6f);
			renderer.renderFaceXNeg(textureBlock, x, y, z, textureBlock.getIcon(ForgeDirection.WEST.ordinal(), 0));
			renderer.renderFaceXPos(textureBlock, x, y, z, textureBlock.getIcon(ForgeDirection.EAST.ordinal(), 0));
			tessellator.setColorOpaque_F(0.5f, 0.5f, 0.5f);
			renderer.renderFaceYNeg(textureBlock, x, y, z, textureBlock.getIcon(ForgeDirection.DOWN.ordinal(), 0));
			tessellator.setColorOpaque_F(1, 1, 1);
			renderer.renderFaceYPos(textureBlock, x, y, z, textureBlock.getIcon(ForgeDirection.UP.ordinal(), 0));
			tessellator.setColorOpaque_F(0.8f, 0.8f, 0.8f);
			renderer.renderFaceZNeg(textureBlock, x, y, z, textureBlock.getIcon(ForgeDirection.NORTH.ordinal(), 0));
			renderer.renderFaceZPos(textureBlock, x, y, z, textureBlock.getIcon(ForgeDirection.SOUTH.ordinal(), 0));
		} else {
			Block textureBlock = Blocks.water;
			renderer.setRenderBounds(0, 0.5, 0, 1, calculateWaterHeight(world.getBlockMetadata(x, y, z)), 1);
			
			tessellator.setColorOpaque_F(0.6f, 0.6f, 0.6f);
			if (shouldSideBeRendered(renderer, world, x, y, z, ForgeDirection.WEST))
				renderer.renderFaceXNeg(textureBlock, x, y, z, textureBlock.getIcon(ForgeDirection.WEST.ordinal(), 0));
			
			if (shouldSideBeRendered(renderer, world, x, y, z, ForgeDirection.EAST))
				renderer.renderFaceXPos(textureBlock, x, y, z, textureBlock.getIcon(ForgeDirection.EAST.ordinal(), 0));
			tessellator.setColorOpaque_F(0.5f, 0.5f, 0.5f);
			
			if (shouldSideBeRendered(renderer, world, x, y, z, ForgeDirection.DOWN))
				renderer.renderFaceYNeg(textureBlock, x, y, z, textureBlock.getIcon(ForgeDirection.DOWN.ordinal(), 0));
			tessellator.setColorOpaque_F(1, 1, 1);
			
			if (shouldSideBeRendered(renderer, world, x, y, z, ForgeDirection.UP))
				renderer.renderFaceYPos(textureBlock, x, y, z, textureBlock.getIcon(ForgeDirection.UP.ordinal(), 0));
			tessellator.setColorOpaque_F(0.8f, 0.8f, 0.8f);
			
			if (shouldSideBeRendered(renderer, world, x, y, z, ForgeDirection.NORTH))
				renderer.renderFaceZNeg(textureBlock, x, y, z, textureBlock.getIcon(ForgeDirection.NORTH.ordinal(), 0));
			
			if (shouldSideBeRendered(renderer, world, x, y, z, ForgeDirection.SOUTH))
				renderer.renderFaceZPos(textureBlock, x, y, z, textureBlock.getIcon(ForgeDirection.SOUTH.ordinal(), 0));
			
		}
		
		return false;
	}
	
	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}
	
	@Override
	public int getRenderId() {
		return ShogunRenderIDs.ID_FARMLAND;
	}
	
	/**
	 * Returns whether that side should be rendered, if it should be, then
	 * it adjusts the RenderBlock's maxX,Y,Z to be correct
	 */
	private boolean shouldSideBeRendered(RenderBlocks renderer, IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
		int modX = x + dir.offsetX;
		int modY = y + dir.offsetY;
		int modZ = z + dir.offsetZ;
		
		boolean adjacentBlockIsFarmland = world.getBlock(modX, modY, modZ) == ShogunBlocks.blockFarmland;
		boolean adjacentBlockIsFarmlandSameWaterLevel = adjacentBlockIsFarmland
				&& world.getBlockMetadata(modX, modY, modZ) == world.getBlockMetadata(x, y, z);
		
		int side = dir.ordinal();
		if ((side == 0 && renderer.renderMinY > 0.0D ? true : (side == 1 && renderer.renderMaxY < 1.0D ? true :
			(side == 2 && renderer.renderMinZ > 0.0D ? true : (side == 3 && renderer.renderMaxZ < 1.0D ? true : 
			(side == 4 && renderer.renderMinX > 0.0D ? true : (side == 5 && renderer.renderMaxX < 1.0D ? true :
			!world.getBlock(x, y, z).isOpaqueCube()))))))
			&& !adjacentBlockIsFarmlandSameWaterLevel) {
			
			if (adjacentBlockIsFarmland) {
				double waterHeightThis = calculateWaterHeight(world.getBlockMetadata(x, y, z));
				double waterHeightAdjacent = calculateWaterHeight(world.getBlockMetadata(modX, modY, modZ));
				if (waterHeightThis < waterHeightAdjacent) {
					// This block is lower than the adjacent block, don't need to render any sides
					renderer.renderMinY = renderer.renderMaxY = 0.01;
				} else {
					// This block is higher than the adjacent block, render only the smidgen of the side
					renderer.renderMinY = waterHeightAdjacent;
					renderer.renderMaxY = waterHeightThis;
				}
			} else {
				renderer.renderMinY = 0.5;
				renderer.renderMaxY = calculateWaterHeight(world.getBlockMetadata(x, y, z));
			}
			
			return true;
			
		}
		
		return false;
	}
	
	private double calculateWaterHeight(int metadata) {
		return 0.49 + metadata / 30.0;
	}
	
}
