package de.rasmusantons.enigmaticsbingogoals.mixin;

import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends PlayerMixin {
    @Unique
    private int carvedPumpkinTimer = 0;

    @Inject(method = "tick", at = @At("TAIL"))
    private void doTick(CallbackInfo ci) {
        ItemStack itemStack = getItemBySlot(EquipmentSlot.HEAD);
        if (!itemStack.isEmpty() && itemStack.getItem() == Items.CARVED_PUMPKIN) {
            if (++carvedPumpkinTimer % 20 == 0)
                EnigmaticsBingoGoalsTriggers.WEAR_PUMPKIN.get().trigger((ServerPlayer) (Object) this, carvedPumpkinTimer / 20);
        } else {
            if (carvedPumpkinTimer > 0)
                EnigmaticsBingoGoalsTriggers.WEAR_PUMPKIN.get().trigger((ServerPlayer) (Object) this, 0);
            carvedPumpkinTimer = 0;
        }
    }

    @Unique
    private BlockPos blockPos;

    @Unique
    private BlockPos lastBlockPos;

    @Shadow
    private Vec3 startingToFallPosition;

    @Inject(method = "onChangedBlock", at = @At("HEAD"))
    protected void onChangedBlock(ServerLevel level, BlockPos pos, CallbackInfo ci) {

        if (level.getBlockState(pos).getBlock() != Blocks.AIR) {

            if (blockPos != null) {
                boolean isSolid = !level.getBlockState(blockPos).getCollisionShape(level, pos).isEmpty();
                boolean isClimbable = level.getBlockState(blockPos).is(BlockTags.CLIMBABLE);

                if (isSolid || isClimbable) {
                    lastBlockPos = blockPos;
                }
            }
            blockPos = pos;
        };
    }

    @Inject(method = "resetFallDistance", at = @At("HEAD"))
    private void resetFallDistance(CallbackInfo ci) {

        if (startingToFallPosition != null && lastBlockPos != null) {
            EnigmaticsBingoGoalsTriggers.FALL_FROM_BLOCK.get().trigger((ServerPlayer) (Object) this, this.lastBlockPos, startingToFallPosition);
        }
    }
}
