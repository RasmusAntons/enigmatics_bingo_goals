package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoTags;
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
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
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
import java.util.function.Consumer;

public abstract class EnigmaticsDifficultyGoalProvider extends DifficultyGoalProvider {
    public static final ResourceLocation ENIGMATICS = new ResourceLocation(Bingo.MOD_ID, "enigmatics");

    public EnigmaticsDifficultyGoalProvider(ResourceLocation difficulty,
                                            Consumer<BingoGoal.Holder> goalAdder,
                                            HolderLookup.Provider registries) {
        super(difficulty, goalAdder, registries);
    }

    protected static BingoGoal.Builder obtainItemGoal(ResourceLocation id, ItemLike item) {
        return obtainItemGoal(id, item, ItemPredicate.Builder.item().of(item))
                .antisynergy(BuiltInRegistries.ITEM.getKey(item.asItem()).getPath())
                .tags(BingoTags.ITEM)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_item", item.asItem().getDescription()));
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
                .name(Component.translatable("enigmaticsbingogoals.goal.get_effect", effect.value().getDisplayName()))
                .icon(EffectIcon.of(effect));
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
                .icon(IndicatorIcon.infer(EffectIcon.of(MobEffects.WITHER), entityType));
    }

    protected static BingoGoal.Builder neverLevelsGoal(ResourceLocation id, int minLevels, int maxLevels) {
        return BingoGoal.builder(id)
                .sub("count", BingoSub.random(minLevels, maxLevels))
                .criterion("obtain", ExperienceChangeTrigger.builder().levels(MinMaxBounds.Ints.atLeast(0)).build(),
                        subber -> subber.sub("conditions.levels.min", "count"))
                .tags(BingoTags.NEVER, BingoTags.STAT)
                .name(Component.translatable("enigmaticsbingogoals.goal.never_levels", 0),
                        subber -> subber.sub("with.0", "count"))
                .icon(
                        new IndicatorIcon(ItemIcon.ofItem(Items.EXPERIENCE_BOTTLE), ItemIcon.ofItem(Items.BARRIER)),
                        subber -> subber.sub("base.item.count", "count"))
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
}
