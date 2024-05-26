package de.rasmusantons.enigmaticsbingogoals.mixin;

import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Projectile.class)
public abstract class ProjectileMixin {
    @Shadow
    public abstract Entity getOwner();

    @Inject(method = "onHitEntity", at = @At("HEAD"))
    private void onHitEntity(EntityHitResult result, CallbackInfo ci) {
        //noinspection ConstantValue
        if (!((Object) this instanceof Snowball)) {
            return;
        }
        if (!(result.getEntity() instanceof ServerPlayer hitPlayer))
            return;
        Entity projectileOwner = this.getOwner();
        if (!(projectileOwner instanceof ServerPlayer serverPlayer))
            return;
        if (hitPlayer.getTeam() == serverPlayer.getTeam())
            return;
        EnigmaticsBingoGoalsTriggers.HIT_OTHER_TEAM_WITH_PROJECTILE.get()
                .trigger(serverPlayer, hitPlayer, (Projectile) (Object) this);
    }
}
