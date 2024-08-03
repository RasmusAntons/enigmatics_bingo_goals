package de.rasmusantons.enigmaticsbingogoals.mixin;

import de.rasmusantons.enigmaticsbingogoals.extension.JukeboxSongPlayerExtension;
import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.JukeboxSongPlayer;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(JukeboxSongPlayer.class)
public abstract class JukeboxSongPlayerMixin implements JukeboxSongPlayerExtension {
    @Shadow
    @Final
    private BlockPos blockPos;

    @Unique
    private final static int MAX_DISTANCE = 60;

    @Unique
    private ServerPlayer lastPlayed;

    public ServerPlayer enigmaticsbingogoals$getLastPlayed() {
        return lastPlayed;
    }

    public void enigmaticsbingogoals$setLastPlayed(ServerPlayer serverPlayer) {
        lastPlayed = serverPlayer;
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/LevelAccessor;gameEvent(Lnet/minecraft/core/Holder;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/gameevent/GameEvent$Context;)V"))
    private void onGameEvent(LevelAccessor levelAccessor, BlockState blockState, CallbackInfo ci) {
        if (lastPlayed == null)
            return;
        if (!(levelAccessor instanceof ServerLevel serverLevel))
            return;
        for (Player player : serverLevel.players()) {
            if (!(player instanceof ServerPlayer serverPlayer))
                continue;
            if (serverPlayer.gameMode.getGameModeForPlayer() == GameType.SPECTATOR)
                continue;
            if (serverPlayer.position().distanceTo(this.blockPos.getCenter()) > MAX_DISTANCE)
                continue;
            if (lastPlayed.getTeam() == serverPlayer.getTeam())
                continue;
            EnigmaticsBingoGoalsTriggers.PLAY_MUSIC_TO_OTHER_TEAM.get().trigger(lastPlayed);
        }
    }
}
