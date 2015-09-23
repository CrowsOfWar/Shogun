package crowsofwar.shogun.client.gui;

import java.util.List;

import cpw.mods.fml.client.config.GuiButtonExt;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import crowsofwar.shogun.Shogun;
import crowsofwar.shogun.common.conversations.ShogunConversation;
import crowsofwar.shogun.common.conversations.ShogunConversationChangeReciever;
import crowsofwar.shogun.common.conversations.ShogunResponse;
import crowsofwar.shogun.common.entity.ShogunNPC;
import crowsofwar.shogun.common.gui.ShogunContainerConversation;
import crowsofwar.shogun.common.packet.ShogunPacketC2SConversationRespond;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumChatFormatting;

@SideOnly(Side.CLIENT)
public class ShogunGUIConversation extends GuiContainer implements ShogunConversationChangeReciever {
	
	private ShogunContainerConversation container;
	
	public ShogunGUIConversation(Container container) {
		super(container);
		this.container = (ShogunContainerConversation) container;
		getConversation().watchChangeReciever(this);
		addButtons();
	}
	
	public ShogunConversation getConversation() {
		return container.getConversation();
	}
	
	public ShogunNPC getNPC() {
		return getConversation().getNPC();
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		fontRendererObj.drawString("Prompt: " + getConversation().getCurrentPrompt(), 10, 10, 0x000000, false);
		
		fontRendererObj.drawString(EnumChatFormatting.UNDERLINE + "Responses", 10, 30, 0x000000, false);
		List<ShogunResponse> responses = getConversation().getCurrentResponses();
		for (ShogunResponse resp : responses) {
			fontRendererObj.drawString(" - " + getConversation().getCurrentPrompt(), 10, 10, 0x000000, false);
		}
	}

	@Override
	public void onChanged() {
		addButtons();
	}
	
	private void addButtons() {
		List<ShogunResponse> responses = getConversation().getCurrentResponses();
		for (int i = 0; i < responses.size(); i++) {
			buttonList.add(new GuiButtonExt(i, 10, i * 10 + 10, 400, 10, "Respond: " + responses.get(i)));
		}
	}
	
	@Override
	public void actionPerformed(GuiButton button) {
		Shogun.network.sendToServer(new ShogunPacketC2SConversationRespond(button.id));
	}
	
}
