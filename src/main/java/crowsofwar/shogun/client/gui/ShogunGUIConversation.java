package crowsofwar.shogun.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import crowsofwar.shogun.common.gui.ShogunContainerConversation;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

@SideOnly(Side.CLIENT)
public class ShogunGUIConversation extends GuiContainer {
	
	private ShogunContainerConversation container;
	
	public ShogunGUIConversation(Container container) {
		super(container);
		this.container = (ShogunContainerConversation) container;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		
	}
	
}
