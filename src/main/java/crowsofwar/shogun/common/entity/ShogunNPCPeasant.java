package crowsofwar.shogun.common.entity;

import crowsofwar.gorecore.GoreCore;
import crowsofwar.gorecore.util.GoreCoreMathHelper;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.world.World;

public class ShogunNPCPeasant extends ShogunNPC {
	
	public ShogunNPCPeasant(World world) {
		super(world);
	}
	
	@Override
	public EntityAgeable createChild(EntityAgeable otherPeasant) {
		return new ShogunNPCPeasant(worldObj);
	}

	@Override
	protected void assignTexture() {
		setTexture(GoreCoreMathHelper.randomInt(1, 2));
	}
	
}
