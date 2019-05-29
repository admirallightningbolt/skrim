package avi.mod.skrim.skills.brewing;

import avi.mod.skrim.skills.Skill;
import avi.mod.skrim.skills.SkillAbility;
import avi.mod.skrim.skills.SkillStorage;
import avi.mod.skrim.utils.Utils;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Most of the logic for this skill is in other places, see:
 * - {@link SkrimPotionRecipes}
 * - {@link SkrimPotionUtils}
 * - {@link PotionModifier}
 * - {@link avi.mod.skrim.items.items.SkrimPotion}
 * - {@link avi.mod.skrim.tileentity.SkrimBrewingStandEntity}
 */
public class SkillBrewing extends Skill implements ISkillBrewing {

  public static SkillStorage<ISkillBrewing> STORAGE = new SkillStorage<>();

  private static SkillAbility DOUBLE_BUBBLE = new SkillAbility("brewing", "Double Bubble", 25,
      "Fire burn and caldron bubble.",
      "Add an additional effect to potions you create.");

  private static SkillAbility TWO_PLACEHOLDER = new SkillAbility("brewing", "Placeholder", 50,
      "I'm impressed that you made it to level 50 before I made the ability for it.", "No description.");

  private static SkillAbility THREE_PLACEHOLDER = new SkillAbility("brewing", "Placeholder", 75,
      "Okay, what the actual fuck.", "No description.");

  private static SkillAbility CHEMIST = new SkillAbility("brewing", "Chemist", 100,
      "Morrowind levels of broken.",
      "Add any number of additional effects to potions you create.");

  public SkillBrewing() {
    this(1, 0);
  }

  public SkillBrewing(int level, int xp) {
    super("Brewing", level, xp);
    this.addAbilities(DOUBLE_BUBBLE, TWO_PLACEHOLDER, THREE_PLACEHOLDER, CHEMIST);
  }

  @Override
  public List<String> getToolTip() {
    return ImmutableList.of(
        "Potions take §a" + Utils.formatPercent(this.brewSpeed()) + "%§r less time to brew.",
        "You can apply §a" + this.totalModifiers() + "§r levels of modification to your potions.");
  }

  public int totalModifiers() {
    return 2 + this.level / 15;
  }

  public double brewSpeed() {
    return Math.min(this.level * 0.005, 1.00);
  }

}