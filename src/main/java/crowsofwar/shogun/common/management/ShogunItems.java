package crowsofwar.shogun.common.management;

import cpw.mods.fml.common.registry.GameRegistry;
import crowsofwar.shogun.common.item.ShogunArmorSet;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import static crowsofwar.shogun.common.management.ShogunBlocks.*;

public class ShogunItems {
	
	public static ShogunArmorSet armorSetOyori;
	
	public static void initAndRegister() {
		armorSetOyori = new ShogunArmorSet(ArmorMaterial.IRON, ShogunCreativeTabs.tabCombat, "o-yori");
	}
	
}
