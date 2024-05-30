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

public class DamageExceptTeamTrigger extends SimpleProgressibleCriterionTrigger<DamageExceptTeamTrigger.TriggerInstance> {
    @NotNull
    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, int totalDamage) {
        final ProgressListener<TriggerInstance> progressListener = getProgressListener(player);
        trigger(player, triggerInstance -> triggerInstance.matches(totalDamage, progressListener));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, MinMaxBounds.Ints targetDamage) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        MinMaxBounds.Ints.CODEC.optionalFieldOf("damage", MinMaxBounds.Ints.ANY).forGetter(TriggerInstance::targetDamage)
                ).apply(instance, TriggerInstance::new)
        );

        public static Criterion<TriggerInstance> dealtDamage(MinMaxBounds.Ints totalDamage) {
            return EnigmaticsBingoGoalsTriggers.DAMAGE_EXCEPT_TEAM.get().createCriterion(
                    new TriggerInstance(Optional.empty(), totalDamage)
            );
        }

        public boolean matches(int totalDamage, ProgressListener<TriggerInstance> progressListener) {
            int targetMin = targetDamage.min().orElse(0);
            progressListener.update(this, Math.min(totalDamage, targetMin), targetMin);
            return targetDamage.matches(totalDamage);
        }
    }
}
