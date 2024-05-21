package de.rasmusantons.enigmaticsbingogoals.conditions;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public enum KillEnemyPlayerCondition implements LootItemCondition {
    INSTANCE;

    public static final MapCodec<KillEnemyPlayerCondition> CODEC = MapCodec.unit(INSTANCE);

    @NotNull
    @Override
    public LootItemConditionType getType() {
        return EnigmaticsBingoGoalsConditions.KILL_ENEMY_PLAYER.get();
    }

    @Override
    public boolean test(LootContext lootContext) {
        if (!(lootContext.getParamOrNull(LootContextParams.THIS_ENTITY) instanceof ServerPlayer killedPlayer))
            return false;
        if (!(killedPlayer.getKillCredit() instanceof ServerPlayer killerPlayer))
            return false;
        return killedPlayer.getTeam() != killerPlayer.getTeam();
    }

    @NotNull
    @Override
    public Set<LootContextParam<?>> getReferencedContextParams() {
        return Set.of(LootContextParams.THIS_ENTITY, LootContextParams.KILLER_ENTITY);
    }
}
