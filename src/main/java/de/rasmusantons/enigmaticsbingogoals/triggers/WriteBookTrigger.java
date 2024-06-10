package de.rasmusantons.enigmaticsbingogoals.triggers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.gaming32.bingo.triggers.progress.SimpleProgressibleCriterionTrigger;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class WriteBookTrigger extends SimpleProgressibleCriterionTrigger<WriteBookTrigger.TriggerInstance> {
    @NotNull
    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, String title, int generation) {
        trigger(player, triggerInstance -> triggerInstance.matches(title, generation));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<String> title, MinMaxBounds.Ints generation)
            implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        Codec.STRING.optionalFieldOf("title").forGetter(TriggerInstance::title),
                        MinMaxBounds.Ints.CODEC.optionalFieldOf("generation", MinMaxBounds.Ints.ANY).forGetter(TriggerInstance::generation)
                ).apply(instance, TriggerInstance::new)
        );

        public static Criterion<TriggerInstance> generation(MinMaxBounds.Ints generation) {
            return EnigmaticsBingoGoalsTriggers.WRITE_BOOK.get().createCriterion(
                    new TriggerInstance(Optional.empty(), Optional.empty(), generation)
            );
        }

        public static Criterion<TriggerInstance> signer() {
            return EnigmaticsBingoGoalsTriggers.WRITE_BOOK.get().createCriterion(
                    new TriggerInstance(Optional.empty(), Optional.empty(), MinMaxBounds.Ints.ANY)
            );
        }

        public boolean matches(String title, int gen) {
            if (!generation.matches(gen))
                return false;
            return this.title.isEmpty() || this.title.get().equals(title);
        }
    }
}
