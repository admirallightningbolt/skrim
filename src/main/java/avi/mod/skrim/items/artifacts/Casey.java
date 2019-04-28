package avi.mod.skrim.items.artifacts;

import avi.mod.skrim.Skrim;
import avi.mod.skrim.init.SkrimSoundEvents;
import avi.mod.skrim.items.SkrimItems;
import avi.mod.skrim.items.weapons.ArtifactSword;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class Casey extends ArtifactSword {

  public Casey() {
    super("casey", SkrimItems.ARTIFACT_DEFAULT);
    this.setMaxDamage(1000);
  }

  @Override
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("§4Buh-bye now.");
    tooltip.add("§e\"And now the air is shattered by the force of Casey's blow.\"");
  }

  @Override
  public void getSubItems(@Nonnull CreativeTabs tab, NonNullList<ItemStack> subItems) {
    ItemStack pick = new ItemStack(SkrimItems.CASEY);
    pick.addEnchantment(Enchantments.KNOCKBACK, 50);
    subItems.add(pick);
  }

  @Mod.EventBusSubscriber(modid = Skrim.MOD_ID)
  public static class CaseyHandler {

    @SubscribeEvent
    public static void homeRun(LivingHurtEvent event) {
      Entity entity = event.getSource().getTrueSource();
      if (!(entity instanceof EntityPlayer)) return;

      EntityPlayer player = (EntityPlayer) entity;
      if (player.getHeldItemMainhand().getItem() != SkrimItems.CASEY) return;

      player.world.playSound(null, player.getPosition(), SkrimSoundEvents.HOME_RUN, SoundCategory.PLAYERS, 10.0F, 1.0F);
    }

  }


}
