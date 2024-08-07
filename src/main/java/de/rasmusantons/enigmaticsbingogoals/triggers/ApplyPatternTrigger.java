package de.rasmusantons.enigmaticsbingogoals.triggers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ApplyPatternTrigger extends SimpleCriterionTrigger<ApplyPatternTrigger.TriggerInstance> {
    @NotNull
    @Override
    public Codec<ApplyPatternTrigger.TriggerInstance> codec() {
        return ApplyPatternTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, ItemStack stack) {
        trigger(player, triggerInstance -> triggerInstance.matches(stack));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<List<ResourceKey<BannerPattern>>> patterns, MinMaxBounds.Ints number)
            implements SimpleInstance {
        public static final Codec<ApplyPatternTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(ApplyPatternTrigger.TriggerInstance::player),
                        Codec.list(ResourceKey.codec(Registries.BANNER_PATTERN)).optionalFieldOf("patterns").forGetter(TriggerInstance::patterns),
                        MinMaxBounds.Ints.CODEC.optionalFieldOf("number", MinMaxBounds.Ints.ANY).forGetter(TriggerInstance::number)
                ).apply(instance, ApplyPatternTrigger.TriggerInstance::new)
        );

        public static Criterion<ApplyPatternTrigger.TriggerInstance> hasPatterns(List<ResourceKey<BannerPattern>> patterns) {
            return EnigmaticsBingoGoalsTriggers.APPLY_PATTERN.get().createCriterion(
                    new ApplyPatternTrigger.TriggerInstance(Optional.empty(), Optional.of(patterns), MinMaxBounds.Ints.ANY)
            );
        }

        public static Criterion<ApplyPatternTrigger.TriggerInstance> numberOfPatterns(MinMaxBounds.Ints number) {
            return EnigmaticsBingoGoalsTriggers.APPLY_PATTERN.get().createCriterion(
                    new ApplyPatternTrigger.TriggerInstance(Optional.empty(), Optional.empty(), number)
            );
        }



        public boolean matches(ItemStack stack) {
            BannerPatternLayers data = stack.get(DataComponents.BANNER_PATTERNS);
            if (data == null)
                return false;

            List<ResourceKey<BannerPattern>> patterns = data.layers().stream().map(layer ->
                            layer.pattern().unwrapKey().orElse(null)
                    ).filter(Objects::nonNull).toList();

            if (!this.number.matches(patterns.size()))
                return false;

            if (this.patterns.isEmpty())
                return true;

            return new HashSet<>(patterns).containsAll(this.patterns.get());
        }
    }
}
