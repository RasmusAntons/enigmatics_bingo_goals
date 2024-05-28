package de.rasmusantons.enigmaticsbingogoals.mixin;

import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractHorse.class)
public abstract class AbstractHorseMixin {
    @Shadow
    @Nullable
    public abstract LivingEntity getControllingPassenger();

    @Inject(method = "containerChanged", at = @At("TAIL"))
    private void onContainerChanged(CallbackInfo ci) {
        if ((this.getControllingPassenger() instanceof ServerPlayer serverPlayer))
            EnigmaticsBingoGoalsTriggers.VEHICLE_INVENTORY_CHANGE.get().trigger(serverPlayer);
    }
}
