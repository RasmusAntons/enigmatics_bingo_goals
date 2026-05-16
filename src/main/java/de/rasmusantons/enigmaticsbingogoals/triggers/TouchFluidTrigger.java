package de.rasmusantons.enigmaticsbingogoals.triggers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.FluidPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class TouchFluidTrigger extends SimpleCriterionTrigger<TouchFluidTrigger.TriggerInstance> {
    @NotNull
    @Override
    public Codec<TouchFluidTrigger.TriggerInstance> codec() {
        return TouchFluidTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, ServerLevel level, BlockPos pos) {
        trigger(player, triggerInstance -> triggerInstance.matches(level, pos));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<FluidPredicate> fluid) implements SimpleInstance {
        public static final Codec<TouchFluidTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TouchFluidTrigger.TriggerInstance::player),
                        FluidPredicate.CODEC.optionalFieldOf("fluid").forGetter(TouchFluidTrigger.TriggerInstance::fluid)
                ).apply(instance, TouchFluidTrigger.TriggerInstance::new)
        );

        public static Criterion<TouchFluidTrigger.TriggerInstance> water() {
            return EnigmaticsBingoGoalsTriggers.TOUCH_FLUID.get().createCriterion(
                    new TouchFluidTrigger.TriggerInstance(
                            Optional.empty(),
                            Optional.of(FluidPredicate.Builder.fluid().of(Fluids.WATER).build())
                    )
            );
        }

        public static Criterion<TouchFluidTrigger.TriggerInstance> flowingWater() {
            return EnigmaticsBingoGoalsTriggers.TOUCH_FLUID.get().createCriterion(
                    new TouchFluidTrigger.TriggerInstance(
                            Optional.empty(),
                            Optional.of(FluidPredicate.Builder.fluid().of(Fluids.FLOWING_WATER).build())
                    )
            );
        }

        public static Criterion<TouchFluidTrigger.TriggerInstance> lava() {
            return EnigmaticsBingoGoalsTriggers.TOUCH_FLUID.get().createCriterion(
                    new TouchFluidTrigger.TriggerInstance(
                            Optional.empty(),
                            Optional.of(FluidPredicate.Builder.fluid().of(Fluids.LAVA).build())
                    )
            );
        }

        public static Criterion<TouchFluidTrigger.TriggerInstance> flowingLava() {
            return EnigmaticsBingoGoalsTriggers.TOUCH_FLUID.get().createCriterion(
                    new TouchFluidTrigger.TriggerInstance(
                            Optional.empty(),
                            Optional.of(FluidPredicate.Builder.fluid().of(Fluids.FLOWING_LAVA).build())
                    )
            );
        }

        public boolean matches(ServerLevel level, BlockPos pos) {
            return this.fluid.isEmpty() || this.fluid.get().matches(level, pos);
        }
    }
}
