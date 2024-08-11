package de.rasmusantons.enigmaticsbingogoals.mixin;

import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.LoomMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(targets = "net.minecraft.world.inventory.LoomMenu$6")
public abstract class LoomMenuMixin  {

    @Unique
    private LoomMenu loomMenu;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void LoomMenu(LoomMenu loomMenu, Container container, int i, int j, int k, ContainerLevelAccess containerLevelAccess, CallbackInfo ci) {
        this.loomMenu = loomMenu;
    }

    @Inject(method = "onTake", at = @At("HEAD"))
    private void onTake(Player player, ItemStack stack, CallbackInfo ci) {

        if (loomMenu != null) {
            EnigmaticsBingoGoalsTriggers.USE_LOOM.get().trigger(
                    (ServerPlayer) player,
                    loomMenu.getResultSlot().getItem(),
                    loomMenu.getBannerSlot().getItem(),
                    loomMenu.getDyeSlot().getItem(),
                    loomMenu.getPatternSlot().getItem());
        }
    }
}
