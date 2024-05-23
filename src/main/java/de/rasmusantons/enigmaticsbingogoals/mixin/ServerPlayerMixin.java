package de.rasmusantons.enigmaticsbingogoals.mixin;

import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends PlayerMixin {
    @Unique
    private int carvedPumpkinTimer = 0;

    @Inject(method = "tick", at = @At("TAIL"))
    private void doTick(CallbackInfo ci) {
        ItemStack itemStack = getItemBySlot(EquipmentSlot.HEAD);
        if (!itemStack.isEmpty() && itemStack.getItem() == Items.CARVED_PUMPKIN) {
            if (++carvedPumpkinTimer % 20 == 0)
                EnigmaticsBingoGoalsTriggers.WEAR_PUMPKIN.get().trigger((ServerPlayer) (Object) this, carvedPumpkinTimer / 20);
        } else {
            if (carvedPumpkinTimer > 0)
                EnigmaticsBingoGoalsTriggers.WEAR_PUMPKIN.get().trigger((ServerPlayer) (Object) this, 0);
            carvedPumpkinTimer = 0;
        }
    }

    @Inject(method="die",  at = @At("HEAD"))
    public void onDie(DamageSource damageSource, CallbackInfo ci) {

        if (damageSource.getEntity() != null) {
            EnigmaticsBingoGoalsTriggers.ENTITY_DIRECTLY_KILLED_PLAYER.get().trigger(
                    (ServerPlayer) (Object) this, damageSource.getEntity().getType()
            );
        }
    }
}
