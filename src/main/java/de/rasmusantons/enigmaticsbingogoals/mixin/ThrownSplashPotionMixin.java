package de.rasmusantons.enigmaticsbingogoals.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownSplashPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownSplashPotion.class)
public abstract class ThrownSplashPotionMixin extends ProjectileMixin {
    @Inject(method = "onHitAsPotion", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z"))
    private void onApplySplash(ServerLevel level, ItemStack potionItem, HitResult hitResult, CallbackInfo ci, @Local(name = "entity") LivingEntity entity, @Local(name = "newEffect") MobEffectInstance newEffect) {
        if (!(this.getOwner() instanceof ServerPlayer serverPlayer))
            return;
        if (!(entity instanceof ServerPlayer hitPlayer))
            return;
        if (hitPlayer.getTeam() == serverPlayer.getTeam())
            return;
        EnigmaticsBingoGoalsTriggers.GIVE_EFFECT_TO_OTHER_TEAM.get().trigger(serverPlayer, hitPlayer, newEffect);
    }
}
