package de.rasmusantons.enigmaticsbingogoals.conditions;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.util.HashMap;
import java.util.Map;

public class EnigmaticsBingoGoalsConditions {
    final public static Map<String, MapCodec<? extends LootItemCondition>> conditions = Map.ofEntries(
            Map.entry(FullUniqueInventoryCondition.KEY, FullUniqueInventoryCondition.CODEC)
    );
    final public static Map<String, Holder.Reference<LootItemConditionType>> registeredConditions = new HashMap<>();
}
