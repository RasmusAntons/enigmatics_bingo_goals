package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoTags;
import de.rasmusantons.enigmaticsbingogoals.conditions.NumberOfEffectsCondition;
import de.rasmusantons.enigmaticsbingogoals.triggers.AdvancementsTrigger;
import io.github.gaming32.bingo.Bingo;
import io.github.gaming32.bingo.data.BingoGoal;
import io.github.gaming32.bingo.data.BingoTags;
import io.github.gaming32.bingo.data.icons.*;
import io.github.gaming32.bingo.data.progresstrackers.CriterionProgressTracker;
import io.github.gaming32.bingo.data.subs.BingoSub;
import io.github.gaming32.bingo.fabric.datagen.goal.DifficultyGoalProvider;
import io.github.gaming32.bingo.triggers.*;
import io.github.gaming32.bingo.util.BingoUtil;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class EnigmaticsDifficultyGoalProvider extends DifficultyGoalProvider {
    public static final ResourceLocation ENIGMATICS = new ResourceLocation(Bingo.MOD_ID, "enigmatics");

    public EnigmaticsDifficultyGoalProvider(ResourceLocation difficulty,
                                            Consumer<BingoGoal.Holder> goalAdder,
                                            HolderLookup.Provider registries) {
        super(difficulty, goalAdder, registries);
    }

    protected static BingoGoal.Builder obtainItemGoal(ResourceLocation id, ItemLike item) {
        return obtainItemGoal(id, item, 1);
    }

    protected static BingoGoal.Builder obtainItemGoal(ResourceLocation id, ItemLike item, int count) {
        Component itemName = item.asItem().getDescription();
        BingoGoal.Builder builder = BingoGoal.builder(id)
                .criterion("obtain", TotalCountInventoryChangeTrigger.builder().items(
                        ItemPredicate.Builder.item().of(item).withCount(MinMaxBounds.Ints.atLeast(count)).build()
                ).build())
                .tags(BingoTags.ITEM);
        if (count == 1)
            builder
                    .icon(ItemIcon.ofItem(item))
                    .name(Component.translatable("enigmaticsbingogoals.goal.obtain_item", itemName));
        else
            builder
                    .progress("obtain")
                    .icon(new ItemIcon(new ItemStack(item, count)))
                    .name(Component.translatable("enigmaticsbingogoals.goal.obtain_item_count", count, itemName));
        return builder;
    }

    protected BingoGoal.Builder obtainAllItemsFromTag(ResourceLocation id, TagKey<Item> tag) {
        return BingoGoal.builder(id)
                .criterion("obtain", HasSomeItemsFromTagTrigger.builder().tag(tag).requiresAll().build())
                .progress("obtain")
                .tags(BingoTags.ITEM)
                .icon(new ItemTagCycleIcon(tag));
    }

    protected BingoGoal.Builder eatItemGoal(ResourceLocation id, Item item) {
        return BingoGoal.builder(id)
                .criterion("eat", ConsumeItemTrigger.TriggerInstance.usedItem(item))
                .name(Component.translatable("enigmaticsbingogoals.goal.eat_something", item.getDescription()))
                .icon(ItemIcon.ofItem(item));
    }

    @SafeVarargs
    protected static BingoGoal.Builder potionGoal(ResourceLocation id, Holder<Potion>... potions) {
        ItemStack potionItem = BingoUtil.setPotion(new ItemStack(Items.POTION), potions[0]);
        return obtainItemGoal(
                id,
                potionItem,
                Arrays.stream(potions)
                        .map(potion -> ItemPredicate.Builder.item()
                                .of(Items.POTION)
                                .withSubPredicate(
                                        ItemSubPredicates.POTIONS,
                                        new ItemPotionsPredicate(HolderSet.direct(potion))
                                )
                        )
                        .toArray(ItemPredicate.Builder[]::new)
        )
                .tags(EnigmaticsBingoTags.POTIONS)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_item", Items.POTION.getName(potionItem)));
    }

    protected static BingoGoal.Builder effectGoal(ResourceLocation id, Holder<MobEffect> effect) {
        return BingoGoal.builder(id)
                .criterion("effect", EffectsChangedTrigger.TriggerInstance.hasEffects(
                                MobEffectsPredicate.Builder.effects().and(effect)
                        )
                )
                .tags(EnigmaticsBingoTags.GET_EFFECT)
                .name(Component.translatable("enigmaticsbingogoals.goal.get_effect", effect.value().getDisplayName()))
                .icon(EffectIcon.of(effect));
    }

    protected static BingoGoal.Builder numberOfEffectsGoal(ResourceLocation id, int minEffects, int maxEffects) {
        ItemStack icon = new ItemStack(Items.GLASS_BOTTLE);
        icon.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
        return BingoGoal.builder(id)
                .sub("count", BingoSub.random(minEffects, maxEffects))
                .criterion("effects", CriteriaTriggers.EFFECTS_CHANGED.createCriterion(new EffectsChangedTrigger.TriggerInstance(
                        Optional.of(ContextAwarePredicate.create(new NumberOfEffectsCondition(MinMaxBounds.Ints.atLeast(0)))),
                        Optional.empty(),
                        Optional.empty()
                )), subber -> subber.sub("conditions.player.0.effects.min", "count"))
                .tags(EnigmaticsBingoTags.GET_EFFECT_BATCH)
                .icon(IndicatorIcon.infer(
                        new ItemIcon(icon),
                        CycleIcon.infer(BuiltInRegistries.MOB_EFFECT.holders().map(EffectIcon::of))
                ), subber -> subber.sub("base.item.count", "count"))
                .name(Component.translatable("enigmaticsbingogoals.goal.get_some_effects", 0),
                        subber -> subber.sub("with.0", "count"));
    }

    protected static BingoGoal.Builder dieToEntityGoal(ResourceLocation id, EntityType<?> entityType) {
        return BingoGoal.builder(id)
                .criterion("die", BingoTriggers.DEATH.get().createCriterion(DeathTrigger.TriggerInstance.death(
                        DamageSourcePredicate.Builder.damageType()
                                .source(EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityType)))
                                .build()
                        )
                ))
                .tags(EnigmaticsBingoTags.DIE_TO)
                .tooltip(Component.translatable("enigmaticsbingogoals.goal.directly_killed.tooltip", entityType.getDescription()))
                .icon(IndicatorIcon.infer(entityType, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)));
    }

    protected static BingoGoal.Builder killEntityGoal(ResourceLocation id, EntityType<?> entityType) {
        return BingoGoal.builder(id)
                .criterion("kill", KilledTrigger.TriggerInstance.playerKilledEntity(
                        EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityType))))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .icon(IndicatorIcon.infer(entityType, Items.NETHERITE_SWORD));
    }

    protected static BingoGoal.Builder neverLevelsGoal(ResourceLocation id, int minLevels, int maxLevels) {
        return BingoGoal.builder(id)
                .sub("count", BingoSub.random(minLevels, maxLevels))
                .criterion("obtain", ExperienceChangeTrigger.builder().levels(MinMaxBounds.Ints.atLeast(0)).build(),
                        subber -> subber.sub("conditions.levels.min", "count"))
                .tags(BingoTags.NEVER, BingoTags.STAT, EnigmaticsBingoTags.REACH_LEVEL, EnigmaticsBingoTags.LEVEL)
                .name(Component.translatable("enigmaticsbingogoals.goal.never_levels", 0),
                        subber -> subber.sub("with.0", "count"))
                .icon(
                        new IndicatorIcon(ItemIcon.ofItem(Items.EXPERIENCE_BOTTLE), ItemIcon.ofItem(Items.BARRIER)),
                        subber -> subber.sub("base.item.count", "count"))
                .antisynergy("levels");
    }

    protected static BingoGoal.Builder reachLevelsGoal(ResourceLocation id, int minLevels, int maxLevels) {
        return BingoGoal.builder(id)
                .sub("count", BingoSub.random(minLevels, maxLevels))
                .criterion("obtain", ExperienceChangeTrigger.builder().levels(MinMaxBounds.Ints.atLeast(0)).build(),
                        subber -> subber.sub("conditions.levels.min", "count"))
                .tags(BingoTags.STAT, EnigmaticsBingoTags.REACH_LEVEL, EnigmaticsBingoTags.LEVEL)
                .name(Component.translatable("enigmaticsbingogoals.goal.reach_levels", 0),
                        subber -> subber.sub("with.0", "count"))
                .icon(
                        ItemIcon.ofItem(Items.EXPERIENCE_BOTTLE),
                        subber -> subber.sub("item.count", "count"))
                .antisynergy("levels");
    }

    protected static BingoGoal.Builder neverDamageGoal(ResourceLocation id, int damage) {
        return BingoGoal.builder(id)
                .criterion("damage", RelativeStatsTrigger.builder()
                        .stat(Stats.DAMAGE_TAKEN, MinMaxBounds.Ints.atLeast(damage * 20)).build()
                )
                .tags(BingoTags.NEVER, EnigmaticsBingoTags.NEVER_TAKE_DAMAGE)
                .name(Component.translatable("enigmaticsbingogoals.goal.never_some_hearts_damage", damage))
                .icon(new IndicatorIcon(EffectIcon.of(MobEffects.HARM), ItemIcon.ofItem(Items.BARRIER)))
                .progress(new CriterionProgressTracker("damage", 0.05f));
    }

    protected static BingoGoal.Builder advancementsGoal(ResourceLocation id, int minNumber, int maxNumber) {
        return BingoGoal.builder(id)
                .sub("number", BingoSub.random(minNumber, maxNumber))
                .criterion("achieve", AdvancementsTrigger.TriggerInstance.advancements(MinMaxBounds.Ints.atLeast(0)),
                        subber -> subber.sub("conditions.number.min", "number"))
                .tags(BingoTags.STAT, EnigmaticsBingoTags.ADVANCEMENTS)
                .name(Component.translatable("enigmaticsbingogoals.goal.get_some_advancements", 0),
                        subber -> subber.sub("with.0", "number"))
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.ELYTRA), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)),
                        subber -> subber.sub("base.item.count", "number"))
                .progress("achieve");
    }

    protected static BingoGoal.Builder breakBlockGoal(ResourceLocation id, Block... oneOfThese) {
        BingoGoal.Builder builder = BingoGoal.builder(id);
        if (oneOfThese.length == 1) {
            builder
                    .criterion("break", BreakBlockTrigger.builder().block(oneOfThese[0]).build())
                    .icon(IndicatorIcon.infer(oneOfThese[0], Items.NETHERITE_PICKAXE));
        } else {
            for (int i = 0; i < oneOfThese.length; i++)
                builder.criterion("break_" + i, BreakBlockTrigger.builder().block(oneOfThese[i]).build());
            builder.icon(
                    IndicatorIcon.infer(
                            CycleIcon.infer(Arrays.stream(oneOfThese).map(BlockIcon::ofBlock)),
                            Items.NETHERITE_PICKAXE
                    )
            );
        }
        return builder
                .requirements(AdvancementRequirements.Strategy.OR)
                .name(Component.translatable("enigmaticsbingogoals.goal.break_block",
                        Component.translatable(oneOfThese[0].getDescriptionId())));
    }

    protected static BingoGoal.Builder breedAnimalGoal(ResourceLocation id, EntityType<?> entityType) {
        return BingoGoal.builder(id)
            .criterion("breed", BredAnimalsTrigger.TriggerInstance.bredAnimals(
                    EntityPredicate.Builder.entity().of(entityType)))
            .tags(EnigmaticsBingoTags.BREED_MOB)
            .name(Component.translatable("enigmaticsbingogoals.goal.breed_animal", entityType.getDescription()))
            .icon(IndicatorIcon.infer(entityType, EffectIcon.of(MobEffects.HEALTH_BOOST)));
    }
}
