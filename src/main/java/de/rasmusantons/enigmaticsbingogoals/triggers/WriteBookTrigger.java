package de.rasmusantons.enigmaticsbingogoals.triggers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.gaming32.bingo.triggers.progress.SimpleProgressibleCriterionTrigger;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class WriteBookTrigger extends SimpleProgressibleCriterionTrigger<WriteBookTrigger.TriggerInstance> {
    @NotNull
    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, String title) {
        trigger(player, triggerInstance -> triggerInstance.matches(title));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<String> title)
            implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        Codec.STRING.optionalFieldOf("title").forGetter(TriggerInstance::title)
                ).apply(instance, TriggerInstance::new)
        );

        public static Criterion<TriggerInstance> signer() {
            return EnigmaticsBingoGoalsTriggers.WRITE_BOOK.get().createCriterion(
                    new TriggerInstance(Optional.empty(), Optional.empty())
            );
        }

        public boolean matches(String title) {
            return this.title.isEmpty() || this.title.get().equals(title);
        }
    }
}
