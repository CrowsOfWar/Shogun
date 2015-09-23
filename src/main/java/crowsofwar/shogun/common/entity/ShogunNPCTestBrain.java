package crowsofwar.shogun.common.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.world.World;
import crowsofwar.shogun.common.conversations.ShogunConversation;
import crowsofwar.shogun.common.conversations.ShogunConversationStage;
import crowsofwar.shogun.common.conversations.ShogunPrompt;
import crowsofwar.shogun.common.conversations.ShogunResponse;

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
	
	@Override
	public ShogunPrompt getNextConversationPrompt(ShogunConversation conversation) {
		
		if (conversation.justStarted()) {
			return ShogunPrompt.ASK_BUY_GOODS;
		} else {
			ShogunConversationStage[] pr = conversation.getPromptResponse();
			ShogunPrompt prompt = (ShogunPrompt) pr[0];
			ShogunResponse response = (ShogunResponse) pr[1];
			
			if (prompt == ShogunPrompt.ASK_BUY_GOODS) {
				if (response == ShogunResponse.OK) return ShogunPrompt.HERE_ARE_MY_GOODS;
				if (response == ShogunResponse.NO) return ShogunPrompt.OKAY;
				if (response == ShogunResponse.MAYBE_LATER) return ShogunPrompt.OKAY;
				if (response == ShogunResponse.NEVER) return ShogunPrompt.GO_AWAY_RUDE;
			}
			
			return null;
		}
	}
	
}
