package crowsofwar.shogun.common.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ShogunNPCTestBrain extends ShogunNPCIntelligent {
	
	public ShogunNPCTestBrain(World world) {
		super(world);
	}
	
	@Override
	public EntityAgeable createChild(EntityAgeable p_90011_1_) {
		return null;
	}

	@Override
	protected void assignTexture() {
		setTexture(1);
	}
	
}
