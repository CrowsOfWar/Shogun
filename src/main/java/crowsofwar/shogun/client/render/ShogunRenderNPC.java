package crowsofwar.shogun.client.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ShogunRenderNPC extends RenderBiped
{
	private final ResourceLocation texture;

	public ShogunRenderNPC(String textureFile) {
		super(new ShogunModelBiped(), 0.5F);
		this.texture = new ResourceLocation("shogun:textures/entity/" + textureFile + ".png");
	}
	
	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(EntityLiving entity) {
		return texture;
	}
	
}