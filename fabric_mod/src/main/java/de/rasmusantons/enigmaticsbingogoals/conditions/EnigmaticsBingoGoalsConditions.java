package de.rasmusantons.enigmaticsbingogoals.conditions;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.Map;

public class EnigmaticsBingoGoalsConditions {
    final public static Map<String, MapCodec<? extends LootItemCondition>> conditions = Map.ofEntries(
            Map.entry("full_unique_inventory", FullUniqueInventoryCondition.CODEC)
    );
}
