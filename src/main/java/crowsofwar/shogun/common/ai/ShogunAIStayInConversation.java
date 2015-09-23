package crowsofwar.shogun.common.ai;

import crowsofwar.shogun.common.entity.ShogunNPC;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

/**
 * AI to walk towards the player that the NPC is having the conversation
 * with
 * 
 * @author CrowsOfWar
 */
public class ShogunAIStayInConversation extends EntityAIBase {
	
	private final ShogunNPC npc;
	
	public ShogunAIStayInConversation(ShogunNPC npc) {
		this.npc = npc;
	}
	
	@Override
	public boolean continueExecuting() {
		if (shouldExecute()) {
			npc.getNavigator().tryMoveToEntityLiving(npc.getPlayerTalkingTo(), 1);
			npc.getLookHelper().setLookPositionWithEntity(npc.getPlayerTalkingTo(), 20, 20);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean shouldExecute() {
		return npc.getPlayerTalkingTo() != null;
	}
	
}
