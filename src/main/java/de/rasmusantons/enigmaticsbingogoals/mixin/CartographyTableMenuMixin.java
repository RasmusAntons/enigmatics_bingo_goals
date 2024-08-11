package de.rasmusantons.enigmaticsbingogoals.mixin;

import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CartographyTableMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.LoomMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(targets = "net.minecraft.world.inventory.CartographyTableMenu$5")
public abstract class CartographyTableMenuMixin  {

    @Unique
    private CartographyTableMenu cartographyTableMenu;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(CartographyTableMenu cartographyTableMenu, Container container, int i, int j, int k, ContainerLevelAccess containerLevelAccess, CallbackInfo ci) {
        this.cartographyTableMenu = cartographyTableMenu;
    }

    @Inject(method = "onTake", at = @At("HEAD"))
    private void onTake(Player player, ItemStack stack, CallbackInfo ci) {

        if (cartographyTableMenu != null) {

            ItemStack mapStack = cartographyTableMenu.container.getItem(0);
            ItemStack modStack = cartographyTableMenu.container.getItem(1);

            EnigmaticsBingoGoalsTriggers.USE_CARTOGRAPHY_TABLE.get().trigger(
                    (ServerPlayer) player,
                    stack,
                    mapStack,
                    modStack
            );
        }
    }
}
