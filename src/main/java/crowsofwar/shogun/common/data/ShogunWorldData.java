package crowsofwar.shogun.common.data;

import net.minecraft.world.World;
import crowsofwar.gorecore.data.GoreCorePlayerData;
import crowsofwar.gorecore.data.GoreCoreWorldDataFetcher;
import crowsofwar.gorecore.data.GoreCoreWorldDataPlayers;

public class ShogunWorldData extends GoreCoreWorldDataPlayers {
	
	public static final GoreCoreWorldDataFetcher<ShogunWorldData> WORLD_DATA_FETCHER
			= new GoreCoreWorldDataFetcher<ShogunWorldData>(){
		
		@Override
		public ShogunWorldData getWorldData(World world) {
			return ShogunWorldData.getWorldData(world);
		}
	};
	
	public static final String KEY = "ShogunData";
	
	public ShogunWorldData() {
		this(KEY);
	}
	
	public ShogunWorldData(String key) {
		super(KEY);
	}
	
	@Override
	public Class<? extends GoreCorePlayerData> playerDataClass() {
		return ShogunPlayerData.class;
	}
	
	public static ShogunWorldData getWorldData(World world) {
		return getDataForWorld(ShogunWorldData.class, KEY, world, false);
	}
	
}
