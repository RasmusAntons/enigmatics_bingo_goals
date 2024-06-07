package de.rasmusantons.enigmaticsbingogoals.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.frog.Tadpole;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Tadpole.class)
public class TadpoleMixin {
    @Inject(method = "ageUp()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntityWithPassengers(Lnet/minecraft/world/entity/Entity;)V"))
    public void ageUp(CallbackInfo ci, @Local ServerLevel level, @Local Frog frog) {
        Player player = level.getNearestPlayer(frog.getX(), frog.getY(), frog.getZ(), 528, false);
        if (!(player instanceof ServerPlayer serverPlayer))
            return;
        EnigmaticsBingoGoalsTriggers.TADPOLE_MATURES.get().trigger(serverPlayer, frog);
    }
}
