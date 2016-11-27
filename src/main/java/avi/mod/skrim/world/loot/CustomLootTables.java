package avi.mod.skrim.world.loot;

import avi.mod.skrim.Skrim;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class CustomLootTables {
	
	public static ResourceLocation CHESTS_BEANSTALK = null;
	
	private static ResourceLocation register(String id) {
		return LootTableList.register(new ResourceLocation(Skrim.modId, id));
	}
	
	public static void registerLootTables() {
		CHESTS_BEANSTALK = register("chests/beanstalk");
	}

}