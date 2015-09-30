package crowsofwar.shogun.common.item;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import crowsofwar.gorecore.util.GoreCoreBodyCategory;
import crowsofwar.shogun.Shogun;
import crowsofwar.shogun.common.management.ShogunArmorType;

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
	 * @param armorType The armor type to be used in shogun APIs
	 */
	public ShogunArmorSet(ItemArmor.ArmorMaterial armorMaterial, CreativeTabs tab, String name, ShogunArmorType armorType) {
		this(armorMaterial, tab, name, armorType, true);
	}
	
	/**
	 * Make a new armor set, making the items and optionally registering them.
	 * 
	 * @param armorMaterial The ArmorMaterial to use for the armor
	 * @param tab The creative tab to put the armor in. Null for no creative tab
	 * @param name The name to be used in textures, language files, and registration, e.g. "lamellar"
	 * @param armorType The armor type to be used in shogun APIs
	 * @param register Whether to register them into GameRegistry, make sure you register the items sometime.
	 */
	public ShogunArmorSet(ItemArmor.ArmorMaterial armorMaterial, CreativeTabs tab, String name, ShogunArmorType armorType,
			boolean register) {
		armor = new ItemArmor[4];
		for (int i = 0; i < armor.length; i++) {
			ItemArmor item = new ModelledArmor(armorMaterial, 2, i, name, armorType);
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
	
	private class ModelledArmor extends ItemArmor {
		
		private final String texture;
		private final ShogunArmorType shogunArmorType;
		
		public ModelledArmor(ArmorMaterial armorMaterial, int renderType, int armorType, String texture, ShogunArmorType sat) {
			super(armorMaterial, renderType, armorType);
			this.texture = texture;
			this.shogunArmorType = sat;
		}
		
		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
			return slot == GoreCoreBodyCategory.LEGS.armorType() ? "shogun:" + texture + "_legs" : "shogun:" + texture;
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public ModelBiped getArmorModel(EntityLivingBase entity, ItemStack stack, int slot) {
			return Shogun.proxy.getArmorModel(shogunArmorType, slot, entity);
		}
		
	}
	
}
