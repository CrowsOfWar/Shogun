package crowsofwar.shogun.common.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * The base class for all NPCs.
 * 
 * @author CrowsOfWar
 */
public abstract class ShogunNPC extends EntityAgeable {
	
	public ShogunNPC(World world) {
		super(world);
		addRegularTasks();
		setSize(0.6f, 1.8f);
	}
	
	/**
	 * Add the "regular" AI modules - wander, look, etc.
	 */
	protected void addRegularTasks() {
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(4, new EntityAIWatchClosest(this, getClass(), 5));
		tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 5));
		tasks.addTask(5, new EntityAILookIdle(this));
		tasks.addTask(6, new EntityAIWander(this, 0.75));
	}
	
}
