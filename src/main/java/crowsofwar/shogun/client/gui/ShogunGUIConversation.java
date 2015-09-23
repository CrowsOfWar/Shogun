package crowsofwar.shogun.client.gui;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import crowsofwar.shogun.Shogun;
import crowsofwar.shogun.common.conversations.ShogunConversation;
import crowsofwar.shogun.common.conversations.ShogunConversationChangeReciever;
import crowsofwar.shogun.common.conversations.ShogunResponse;
import crowsofwar.shogun.common.entity.ShogunNPC;
import crowsofwar.shogun.common.gui.ShogunContainerConversation;
import crowsofwar.shogun.common.packet.ShogunPacketC2SConversationRespond;

@SideOnly(Side.CLIENT)
public class ShogunGUIConversation extends GuiContainer implements ShogunConversationChangeReciever {
	
	private boolean needToAddButtons;
	
	public ShogunGUIConversation(Container container) {
		super(container);
		System.out.println(getConversation());
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
		fontRendererObj.drawString("Prompt: " + getConversation().getCurrentPrompt(), 10, 10, 0x000000, false);
//		System.out.println(System.identityHashCode(buttonList));
//		System.out.println(needToAddButtons + ", " + buttonList); // WHY THE HECK IS BUTTON LIST CLEARING!?!
		// It's not changing the actual instance... identity hashcode is the same
//		fontRendererObj.drawString(EnumChatFormatting.UNDERLINE + "Responses", 10, 30, 0x000000, false);
//		List<ShogunResponse> responses = getConversation().getCurrentResponses();
//		for (ShogunResponse resp : responses) {
//			fontRendererObj.drawString(" - " + getConversation().getCurrentPrompt(), 10, 10, 0x000000, false);
//		}
	}

	@Override
	public void onChanged() {
		System.out.println("onChanged()");
		needToAddButtons = true;
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
//		System.out.println("Converation " + getConversation());
//		System.out.println("need to add buttons? " + needToAddButtons);
//		System.out.println("Responses: " + getConversation().getCurrentResponses());
		if (needToAddButtons && getConversation().getCurrentResponses() != null) {
			addButtons();
			needToAddButtons = false;
		}
	}
	
	private void addButtons() {
		System.out.println("BUTTONS! for prompt " + getConversation().getCurrentPrompt());
		buttonList.clear();
		List<ShogunResponse> responses = getConversation().getCurrentResponses();
		for (int i = 0; i < responses.size(); i++) {
			System.out.println("Respond " + i + ": " + responses.get(i));
			buttonList.add(new GuiButton(i, 10, (i + 1) * 30, 400, 20, "Respond: " + responses.get(i)));
		}
		System.out.println("button list is now:" + buttonList);
//		System.out.println(System.identityHashCode(buttonList));
	}
	
	@Override
	public void actionPerformed(GuiButton button) {
		getConversation().addToHistory(getConversation().getCurrentResponses().get(button.id));
		Shogun.network.sendToServer(new ShogunPacketC2SConversationRespond(button.id));
	}
	
}
