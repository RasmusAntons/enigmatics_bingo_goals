package de.rasmusantons.enigmaticsbingogoals.triggers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class HitOtherTeamWithProjectileTrigger extends SimpleCriterionTrigger<HitOtherTeamWithProjectileTrigger.TriggerInstance> {
    @NotNull
    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, ServerPlayer target, Projectile projectile) {
        trigger(player, triggerInstance -> triggerInstance.matches(player, target, projectile));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<EntityPredicate> target,
                                  Optional<EntityPredicate> projectile) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        EntityPredicate.CODEC.optionalFieldOf("target").forGetter(TriggerInstance::target),
                        EntityPredicate.CODEC.optionalFieldOf("projectile").forGetter(TriggerInstance::projectile)
                ).apply(instance, TriggerInstance::new)
        );

        public static Criterion<TriggerInstance> ofType(EntityType<? extends Projectile> entityType) {
            return EnigmaticsBingoGoalsTriggers.HIT_OTHER_TEAM_WITH_PROJECTILE.get().createCriterion(
                    new TriggerInstance(
                            Optional.empty(),
                            Optional.empty(),
                            Optional.of(EntityPredicate.Builder.entity().of(entityType).build())
                    )
            );
        }

        public boolean matches(ServerPlayer player, ServerPlayer target, Projectile projectile) {
            if (this.target.isPresent() && !this.target.get().matches(player, target))
                return false;
            return this.projectile.isEmpty() || this.projectile.get().matches(player, projectile);
        }
    }
}