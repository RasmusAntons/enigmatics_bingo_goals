package de.rasmusantons.enigmaticsbingogoals.triggers;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class EnigmaticsBingoGoalsTriggers {
    final public static Map<String, Supplier<? extends SimpleCriterionTrigger<? extends SimpleCriterionTrigger.SimpleInstance>>> triggers = Map.ofEntries(
            Map.entry(WearPumpkinTrigger.KEY, WearPumpkinTrigger::new),
            Map.entry(PlayMusicToOtherTeamTrigger.KEY, PlayMusicToOtherTeamTrigger::new)
    );
    final public static Map<String, CriterionTrigger<? extends CriterionTriggerInstance>> registeredTriggers = new HashMap<>();
}
