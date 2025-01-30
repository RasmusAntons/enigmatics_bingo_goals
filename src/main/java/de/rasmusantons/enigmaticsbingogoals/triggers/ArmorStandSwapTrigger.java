package de.rasmusantons.enigmaticsbingogoals.triggers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ArmorStandSwapTrigger extends SimpleCriterionTrigger<ArmorStandSwapTrigger.TriggerInstance> {
    @NotNull
    @Override
    public Codec<ArmorStandSwapTrigger.TriggerInstance> codec() {
        return ArmorStandSwapTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, ItemStack helmet, ItemStack chest, ItemStack legs, ItemStack boots) {
        trigger(player, triggerInstance -> triggerInstance.matches(helmet, chest, legs, boots));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ItemPredicate> helmet,
                                  Optional<ItemPredicate> chest, Optional<ItemPredicate> legs, Optional<ItemPredicate> boots)
            implements SimpleInstance {
        public static final Codec<ArmorStandSwapTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(ArmorStandSwapTrigger.TriggerInstance::player),
                        ItemPredicate.CODEC.optionalFieldOf("helmet").forGetter(TriggerInstance::helmet),
                        ItemPredicate.CODEC.optionalFieldOf("chest").forGetter(TriggerInstance::chest),
                        ItemPredicate.CODEC.optionalFieldOf("legs").forGetter(TriggerInstance::legs),
                        ItemPredicate.CODEC.optionalFieldOf("boots").forGetter(TriggerInstance::boots)
                ).apply(instance, ArmorStandSwapTrigger.TriggerInstance::new)
        );

        public static Criterion<ArmorStandSwapTrigger.TriggerInstance> fullArmor(HolderGetter<Item> items) {
            return EnigmaticsBingoGoalsTriggers.SWAP_ARMOR_STAND_ITEM.get().createCriterion(
                    new ArmorStandSwapTrigger.TriggerInstance(Optional.empty(),
                            Optional.of(ItemPredicate.Builder.item().of(items, ItemTags.HEAD_ARMOR).build()),
                            Optional.of(ItemPredicate.Builder.item().of(items, ItemTags.CHEST_ARMOR).build()),
                            Optional.of(ItemPredicate.Builder.item().of(items, ItemTags.LEG_ARMOR).build()),
                            Optional.of(ItemPredicate.Builder.item().of(items, ItemTags.FOOT_ARMOR).build()))
            );
        }

        public boolean matches(ItemStack helmet, ItemStack chest, ItemStack legs, ItemStack boots) {

            boolean helmetMatch = this.helmet.isEmpty() || this.helmet.get().test(helmet);
            boolean chestMatch = this.chest.isEmpty() || this.chest.get().test(chest);
            boolean legsMatch = this.legs.isEmpty() || this.legs.get().test(legs);
            boolean bootsMatch = this.boots.isEmpty() || this.boots.get().test(boots);

            return (helmetMatch && chestMatch && legsMatch && bootsMatch);
        }
    }
}
