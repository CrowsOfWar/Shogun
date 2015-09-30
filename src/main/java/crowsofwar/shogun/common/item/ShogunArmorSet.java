package crowsofwar.shogun.common.item;

import cpw.mods.fml.common.registry.GameRegistry;
import crowsofwar.gorecore.util.GoreCoreBodyCategory;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemArmor;

/**
 * A class used to create, register, & hold on to the armor
 * items in an armor set. Basically utility.
 * 
 * @author CrowsOfWar
 */
public class ShogunArmorSet {
	
	private static final String[] PIECE_NAMES = { "helmet", "chestplate", "leggings", "boots" };
	
	/**
	 * The armor items in this set.
	 */
	private final ItemArmor[] armor;
	
	/**
	 * Make a new armor set, making the items and registering them.
	 * 
	 * @param armorMaterial The ArmorMaterial to use for the armor
	 * @param tab The creative tab to put the armor in. Null for no creative tab
	 * @param name The name to be used in textures, language files, and registration, e.g. "lamellar"
	 */
	public ShogunArmorSet(ItemArmor.ArmorMaterial armorMaterial, CreativeTabs tab, String name) {
		this(armorMaterial, tab, name, true);
	}
	
	/**
	 * Make a new armor set, making the items and optionally registering them.
	 * 
	 * @param armorMaterial The ArmorMaterial to use for the armor
	 * @param tab The creative tab to put the armor in. Null for no creative tab
	 * @param name The name to be used in textures, language files, and registration, e.g. "lamellar"
	 * @param register Whether to register them into GameRegistry, make sure you register the items sometime.
	 */
	public ShogunArmorSet(ItemArmor.ArmorMaterial armorMaterial, CreativeTabs tab, String name, boolean register) {
		armor = new ItemArmor[4];
		for (int i = 0; i < armor.length; i++) {
			ItemArmor item = new ItemArmor(armorMaterial, 2, i);
			item.setCreativeTab(null);
			item.setTextureName("shogun:" + name + "_" + PIECE_NAMES[i]);
			item.setUnlocalizedName("shogun." + name + "_" + PIECE_NAMES[i]);
			GameRegistry.registerItem(item, name + "_" + PIECE_NAMES[i]);
		}
	}
	
	public ItemArmor getArmorPiece(int armorPart) {
		if (armorPart < 0 || armorPart >= armor.length)
				new IllegalArgumentException("The armor part should be 0-3 as described in ItemArmor!");
		return armor[armorPart];
	}
	
	public ItemArmor getArmorPiece(GoreCoreBodyCategory armorPart) {
		return getArmorPiece(armorPart.armorType());
	}
	
}
