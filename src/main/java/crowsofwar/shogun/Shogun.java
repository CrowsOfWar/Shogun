package crowsofwar.shogun;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid=Shogun.MOD_ID, name=Shogun.MOD_NAME, version=Shogun.VERSION)
public class Shogun {
	
	public static final String MOD_ID = "Shogun";
	public static final String MOD_NAME = "Sh\u014Dgun";
	public static final String VERSION = "1.7.10-0.5.0";
	
	@SubscribeEvent
	public void preInit(FMLPreInitializationEvent event) {
		
	}
	
}
