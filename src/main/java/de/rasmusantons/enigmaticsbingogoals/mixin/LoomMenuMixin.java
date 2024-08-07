package de.rasmusantons.enigmaticsbingogoals.mixin;

import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.world.inventory.LoomMenu$6")
public abstract class LoomMenuMixin {

    @Inject(method = "onTake", at = @At("HEAD"))
    private void onTake(Player player, ItemStack stack, CallbackInfo ci) {
        EnigmaticsBingoGoalsTriggers.APPLY_PATTERN.get().trigger((ServerPlayer) player, stack);
    }
}
