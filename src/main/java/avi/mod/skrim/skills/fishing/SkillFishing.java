package avi.mod.skrim.skills.fishing;

import java.util.ArrayList;
import java.util.List;

import avi.mod.skrim.skills.Skill;
import avi.mod.skrim.skills.SkillAbility;
import avi.mod.skrim.skills.SkillStorage;
import avi.mod.skrim.utils.Utils;
import net.minecraft.util.ResourceLocation;

public class SkillFishing extends Skill implements ISkillFishing {

	public static SkillStorage<ISkillFishing> skillStorage = new SkillStorage<ISkillFishing>();

	public static SkillAbility BATMAN = new SkillAbility("fishing", "Batman", 25, "na na na na na na na na",
			"Your fishing rod can now be used as a grappling hook.");

	public static SkillAbility TRIPLE_HOOK = new SkillAbility("fishing", "Triple Hook", 50, "Triple the hooks, triple the pleasure.",
			"You now catch §a3x" + SkillAbility.descColor + " as many items.");

	public static SkillAbility BOUNTIFUL_CATCH = new SkillAbility("fishing", "Bountiful Catch", 75, "On that E-X-P grind.",
			"Catching a fish provides an additional§a 9-24" + SkillAbility.descColor + " xp.");

	public static SkillAbility FLING = new SkillAbility("fishing", "Fling", 100, "Sometimes I don't know my own strength.",
			"Launch hooked entities into the air.");

	public SkillFishing() {
		this(1, 0);
	}

	public SkillFishing(int level, int currentXp) {
		super("Fishing", level, currentXp);
		this.addAbilities(BATMAN, TRIPLE_HOOK, BOUNTIFUL_CATCH, FLING);
	}

	public double getTreasureChance() {
		return 0.01 * this.level;
	}

	public double getDelayReduction() {
		return 0.0075 * this.level;
	}

	@Override
	public List<String> getToolTip() {
		List<String> tooltip = new ArrayList<String>();
		tooltip.add("§a" + Utils.formatPercentTwo(this.getDelayReduction()) + "%§r reduced fishing time.");
		tooltip.add("§a" + Utils.formatPercent(this.getTreasureChance()) + "%§r chance to fish additional treasure.");
		return tooltip;
	}

}
