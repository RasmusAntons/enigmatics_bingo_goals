package de.rasmusantons.enigmaticsbingogoals.triggers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.predicates.ContextAwarePredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Pose;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PoseChangeTrigger extends SimpleCriterionTrigger<PoseChangeTrigger.TriggerInstance> {
    @NotNull
    @Override
    public Codec<PoseChangeTrigger.TriggerInstance> codec() {
        return PoseChangeTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, Pose pose) {
        trigger(player, triggerInstance -> triggerInstance.matches(pose));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<Pose> pose) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        Pose.CODEC.optionalFieldOf("pose").forGetter(TriggerInstance::pose)
                ).apply(instance, TriggerInstance::new)
        );

        public static Criterion<TriggerInstance> crouch() {
            return EnigmaticsBingoGoalsTriggers.POSE_CHANGE.get().createCriterion(
                    new TriggerInstance(Optional.empty(), Optional.of(Pose.CROUCHING))
            );
        }

        public boolean matches(Pose pose) {
            return this.pose.isEmpty() || this.pose.get() == pose;
        }
    }
}
