package crowsofwar.shogun.common.entity;

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
	
}
