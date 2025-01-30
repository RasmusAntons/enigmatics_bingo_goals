package de.rasmusantons.enigmaticsbingogoals.mixin;

import de.rasmusantons.enigmaticsbingogoals.extension.BingoGameExtension;
import de.rasmusantons.enigmaticsbingogoals.triggers.AdvancementProgressTrigger;
import de.rasmusantons.enigmaticsbingogoals.triggers.AdvancementsTrigger;
import io.github.gaming32.bingo.game.ActiveGoal;
import io.github.gaming32.bingo.game.BingoBoard;
import io.github.gaming32.bingo.game.BingoGame;
import io.github.gaming32.bingo.game.mode.BingoGameMode;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.world.scores.PlayerTeam;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Mixin(BingoGame.class)
public abstract class BingoGameMixin implements BingoGameExtension {
    @Unique
    private final Map<UUID, Integer> totalDamage = new HashMap<>();

    @Shadow(remap = false)
    @Final
    private Map<UUID, Object2IntMap<Stat<?>>> baseStats;

    @Shadow(remap = false)
    public abstract BingoBoard getBoard();

    @Unique
    public Map<UUID, Integer> enigmatics_bingo_goals$getTotalDamage() {
        return totalDamage;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(BingoBoard board, BingoGameMode gameMode, boolean requireClient, boolean continueAfterWin, int autoForfeitTicks, PlayerTeam[] teams, CallbackInfo ci) {
        totalDamage.clear();
    }

    @Inject(method = "addPlayer", at = @At(value = "INVOKE", target = "Lio/github/gaming32/bingo/network/messages/s2c/RemoveBoardPayload;sendTo(Lnet/minecraft/server/level/ServerPlayer;)V"))
    private void onAddPlayer(ServerPlayer player, CallbackInfo ci) {
        if (baseStats.containsKey(player.getUUID()))
            return;
        for (ActiveGoal goal : getBoard().getGoals()) {
            for (var entry : goal.criteria().entrySet()) {
                Optional<ResourceLocation> advancement = Optional.empty();
                if (entry.getValue().triggerInstance() instanceof AdvancementsTrigger.TriggerInstance advancementsTrigger) {
                    advancement = advancementsTrigger.advancement();
                } else if (entry.getValue().triggerInstance() instanceof AdvancementProgressTrigger.TriggerInstance advancementProgressTrigger) {
                    advancement = advancementProgressTrigger.advancement();
                }
                if (advancement.isEmpty())
                    continue;
                if (player.getServer() == null)
                    continue;
                AdvancementHolder advancementHolder = player.getServer().getAdvancements().get(advancement.get());
                AdvancementProgress progress = player.getAdvancements().progress.get(advancementHolder);
                if (progress == null)
                    continue;
                for (var criterionKey : progress.getCompletedCriteria()) {
                    player.getAdvancements().revoke(advancementHolder, criterionKey);
                }
            }
        }
    }
}
