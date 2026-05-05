package de.rasmusantons.enigmaticsbingogoals.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import de.rasmusantons.enigmaticsbingogoals.extension.BingoGameExtension;
import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import io.github.gaming32.bingo.ext.MinecraftServerExt;
import io.github.gaming32.bingo.game.BingoGame;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntityMixin {
    @Inject(method = "damageStatsAndHearts", at = @At(value = "TAIL"))
    private void onAttackLivingEntity(Entity entity, float oldLivingEntityHealth, CallbackInfo ci, @Local(name = "actualDamage") float actualDamage) {
        //noinspection ConstantValue
        if (!(((Object) this) instanceof ServerPlayer serverPlayer))
            return;
        if (entity instanceof ServerPlayer targetPlayer && targetPlayer.getTeam() == serverPlayer.getTeam())
            return;
        final BingoGame activeGame = ((MinecraftServerExt) serverPlayer.level().getServer()).bingo$getGame();
        if (activeGame == null)
            return;
        var totalDamageMap = ((BingoGameExtension) activeGame).enigmatics_bingo_goals$getTotalDamage();
        int totalDamage = totalDamageMap.getOrDefault(serverPlayer.getUUID(), 0) + Math.round(actualDamage * 10.0F);
        totalDamageMap.put(serverPlayer.getUUID(), totalDamage);
        EnigmaticsBingoGoalsTriggers.DAMAGE_EXCEPT_TEAM.get().trigger(serverPlayer, totalDamage);
    }
}
