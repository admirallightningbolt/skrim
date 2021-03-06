package avi.mod.skrim.skills.blacksmithing;

import avi.mod.skrim.Skrim;
import avi.mod.skrim.skills.SkillProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlacksmithingProvider {

  @CapabilityInject(ISkillBlacksmithing.class)
  public static final Capability<ISkillBlacksmithing> BLACKSMITHING = null;
  public static final EnumFacing DEFAULT_FACING = null;
  public static final ResourceLocation ID = new ResourceLocation(Skrim.MOD_ID, "SkillBlacksmithing");

  public static void register() {
    CapabilityManager.INSTANCE.register(ISkillBlacksmithing.class, SkillBlacksmithing.STORAGE,
        SkillBlacksmithing::new);
    MinecraftForge.EVENT_BUS.register(new EventHandler());
  }

  public static SkillProvider<ISkillBlacksmithing> createProvider() {
    return new SkillProvider<>(BLACKSMITHING, EnumFacing.NORTH);
  }

  public static SkillProvider<ISkillBlacksmithing> createProvider(ISkillBlacksmithing blacksmithing) {
    return new SkillProvider<>(BLACKSMITHING, EnumFacing.NORTH, blacksmithing);
  }

  public static class EventHandler {

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
      Entity player = event.getObject();
      if (player instanceof EntityPlayer) {
        if (!player.hasCapability(BLACKSMITHING, EnumFacing.NORTH)) {
          event.addCapability(ID, createProvider());
        }
      }
    }

  }

}
