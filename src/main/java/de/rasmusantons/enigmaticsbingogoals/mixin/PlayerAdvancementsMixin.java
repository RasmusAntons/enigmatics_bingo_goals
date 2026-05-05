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
    @Shadow
    @Final
    public Map<AdvancementHolder, AdvancementProgress> progress;

    @Shadow
    private ServerPlayer player;

    @Shadow
    @Final
    private Set<AdvancementHolder> visible;

    @Inject(method = "award", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/AdvancementRewards;grant(Lnet/minecraft/server/level/ServerPlayer;)V", shift = At.Shift.AFTER))
    private void award(AdvancementHolder holder, String criterion, CallbackInfoReturnable<Boolean> cir) {
        if (holder.value().display().isPresent()) {
            int number = (int) progress.entrySet().stream().filter(
                    entry -> entry.getKey().value().display().isPresent()
                            && entry.getValue().isDone()
            ).count();
            EnigmaticsBingoGoalsTriggers.ADVANCEMENTS.get().trigger(player, holder.id(), number);
        }
    }


    @Inject(method = "award", at = @At(value = "TAIL"))
    private void advancementProgress(AdvancementHolder holder, String criterion, CallbackInfoReturnable<Boolean> cir) {
        AdvancementProgress advancementProgress = this.progress.get(holder);
        int countCompletedRequirements = 0;

        if (advancementProgress != null) {
            countCompletedRequirements = advancementProgress.countCompletedRequirements();
        }

        EnigmaticsBingoGoalsTriggers.CHECK_ADVANCEMENT_PROGRESS.get().trigger(
                player,
                holder.id(),
                countCompletedRequirements
        );
    }
}
