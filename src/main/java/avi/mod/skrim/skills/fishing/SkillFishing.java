package avi.mod.skrim.skills.fishing;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import avi.mod.skrim.Utils;
import avi.mod.skrim.skills.RandomTreasure;
import avi.mod.skrim.skills.Skill;
import avi.mod.skrim.skills.SkillStorage;

public class SkillFishing extends Skill implements ISkillFishing {

	public static SkillStorage<ISkillFishing> skillStorage = new SkillStorage<ISkillFishing>();
	private static Random xpRand = new Random();

	public boolean canCatch = false;
	public static Map<String, Integer> xpMap;
	static {
		xpMap = new HashMap<String, Integer>();
		String[] validItems = {
			"item.item.fish.cod.raw",
			"item.item.fish.salmon.raw",
			"item.item.fish.clownfish.raw",
			"item.item.fish.pufferfish.raw",
			"item.item.bow",
			"item.item.enchantedbook",
			"item.item.fishingrod",
			"item.item.nametag",
			"item.item.saddle",
			"item.item.waterlily",
			"item.item.bowl",
			"item.item.leather",
			"item.item.bootscloth",
			"item.item.rottenflesh",
			"item.item.stick",
			"item.item.string",
			"item.item.potion",
			"item.item.bone",
			"item.item.dyepowder.black",
			"item.tile.tripwiresource"
		};
		/**
		 * This may be unnecessary, but we want to keep this way so we can
		 * easily change experience in the future.
		 */
		for (String itemName : validItems) {
			xpMap.put(itemName, 75);
		}
	}

	public SkillFishing() {
		this(1, 0);
	}

	public SkillFishing(int level, int currentXp) {
		super("Fishing", level, currentXp);
		this.iconTexture = new ResourceLocation("skrim", "textures/guis/skills/fishing.png");
	}

	private int getXp(String blockName) {
		return (xpMap.containsKey(blockName)) ? xpMap.get(blockName) : 0;
	}

	private boolean canGrapple() {
		return (this.level >= 25);
	}

	private double getTreasureChance() {
		return 0.01 * this.level;
	}

	private boolean isValidFish(EntityItem item) {
		return this.canCatch && this.xpMap.containsKey(Utils.snakeCase(item.getName()));
	}
	
	private int randomXPOrb() {
		int min = this.getMinXP();
		int max = this.getMaxXP();
		return xpRand.nextInt(max - min) + min;
	}
	
	private int getMinXP() {
		return (int) (this.level * 0.3) + 1;
	}
	
	private int getMaxXP() {
		return (int) (this.level * 0.6) + 2;
	}

	@Override
	public List<String> getToolTip() {
		DecimalFormat fmt = new DecimalFormat("0.0");
		List<String> tooltip = new ArrayList<String>();
		tooltip.add("§a" + (this.getTreasureChance() * 100) + "%§r chance to fish additional treasure.");
		tooltip.add("Fishing provides an additional §a" + this.getMinXP() + "§r-§a" + this.getMaxXP() + "§r xp.");
		return tooltip;
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onFishEvent(PlayerInteractEvent event) {
		ItemStack stack = event.getItemStack();
		if (stack != null) {
			Item item = stack.getItem();
			if (item != null && item == Items.FISHING_ROD) {
				EntityPlayer player = event.getEntityPlayer();
				Entity entity = event.getEntity();
				EntityFishHook fishHook = player.fishEntity;
				if (fishHook != null) {
					if (!fishHook.isAirBorne) {
						if (fishHook.onGround) {
							if (this.canGrapple()) {
								MinecraftServer server = Minecraft.getMinecraft().getIntegratedServer();
								ICommandManager cm = server.getCommandManager();
								BlockPos pos = fishHook.getPosition();
								cm.executeCommand(server, "/tp @p " + pos.getX() + " " + pos.getY() + " " + pos.getZ());
							}
						} else if (fishHook.isInWater()) {
							System.out.println("canCatch = true");
							this.canCatch = true;
							new Timer().schedule(
								new TimerTask() {
									@Override
									public void run() {
										System.out.println("setting canCatch to false.");
										SkillFishing.this.canCatch = false;
									}
								}, 1000
							);
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onItemPickup(EntityItemPickupEvent event) {
		System.out.println("Picked up item: " + Utils.snakeCase(event.getItem().getName()));
		EntityPlayer player = event.getEntityPlayer();
		if (player != null) {
			EntityItem item = event.getItem();
			System.out.println("caught item.");
			if (this.isValidFish(item)) {
				double random = Math.random();
				System.out.println("'Fish' caught!");
				this.canCatch = false;
				this.xp += this.getXp(Utils.snakeCase(event.getItem().getName()));
				player.worldObj.spawnEntityInWorld(new EntityXPOrb(player.worldObj, player.posX, player.posY, player.posZ, this.randomXPOrb()));
				if (random < this.getTreasureChance()) {
					player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, RandomTreasure.generate()));
					this.xp += 50; // And an xp bonus!
				}
				this.levelUp();
			}
		}
	}

}
