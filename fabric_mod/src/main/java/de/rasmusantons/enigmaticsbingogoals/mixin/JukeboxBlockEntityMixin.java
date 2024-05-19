package de.rasmusantons.enigmaticsbingogoals.mixin;

import de.rasmusantons.enigmaticsbingogoals.extension.RecordItemExtension;
import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import de.rasmusantons.enigmaticsbingogoals.triggers.PlayMusicToOtherTeamTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(JukeboxBlockEntity.class)
public abstract class JukeboxBlockEntityMixin {
    @Shadow public abstract ItemStack getTheItem();

    @Unique
    private final static int MAX_DISTANCE = 60;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;gameEvent(Lnet/minecraft/core/Holder;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/gameevent/GameEvent$Context;)V"))
    private void onGameEvent(Level level, BlockPos pos, BlockState state, CallbackInfo ci) {
        for (Player player : level.players()) {
            if (!(player instanceof ServerPlayer serverPlayer))
                continue;
            if (serverPlayer.position().distanceTo(pos.getCenter()) > MAX_DISTANCE)
                continue;
            ServerPlayer recordPlayer = ((RecordItemExtension) this.getTheItem().getItem()).enigmaticsbingogoals$getLastPlayer();
            if (recordPlayer.getTeam() == serverPlayer.getTeam())
                continue;
            PlayMusicToOtherTeamTrigger trigger = (PlayMusicToOtherTeamTrigger) EnigmaticsBingoGoalsTriggers.registeredTriggers.get(PlayMusicToOtherTeamTrigger.KEY);
            trigger.trigger(recordPlayer);
        }
    }
}
