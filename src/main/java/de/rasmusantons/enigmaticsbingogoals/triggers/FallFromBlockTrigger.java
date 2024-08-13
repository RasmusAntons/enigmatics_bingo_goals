package de.rasmusantons.enigmaticsbingogoals.triggers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class FallFromBlockTrigger extends SimpleCriterionTrigger<FallFromBlockTrigger.TriggerInstance> {
    @NotNull
    @Override
    public Codec<FallFromBlockTrigger.TriggerInstance> codec() {
        return FallFromBlockTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, BlockPos block, Vec3 origin) {
        trigger(player, triggerInstance -> triggerInstance.matches(player, block, origin));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<BlockPredicate> block,
                                  Optional<DistancePredicate> distance)
            implements SimpleInstance {
        public static final Codec<FallFromBlockTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        BlockPredicate.CODEC.optionalFieldOf("block").forGetter(TriggerInstance::block),
                        DistancePredicate.CODEC.optionalFieldOf("distance").forGetter(TriggerInstance::distance)
                ).apply(instance, FallFromBlockTrigger.TriggerInstance::new)
        );

        public static Criterion<FallFromBlockTrigger.TriggerInstance> fallFromBlock(BlockPredicate block, DistancePredicate distance) {
            return EnigmaticsBingoGoalsTriggers.FALL_FROM_BLOCK.get().createCriterion(
                    new FallFromBlockTrigger.TriggerInstance(Optional.empty(), Optional.of(block), Optional.of(distance))
            );
        }

        public boolean matches(ServerPlayer player, BlockPos block, Vec3 origin) {

            Vec3 currentPos = player.position();
            boolean blockMatch = this.block.isEmpty() || this.block.get().matches(player.serverLevel(), block);
            boolean distanceMatch = this.distance.isEmpty() || this.distance.get().matches(
                    origin.x, origin.y, origin.z, currentPos.x, currentPos.y, currentPos.z);

            return (blockMatch && distanceMatch);
        }
    }
}
