package de.rasmusantons.enigmaticsbingogoals.triggers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.resources.ResourceLocation;
import io.github.gaming32.bingo.triggers.progress.SimpleProgressibleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.Resource;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class AdvancementsTrigger extends SimpleProgressibleCriterionTrigger<AdvancementsTrigger.TriggerInstance> {
    @NotNull
    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, ResourceLocation advancement, int number) {
        final ProgressListener<TriggerInstance> progressListener = getProgressListener(player);
        trigger(player, triggerInstance -> triggerInstance.matches(advancement, number, progressListener));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ResourceLocation> advancement, MinMaxBounds.Ints targetNumber) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        ResourceLocation.CODEC.optionalFieldOf("advancement").forGetter(TriggerInstance::advancement),
                        MinMaxBounds.Ints.CODEC.optionalFieldOf("number", MinMaxBounds.Ints.ANY).forGetter(TriggerInstance::targetNumber)
                ).apply(instance, TriggerInstance::new)
        );

        public static Criterion<TriggerInstance> advancements(MinMaxBounds.Ints number) {
            return EnigmaticsBingoGoalsTriggers.ADVANCEMENTS.get().createCriterion(
                    new TriggerInstance(Optional.empty(), Optional.empty(), number)
            );
        }

        public static Criterion<TriggerInstance> advancement(ResourceLocation advancement) {
            return EnigmaticsBingoGoalsTriggers.ADVANCEMENTS.get().createCriterion(
                    new TriggerInstance(Optional.empty(), Optional.of(advancement), MinMaxBounds.Ints.ANY)
            );
        }

        public boolean matches(ResourceLocation advancement, int number, ProgressListener<TriggerInstance> progressListener) {
            progressListener.update(this, number, targetNumber.min().orElse(1));
            if(!targetNumber.matches(number))
                return false;
            return this.advancement.isEmpty() || this.advancement.equals(advancement);
        }
    }
}
