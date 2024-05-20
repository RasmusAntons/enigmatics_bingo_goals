package de.rasmusantons.enigmaticsbingogoals;

import de.rasmusantons.enigmaticsbingogoals.conditions.EnigmaticsBingoGoalsConditions;
import de.rasmusantons.enigmaticsbingogoals.conditions.FullUniqueInventoryCondition;
import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class EnigmaticsBingoGoals implements ModInitializer {
    public static final String MOD_ID = "enigmaticsbingogoals";

    @Override
    public void onInitialize() {
        EnigmaticsBingoGoalsConditions.conditions.forEach((key, condition) -> {
            ResourceLocation resourceLocation = new ResourceLocation(EnigmaticsBingoGoals.MOD_ID, key);
            EnigmaticsBingoGoalsConditions.registeredConditions.put(key, Registry.registerForHolder(
                    BuiltInRegistries.LOOT_CONDITION_TYPE,
                    resourceLocation,
                    new LootItemConditionType(FullUniqueInventoryCondition.CODEC)
            ));
        });
        EnigmaticsBingoGoalsTriggers.triggers.forEach((key, trigger) -> {
            ResourceLocation resourceLocation = new ResourceLocation(EnigmaticsBingoGoals.MOD_ID, key);
            EnigmaticsBingoGoalsTriggers.registeredTriggers.put(key, Registry.registerForHolder(
                    BuiltInRegistries.TRIGGER_TYPES,
                    resourceLocation,
                    trigger.get()
            ).value());
        });
    }
}
