package de.rasmusantons.enigmaticsbingogoals.mixin;

import de.rasmusantons.enigmaticsbingogoals.extension.BingoGameExtension;
import io.github.gaming32.bingo.game.BingoBoard;
import io.github.gaming32.bingo.game.BingoGame;
import io.github.gaming32.bingo.game.BingoGameMode;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.PlayerTeam;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(BingoGame.class)
public class BingoGameMixin implements BingoGameExtension {
    @Unique
    private final Map<Player, Integer> totalDamage = new HashMap<>();

    @Unique
    public Map<Player, Integer> enigmatics_bingo_goals$getTotalDamage() {
        return totalDamage;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(BingoBoard board, BingoGameMode gameMode, boolean requireClient, boolean persistent, boolean continueAfterWin, PlayerTeam[] teams, CallbackInfo ci) {
        totalDamage.clear();
    }
}
