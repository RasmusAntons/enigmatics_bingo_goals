package de.rasmusantons.enigmaticsbingogoals.triggers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;

public class GiveEffectToOtherTeamTrigger extends SimpleCriterionTrigger<GiveEffectToOtherTeamTrigger.TriggerInstance> {
    @NotNull
    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, ServerPlayer target, MobEffectInstance effect) {
        trigger(player, triggerInstance -> triggerInstance.matches(player, target, effect));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<EntityPredicate> target,
                                  Optional<MobEffectsPredicate> effect) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        EntityPredicate.CODEC.optionalFieldOf("target").forGetter(TriggerInstance::target),
                        MobEffectsPredicate.CODEC.optionalFieldOf("effect").forGetter(TriggerInstance::effect)
                ).apply(instance, TriggerInstance::new)
        );

        public static Criterion<TriggerInstance> effect(Holder<MobEffect> effect) {
            return EnigmaticsBingoGoalsTriggers.GIVE_EFFECT_TO_OTHER_TEAM.get().createCriterion(
                    new TriggerInstance(
                            Optional.empty(),
                            Optional.empty(),
                            MobEffectsPredicate.Builder.effects().and(effect).build()
                    )
            );
        }

        public static Criterion<TriggerInstance> anyEffect() {
            return EnigmaticsBingoGoalsTriggers.GIVE_EFFECT_TO_OTHER_TEAM.get().createCriterion(
                    new TriggerInstance(Optional.empty(), Optional.empty(), Optional.empty()));
        }

        public boolean matches(ServerPlayer player, ServerPlayer target, MobEffectInstance effect) {
            if (this.target.isPresent() && !this.target.get().matches(player, target))
                return false;
            return this.effect.isEmpty() || this.effect.get().matches(Map.of(effect.getEffect(), effect));
        }
    }
}