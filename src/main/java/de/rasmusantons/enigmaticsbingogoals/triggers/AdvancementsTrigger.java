package de.rasmusantons.enigmaticsbingogoals.triggers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import io.github.gaming32.bingo.triggers.progress.SimpleProgressibleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class AdvancementsTrigger extends SimpleProgressibleCriterionTrigger<AdvancementsTrigger.TriggerInstance> {
    @NotNull
    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, int number) {
        final ProgressListener<TriggerInstance> progressListener = getProgressListener(player);
        trigger(player, triggerInstance -> triggerInstance.matches(number, progressListener));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, MinMaxBounds.Ints targetNumber) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        MinMaxBounds.Ints.CODEC.optionalFieldOf("number", MinMaxBounds.Ints.ANY).forGetter(TriggerInstance::targetNumber)
                ).apply(instance, TriggerInstance::new)
        );

        public static Criterion<TriggerInstance> advancements(MinMaxBounds.Ints number) {
            return EnigmaticsBingoGoalsTriggers.ADVANCEMENTS.get().createCriterion(
                    new TriggerInstance(Optional.empty(), number)
            );
        }

        public boolean matches(int number, ProgressListener<TriggerInstance> progressListener) {
            progressListener.update(this, number, targetNumber.min().orElse(0));
            return targetNumber.matches(number);
        }
    }
}
