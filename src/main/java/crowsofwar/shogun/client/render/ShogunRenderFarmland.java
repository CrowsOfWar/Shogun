package crowsofwar.shogun.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import crowsofwar.gorecore.util.GoreCoreBlockSide;
import crowsofwar.shogun.common.management.ShogunRenderIDs;

public class ShogunRenderFarmland implements ISimpleBlockRenderingHandler {
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		
		Block textureBlock = block;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		renderer.setRenderBounds(0, 0, 0, 1, 0.5, 1);
		
		tessellator.setColorOpaque_F(0.6f, 0.6f, 0.6f);
		renderer.renderFaceXNeg(textureBlock, x, y, z, textureBlock.getIcon(GoreCoreBlockSide.WEST.side(), 0));
		renderer.renderFaceXPos(textureBlock, x, y, z, textureBlock.getIcon(GoreCoreBlockSide.EAST.side(), 0));
		tessellator.setColorOpaque_F(0.5f, 0.5f, 0.5f);
		renderer.renderFaceYNeg(textureBlock, x, y, z, textureBlock.getIcon(GoreCoreBlockSide.BOTTOM.side(), 0));
		tessellator.setColorOpaque_F(1, 1, 1);
		renderer.renderFaceYPos(textureBlock, x, y, z, textureBlock.getIcon(GoreCoreBlockSide.TOP.side(), 0));
		tessellator.setColorOpaque_F(0.8f, 0.8f, 0.8f);
		renderer.renderFaceZNeg(textureBlock, x, y, z, textureBlock.getIcon(GoreCoreBlockSide.NORTH.side(), 0));
		renderer.renderFaceZPos(textureBlock, x, y, z, textureBlock.getIcon(GoreCoreBlockSide.SOUTH.side(), 0));
		
		textureBlock = Blocks.water;
		renderer.setRenderBounds(0, 0.5, 0, 1, 1, 1);
		
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		tessellator.setColorOpaque_F(0.6f, 0.6f, 0.6f);
		renderer.renderFaceXNeg(textureBlock, x, y, z, textureBlock.getIcon(GoreCoreBlockSide.WEST.side(), 0));
		renderer.renderFaceXPos(textureBlock, x, y, z, textureBlock.getIcon(GoreCoreBlockSide.EAST.side(), 0));
		tessellator.setColorOpaque_F(0.5f, 0.5f, 0.5f);
		renderer.renderFaceYNeg(textureBlock, x, y, z, textureBlock.getIcon(GoreCoreBlockSide.BOTTOM.side(), 0));
		tessellator.setColorOpaque_F(1, 1, 1);
		renderer.renderFaceYPos(textureBlock, x, y, z, textureBlock.getIcon(GoreCoreBlockSide.TOP.side(), 0));
		tessellator.setColorOpaque_F(0.8f, 0.8f, 0.8f);
		renderer.renderFaceZNeg(textureBlock, x, y, z, textureBlock.getIcon(GoreCoreBlockSide.NORTH.side(), 0));
		renderer.renderFaceZPos(textureBlock, x, y, z, textureBlock.getIcon(GoreCoreBlockSide.SOUTH.side(), 0));
		GL11.glPopMatrix();
		
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
	
}
