package de.rasmusantons.enigmaticsbingogoals.triggers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class UseAnvilTrigger extends SimpleCriterionTrigger<UseAnvilTrigger.TriggerInstance> {
    @NotNull
    @Override
    public Codec<UseAnvilTrigger.TriggerInstance> codec() {
        return UseAnvilTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, ItemStack result, ItemStack first, ItemStack second) {
        trigger(player, triggerInstance -> triggerInstance.matches(result, first, second));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ItemPredicate> result,
                                  Optional<ItemPredicate> first, Optional<ItemPredicate> second)
            implements SimpleInstance {
        public static final Codec<UseAnvilTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(UseAnvilTrigger.TriggerInstance::player),
                        ItemPredicate.CODEC.optionalFieldOf("result").forGetter(TriggerInstance::result),
                        ItemPredicate.CODEC.optionalFieldOf("first").forGetter(TriggerInstance::first),
                        ItemPredicate.CODEC.optionalFieldOf("second").forGetter(TriggerInstance::second)
                ).apply(instance, UseAnvilTrigger.TriggerInstance::new)
        );

        public static Criterion<UseAnvilTrigger.TriggerInstance> used() {
            return EnigmaticsBingoGoalsTriggers.USE_ANVIL.get().createCriterion(
                    new UseAnvilTrigger.TriggerInstance(Optional.empty(), Optional.empty(),
                            Optional.empty(), Optional.empty())
            );
        }

        public boolean matches(ItemStack result, ItemStack first, ItemStack second) {

            boolean resultMatch = this.result.isEmpty() || this.result.get().test(result);
            boolean firstMatch = this.first.isEmpty() || this.first.get().test(first);
            boolean secondMatch = this.second.isEmpty() || this.second.get().test(second);

            return (resultMatch && firstMatch && secondMatch);
        }
    }
}
