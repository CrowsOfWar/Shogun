package crowsofwar.shogun.client.gui;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.client.config.GuiButtonExt;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import crowsofwar.shogun.Shogun;
import crowsofwar.shogun.common.conversations.ShogunConversation;
import crowsofwar.shogun.common.conversations.ShogunConversationChangeReciever;
import crowsofwar.shogun.common.conversations.ShogunPrompt;
import crowsofwar.shogun.common.conversations.ShogunResponse;
import crowsofwar.shogun.common.entity.ShogunNPC;
import crowsofwar.shogun.common.packet.ShogunPacketC2SConversationRespond;

@SideOnly(Side.CLIENT)
public class ShogunGUIConversation extends GuiContainer implements ShogunConversationChangeReciever {
	
	private boolean needToAddButtons;
	
	public ShogunGUIConversation(Container container) {
		super(container);
		getConversation().watchChangeReciever(this);
		needToAddButtons = true;
	}
	
	public ShogunConversation getConversation() {
		return Shogun.proxy.getCurrentConversation();
	}
	
	public ShogunNPC getNPC() {
		return getConversation().getNPC();
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		String key;
		
		ShogunConversation conversation = getConversation();
		if (conversation == null) {
			key = "shogun.prompt.bye";
		} else {
			ShogunPrompt prompt = conversation.getCurrentPrompt();
			key = "shogun.prompt." + (prompt == null ? "thinking" : prompt.getLanguageKey());
		}
		drawCenteredString(fontRendererObj, StatCollector.translateToLocalFormatted(key), width / 2, 10, 0xffffff);
	}//GuiCrafting

	@Override
	public void onChanged() {
		needToAddButtons = true;
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		if (needToAddButtons && getConversation().getCurrentResponses() != null) {
			addButtons();
			needToAddButtons = false;
		}
	}
	
	private void addButtons() {
		buttonList.clear();
		List<ShogunResponse> responses = getConversation().getCurrentResponses();
		for (int i = 0; i < responses.size(); i++) {
			buttonList.add(new GuiButtonExt(i, width - 150 - 10, (i + 1) * 20, 150, 15,
					StatCollector.translateToLocalFormatted("shogun.response." + responses.get(i).getLanguageKey())));
		}
	}
	
	@Override
	public void actionPerformed(GuiButton button) {
		if (getConversation().conversationEnded()) {
			Minecraft.getMinecraft().thePlayer.closeScreen();
		} else {
			getConversation().addToHistory(getConversation().getCurrentResponses().get(button.id));
			Shogun.network.sendToServer(new ShogunPacketC2SConversationRespond(button.id));
			if (getConversation().conversationEnded())
				Minecraft.getMinecraft().thePlayer.closeScreenNoPacket(); // packet is sent by server
		}
	}
	
	@Override
	public void drawWorldBackground(int dirtScreenScroll) {
		if (this.mc.theWorld == null) {
			this.drawBackground(dirtScreenScroll);
		}
	}
	
}
