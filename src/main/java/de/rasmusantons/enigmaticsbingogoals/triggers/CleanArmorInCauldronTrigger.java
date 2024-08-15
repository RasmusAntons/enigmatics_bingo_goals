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

public class CleanArmorInCauldronTrigger extends SimpleCriterionTrigger<CleanArmorInCauldronTrigger.TriggerInstance> {
    @NotNull
    @Override
    public Codec<CleanArmorInCauldronTrigger.TriggerInstance> codec() {
        return CleanArmorInCauldronTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, ItemStack uncleaned) {
        trigger(player, triggerInstance -> triggerInstance.matches(uncleaned));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ItemPredicate> uncleaned)
            implements SimpleInstance {
        public static final Codec<CleanArmorInCauldronTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(CleanArmorInCauldronTrigger.TriggerInstance::player),
                        ItemPredicate.CODEC.optionalFieldOf("uncleaned").forGetter(TriggerInstance::uncleaned)
                ).apply(instance, CleanArmorInCauldronTrigger.TriggerInstance::new)
        );

        public static Criterion<CleanArmorInCauldronTrigger.TriggerInstance> cleanArmor() {
            return EnigmaticsBingoGoalsTriggers.CLEAN_ARMOR_IN_CAULDRON.get().createCriterion(
                    new CleanArmorInCauldronTrigger.TriggerInstance(Optional.empty(), Optional.empty())
            );
        }

        public boolean matches(ItemStack uncleaned) {
            return this.uncleaned.isEmpty() || this.uncleaned.get().test(uncleaned);
        }
    }
}
