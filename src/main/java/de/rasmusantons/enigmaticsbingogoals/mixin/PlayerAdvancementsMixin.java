package de.rasmusantons.enigmaticsbingogoals.mixin;

import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Set;

@Mixin(PlayerAdvancements.class)
public class PlayerAdvancementsMixin {
    @Shadow @Final private Map<AdvancementHolder, AdvancementProgress> progress;

    @Shadow private ServerPlayer player;

    @Shadow @Final private Set<AdvancementHolder> visible;

    @Inject(method = "award", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/AdvancementRewards;grant(Lnet/minecraft/server/level/ServerPlayer;)V", shift = At.Shift.AFTER))
    private void award(AdvancementHolder advancement, String criterionKey, CallbackInfoReturnable<Boolean> cir) {
        if (advancement.value().display().isPresent()) {
            int number = (int) progress.entrySet().stream().filter(
                    entry -> entry.getKey().value().display().isPresent()
                            && entry.getValue().isDone()
            ).count();
            EnigmaticsBingoGoalsTriggers.ADVANCEMENTS.get().trigger(player, advancement.id(), number);
        }
    }


    @Inject(method = "award", at = @At(value = "TAIL"))
    private void advancementProgress(AdvancementHolder advancement, String criterionKey, CallbackInfoReturnable<Boolean> cir) {

        AdvancementProgress advancementProgress = this.progress.get(advancement);
        int countCompletedRequirements = 0;

        if (advancementProgress != null) {
            countCompletedRequirements = advancementProgress.countCompletedRequirements();
        }

        EnigmaticsBingoGoalsTriggers.CHECK_ADVANCEMENT_PROGRESS.get().trigger(
                player,
                advancement.id(),
                countCompletedRequirements
        );
    }
}
