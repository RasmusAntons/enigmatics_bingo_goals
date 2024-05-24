package de.rasmusantons.enigmaticsbingogoals.conditions;

import com.mojang.serialization.MapCodec;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public record NumberOfEffectsCondition(MinMaxBounds.Ints effects) implements LootItemCondition {
    public static final MapCodec<NumberOfEffectsCondition> CODEC = MinMaxBounds.Ints.CODEC
            .fieldOf("effects")
            .xmap(NumberOfEffectsCondition::new, NumberOfEffectsCondition::effects);

    @NotNull
    @Override
    public LootItemConditionType getType() {
        return EnigmaticsBingoGoalsConditions.NUMBER_OF_EFFECTS.get();
    }

    @Override
    public boolean test(LootContext lootContext) {
        if (!(lootContext.getParamOrNull(LootContextParams.THIS_ENTITY) instanceof ServerPlayer serverPlayer))
            return false;
        return effects.matches(serverPlayer.getActiveEffects().size());
    }

    @NotNull
    @Override
    public Set<LootContextParam<?>> getReferencedContextParams() {
        return Set.of(LootContextParams.THIS_ENTITY);
    }
}
