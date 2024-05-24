package de.rasmusantons.enigmaticsbingogoals.conditions;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public enum PlayerAliveCondition implements LootItemCondition {
    INSTANCE;

    public static final MapCodec<PlayerAliveCondition> CODEC = MapCodec.unit(INSTANCE);

    @NotNull
    @Override
    public LootItemConditionType getType() {
        return EnigmaticsBingoGoalsConditions.PLAYER_ALIVE.get();
    }

    @Override
    public boolean test(LootContext lootContext) {
        return lootContext.getParamOrNull(LootContextParams.THIS_ENTITY) instanceof ServerPlayer serverPlayer
                && serverPlayer.isAlive();
    }

    @NotNull
    @Override
    public Set<LootContextParam<?>> getReferencedContextParams() {
        return Set.of(LootContextParams.THIS_ENTITY);
    }
}
