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
    @Inject(method = "method_63651", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/frog/Frog;fudgePositionAfterSizeChange(Lnet/minecraft/world/entity/EntityDimensions;)Z"))
    public void multiply(CallbackInfo ci, @Local(argsOnly = true) ServerLevel level, @Local(argsOnly = true) Frog frog) {
        Player player = level.getNearestPlayer(frog.getX(), frog.getY(), frog.getZ(), 528, false);
        if (!(player instanceof ServerPlayer serverPlayer))
            return;
        EnigmaticsBingoGoalsTriggers.TADPOLE_MATURES.get().trigger(serverPlayer, frog);
    }
}
