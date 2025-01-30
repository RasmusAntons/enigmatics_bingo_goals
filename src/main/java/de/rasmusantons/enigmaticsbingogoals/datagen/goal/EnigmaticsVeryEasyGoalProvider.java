package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoDifficulties;
import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoTags;
import de.rasmusantons.enigmaticsbingogoals.conditions.FullUniqueInventoryCondition;
import de.rasmusantons.enigmaticsbingogoals.datagen.EnigmaticsBingoSynergies;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoDamageTypeTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoItemTags;
import de.rasmusantons.enigmaticsbingogoals.triggers.EmptyHungerTrigger;
import io.github.gaming32.bingo.data.BingoDifficulties;
import io.github.gaming32.bingo.data.BingoTags;
import io.github.gaming32.bingo.data.goal.BingoGoal;
import io.github.gaming32.bingo.data.icons.EffectIcon;
import io.github.gaming32.bingo.data.icons.IndicatorIcon;
import io.github.gaming32.bingo.data.icons.ItemIcon;
import io.github.gaming32.bingo.data.progresstrackers.CriterionProgressTracker;
import io.github.gaming32.bingo.triggers.ChickenHatchTrigger;
import io.github.gaming32.bingo.triggers.RelativeStatsTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;

import java.util.Collections;
import java.util.Optional;
import java.util.function.BiConsumer;

public class EnigmaticsVeryEasyGoalProvider extends EnigmaticsDifficultyGoalProvider {
    public EnigmaticsVeryEasyGoalProvider(BiConsumer<ResourceLocation, BingoGoal> goalAdder, HolderLookup.Provider registries) {
        super(EnigmaticsBingoDifficulties.VERY_EASY, goalAdder, registries);
    }

    @Override
    public void addGoals() {
        final var entityTypes = registries.lookupOrThrow(Registries.ENTITY_TYPE);
        final var items = registries.lookupOrThrow(Registries.ITEM);
        final var blocks = registries.lookupOrThrow(Registries.BLOCK);

        addGoal(breedAnimalGoal(eid("breed_chicken"), entityTypes, EntityType.CHICKEN)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY)
                .antisynergy(EnigmaticsBingoSynergies.CHICKEN)
                .catalyst(EnigmaticsBingoSynergies.BABY)
        );
        addGoal(BingoGoal.builder(eid("crouch_500_meters"))
                .criterion("crouch", RelativeStatsTrigger.builder()
                        .stat(Stats.CROUCH_ONE_CM, MinMaxBounds.Ints.atLeast(50000)).build())
                .progress(new CriterionProgressTracker("crouch", 0.01f))
                .name(Component.translatable("bingo.goal.crouch_distance", 500))
                .tags(BingoTags.STAT, EnigmaticsBingoTags.COVER_DISTANCE)
                .icon(Items.LEATHER_BOOTS)
        );
        addGoal(BingoGoal.builder(eid("empty_hunger"))
                .criterion("empty_hunger", EmptyHungerTrigger.TriggerInstance.emptyHunger())
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY, EnigmaticsBingoTags.COVER_DISTANCE)
                .reactant(EnigmaticsBingoSynergies.TAKE_DAMAGE)
                .name(Component.translatable("enigmaticsbingogoals.goal.empty_hunger"))
                .icon(new IndicatorIcon(EffectIcon.of(MobEffects.HUNGER), ItemIcon.ofItem(Items.CLOCK)))
        );
        addGoal(BingoGoal.builder(eid("fill_a_composter"))
                .criterion("use", CriteriaTriggers.DEFAULT_BLOCK_USE.createCriterion(
                        new DefaultBlockInteractionTrigger.TriggerInstance(
                                Optional.empty(),
                                Optional.of(ContextAwarePredicate.create(LocationCheck.checkLocation(
                                        LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(blocks, Blocks.COMPOSTER))
                                ).build())))
                ))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY, EnigmaticsBingoTags.USE_WORKSTATION)
                .name(Component.translatable("enigmaticsbingogoals.goal.fill_composter"))
                .icon(ItemIcon.ofItem(Items.COMPOSTER))
        );
        addGoal(BingoGoal.builder(eid("full_unique_inventory"))
                .criterion("fill", CriteriaTriggers.INVENTORY_CHANGED.createCriterion(
                        new InventoryChangeTrigger.TriggerInstance(
                                Optional.of(ContextAwarePredicate.create(FullUniqueInventoryCondition.INSTANCE)),
                                InventoryChangeTrigger.TriggerInstance.Slots.ANY,
                                Collections.emptyList()
                        )
                ))
                .tags(
                        BingoTags.OVERWORLD,
                        EnigmaticsBingoTags.OVERWORLD_ENTRY
                )
                .name(Component.translatable("enigmaticsbingogoals.goal.full_unique_inventory"))
                .icon(ItemIcon.ofItem(Items.CHEST))
        );
        addGoal(BingoGoal.builder(eid("hatch_baby_chicken"))
                .criterion("hatch", ChickenHatchTrigger.builder().build())
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY)
                .antisynergy(EnigmaticsBingoSynergies.CHICKEN)
                .catalyst(EnigmaticsBingoSynergies.BABY)
                .name(Component.translatable("enigmaticsbingogoals.goal.hatch_baby_chicken",
                        EntityType.CHICKEN.getDescription(),
                        Items.EGG.getName()
                ))
                .icon(IndicatorIcon.infer(EntityType.CHICKEN, Items.EGG))
        );
        addGoal(obtainAllItemsFromTagGoal(eid("obtain_all_wooden_tools"), EnigmaticsBingoItemTags.WOODEN_TOOLS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET, EnigmaticsBingoTags.OVERWORLD_ENTRY)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_material_tools",
                        Component.translatable(EnigmaticsBingoItemTags.WOODEN_TOOLS.getTranslationKey())))
        );
        addGoal(obtainAllItemsFromTagGoal(eid("obtain_all_stone_tools"), EnigmaticsBingoItemTags.STONE_TOOLS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET, EnigmaticsBingoTags.OVERWORLD_ENTRY)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_material_tools",
                        Component.translatable(EnigmaticsBingoItemTags.STONE_TOOLS.getTranslationKey())))
        );
        addGoal(obtainItemGoal(eid("obtain_black_glazed_terracotta"), items, Items.BLACK_GLAZED_TERRACOTTA)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TRAIL_RUINS)
                .antisynergy(EnigmaticsBingoSynergies.TERRACOTTA)
                .infrequency(7)
        );
        addGoal(obtainItemGoal(eid("obtain_blue_glazed_terracotta"), items, Items.BLUE_GLAZED_TERRACOTTA)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TRAIL_RUINS)
                .antisynergy(EnigmaticsBingoSynergies.TERRACOTTA)
                .infrequency(7)
        );
        addGoal(obtainItemGoal(eid("obtain_gray_glazed_terracotta"), items, Items.GRAY_GLAZED_TERRACOTTA)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TRAIL_RUINS)
                .antisynergy(EnigmaticsBingoSynergies.TERRACOTTA)
                .infrequency(7)
        );
        addGoal(obtainItemGoal(eid("obtain_orange_glazed_terracotta"), items, Items.ORANGE_GLAZED_TERRACOTTA)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TRAIL_RUINS)
                .antisynergy(EnigmaticsBingoSynergies.TERRACOTTA)
                .infrequency(7)
        );
        addGoal(obtainItemGoal(eid("obtain_bookshelf"), items, Items.BOOKSHELF)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.BOOK)
        );
        addGoal(obtainItemGoal(eid("obtain_white_glazed_terracotta"), items, Items.WHITE_GLAZED_TERRACOTTA)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TRAIL_RUINS)
                .antisynergy(EnigmaticsBingoSynergies.TERRACOTTA)
                .infrequency(7)
        );
        addGoal(BingoGoal.builder(eid("reach_build_limit"))
                .criterion("reach", PlayerTrigger.TriggerInstance.located(
                        LocationPredicate.Builder.atYLocation(MinMaxBounds.Doubles.atLeast(320))
                ))
                .tags(
                        BingoTags.OVERWORLD,
                        EnigmaticsBingoTags.OVERWORLD_ENTRY,
                        EnigmaticsBingoTags.REACH_WORLD_LIMIT
                )
                .name(Component.translatable("enigmaticsbingogoals.goal.reach_build_limit"))
                .icon(ItemIcon.ofItem(Items.LADDER))
        );
        addGoal(BingoGoal.builder(eid("reach_world_center"))
                .criterion("reach", PlayerTrigger.TriggerInstance.located(
                        LocationPredicate.Builder.location()
                                .setX(MinMaxBounds.Doubles.between(-1.0, 1.0))
                                .setZ(MinMaxBounds.Doubles.between(-1.0, 1.0))
                ))
                .tags(
                        BingoTags.OVERWORLD,
                        EnigmaticsBingoTags.COVER_DISTANCE
                )
                .name(Component.translatable("enigmaticsbingogoals.goal.reach_world_center"))
                .icon(ItemIcon.ofItem(Items.COMPASS))
        );
        addGoal(BingoGoal.builder(eid("sprint_1k_meters"))
                .criterion("sprint", RelativeStatsTrigger.builder()
                        .stat(Stats.SPRINT_ONE_CM, MinMaxBounds.Ints.atLeast(100000)).build())
                .progress(new CriterionProgressTracker("sprint", 0.01f))
                .name(Component.translatable("enigmaticsbingogoals.goal.sprint_distance", 1000))
                .tags(BingoTags.STAT, EnigmaticsBingoTags.COVER_DISTANCE)
                .icon(Items.GOLDEN_BOOTS)
        );
        addGoal(BingoGoal.builder(eid("stand_on_bedrock"))
                .criterion("stand_on", PlayerTrigger.TriggerInstance.located(
                        EntityPredicate.Builder.entity().steppingOn(
                                LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(
                                        blocks, Block.byItem(Items.BEDROCK)
                                ))
                        ))
                )
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY, EnigmaticsBingoTags.REACH_WORLD_LIMIT)
                .name(Component.translatable("enigmaticsbingogoals.goal.stand_on_bedrock"))
                .icon(ItemIcon.ofItem(Items.BEDROCK))
        );
        addGoal(dieToDamageTypeGoal(eid("die_to_suffocation"), EnigmaticsBingoDamageTypeTags.SUFFOCATION)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_suffocation"))
                .icon(IndicatorIcon.infer(Items.SAND, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        addGoal(BingoGoal.builder(eid("kill_baby_mob"))
                .criterion("kill", KilledTrigger.TriggerInstance.playerKilledEntity(
                        EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setIsBaby(true))))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY, EnigmaticsBingoTags.KILL_MOB)
                .reactant(EnigmaticsBingoSynergies.BABY)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_baby_mob"))
                .icon(IndicatorIcon.infer(BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.BABY), Items.NETHERITE_SWORD))
        );
    }
}
