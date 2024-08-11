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

public class UseCartographyTableTrigger extends SimpleCriterionTrigger<UseCartographyTableTrigger.TriggerInstance> {
    @NotNull
    @Override
    public Codec<UseCartographyTableTrigger.TriggerInstance> codec() {
        return UseCartographyTableTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, ItemStack result, ItemStack map, ItemStack modifier) {
        trigger(player, triggerInstance -> triggerInstance.matches(result, map, modifier));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ItemPredicate> result,
                                  Optional<ItemPredicate> map, Optional<ItemPredicate> modifier)
            implements SimpleInstance {
        public static final Codec<UseCartographyTableTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(UseCartographyTableTrigger.TriggerInstance::player),
                        ItemPredicate.CODEC.optionalFieldOf("result").forGetter(TriggerInstance::result),
                        ItemPredicate.CODEC.optionalFieldOf("map").forGetter(TriggerInstance::map),
                        ItemPredicate.CODEC.optionalFieldOf("modifier").forGetter(TriggerInstance::modifier)
                ).apply(instance, UseCartographyTableTrigger.TriggerInstance::new)
        );

        public static Criterion<UseCartographyTableTrigger.TriggerInstance> used() {
            return EnigmaticsBingoGoalsTriggers.USE_CARTOGRAPHY_TABLE.get().createCriterion(
                    new UseCartographyTableTrigger.TriggerInstance(Optional.empty(), Optional.empty(),
                            Optional.empty(), Optional.empty())
            );
        }

        public boolean matches(ItemStack result, ItemStack map, ItemStack modifier) {

            boolean resultMatch = this.result.isEmpty() || this.result.get().test(result);
            boolean bannerMatch = this.map.isEmpty() || this.map.get().test(map);
            boolean dyeMatch = this.modifier.isEmpty() || this.modifier.get().test(modifier);

            return (resultMatch && bannerMatch && dyeMatch);
        }
    }
}
