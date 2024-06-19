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
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    @ModifyArg(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;awardStat(Lnet/minecraft/resources/ResourceLocation;I)V"))
    private int onAttackLivingEntity(int damage, @Local(argsOnly = true) Entity target) {
        //noinspection ConstantValue
        if (!(((Object) this) instanceof ServerPlayer serverPlayer))
            return damage;
        if (target instanceof ServerPlayer targetPlayer && targetPlayer.getTeam() == serverPlayer.getTeam())
            return damage;
        if (Bingo.activeGame == null)
            return damage;
        var totalDamageMap = ((BingoGameExtension) Bingo.activeGame).enigmatics_bingo_goals$getTotalDamage();
        int totalDamage = totalDamageMap.getOrDefault(serverPlayer.getUUID(), 0) + damage;
        totalDamageMap.put(serverPlayer.getUUID(), totalDamage);
        EnigmaticsBingoGoalsTriggers.DAMAGE_EXCEPT_TEAM.get().trigger(serverPlayer, totalDamage);
        return damage;
    }
}
