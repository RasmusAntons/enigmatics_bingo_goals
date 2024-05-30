package de.rasmusantons.enigmaticsbingogoals.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import de.rasmusantons.enigmaticsbingogoals.extension.BingoGameExtension;
import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import io.github.gaming32.bingo.Bingo;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;awardStat(Lnet/minecraft/resources/ResourceLocation;I)V"))
    private void onAttackLivingEntity(Entity target, CallbackInfo ci, @Local(ordinal = 4) float m) {
        //noinspection ConstantValue
        if (!(((Object) this) instanceof ServerPlayer serverPlayer))
            return;
        if (target instanceof ServerPlayer targetPlayer && targetPlayer.getTeam() == serverPlayer.getTeam())
            return;
        if (Bingo.activeGame == null)
            return;
        var totalDamageMap = ((BingoGameExtension) Bingo.activeGame).enigmatics_bingo_goals$getTotalDamage();
        int totalDamage = totalDamageMap.getOrDefault(serverPlayer.getUUID(), 0) + Math.round(m * 10F);
        totalDamageMap.put(serverPlayer.getUUID(), totalDamage);
        EnigmaticsBingoGoalsTriggers.DAMAGE_EXCEPT_TEAM.get().trigger(serverPlayer, totalDamage);
    }
}
