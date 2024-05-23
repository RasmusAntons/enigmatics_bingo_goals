package de.rasmusantons.enigmaticsbingogoals.triggers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class EntityDirectlyKilledPlayerTrigger extends SimpleCriterionTrigger<EntityDirectlyKilledPlayerTrigger.TriggerInstance> {
    @NotNull
    @Override
    public Codec<EntityDirectlyKilledPlayerTrigger.TriggerInstance> codec() {
        return EntityDirectlyKilledPlayerTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, EntityType<?> entityType) {
        trigger(player, triggerInstance -> triggerInstance.matches(entityType));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<EntityTypePredicate> entity) implements SimpleInstance {
        public static final Codec<EntityDirectlyKilledPlayerTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(EntityDirectlyKilledPlayerTrigger.TriggerInstance::player),
                        EntityTypePredicate.CODEC.optionalFieldOf("entity").forGetter(EntityDirectlyKilledPlayerTrigger.TriggerInstance::entity)
                ).apply(instance, EntityDirectlyKilledPlayerTrigger.TriggerInstance::new)
        );

        public static Criterion<EntityDirectlyKilledPlayerTrigger.TriggerInstance> entityType(EntityType<?> entityType) {
            return EnigmaticsBingoGoalsTriggers.ENTITY_DIRECTLY_KILLED_PLAYER.get().createCriterion(
                    new EntityDirectlyKilledPlayerTrigger.TriggerInstance(Optional.empty(), Optional.of(EntityTypePredicate.of(entityType)))
            );
        }

        public boolean matches(EntityType<?> entityType) {
            return entity.isEmpty() || entity.get().matches(entityType);
        }
    }
}
