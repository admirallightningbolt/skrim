package avi.mod.skrim.client.gui;

import avi.mod.skrim.client.gui.GuiUtils.Icon;
import avi.mod.skrim.skills.Skill;
import avi.mod.skrim.skills.SkillAbility;
import avi.mod.skrim.skills.Skills;
import avi.mod.skrim.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Skill UI!
 */
public class SkillScreen extends GuiScreen {

  private static final double SCROLL_MULTIPLIER = 0.03;

  private GuiButton inventoryTab;
  private int left;
  private int top;
  /**
   * Current scroll position
   */
  private int scrollY = 0;
  /**
   * Max scroll position.
   */
  private int maxScroll = 490;
  private int scrollBarWidth = 6;
  private int scrollBarHeight = 40;
  private int scrollPaddingLeft = 5;
  private int paddingTop = 5;
  private int paddingBottom = 5;
  private int paddingRight = 5;

  private int titleHeight = 30;
  private int titlePaddingLeft = 5;

  private int dividerPadding = 2;
  private int skillPaddingLeft = 5;
  private int skillHeight = 39;
  private int skillIconSize = 32;
  private int skillPaddingDesc = 5;
  private int skillPaddingTop = 10 + this.dividerPadding;
  private int skillHeaderHeight = 11;
  private int levelTextLeft;

  private int abilityIconSize = 16;
  private int abilityIconPadding = 10;

  private int levelBarHeight = 9;
  private int levelBarWidth =
			176 - this.paddingRight - this.scrollBarWidth - this.scrollPaddingLeft - this.skillPaddingLeft;

  private int headerColor = 0xFF333333;
  private int levelBarColor = 0x8055dd55;
  private int levelBarTextColor = 0xFFFFFFFF;
  private int dividerColor = 0xFFAAAAAA;

  private int boundTop;
  private int boundBottom;

  private EntityPlayer player;

  public SkillScreen(int left, int top) {
    super();
    this.left = left;
    this.top = top;
    this.boundTop = this.top + 3;
    this.boundBottom = this.top + 165 - 3;
    this.levelTextLeft = this.left + 176 - 55;
  }

  @Override
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    this.drawDefaultBackground();
    this.mc.getTextureManager().bindTexture(new ResourceLocation("skrim", "textures/guis/skills/background.png"));
    this.drawTexturedModalRect(this.left, this.top, 0, 0, 176, 176);
    this.drawScrollBar();
    this.drawTitleBar();
    this.drawSkills(mouseX, mouseY);
    super.drawScreen(mouseX, mouseY, partialTicks);
  }

  public void drawScrollBar() {
    int adjust =
				(int) Math.floor(((double) this.scrollY / this.maxScroll) * (176 - this.scrollBarHeight - this.paddingTop - this.paddingBottom - 10));
    int left = this.left + this.scrollPaddingLeft;
    int top = this.top + this.paddingTop + adjust;
    int right = left + this.scrollBarWidth;
    int bottom = top + this.scrollBarHeight;
    drawRect(left, top, right, bottom, 0xAAAAAAAA);
  }

  public void drawTitleBar() {
    int textLeft = this.left + this.scrollPaddingLeft + this.scrollBarWidth + this.titlePaddingLeft;
    int titleTop = this.top + this.paddingTop - this.scrollY;
    if (shouldRender(titleTop, titleTop + 7)) {
      this.mc.fontRenderer.drawString("Total Skill Level: " + Skills.getTotalSkillLevels(this.player), textLeft,
					titleTop, this.headerColor);
    }
    if (shouldRender(titleTop + 12, titleTop + 19)) {
      this.mc.fontRenderer.drawString("Total Experience Boost: " + Utils.formatPercent(Skills.getTotalXpBonus(this.player)) + "%", textLeft,
          titleTop + 12, this.headerColor);
    }
    if (shouldRender(titleTop + 23, titleTop + 25)) {
      this.drawHorizontalLine(textLeft, textLeft + this.levelBarWidth, titleTop + 24, this.dividerColor);
    }
  }

  public void drawSkills(int mouseX, int mouseY) {
    List<Integer> leftValues = new ArrayList<Integer>();
    List<Integer> topValues = new ArrayList<Integer>();
    List<Skill> skills = new ArrayList<Skill>();
    for (int i = 0; i < Skills.ALL_SKILLS.size(); i++) {
      skills.add((Skill) this.player.getCapability(Skills.ALL_SKILLS.get(i), EnumFacing.NORTH));
      leftValues.add(this.left + this.scrollPaddingLeft + this.scrollBarWidth + this.skillPaddingLeft);
      int top = this.top + this.titleHeight + this.paddingTop - this.scrollY + i * this.skillHeight;
      if (i > 0) {
        top += this.skillPaddingTop * i;
      }
      topValues.add(top);
    }
    /**
     * I really wish I could just control the goddamn z-index of any gui
     * element. Since we can't, we have to control the order we draw things
     * in. IE loop over the skills first THEN the functions.
     *
     * Hello again, time to make this worse. We want to draw ability icons
     * AND hover text which means two more in the right places!
     */
    for (int q = 0; q <= 5; q++) {
      for (int i = 0; i < skills.size(); i++) {
        Skill skill = skills.get(i);
        int left = leftValues.get(i);
        int top = topValues.get(i);
        if (q == 0) {
          this.drawSkillHeader(skill, left, top);
        } else if (q == 1) {
          this.drawSkillLevelUp(skill, left, top);
        } else if (q == 2) {
          this.drawSkillIcon(skill, left, top);
        } else if (q == 3) {
          this.drawAbilityIcons(skill, left, top);
        } else if (q == 4) {
          this.drawAbilityHoverText(skill, left, top, mouseX, mouseY);
        } else if (q == 5) {
          this.drawSkillHoverText(skill, left, top, mouseX, mouseY);
        }
      }
    }
  }

  public void drawSkillHeader(Skill skill, int left, int top) {
    int textLeft = left + this.skillPaddingDesc + this.skillIconSize;
    if (this.shouldRender(top, top + 7)) {
      this.mc.fontRenderer.drawString(skill.name, textLeft, top, this.headerColor);
      this.mc.fontRenderer.drawString("Level " + skill.level, this.levelTextLeft, top, this.headerColor);
    }
  }

  public void drawSkillLevelUp(Skill skill, int left, int top) {
    int levelLeft = left;
    int levelTop = top + this.skillIconSize + this.dividerPadding;
    int levelRight = levelLeft + (int) Math.floor(this.levelBarWidth * skill.getPercentToNext());
    int levelBottom = levelTop + this.levelBarHeight;
    if (shouldRender(levelTop, levelBottom)) {
      drawRect(levelLeft, levelTop, levelRight, levelBottom, this.levelBarColor);
      String levelText = skill.getIntXp() + " / " + skill.getNextLevelTotal();
      int levelTextWidth = this.mc.fontRenderer.getStringWidth(levelText);
      int levelTextLeft = levelLeft + (this.levelBarWidth / 2) - (int) ((double) levelTextWidth / 2);
      this.mc.fontRenderer.drawString(levelText, levelTextLeft, levelTop + 1, this.levelBarTextColor);
    }
  }

  public void drawSkillIcon(Skill skill, int left, int top) {
    this.mc.getTextureManager().bindTexture(GuiUtils.SKILL_ICONS);
    GuiUtils.drawIconWithBounds(this, left, top, skill.getIcon(), this.boundTop, this.boundBottom);
  }

  public void drawSkillHoverText(Skill skill, int left, int top, int mouseX, int mouseY) {
    if (shouldRender(top, top + this.skillIconSize)) {
      if (Utils.isPointInRegion(left, top, left + this.skillIconSize, top + this.skillIconSize, mouseX, mouseY)) {
        this.drawHoveringText(skill.getToolTip(), mouseX, mouseY);
      }
    }
  }

  private void drawAbilityIcons(Skill skill, int left, int top) {
    int startLeft = left + this.skillPaddingDesc + this.skillIconSize;
    int abilityTop = top + this.skillHeaderHeight;
    this.mc.getTextureManager().bindTexture(GuiUtils.ABILITY_ICONS);
    for (int i = 1; i <= 4; i++) {
      int abilityLeft = startLeft + (i - 1) * (this.abilityIconSize + this.abilityIconPadding);
      Icon icon = skill.getAbilityIcon(i);
      GuiUtils.drawIconWithBounds(this, abilityLeft, abilityTop, icon, this.boundTop, this.boundBottom);
    }
  }

  private void drawAbilityHoverText(Skill skill, int left, int top, int mouseX, int mouseY) {
    int startLeft = left + this.skillPaddingDesc + this.skillIconSize;
    int abilityTop = top + this.skillHeaderHeight;
    int abilityBottom = abilityTop + this.abilityIconSize;
    if (shouldRender(abilityTop, abilityBottom)) {
      for (int i = 1; i <= 4; i++) {
        int abilityLeft = startLeft + (i - 1) * (this.abilityIconSize + this.abilityIconPadding);
        int abilityRight = abilityLeft + this.abilityIconSize;
        if (Utils.isPointInRegion(abilityLeft, abilityTop, abilityRight, abilityBottom, mouseX, mouseY)) {
          this.drawHoveringText(SkillAbility.getAbilityTooltip(skill.getAbility(i), skill.hasAbility(i)), mouseX,
							mouseY);
        }
      }
    }
  }

  private boolean shouldRender(int top, int bottom) {
    return (top > this.boundTop && this.boundBottom > bottom);
  }

  @Override
  public boolean doesGuiPauseGame() {
    return false;
  }

  @Override
  public void initGui() {
    this.inventoryTab = new GuiButton(1995, this.left, this.top - 20 - 1, 176, 20, "Inventory");
    this.buttonList.add(this.inventoryTab);
    this.player = Minecraft.getMinecraft().player;
  }

  /**
   * Handles button clicks, in this case there's only the inventory button.
   */
  @Override
  protected void actionPerformed(GuiButton button) {
    if (button == this.inventoryTab) {
      this.mc.displayGuiScreen(new CustomGuiInventory(Minecraft.getMinecraft().player));
    }
  }

  /**
   * Swap back to the inventory screen if the inventory button is pressed.
   */
  @Override
  protected void keyTyped(char typedChar, int keyCode) throws IOException {
    if (this.mc.gameSettings.keyBindInventory.getKeyCode() == keyCode) {
      this.mc.displayGuiScreen(null);
      if (this.mc.currentScreen == null) this.mc.setIngameFocus();
    }
    super.keyTyped(typedChar, keyCode);
  }

  @Override
  public void handleMouseInput() throws IOException {
    int i = Mouse.getEventDWheel();
    if (i != 0) {
      /**
       * Scrolling DOWN is a negative number. Scrolling UP is a positive
       * number. However, 0 is our lowest scroll y, so we can always
       * subtract.
       */
      this.scrollY -= (int) Math.floor(SCROLL_MULTIPLIER * i);
      this.scrollY = Math.max(Math.min(this.scrollY, this.maxScroll), 0);
    }
    super.handleMouseInput();
  }

}
