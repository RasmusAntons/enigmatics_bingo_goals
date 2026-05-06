package de.rasmusantons.enigmaticsbingogoals.mixin;

import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.inventory.ArmorSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorSlot.class)
public class ArmorSlotMixin {
    @Shadow
    @Final
    private LivingEntity owner;

    @Inject(method = "setByPlayer", at = @At(value = "TAIL"))
    public void setByPlayer(ItemStack itemStack, ItemStack previous, CallbackInfo ci) {
        if (this.owner instanceof AbstractHorse abstractHorse && abstractHorse.getControllingPassenger() instanceof ServerPlayer serverPlayer) {
            EnigmaticsBingoGoalsTriggers.VEHICLE_INVENTORY_CHANGE.get().trigger(serverPlayer);
        }
    }
}
