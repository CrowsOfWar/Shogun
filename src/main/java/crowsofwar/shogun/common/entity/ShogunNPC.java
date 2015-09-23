package crowsofwar.shogun.common.entity;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import crowsofwar.shogun.Shogun;
import crowsofwar.shogun.common.ai.ShogunAIStayInConversation;
import crowsofwar.shogun.common.conversations.ShogunConversation;
import crowsofwar.shogun.common.conversations.ShogunPrompt;
import crowsofwar.shogun.common.data.ShogunPlayerDataFetcher;
import crowsofwar.shogun.common.data.ShogunWorldData;
import crowsofwar.shogun.common.management.ShogunGuiIDs;
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
	private EntityPlayer talkingTo;
	
	public ShogunNPC(World world) {
		super(world);
		addRegularTasks();
		setSize(0.6f, 1.8f);
		shogunID = ShogunWorldData.getWorldData(world).nextEntityID();
		texture = 1;
		assignTexture();
		talkingTo = null;
	}
	
	// VARIABLES
	
	public long getShogunID() {
		return shogunID;
	}
	
	public int getTexture() {
		return texture;
	}
	
	public void setTexture(int texture) {
		this.texture = texture;
	}
	
	public EntityPlayer getPlayerTalkingTo() {
		return talkingTo;
	}
	
	public void talkTo(EntityPlayer player) {
		talkingTo = player;
	}
	
	// READ/WRITE
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		shogunID = nbt.getLong("ShogunID");
		setTexture(nbt.getInteger("Texture"));
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setLong("ShogunID", shogunID);
		nbt.setInteger("Texture", getTexture());
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
		tasks.addTask(0, new ShogunAIStayInConversation(this));
		tasks.addTask(4, new EntityAIWatchClosest(this, getClass(), 5));
		tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 5));
		tasks.addTask(5, new EntityAILookIdle(this));
		tasks.addTask(6, new EntityAIWander(this, 0.75));
	}
	
	protected abstract void assignTexture();
	
	/**
	 * Returns whether to start a conversation with that player
	 */
	protected boolean engageInConversation(EntityPlayer player) {
		return false;
	}
	
	/**
	 * Get the next prompt in the conversation. Return null to end the conversation.
	 */
	public ShogunPrompt getNextConversationPrompt(ShogunConversation conversation) {
		return null;
	}
	
	// IMPLEMENTATION OF VANILLA METHODS
	
	@Override
	public boolean interact(EntityPlayer player) {
		if (engageInConversation(player)) {
			talkTo(player);
			ShogunPlayerDataFetcher.FETCHER.getDataPerformance(player).setTalkingToNPC(this);
			if (!worldObj.isRemote)
				player.openGui(Shogun.instance, ShogunGuiIDs.ID_CONVERSATION, worldObj, (int) posX, (int) posY, (int) posZ);
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean isAIEnabled() {
		return true; // I really don't get why this isn't true by default...
	}
	
}
