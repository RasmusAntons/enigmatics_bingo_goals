package de.rasmusantons.enigmaticsbingogoals.conditions;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jspecify.annotations.NonNull;

import java.util.Set;

public enum PlayerAliveCondition implements LootItemCondition {
    INSTANCE;

    public static final MapCodec<PlayerAliveCondition> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public @NonNull MapCodec<PlayerAliveCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(LootContext lootContext) {
        return lootContext.getOptionalParameter(LootContextParams.THIS_ENTITY) instanceof ServerPlayer serverPlayer
                && serverPlayer.isAlive();
    }

    @Override
    public @NonNull Set<ContextKey<?>> getReferencedContextParams() {
        return Set.of(LootContextParams.THIS_ENTITY);
    }
}
