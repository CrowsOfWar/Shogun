package crowsofwar.shogun.common.conversations;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import crowsofwar.shogun.Shogun;
import crowsofwar.shogun.common.entity.ShogunNPC;
import crowsofwar.shogun.common.packet.ShogunPacketS2CConversationUpdate;

/**
 * <p>By definition, a conversation is an exchange of questions
 * and responses between a NPC and a player. Therefore, this
 * class contains a log of the questions and responses from
 * the NPC and the player.</p>
 * 
 * <p>The conversation object also manages the current prompt
 * and the responses. The possible responses are calculated by
 * first taking into account the prompt's type, e.g. "yes
 * no question". The prompt type has a number of responses which
 * are added to the list. Then, the prompt can have other responses
 * which are added to the list too. Finally, other factors are
 * taken into account, mainly the player's recent actions.</p>
 * 
 * @author CrowsOfWar
 */
public class ShogunConversation {
	
	private final List<ShogunConversationStage> stages;
	
	/**
	 * The responses currently available. This is null when the current stage is a
	 * response.
	 */
	private List<ShogunResponse> currentResponses;
	
	private final EntityPlayer player;
	private final ShogunNPC npc;
	
	private final List<ShogunConversationChangeReciever> changeRecievers;
	private boolean ended;
	
	public ShogunConversation(EntityPlayer player, ShogunNPC npc) {
		this.player = player;
		this.npc = npc;
		stages = new ArrayList<ShogunConversationStage>();
		currentResponses = null;
		changeRecievers = new ArrayList<ShogunConversationChangeReciever>();
		ended = false;
		ShogunPrompt next = npc.getNextConversationPrompt(this);
		if (next == null) {
			ended = true;
		} else {
			if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) addToHistory(next);
		}
	}
	
	public void watchChangeReciever(ShogunConversationChangeReciever reciever) {
		changeRecievers.add(reciever);
	}
	
	public void addToHistory(ShogunConversationStage stage) {
		System.out.println("Adding stage to history: " + stage);
		if (getCurrentStage() != null && getCurrentStage().getClass() == stage.getClass()) {
			FMLLog.bigWarning("Shogun> Someone is messing around with conversations and is trying to add a conversation stage " +
					"out of order. It's supposed to be prompt -> response, prompt -> response, but the order has been broken. " +
					"The stages: %1$s", stages.toString());
		}
		stages.add(stage);
		if (stage instanceof ShogunPrompt) {
			
			// Only actually add responses if it's the server-side, client just has to wait for the server to send responses
			if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
				currentResponses = getResponsesForCurrentPrompt();
				Shogun.network.sendTo(new ShogunPacketS2CConversationUpdate((ShogunPrompt) stage, currentResponses),
						(EntityPlayerMP) player);
			}
			
		} else {
			System.out.println("Recieved respose; setting current responses to null");
			currentResponses = null;
			ShogunPrompt next = npc.getNextConversationPrompt(this);
			if (next == null) {
				ended = true;
				return;
			} else {
				if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) addToHistory(next);
			}
		}
		
		for (ShogunConversationChangeReciever rec : changeRecievers) rec.onChanged();
	}
	
	/**
	 * Get the latest stage, null if there are no stages
	 * @return
	 */
	public ShogunConversationStage getCurrentStage() {
		return stages.size() == 0 ? null : stages.get(stages.size() - 1);
	}
	
	/**
	 * Get the current prompt, null if the current stage is not a prompt or if there are no stages
	 * @return
	 */
	public ShogunPrompt getCurrentPrompt() {
		ShogunConversationStage stage = getCurrentStage();
		return stage == null ? null : (stage instanceof ShogunPrompt ? (ShogunPrompt) stage : null);
	}
	
	/**
	 * Get the current response, null if the current stage is not a response or if there are no stages
	 * @return
	 */
	public ShogunResponse getCurrentResponse() {
		ShogunConversationStage stage = getCurrentStage();
		return stage == null ? null : (stage instanceof ShogunResponse ? (ShogunResponse) stage : null);
	}
	
	/**
	 * Get an array containing the prompt, then the response. This only works if the latest stage was a
	 * response.
	 * @return
	 */
	public ShogunConversationStage[] getPromptResponse() {
		if (getCurrentResponse() != null) {
			return new ShogunConversationStage[] { stages.get(stages.size() - 2), stages.get(stages.size() - 1) };
		}
		return null;
	}
	
	/**
	 * Get the responses for this current prompt. Null if there is no
	 * open prompt or if nothing happened in the conversation.
	 * @return
	 */
	private List<ShogunResponse> getResponsesForCurrentPrompt() {
		ShogunPrompt prompt = getCurrentPrompt();
		if (prompt != null) {
			List<ShogunResponse> res = prompt.getAllResponses();
			// Now: take into account the player's recent actions...
			// TODO Take into account the recent actions
			// (not done because there are virtually no actions currently)
			return res;
		}
		
		return null;
	}
	
	/**
	 * Get a list of responses for the current prompt. This is null if there is not a prompt
	 * right now.
	 * @return
	 */
	public List<ShogunResponse> getCurrentResponses() {
		return currentResponses;
	}
	
	/**
	 * Override the list of current responses to that list. The list of current responses is
	 * automatically calculated, so only do this if you know what you're doing.
	 * @param currentResponses
	 */
	public void overrideCurrentResponses(List<ShogunResponse> currentResponses) {
		this.currentResponses = currentResponses;
		System.out.println("OVerride current responses to  "+ this.currentResponses);
	}
	
	public boolean justStarted() {
		return stages.size() == 0;
	}
	
	public EntityPlayer getPlayer() {
		return player;
	}
	
	public ShogunNPC getNPC() {
		return npc;
	}
	
	public boolean conversationEnded() {
		return ended;
	}
	
}
