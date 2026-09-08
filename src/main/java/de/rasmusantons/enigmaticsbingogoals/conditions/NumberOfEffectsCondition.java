package de.rasmusantons.enigmaticsbingogoals.conditions;

import com.mojang.serialization.MapCodec;
import net.minecraft.advancements.predicates.MinMaxBounds;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jspecify.annotations.NonNull;

import java.util.Set;

public record NumberOfEffectsCondition(MinMaxBounds.Ints effects) implements LootItemCondition {
    public static final MapCodec<NumberOfEffectsCondition> CODEC = MinMaxBounds.Ints.CODEC
            .fieldOf("effects")
            .xmap(NumberOfEffectsCondition::new, NumberOfEffectsCondition::effects);

    @Override
    public @NonNull MapCodec<NumberOfEffectsCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(LootContext lootContext) {
        if (!(lootContext.getOptionalParameter(LootContextParams.THIS_ENTITY) instanceof ServerPlayer serverPlayer))
            return false;
        return effects.matches(serverPlayer.getActiveEffects().size());
    }

    @Override
    public @NonNull Set<ContextKey<?>> getReferencedContextParams() {
        return Set.of(LootContextParams.THIS_ENTITY);
    }
}
