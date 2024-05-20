package de.rasmusantons.enigmaticsbingogoals.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import de.rasmusantons.enigmaticsbingogoals.extension.JukeboxBlockEntityExtension;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(RecordItem.class)
public class RecordItemMixin {
    @Inject(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/JukeboxBlockEntity;setTheItem(Lnet/minecraft/world/item/ItemStack;)V"))
    public void onUseOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir, @Local Player player, @Local JukeboxBlockEntity jukeboxBlockEntity) {
        if (player instanceof ServerPlayer serverPlayer) {
            ((JukeboxBlockEntityExtension) jukeboxBlockEntity).enigmaticsbingogoals$setLastPlayed(serverPlayer);
        }
    }
}
