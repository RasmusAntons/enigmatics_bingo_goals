package de.rasmusantons.enigmaticsbingogoals.triggers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.ListCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.gaming32.bingo.Bingo;
import io.github.gaming32.bingo.game.BingoGame;
import io.github.gaming32.bingo.triggers.progress.SimpleProgressibleCriterionTrigger;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.stats.StatsCounter;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class KillMobsTrigger extends SimpleProgressibleCriterionTrigger<KillMobsTrigger.TriggerInstance> {
    @NotNull
    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        final ProgressListener<TriggerInstance> progressListener = getProgressListener(player);
        trigger(player, triggerInstance -> triggerInstance.matches(player, progressListener));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, TagKey<EntityType<?>> typeTag,
                                  MinMaxBounds.Ints amount, boolean unique) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        TagKey.codec(Registries.ENTITY_TYPE).fieldOf("types").forGetter(TriggerInstance::typeTag),
                        MinMaxBounds.Ints.CODEC.fieldOf("amount").forGetter(TriggerInstance::amount),
                        ListCodec.BOOL.fieldOf("unique").forGetter(TriggerInstance::unique)
                ).apply(instance, TriggerInstance::new)
        );

        public static Criterion<TriggerInstance> ofTag(@NotNull TagKey<EntityType<?>> typeTag, int amount, boolean unique) {
            return EnigmaticsBingoGoalsTriggers.KILL_MOBS.get().createCriterion(
                    new TriggerInstance(Optional.empty(), typeTag, MinMaxBounds.Ints.atLeast(amount), unique)
            );
        }

        public boolean matches(ServerPlayer player, ProgressListener<TriggerInstance> progressListener) {
            final BingoGame activeGame = player.server.bingo$getGame();
            if (activeGame == null)
                return false;
            final Object2IntMap<Stat<?>> baseStats = activeGame.getBaseStats(player);
            if (baseStats == null)
                return false;
            final StatsCounter currentStats = player.getStats();

            int relevantAmount = 0;
            for (Holder<EntityType<?>> holder : BuiltInRegistries.ENTITY_TYPE.getTagOrEmpty(typeTag)) {
                Stat<EntityType<?>> stat = Stats.ENTITY_KILLED.get(holder.value());
                int value = currentStats.getValue(stat) - baseStats.getInt(stat);
                if (unique && value > 1)
                    value = 1;
                relevantAmount += value;
            }

            progressListener.update(this, relevantAmount, amount.min().orElse(0));
            return amount.matches(relevantAmount);
        }
    }
}
