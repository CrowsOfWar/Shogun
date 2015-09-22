package crowsofwar.shogun.common.entity;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import crowsofwar.shogun.common.data.ShogunWorldData;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * The base class for all NPCs.
 * 
 * @author CrowsOfWar
 */
public abstract class ShogunNPC extends EntityAgeable implements IEntityAdditionalSpawnData {
	
	private long shogunID;
	private int texture;
	
	public ShogunNPC(World world) {
		super(world);
		addRegularTasks();
		setSize(0.6f, 1.8f);
		shogunID = ShogunWorldData.getWorldData(world).nextEntityID();
		texture = 1;
		assignTexture();
	}
	
	public long getShogunID() {
		return shogunID;
	}
	
	public int getTexture() {
		return texture;
	}
	
	public void setTexture(int texture) {
		this.texture = texture;
	}
	
	protected abstract void assignTexture();
	
	// READ/WRITE
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		shogunID = nbt.getLong("ShogunID");
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setLong("ShogunID", shogunID);
	}
	
	// SYNC PACKET
	
	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeInt(getTexture());
	}
	
	@Override
	public void readSpawnData(ByteBuf additionalData) {
		setTexture(additionalData.readInt());
	}
	
	// HOOKS
	
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
