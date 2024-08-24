package de.rasmusantons.enigmaticsbingogoals.mixin;

import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorStand.class)
public class ArmorStandMixin {


    @Shadow 
    @Final 
    private NonNullList<ItemStack> armorItems;

    @Inject(method = "swapItem", at = @At(value = "RETURN"))
    protected void swapItem(Player player, EquipmentSlot slot, ItemStack stack, InteractionHand hand, CallbackInfoReturnable<Boolean> cir) {

        if (cir.getReturnValue()) {
            EnigmaticsBingoGoalsTriggers.SWAP_ARMOR_STAND_ITEM.get().trigger(
                    (ServerPlayer) player,
                    armorItems.get(3),
                    armorItems.get(2),
                    armorItems.get(1),
                    armorItems.getFirst()
            );
        }
    }
}
