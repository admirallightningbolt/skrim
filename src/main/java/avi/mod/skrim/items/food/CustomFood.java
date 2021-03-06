package avi.mod.skrim.items.food;

import avi.mod.skrim.Skrim;
import avi.mod.skrim.items.ItemBase;
import avi.mod.skrim.skills.cooking.SkillCooking;
import avi.mod.skrim.utils.Utils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Custom class for food overrides. In order to add special effects / additional hunger restored to foods we need to create duplicate
 * versions of normal minecraft food.
 */
public class CustomFood extends ItemFood implements ItemBase {

  protected String name;

  public CustomFood(String name, int amount, float saturation, boolean isWolfFood) {
    super(amount, saturation, isWolfFood);
    this.name = name;
    this.setUnlocalizedName(name);
    this.setRegistryName(name);
  }

  @Override
  protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
    if (stack.hasTagCompound()) {
      NBTTagCompound compound = stack.getTagCompound();
      if (compound.hasKey("level")) {
        ItemFood food = (ItemFood) stack.getItem();
        int level = compound.getInteger("level");

        boolean overFull = level >= 25;
        boolean panacea = level >= 50;
        boolean superFood = level >= 75;

        int additionalHeal = getTotalFood(food, stack, level);
        float additionalSaturation = getTotalSaturation(food, stack, level);

        FoodStats playerStats = player.getFoodStats();

        int newFood = playerStats.getFoodLevel() + additionalHeal;
        float newSaturation = playerStats.getSaturationLevel() + additionalSaturation;

        newFood = (newFood > 20 && !overFull) ? 20 : newFood;
        newSaturation = (newSaturation > newFood && !overFull) ? newFood : newSaturation;

        if (panacea) {
          player.removePotionEffect(MobEffects.POISON);
          player.removePotionEffect(MobEffects.HUNGER);
          player.removePotionEffect(MobEffects.NAUSEA);
        }

        if (superFood) {
          PotionEffect regenEffect = new PotionEffect(MobEffects.REGENERATION, 200, 1, false, false);
          PotionEffect speedEffect = new PotionEffect(MobEffects.SPEED, 200, 1, false, false);
          Utils.addOrCombineEffect(player, regenEffect);
          Utils.addOrCombineEffect(player, speedEffect);
        }

        // A valiant attempt to keep me from over-filling. But not
        // valiant enough. For some reason setFoodSaturationLevel is
        // client side only. But setFoodLevel bypasses the maximum for
        // food... BUT, addStats will reset the maximums for both.....
        // BUUTTT, readNBT(NBTTagCompound compound) assigns directly
        //so..... we need to set foodLevel, foodTimer,
        // foodSaturationLevel, foodExhaustionLevel
        NBTTagCompound storeCompound = new NBTTagCompound();
        storeCompound.setInteger("foodLevel", newFood);
        storeCompound.setInteger("foodTimer", 0); // Starts at 0, counts up to 80 ticks
        storeCompound.setFloat("foodSaturationLevel", newSaturation);
        storeCompound.setFloat("foodExhaustionLevel", 0); // Food exhaustion max 40 when fully exhausted
        playerStats.readNBT(storeCompound);
      }
    }
  }

  public static int getExtraFood(ItemFood food, ItemStack stack, int level) {
    int baseHeal = food.getHealAmount(stack);
    double extraFood = SkillCooking.extraFood(level);
    return (int) (baseHeal * extraFood);
  }

  public static float getExtraSaturation(ItemFood food, ItemStack stack, int level) {
    int baseHeal = food.getHealAmount(stack);
    int additionalHeal = getExtraFood(food, stack, level);
    float satMod = food.getSaturationModifier(stack);
    double extraSaturation = SkillCooking.extraSaturation(level);
    // Apply the old sat mod to the new healing, and the new sat mod to everything
    return satMod * additionalHeal + (int) (extraSaturation * (baseHeal + additionalHeal));
  }

  public static int getTotalFood(ItemFood food, ItemStack stack, int level) {
    return getExtraFood(food, stack, level) + food.getHealAmount(stack);
  }

  public static float getTotalSaturation(ItemFood food, ItemStack stack, int level) {
    return getExtraSaturation(food, stack, level) + food.getSaturationModifier(stack) * food.getHealAmount(stack);
  }

  @Override
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    if (!stack.hasTagCompound()) return;
    NBTTagCompound compound = stack.getTagCompound();
    if (compound == null) return;
    if (!compound.hasKey("level")) return;

    int level = compound.getInteger("level");
    tooltip.add("Cooking Level: §a" + level + "§r");
    tooltip.add("Food restored: §a" + getTotalFood((ItemFood) stack.getItem(), stack, level) + "§r, saturation restored: §a"
        + String.format("%.1f", getTotalSaturation((ItemFood) stack.getItem(), stack, level)) + "§r.");

    if (level < 25) return;
    tooltip.add("§4Overfull§r");

    if (level < 50) return;
    tooltip.add("§4Panacea§r");

    if (level < 75) return;
    tooltip.add("§4Super Food§r");
  }

  @Override
  public String getTexturePath() {
    return "food";
  }
}
