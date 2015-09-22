package crowsofwar.shogun.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ShogunCommonProxy {
	
	public void sideSpecifics() {}
	
	/**
	 * Make a client-side GUI element
	 */
	public Object createClientGUI(int id, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
}
