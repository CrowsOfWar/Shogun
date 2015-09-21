package crowsofwar.shogun.common.data;

import net.minecraft.world.World;
import crowsofwar.gorecore.data.GoreCoreEasyPlayerDataFetcher;
import crowsofwar.gorecore.data.GoreCorePlayerData;
import crowsofwar.gorecore.data.GoreCorePlayerDataCreationHandler;
import crowsofwar.gorecore.data.GoreCoreWorldDataFetcher;
import crowsofwar.gorecore.data.GoreCoreWorldDataPlayers;
import crowsofwar.shogun.Shogun;

public class ShogunPlayerDataFetcher extends GoreCoreEasyPlayerDataFetcher<ShogunPlayerData> {
	
	public static final ShogunPlayerDataFetcher FETCHER = new ShogunPlayerDataFetcher();
	
	private ShogunPlayerDataFetcher() {
		super(ShogunPlayerData.class, Shogun.MOD_ID, null, ShogunWorldData.WORLD_DATA_FETCHER);
	}
	
}
