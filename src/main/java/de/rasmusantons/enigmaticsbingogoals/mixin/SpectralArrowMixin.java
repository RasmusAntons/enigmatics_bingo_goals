package de.rasmusantons.enigmaticsbingogoals.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.SpectralArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpectralArrow.class)
public abstract class SpectralArrowMixin extends ProjectileMixin {
    @Inject(method = "doPostHurtEffects", at = @At("TAIL"))
    private void doPostHurtEffects(LivingEntity mob, CallbackInfo ci, @Local(name = "effect") MobEffectInstance effect) {
        if (!(this.getOwner() instanceof ServerPlayer serverPlayer))
            return;
        if (!(mob instanceof ServerPlayer hitPlayer))
            return;
        if (hitPlayer.getTeam() == serverPlayer.getTeam())
            return;
        EnigmaticsBingoGoalsTriggers.GIVE_EFFECT_TO_OTHER_TEAM.get().trigger(serverPlayer, hitPlayer, effect);
    }
}
