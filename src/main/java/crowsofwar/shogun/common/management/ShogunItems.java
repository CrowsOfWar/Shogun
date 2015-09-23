package crowsofwar.shogun.common.management;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;
import static crowsofwar.shogun.common.management.ShogunBlocks.*;

public class ShogunItems {
	
	public static Item itemRoofingSlab;
	public static Item itemRoofingSlabDouble;
	
	public static void initAndRegister() {
		itemRoofingSlab = new ItemSlab(blockRoofingSlab, blockRoofingSlab, blockRoofingSlabDouble, false);
		itemRoofingSlabDouble = new ItemSlab(blockRoofingSlabDouble, blockRoofingSlab, blockRoofingSlabDouble, true);
		
		
	}
}
