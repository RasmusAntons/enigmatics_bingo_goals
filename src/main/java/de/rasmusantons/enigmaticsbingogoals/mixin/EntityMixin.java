package de.rasmusantons.enigmaticsbingogoals.mixin;

import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.atomic.AtomicInteger;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "lambda$checkInsideBlocks$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;entityInside(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/InsideBlockEffectApplier;)V"))
    private void onInsideFluid(int maxMovementIterations, AtomicInteger iterations, boolean debugEntityBlockIntersections, Vec3 from, Vec3 _to, LongSet visitedBlocks, boolean movedFar, AABB deflatedBoundingBoxAtTarget, InsideBlockEffectApplier.StepBasedCollector effectCollector, BlockPos blockIntersection, int iteration, CallbackInfoReturnable<Boolean> cir) {
        if (((Object) this) instanceof ServerPlayer player) {
            EnigmaticsBingoGoalsTriggers.TOUCH_FLUID.get().trigger(player, player.level(), blockIntersection);
        }
    }
}
