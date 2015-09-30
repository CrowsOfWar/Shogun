package crowsofwar.shogun.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * The model for O-yori armor (created using Tabula v4.1.1, if that
 * matters. I think iChun just wants to write his name everywhere.)
 * 
 * @author CrowsOfWar
 */
public class ShogunModelOyoriArmor extends ModelBase {
    public ModelRenderer bipedHeadwear;
    public ModelRenderer bipedLeftLeg;
    public ModelRenderer bipedEars;
    public ModelRenderer bipedRightLeg;
    public ModelRenderer bipedLeftArm;
    public ModelRenderer bipedRightArm;
    public ModelRenderer bipedBody;
    public ModelRenderer bipedHead;
    public ModelRenderer KusazuriFront;
    public ModelRenderer KusazuriBack;
    public ModelRenderer KusazuriLeft;
    public ModelRenderer KusazuriRight;
    public ModelRenderer LeftHeadThingy;
    public ModelRenderer RightHeadThingy;
    public ModelRenderer Top;

    public ShogunModelOyoriArmor() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.bipedEars = new ModelRenderer(this, 24, 0);
        this.bipedEars.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedEars.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, 0.0F);
        this.RightHeadThingy = new ModelRenderer(this, 0, 32);
        this.RightHeadThingy.mirror = true;
        this.RightHeadThingy.setRotationPoint(-5.0F, -8.0F, -3.5F);
        this.RightHeadThingy.addBox(0.0F, 0.0F, 0.0F, 2, 5, 7, 0.0F);
        this.setRotateAngle(RightHeadThingy, 0.0F, 0.0F, 0.4363323129985824F);
        this.LeftHeadThingy = new ModelRenderer(this, 0, 32);
        this.LeftHeadThingy.setRotationPoint(3.0F, -7.0F, -3.5F);
        this.LeftHeadThingy.addBox(0.0F, 0.0F, 0.0F, 2, 5, 7, 0.0F);
        this.setRotateAngle(LeftHeadThingy, 0.0F, 0.0F, -0.4363323129985824F);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.bipedHeadwear = new ModelRenderer(this, 32, 0);
        this.bipedHeadwear.setRotationPoint(0.0F, -100.0F, 0.0F);
        this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);
        this.setRotateAngle(bipedHeadwear, 0.0F, -1.5481070465189704F, 0.0F);
        this.KusazuriBack = new ModelRenderer(this, 32, 37);
        this.KusazuriBack.setRotationPoint(-4.0F, 8.35F, 1.0F);
        this.KusazuriBack.addBox(0.0F, 0.0F, 0.0F, 8, 6, 1, 0.0F);
        this.setRotateAngle(KusazuriBack, 0.4363323129985824F, 0.0F, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(bipedLeftArm, -1.5481070465189704F, 0.0F, 0.0F);
        this.KusazuriFront = new ModelRenderer(this, 32, 37);
        this.KusazuriFront.setRotationPoint(-4.0F, 8.0F, -2.0F);
        this.KusazuriFront.addBox(0.0F, 0.0F, 0.0F, 8, 6, 1, 0.0F);
        this.setRotateAngle(KusazuriFront, -0.4363323129985824F, 0.0F, 0.0F);
        this.KusazuriLeft = new ModelRenderer(this, 52, 34);
        this.KusazuriLeft.setRotationPoint(3.1F, 8.4F, -2.0F);
        this.KusazuriLeft.addBox(0.0F, 0.0F, 0.0F, 1, 6, 4, 0.0F);
        this.setRotateAngle(KusazuriLeft, 0.0F, 0.0F, -0.4363323129985824F);
        this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.setRotationPoint(1.899999976158142F, 12.0F, 0.0F);
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.Top = new ModelRenderer(this, 18, 37);
        this.Top.setRotationPoint(-2.75F, -13.5F, -5.5F);
        this.Top.addBox(0.0F, 0.0F, 0.0F, 6, 6, 1, 0.0F);
        this.setRotateAngle(Top, 0.2617993877991494F, 0.0F, 0.0F);
        this.bipedRightLeg = new ModelRenderer(this, 0, 16);
        this.bipedRightLeg.setRotationPoint(-1.899999976158142F, 12.0F, 0.0F);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.bipedRightArm = new ModelRenderer(this, 40, 16);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(bipedRightArm, 0.6373942428283291F, 0.0F, 0.0F);
        this.bipedBody = new ModelRenderer(this, 16, 16);
        this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
        this.KusazuriRight = new ModelRenderer(this, 52, 34);
        this.KusazuriRight.setRotationPoint(-4.0F, 8.0F, -2.0F);
        this.KusazuriRight.addBox(0.0F, 0.0F, 0.0F, 1, 6, 4, 0.0F);
        this.setRotateAngle(KusazuriRight, 0.0F, 0.0F, 0.4363323129985824F);
        this.bipedHead.addChild(this.RightHeadThingy);
        this.bipedHead.addChild(this.LeftHeadThingy);
        this.bipedBody.addChild(this.KusazuriBack);
        this.bipedBody.addChild(this.KusazuriFront);
        this.bipedBody.addChild(this.KusazuriLeft);
        this.bipedHead.addChild(this.Top);
        this.bipedBody.addChild(this.KusazuriRight);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.bipedEars.render(f5);
        this.bipedHead.render(f5);
        this.bipedHeadwear.render(f5);
        this.bipedLeftArm.render(f5);
        this.bipedLeftLeg.render(f5);
        this.bipedRightLeg.render(f5);
        this.bipedRightArm.render(f5);
        this.bipedBody.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
