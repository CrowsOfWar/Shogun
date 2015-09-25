package crowsofwar.shogun.common.management;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

/**
 * Keeps track of the creative tabs in the Shogun mod.
 * 
 * @author CrowsOfWar
 */
public final class ShogunCreativeTabs {
	
	public static final CreativeTabs tabBuilding = new CreativeTabs("shogunBuilding") {
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(ShogunBlocks.blockRoofingStairs[4]); // Color = green
		}
	};
	
	public static final CreativeTabs tabCombat = new CreativeTabs("shogunCombat") {
		@Override
		public Item getTabIconItem() {
			return Items.diamond_sword; // Will be changed once actual combat items are added
		}
	};
	
	public static final CreativeTabs tabItems = new CreativeTabs("shogunItems") {
		@Override
		public Item getTabIconItem() {
			return Items.wheat; // Will be changed once items are added, e.g. rice
		}
	};
	
}
