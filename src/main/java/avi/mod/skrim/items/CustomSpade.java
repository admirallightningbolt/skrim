package avi.mod.skrim.items;

import avi.mod.skrim.Skrim;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemSpade;

public class CustomSpade extends ItemSpade implements ItemModelProvider {
	
	private String name;
	
	public CustomSpade(String name, ToolMaterial material) {
    super(material);
    this.name = name;
    this.setUnlocalizedName(name);
    this.setRegistryName(name);
    setCreativeTab(Skrim.creativeTab);
	}

	@Override
	public void registerItemModel(Item item) {
		Skrim.proxy.registerItemRenderer(this, 0, this.name);
	}
	
}