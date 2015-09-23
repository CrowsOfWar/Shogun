package crowsofwar.shogun.common.packet;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import crowsofwar.shogun.common.conversations.ShogunConversation;
import crowsofwar.shogun.common.conversations.ShogunResponse;
import crowsofwar.shogun.common.gui.ShogunContainerConversation;

public class ShogunPacketC2SConversationRespond implements IMessage {
	
	/**
	 * The index of the response in the list of responses to respond to
	 */
	private int response;
	
	public ShogunPacketC2SConversationRespond() {}
	
	public ShogunPacketC2SConversationRespond(int response) {
		this.response = response;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		response = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(response);
	}
	
	public static class Handler implements IMessageHandler<ShogunPacketC2SConversationRespond, IMessage> {

		@Override
		public IMessage onMessage(ShogunPacketC2SConversationRespond message, MessageContext ctx) {
			if (ctx.side == Side.SERVER) {
				EntityPlayer player = ctx.getServerHandler().playerEntity;
				if (player.openContainer instanceof ShogunContainerConversation) {
					ShogunConversation currentConversation = ((ShogunContainerConversation) player.openContainer).getConversation();
					if (!currentConversation.conversationEnded()) {
						List<ShogunResponse> currentResponseList = currentConversation.getCurrentResponses();
						if (message.response >= 0 && message.response < currentResponseList.size()) {
							currentConversation.addToHistory(ShogunResponse.getByID(message.response));
							if (currentConversation.conversationEnded()) player.closeScreen();
						}
					}
				}
			}
			
			return null;
		}
		
	}

}
