package de.rasmusantons.enigmaticsbingogoals.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownPotion;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownPotion.class)
public abstract class ThrownPotionMixin extends ProjectileMixin {
    @Inject(method = "applySplash", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z"))
    private void onApplySplash(ServerLevel level, Iterable<MobEffectInstance> effects, @Nullable Entity entity, CallbackInfo ci, @Local LivingEntity hitEntity, @Local(ordinal = 1) MobEffectInstance effect) {
        if (!(this.getOwner() instanceof ServerPlayer serverPlayer))
            return;
        if (!(hitEntity instanceof ServerPlayer hitPlayer))
            return;
        if (hitPlayer.getTeam() == serverPlayer.getTeam())
            return;
        EnigmaticsBingoGoalsTriggers.GIVE_EFFECT_TO_OTHER_TEAM.get().trigger(serverPlayer, hitPlayer, effect);
    }
}
