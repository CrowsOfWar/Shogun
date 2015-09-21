package crowsofwar.shogun.common.data;

import crowsofwar.gorecore.data.GoreCorePlayerData;
import crowsofwar.gorecore.data.GoreCoreWorldDataPlayers;

public class ShogunWorldData extends GoreCoreWorldDataPlayers {
	
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
	
}
