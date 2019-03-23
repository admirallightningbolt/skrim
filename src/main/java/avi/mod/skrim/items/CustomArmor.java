package avi.mod.skrim.items;

import avi.mod.skrim.Skrim;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public class CustomArmor extends ItemArmor implements ItemBase {

  protected String name;

  public CustomArmor(String name, ArmorMaterial material, int renderIndex, EntityEquipmentSlot armorType) {
    super(material, renderIndex, armorType);
    this.name = name;
    this.setUnlocalizedName(name);
    this.setRegistryName(name);
    this.setCreativeTab(Skrim.creativeTab);
  }

  @Override
  public String getTexturePath() {
    return "armor";
  }
}
