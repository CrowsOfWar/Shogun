package crowsofwar.shogun.client.render;

import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import crowsofwar.shogun.common.entity.ShogunNPC;

@SideOnly(Side.CLIENT)
public class ShogunRenderNPC extends RenderBiped
{
	private final ResourceLocation[] textures;

	public ShogunRenderNPC(String textureFile, int textures) {
		super(new ShogunModelBiped(), 0.5F);
		this.textures = new ResourceLocation[textures];
		for (int i = 1; i <= textures; i++) {
			this.textures[i] = new ResourceLocation("shogun:textures/entity/" + textureFile + i + ".png");
		}
	}
	
	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(EntityLiving entity) {
		return textures[((ShogunNPC) entity).getTexture() - 1];
	}
	
}