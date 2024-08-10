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

public class UseLoomTrigger extends SimpleCriterionTrigger<UseLoomTrigger.TriggerInstance> {
    @NotNull
    @Override
    public Codec<UseLoomTrigger.TriggerInstance> codec() {
        return UseLoomTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, ItemStack result, ItemStack banner, ItemStack dye,
                        ItemStack pattern) {
        trigger(player, triggerInstance -> triggerInstance.matches(result, banner, dye, pattern));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ItemPredicate> result,
                                  Optional<ItemPredicate> banner, Optional<ItemPredicate> dye, Optional<ItemPredicate> pattern)
            implements SimpleInstance {
        public static final Codec<UseLoomTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(UseLoomTrigger.TriggerInstance::player),
                        ItemPredicate.CODEC.optionalFieldOf("result").forGetter(TriggerInstance::result),
                        ItemPredicate.CODEC.optionalFieldOf("banner").forGetter(TriggerInstance::banner),
                        ItemPredicate.CODEC.optionalFieldOf("dye").forGetter(TriggerInstance::dye),
                        ItemPredicate.CODEC.optionalFieldOf("pattern").forGetter(TriggerInstance::pattern)
                ).apply(instance, UseLoomTrigger.TriggerInstance::new)
        );

        public static Criterion<UseLoomTrigger.TriggerInstance> used() {
            return EnigmaticsBingoGoalsTriggers.USE_LOOM.get().createCriterion(
                    new UseLoomTrigger.TriggerInstance(Optional.empty(), Optional.empty(),
                            Optional.empty(), Optional.empty(), Optional.empty())
            );
        }

        public boolean matches(ItemStack result, ItemStack banner, ItemStack dye, ItemStack pattern) {

            boolean resultMatch = this.result.isEmpty() || this.result.get().test(result);
            boolean bannerMatch = this.banner.isEmpty() || this.banner.get().test(banner);
            boolean dyeMatch = this.dye.isEmpty() || this.dye.get().test(dye);
            boolean patternMatch = this.pattern.isEmpty() || this.pattern.get().test(pattern);

            return (resultMatch && bannerMatch && dyeMatch && patternMatch);
        }
    }
}
