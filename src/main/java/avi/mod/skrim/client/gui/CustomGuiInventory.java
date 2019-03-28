package avi.mod.skrim.client.gui;

import avi.mod.skrim.advancements.ModAdvancements;
import avi.mod.skrim.network.AdvancementPacket;
import avi.mod.skrim.network.SkrimPacketHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;

import java.io.IOException;

public class CustomGuiInventory extends GuiInventory {

  private GuiButton skillTab;
  private EntityPlayer player;

  public CustomGuiInventory(EntityPlayer player) {
    super(player);
    this.player = player;
  }

  @Override
  public void initGui() {
    super.initGui();
    int buttonWidth = 176;
    int buttonHeight = 20;
    this.skillTab = new GuiButton(1337, this.guiLeft, this.guiTop - buttonHeight - 1, buttonWidth, buttonHeight,
				"Skills");
    this.buttonList.add(this.skillTab);
  }

  @Override
  public void actionPerformed(GuiButton button) throws IOException {
    if (button == this.skillTab) {
      SkrimPacketHandler.INSTANCE.sendToServer(new AdvancementPacket(ModAdvancements.FOUND_SKILLS.name));
      this.mc.displayGuiScreen(new SkillScreen(this.guiLeft, this.guiTop));
      if (this.mc.currentScreen == null) {
        this.mc.setIngameFocus();
      }
    }
    super.actionPerformed(button);
  }

}
