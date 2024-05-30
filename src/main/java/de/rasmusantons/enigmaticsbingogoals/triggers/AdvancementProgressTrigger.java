package de.rasmusantons.enigmaticsbingogoals.triggers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.gaming32.bingo.triggers.progress.SimpleProgressibleCriterionTrigger;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class AdvancementProgressTrigger extends SimpleProgressibleCriterionTrigger<AdvancementProgressTrigger.TriggerInstance> {
    @NotNull
    @Override
    public Codec<AdvancementProgressTrigger.TriggerInstance> codec() {
        return AdvancementProgressTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, ResourceLocation advancement, int count) {
        final ProgressListener<AdvancementProgressTrigger.TriggerInstance> progressListener = getProgressListener(player);
        trigger(player, triggerInstance -> triggerInstance.matches(advancement, count, progressListener));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ResourceLocation> advancement,
                                  MinMaxBounds.Ints targetNumber) implements SimpleInstance {
        public static final Codec<AdvancementProgressTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(AdvancementProgressTrigger.TriggerInstance::player),
                        ResourceLocation.CODEC.optionalFieldOf("advancement").forGetter(AdvancementProgressTrigger.TriggerInstance::advancement),
                        MinMaxBounds.Ints.CODEC.optionalFieldOf("count", MinMaxBounds.Ints.ANY).forGetter(AdvancementProgressTrigger.TriggerInstance::targetNumber)
                ).apply(instance, AdvancementProgressTrigger.TriggerInstance::new)
        );

        public static Criterion<AdvancementProgressTrigger.TriggerInstance> reach(ResourceLocation advancement, MinMaxBounds.Ints count) {
            return EnigmaticsBingoGoalsTriggers.CHECK_ADVANCEMENT_PROGRESS.get().createCriterion(
                    new AdvancementProgressTrigger.TriggerInstance(
                            Optional.empty(),
                            Optional.ofNullable(advancement),
                            count
                    )
            );
        }

        public boolean matches(ResourceLocation advancement, int count, ProgressListener<AdvancementProgressTrigger.TriggerInstance> progressListener) {
            if (this.advancement.isPresent() && !this.advancement.get().equals(advancement))
                return false;

            int min = targetNumber.min().orElse(1);
            if (!targetNumber.matches(count)) {
                progressListener.update(this, count, min);
                return false;
            }
            progressListener.update(this, min, min);
            return true;
        }
    }
}
