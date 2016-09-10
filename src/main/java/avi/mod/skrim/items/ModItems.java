package avi.mod.skrim.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scala.actors.threadpool.Arrays;

import avi.mod.skrim.Skrim;
import avi.mod.skrim.items.CustomFishHook;
import net.minecraft.block.BlockBush;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry.EntityRegistration;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

	public static ItemBase tux;

	public static CustomFood overwritePorkchop;
	public static CustomFood overwriteChicken;
	public static CustomFood overwriteMutton;
	public static CustomFood overwriteRabbit;
	public static CustomFood overwriteBakedPotato;
	public static CustomFood overwriteFish;
	public static CustomFood overwriteSalmon;
	public static CustomFood overwriteCake;
	public static CustomFood overwriteBeetrootSoup;
	public static CustomFood overwriteMushroomStew;
	public static CustomFood overwriteCookie;
	public static CustomFood overwriteBread;
	public static CustomFood overwritePumpkinPie;
	public static CustomFood overwriteRabbitStew;
	public static CustomFood overwriteSteak;
	public static CustomFood canesChicken;

	public static CustomFishingRod fishingRod;

	public static EnumRarity ARTIFACT_RARITY = EnumHelper.addRarity("artifact", TextFormatting.GOLD, "Artifact");

	/**
	 * Default ArmorMaterials Durability: leather -> 5 chain/iron -> 15 gold -> 7 diamond -> 33 Reductions: leather -> {1, 3, 2, 1} Total 7 chain -> {2, 5, 4, 1} Total 12 iron -> {2, 6, 5, 2} Total 15 gold -> {2, 5, 3, 1} Total 11 diamond -> {3, 8, 6, 3} Total 20 Enchantability: leather -> 15 chain -> 12 iron -> 9 gold -> 25 diamond -> 10 Toughness
	 */
	public static ArmorMaterial ARTIFACT_DARK = EnumHelper.addArmorMaterial("artifact_dark", "skrim:artifact_dark", 50, new int[] { 3, 8, 6, 3 }, 30, null, 0.0F);
	public static ArmorMaterial OBSIDIAN_ARMOR = EnumHelper.addArmorMaterial("obsidian", "skrim:obsidian_armor", 165, new int[] { 4, 10, 8, 4 }, 20, null, 3.0F);

	/**
	 * Default ToolMaterials HarvestLevel: wood: 0 stone: 1 iron: 2 diamond: 3 Durability: wood: 59 stone: 131 iron: 250 gold: 32 diamond: 1561 Mining Speed: wood: 2.0F stone: 4.0F iron: 6.0F gold: 12.0F diamond: 8.0F Damage vs. Entity wood: 0.0F stone: 1.0F iron: 2.0F gold: 0.0F diamond: 3.0F Enchantability: wood: 15 stone: 5 iron: 14 gold: 22 diamond: 10
	 */

	public static ToolMaterial ARTIFACT_DEFAULT = EnumHelper.addToolMaterial("artifact_default", 3, 4500, 6.0F, 4.0F, 0);
	public static ToolMaterial OBSIDIAN_TOOL = EnumHelper.addToolMaterial("obsidian", 3, 7500, 9.0F, 4.0F, 20);

	public static CustomSword obsidianSword;
	public static CustomSpade obsidianShovel;
	public static CustomPickaxe obsidianPickaxe;
	public static CustomHoe obsidianHoe;
	public static CustomAxe obsidianAxe;

	public static CustomArmor obsidianBoots;
	public static CustomArmor obsidianPants;
	public static CustomArmor obsidianChest;
	public static CustomArmor obsidianHelmet;

	public static ArtifactArmor bootsOfSpringheelJak;
	public static ArtifactSword raisingCanesFrySword;

	public static void createItems() {
		tux = register(new ItemBase("tux").setCreativeTab(Skrim.creativeTab));

		// Food!
		overwritePorkchop = register(new CustomFood("overwrite_porkchop", 8, 1.6F, true).setCreativeTab(Skrim.creativeTab));
		overwriteBakedPotato = register(new CustomFood("overwrite_baked_potato", 5, 1.2F, false).setCreativeTab(Skrim.creativeTab));
		overwriteBeetrootSoup = register(new CustomFood("overwrite_beetroot_soup", 6, 1.2F, false).setCreativeTab(Skrim.creativeTab));
		overwriteBread = register(new CustomFood("overwrite_bread", 5, 1.2F, false).setCreativeTab(Skrim.creativeTab));
		overwriteChicken = register(new CustomFood("overwrite_chicken", 6, 1.2F, true).setCreativeTab(Skrim.creativeTab));
		overwriteFish = register(new CustomFood("overwrite_fish", 5, 1.2F, true).setCreativeTab(Skrim.creativeTab));
		overwriteMutton = register(new CustomFood("overwrite_mutton", 6, 1.6F, true).setCreativeTab(Skrim.creativeTab));
		overwriteSalmon = register(new CustomFood("overwrite_salmon", 6, 1.6F, true).setCreativeTab(Skrim.creativeTab));
		overwriteCookie = register(new CustomFood("overwrite_cookie", 2, 0.2F, false).setCreativeTab(Skrim.creativeTab));
		overwriteMushroomStew = register(new CustomFood("overwrite_mushroom_stew", 6, 1.2F, false).setCreativeTab(Skrim.creativeTab));
		overwritePumpkinPie = register(new CustomFood("overwrite_pumpkin_pie", 8, 0.6F, false).setCreativeTab(Skrim.creativeTab));
		overwriteRabbitStew = register(new CustomFood("overwrite_rabbit_stew", 10, 1.2F, false).setCreativeTab(Skrim.creativeTab));
		overwriteSteak = register(new CustomFood("overwrite_steak", 8, 1.6F, true).setCreativeTab(Skrim.creativeTab));
		overwriteRabbit = register(new CustomFood("overwrite_rabbit", 5, 1.2F, true).setCreativeTab(Skrim.creativeTab));
		canesChicken = register(new CustomFood("canes_chicken", 20, 1.5F, true).setCreativeTab(Skrim.creativeTab));

		// Overwrite that fishing rod!
		fishingRod = register(new CustomFishingRod("fishing_rod"));
		EntityRegistry.registerModEntity(CustomFishHook.class, "CustomFishHook", 0, Skrim.instance, 64, 5, true);

		// Obsidian tools
		obsidianSword = register(new CustomSword("obsidian_sword", OBSIDIAN_TOOL));
		obsidianHoe = register(new CustomHoe("obsidian_hoe", OBSIDIAN_TOOL));
		obsidianShovel = register(new CustomSpade("obsidian_spade", OBSIDIAN_TOOL));
		obsidianAxe = register(new CustomAxe("obsidian_axe", OBSIDIAN_TOOL));
		obsidianPickaxe = register(new CustomPickaxe("obsidian_pickaxe", OBSIDIAN_TOOL));

		// Obsidian armor
		obsidianBoots = register(new CustomArmor("obsidian_boots", OBSIDIAN_ARMOR, 1, EntityEquipmentSlot.FEET));
		obsidianPants = register(new CustomArmor("obsidian_pants", OBSIDIAN_ARMOR, 2, EntityEquipmentSlot.LEGS));
		obsidianChest = register(new CustomArmor("obsidian_chest", OBSIDIAN_ARMOR, 3, EntityEquipmentSlot.CHEST));
		obsidianHelmet = register(new CustomArmor("obsidian_helmet", OBSIDIAN_ARMOR, 4, EntityEquipmentSlot.HEAD));

		// Artifact Armors
		bootsOfSpringheelJak = register(new ArtifactArmor("boots_of_springheel_jak", ARTIFACT_DARK, 1, EntityEquipmentSlot.FEET));

		// Artifact Swords
		raisingCanesFrySword = register(new ArtifactSword("raising_canes_fry_sword", ARTIFACT_DEFAULT));

		
		/**
		 * Register crafting recipes!
		 */
		registerCraftingRecipes();
	}
	
	public static void registerCraftingRecipes() {
		registerRabbitStew();
		registerObsidian();
	}

	/**
	 * We have to be explicit with our recipes. Which means since we're injecting our own food.... Sigh.
	 */
	public static void registerRabbitStew() {
		Item[] rabbits = { (Item) overwriteRabbit, Items.COOKED_RABBIT };
		BlockBush[] mushrooms = { Blocks.RED_MUSHROOM, Blocks.BROWN_MUSHROOM };
		Item[] potatoes = { overwriteBakedPotato, Items.BAKED_POTATO };
		for (Item rabbit : rabbits) {
			for (Item potato : potatoes) {
				if (rabbit != Items.COOKED_RABBIT || potato != Items.BAKED_POTATO) {
					for (BlockBush mushroom : mushrooms) {
						GameRegistry.addRecipe(new ItemStack(Items.RABBIT_STEW), " a ", "bcd", " e ", 'a', new ItemStack(rabbit), 'b', new ItemStack(Items.CARROT), 'c', new ItemStack(potato), 'd', new ItemStack(mushroom), 'e', new ItemStack(Items.BOWL));
					}
				}
			}
		}
	}

	private static void addToolRecipes(ItemStack recipeItemStack, Item axe, Item hoe, Item pickaxe, Item spade) {
		if (axe != null) {
			GameRegistry.addRecipe(new ItemStack(axe), "AA ", "AS ", " S ", 'A', recipeItemStack, 'S', Items.STICK);
			GameRegistry.addRecipe(new ItemStack(axe), " AA", " SA", " S ", 'A', recipeItemStack, 'S', Items.STICK);
		}
		if (hoe != null) {
			GameRegistry.addRecipe(new ItemStack(hoe), "AA ", " S ", " S ", 'A', recipeItemStack, 'S', Items.STICK);
			GameRegistry.addRecipe(new ItemStack(hoe), " AA", " S ", " S ", 'A', recipeItemStack, 'S', Items.STICK);
		}
		if (pickaxe != null) {
			GameRegistry.addRecipe(new ItemStack(pickaxe), "AAA", " S ", " S ", 'A', recipeItemStack, 'S', Items.STICK);
		}
		if (spade != null) {
			GameRegistry.addRecipe(new ItemStack(spade), " A ", " S ", " S ", 'A', recipeItemStack, 'S', Items.STICK);
		}
	}

	private static void addArmorRecipes(ItemStack recipeItemStack, Item helmet, Item chest, Item pants, Item boots) {
		if (helmet != null) {
			GameRegistry.addRecipe(new ItemStack(helmet), "AAA", "A A", "   ", 'A', recipeItemStack);
		}
		if (chest != null) {
			GameRegistry.addRecipe(new ItemStack(chest), "A A", "AAA", "AAA", 'A', recipeItemStack);
		}
		if (pants != null) {
			GameRegistry.addRecipe(new ItemStack(pants), "AAA", "A A", "A A", 'A', recipeItemStack);
		}
		if (boots != null) {
			GameRegistry.addRecipe(new ItemStack(boots), "   ", "A A", "A A", 'A', recipeItemStack);
		}
	}

	private static void addWeaponRecipes(ItemStack recipeItemStack, Item sword) {
		if (sword != null) {
			GameRegistry.addRecipe(new ItemStack(sword), " A ", " A ", " S ", 'A', recipeItemStack, 'S', Items.STICK);
		}
	}

	public static void registerObsidian() {
		addArmorRecipes(new ItemStack(Blocks.OBSIDIAN), obsidianHelmet, obsidianChest, obsidianPants, obsidianBoots);
		addToolRecipes(new ItemStack(Blocks.OBSIDIAN), obsidianAxe, obsidianHoe, obsidianPickaxe, obsidianShovel);
		addWeaponRecipes(new ItemStack(Blocks.OBSIDIAN), obsidianSword);
	}

	private static <T extends Item> T register(T item) {
		GameRegistry.register(item);
		if (item instanceof ItemModelProvider) {
			((ItemModelProvider) item).registerItemModel(item);
		}
		item.setCreativeTab(Skrim.creativeTab);
		return item;
	}

}
