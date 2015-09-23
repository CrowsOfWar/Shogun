package crowsofwar.shogun.common.packet;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import crowsofwar.gorecore.GoreCore;
import crowsofwar.shogun.common.conversations.ShogunConversation;
import crowsofwar.shogun.common.conversations.ShogunPrompt;
import crowsofwar.shogun.common.conversations.ShogunResponse;
import crowsofwar.shogun.common.gui.ShogunContainerConversation;

/**
 * Sent to update the client of a new prompt in the conversation.
 * It also sends the responses to pick from.
 * 
 * @author CrowsOfWar
 */
public class ShogunPacketS2CConversationUpdate implements IMessage {
	
	private ShogunPrompt prompt;
	private List<ShogunResponse> responses;
	
	public ShogunPacketS2CConversationUpdate() {}
	
	public ShogunPacketS2CConversationUpdate(ShogunPrompt prompt, List<ShogunResponse> responses) {
		this.prompt = prompt;
		this.responses = responses;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		prompt = ShogunPrompt.getByID(buf.readInt());
		responses = new ArrayList<ShogunResponse>(buf.readInt());
		for (int i = 0; i < responses.size(); i++) {
			responses.set(i, ShogunResponse.getByID(buf.readInt()));
		}
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(prompt.id());
		buf.writeInt(responses.size());
		for (ShogunResponse resp : responses) buf.writeInt(resp.id());
	}
	
	public static class Handler implements IMessageHandler<ShogunPacketS2CConversationUpdate, IMessage> {

		@Override
		public IMessage onMessage(ShogunPacketS2CConversationUpdate message, MessageContext ctx) {
			if (ctx.side == Side.CLIENT) {
				EntityPlayer player = GoreCore.proxy.getClientSidePlayer();
				if (player.openContainer instanceof ShogunContainerConversation) {
					ShogunConversation conversation = ((ShogunContainerConversation) player.openContainer).getConversation();
					conversation.addToHistory(message.prompt);
					conversation.overrideCurrentResponses(message.responses);
				}
			}
			
			return null;
		}
		
	}
	
}
