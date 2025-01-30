package de.rasmusantons.enigmaticsbingogoals.mixin;

import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public class FoodDataMixin {

    @Shadow
    private int foodLevel;

    @Inject(method = "tick", at = @At("TAIL"))
    public void onTick(ServerPlayer player, CallbackInfo ci) {
        if (foodLevel <= 0.0 && player instanceof ServerPlayer serverPlayer) {

            EnigmaticsBingoGoalsTriggers.EMPTY_HUNGER.get().trigger(serverPlayer);
        }
    }
}