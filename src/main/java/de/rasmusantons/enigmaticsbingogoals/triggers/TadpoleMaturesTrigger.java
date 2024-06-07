package de.rasmusantons.enigmaticsbingogoals.triggers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.frog.Frog;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class TadpoleMaturesTrigger extends SimpleCriterionTrigger<TadpoleMaturesTrigger.TriggerInstance> {
    @NotNull
    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, Frog frog) {
        trigger(player, triggerInstance -> triggerInstance.matches(frog));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ResourceKey<FrogVariant>> variant) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        ResourceKey.codec(Registries.FROG_VARIANT).optionalFieldOf("variant").forGetter(TriggerInstance::variant)
                ).apply(instance, TriggerInstance::new)
        );

        public static Criterion<TriggerInstance> ofVariant(ResourceKey<FrogVariant> variant) {
            return EnigmaticsBingoGoalsTriggers.TADPOLE_MATURES.get().createCriterion(
                    new TadpoleMaturesTrigger.TriggerInstance(Optional.empty(), Optional.of(variant))
            );
        }

        public boolean matches(Frog frog) {
            return variant.isEmpty() || frog.getVariant().is(variant.get());
        }
    }
}
