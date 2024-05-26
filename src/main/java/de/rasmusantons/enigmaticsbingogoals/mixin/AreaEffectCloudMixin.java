package de.rasmusantons.enigmaticsbingogoals.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AreaEffectCloud.class)
public class AreaEffectCloudMixin {
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z"))
    private void onTick(CallbackInfo ci, @Local LivingEntity hitEntity, @Local MobEffectInstance effect) {
        if (!(((TraceableEntity) this).getOwner() instanceof ServerPlayer serverPlayer))
            return;
        if (!(hitEntity instanceof ServerPlayer hitPlayer))
            return;
        if (hitPlayer.getTeam() == serverPlayer.getTeam())
            return;
        EnigmaticsBingoGoalsTriggers.GIVE_EFFECT_TO_OTHER_TEAM.get().trigger(serverPlayer, hitPlayer, effect);
    }
}
