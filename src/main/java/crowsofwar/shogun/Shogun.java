package crowsofwar.shogun;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import crowsofwar.shogun.common.ShogunCommonProxy;
import crowsofwar.shogun.common.entity.ShogunNPCPeasant;

@Mod(modid=Shogun.MOD_ID, name=Shogun.MOD_NAME, version=Shogun.VERSION)
public class Shogun {
	
	public static final String MOD_ID = "Shogun";
	public static final String MOD_NAME = "Sh\u014Dgun";
	public static final String VERSION = "1.7.10-0.5.0";
	
	@Instance(value=Shogun.MOD_ID)
	public static Shogun instance;
	
	@SidedProxy(serverSide="crowsofwar.shogun.common.ShogunCommonProxy", clientSide="crowsofwar.shogun.client.ShogunClientProxy")
	public static ShogunCommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		EntityRegistry.registerGlobalEntityID(ShogunNPCPeasant.class, "ShogunPeasant", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerModEntity(ShogunNPCPeasant.class, "ShogunPeasant", 1, instance, 64, 3, true);
		
		proxy.sideSpecifics();
		
	}
	
}
