package de.rasmusantons.enigmaticsbingogoals.triggers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import io.github.gaming32.bingo.triggers.progress.SimpleProgressibleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class WearPumpkinTrigger extends SimpleProgressibleCriterionTrigger<WearPumpkinTrigger.TriggerInstance> {
    @NotNull
    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, int seconds) {
        final ProgressListener<TriggerInstance> progressListener = getProgressListener(player);
        trigger(player, triggerInstance -> triggerInstance.matches(seconds, progressListener));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, MinMaxBounds.Ints targetSeconds) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        MinMaxBounds.Ints.CODEC.optionalFieldOf("seconds", MinMaxBounds.Ints.ANY).forGetter(TriggerInstance::targetSeconds)
                ).apply(instance, TriggerInstance::new)
        );

        public static Criterion<TriggerInstance> wearPumpkin(MinMaxBounds.Ints seconds) {
            return EnigmaticsBingoGoalsTriggers.WEAR_PUMPKIN.get().createCriterion(
                    new TriggerInstance(Optional.empty(), seconds)
            );
        }

        public boolean matches(int seconds, ProgressListener<TriggerInstance> progressListener) {
            progressListener.update(this, seconds, targetSeconds.min().orElse(0));
            return targetSeconds.matches(seconds);
        }
    }
}
