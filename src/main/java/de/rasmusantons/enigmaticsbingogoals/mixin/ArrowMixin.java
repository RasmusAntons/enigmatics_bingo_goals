package de.rasmusantons.enigmaticsbingogoals.mixin;

import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Arrow.class)
public abstract class ArrowMixin extends ProjectileMixin {
    @Inject(method = "lambda$doPostHurtEffects$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z"))
    private static void doPostHurtEffects(LivingEntity mob, Entity effectSource, MobEffectInstance effect, CallbackInfo ci) {
        if (!(effectSource instanceof ServerPlayer serverPlayer))
            return;
        if (!(mob instanceof ServerPlayer hitPlayer))
            return;
        if (hitPlayer.getTeam() == serverPlayer.getTeam())
            return;
        EnigmaticsBingoGoalsTriggers.GIVE_EFFECT_TO_OTHER_TEAM.get().trigger(serverPlayer, hitPlayer, effect);
    }
}
