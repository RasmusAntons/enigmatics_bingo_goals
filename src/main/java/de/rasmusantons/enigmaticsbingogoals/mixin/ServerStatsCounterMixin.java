package de.rasmusantons.enigmaticsbingogoals.mixin;

import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stat;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerStatsCounter.class)
public class ServerStatsCounterMixin {
    @Inject(method = "setValue", at = @At("RETURN"))
    private void onSetValue(Player player, Stat<?> stat, int value, CallbackInfo ci) {
        if (player instanceof ServerPlayer serverPlayer) {
            EnigmaticsBingoGoalsTriggers.KILL_MOBS.get().trigger(serverPlayer);
        }
    }
}
