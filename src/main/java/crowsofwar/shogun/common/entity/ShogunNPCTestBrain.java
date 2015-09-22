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
	public boolean interact(EntityPlayer player) {
		player.addChatMessage(new ChatComponentText("Hmm... i think your honor is " + getHonorLevel(player)));
		return true;
	}

	@Override
	protected void assignTexture() {
		setTexture(1);
	}
	
}
