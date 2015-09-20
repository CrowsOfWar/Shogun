package crowsofwar.shogun.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import crowsofwar.shogun.client.render.ShogunRenderNPC;
import crowsofwar.shogun.common.ShogunCommonProxy;
import crowsofwar.shogun.common.entity.ShogunNPCPeasant;

public class ShogunClientProxy extends ShogunCommonProxy {

	@Override
	public void sideSpecifics() {
		RenderingRegistry.registerEntityRenderingHandler(ShogunNPCPeasant.class, new ShogunRenderNPC("peasant"));
	}
	
}
