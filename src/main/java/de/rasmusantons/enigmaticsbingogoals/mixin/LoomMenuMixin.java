package de.rasmusantons.enigmaticsbingogoals.mixin;

import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.LoomMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;


@Mixin(targets = "net.minecraft.world.inventory.LoomMenu$6")
public abstract class LoomMenuMixin  {

    @Inject(method = "onTake", at = @At("HEAD"))
    private void onTake(Player player, ItemStack stack, CallbackInfo ci) throws NoSuchFieldException, IllegalAccessException {

        LoomMenu loomMenu = null;

        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.getType() == LoomMenu.class) {
                field.setAccessible(true);
                loomMenu = (LoomMenu) field.get(this);
            }
        }

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
