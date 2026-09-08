package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoAdvancements;
import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoDifficulties;
import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoTags;
import de.rasmusantons.enigmaticsbingogoals.conditions.PlayerAliveCondition;
import de.rasmusantons.enigmaticsbingogoals.conditions.UniqueFoodsOnCampfireCondition;
import de.rasmusantons.enigmaticsbingogoals.datagen.EnigmaticsBingoSynergies;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoDamageTypeTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoEntityTypeTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoItemTags;
import de.rasmusantons.enigmaticsbingogoals.triggers.*;
import io.github.gaming32.bingo.conditions.HasAnyEffectCondition;
import io.github.gaming32.bingo.data.BingoTags;
import io.github.gaming32.bingo.data.goal.BingoGoal;
import io.github.gaming32.bingo.data.icons.*;
import io.github.gaming32.bingo.data.progresstrackers.CriterionProgressTracker;
import io.github.gaming32.bingo.data.tags.bingo.BingoFeatureTags;
import io.github.gaming32.bingo.triggers.GrowFeatureTrigger;
import net.minecraft.advancements.predicates.BlockPredicate;
import net.minecraft.advancements.predicates.ContextAwarePredicate;
import net.minecraft.advancements.predicates.DamagePredicate;
import net.minecraft.advancements.predicates.DamageSourcePredicate;
import net.minecraft.advancements.predicates.DataComponentMatchers;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.predicates.LocationPredicate;
import net.minecraft.advancements.predicates.MinMaxBounds;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.advancements.predicates.TagPredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.predicates.entity.EntityTypePredicate;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.advancements.triggers.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentExactPredicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.predicates.DataComponentPredicates;
import net.minecraft.core.component.predicates.WrittenBookPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.packs.VanillaHusbandryAdvancements;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.minecraft.world.level.storage.loot.LootContext;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

import static de.rasmusantons.enigmaticsbingogoals.datagen.goal.BingoGoalGeneratorUtils.getAllEffectsIcon;
import static de.rasmusantons.enigmaticsbingogoals.datagen.goal.EnigmaticsBingoGoalIds.Easy.*;

public class EnigmaticsEasyGoalProvider extends EnigmaticsDifficultyGoalProvider {
    public EnigmaticsEasyGoalProvider(BiConsumer<Identifier, BingoGoal> goalAdder, HolderLookup.Provider registries) {
        super(EnigmaticsBingoDifficulties.EASY, goalAdder, registries);
    }

    @Override
    public void addGoals() {
        final var entityTypes = registries.lookupOrThrow(Registries.ENTITY_TYPE);
        final var items = registries.lookupOrThrow(Registries.ITEM);
        final var blocks = registries.lookupOrThrow(Registries.BLOCK);

        addGoal(BingoGoal.builder(NEVER_SEEDS)
                .criterion("obtain", InventoryChangeTrigger.TriggerInstance.hasItems(Items.WHEAT_SEEDS))
                .tags(EnigmaticsBingoTags.NEVER, BingoTags.LOCKOUT_INFLICTABLE)
                .antisynergy(EnigmaticsBingoSynergies.SEEDS)
                .name(Component.translatable("enigmaticsbingogoals.goal.never_wheat_seeds",
                        Component.translatable(Items.WHEAT_SEEDS.getDescriptionId())))
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.WHEAT_SEEDS), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(BingoGoal.builder(NEVER_TOUCH_WATER)
                .criterion("touch_water", TouchFluidTrigger.TriggerInstance.water())
                .tags(EnigmaticsBingoTags.NEVER, BingoTags.LOCKOUT_INFLICTABLE)
                .name(Component.translatable("enigmaticsbingogoals.goal.never_touch_water",
                        Component.translatable(Blocks.WATER.getDescriptionId())))
                .icon(IndicatorIcon.infer(BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.WATER), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(BingoGoal.builder(NEVER_FALL_DAMAGE)
                .criterion("damage", EntityHurtPlayerTrigger.TriggerInstance.entityHurtPlayer(
                        DamagePredicate.Builder.damageInstance().type(
                                DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(DamageTypeTags.IS_FALL))
                        )
                ))
                .tags(EnigmaticsBingoTags.NEVER, BingoTags.LOCKOUT_INFLICTABLE, EnigmaticsBingoTags.NEVER_TAKE_DAMAGE)
                .name(Component.translatable("enigmaticsbingogoals.goal.never_fall_damage"))
                .icon(new IndicatorIcon(EffectIcon.of(MobEffects.INSTANT_DAMAGE), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(BingoGoal.builder(NEVER_FIRE_DAMAGE)
                .criterion("damage", EntityHurtPlayerTrigger.TriggerInstance.entityHurtPlayer(
                        DamagePredicate.Builder.damageInstance().type(
                                DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(DamageTypeTags.IS_FIRE))
                        )
                ))
                .tags(EnigmaticsBingoTags.NEVER, BingoTags.LOCKOUT_INFLICTABLE, EnigmaticsBingoTags.NEVER_TAKE_DAMAGE)
                .name(Component.translatable("enigmaticsbingogoals.goal.never_fire_damage"))
                .icon(new IndicatorIcon(BlockIcon.ofBlock(Blocks.FIRE), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(neverLevelsGoal(NEVER_LEVELS, 2, 4));
        addGoal(advancementsGoal(GET_ADVANCEMENTS, 15, 20));
        addGoal(obtainItemGoal(OBTAIN_HEART_OF_THE_SEA, items, Items.HEART_OF_THE_SEA)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.BURIED_TREASURE, EnigmaticsBingoTags.SHIPWRECK)
        );
        addGoal(potionGoal(OBTAIN_POTION_OF_WATER_BREATHING, items,
                Potions.WATER_BREATHING, Potions.LONG_WATER_BREATHING)
                .tags(EnigmaticsBingoTags.BURIED_TREASURE, EnigmaticsBingoTags.SHIPWRECK)
        );
        // TODO (requires OVERTAKABLE): Have a higher level than the enemy
        addGoal(numberOfEffectsGoal(GET_SOME_EFFECTS, 3, 4)
                .tags(EnigmaticsBingoTags.PUFFER_FISH, EnigmaticsBingoTags.IGLOO,
                        EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.ANCIENT_CITY)
        );
        addGoal(effectGoal(GET_POISON, MobEffects.POISON)
                .tags(EnigmaticsBingoTags.PUFFER_FISH,
                        EnigmaticsBingoTags.BEEHIVE, EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.SWAMP,
                        EnigmaticsBingoTags.TRIAL_CHAMBER)
                .antisynergy(EnigmaticsBingoSynergies.POISON)
                .reactant(EnigmaticsBingoSynergies.SUSPICIOUS_STEW)
        );
        addGoal(effectGoal(GET_JUMP_BOOST, MobEffects.JUMP_BOOST)
                .antisynergy(EnigmaticsBingoSynergies.JUMP_BOOST)
                .reactant(EnigmaticsBingoSynergies.SUSPICIOUS_STEW)
        );
        addGoal(effectGoal(GET_BLINDNESS, MobEffects.BLINDNESS)
                .reactant(EnigmaticsBingoSynergies.BLINDNESS)
        );
        addGoal(effectGoal(GET_SATURATION, MobEffects.SATURATION)
                .antisynergy(EnigmaticsBingoSynergies.SATURATION)
                .reactant(EnigmaticsBingoSynergies.SATURATION)
        );
        addGoal(effectGoal(GET_ABSORPTION, MobEffects.ABSORPTION)
                .tags(EnigmaticsBingoTags.IGLOO, EnigmaticsBingoTags.WOODLAND_MANSION,
                        EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(eatItemGoal(EAT_SUSPICIOUS_STEW, items, Items.SUSPICIOUS_STEW)
                .tags(EnigmaticsBingoTags.END, EnigmaticsBingoTags.STEW)
                .catalyst(EnigmaticsBingoSynergies.SUSPICIOUS_STEW)
                .antisynergy(EnigmaticsBingoSynergies.GET_EFFECT_BATCH)
        );
        addGoal(BingoGoal.builder(CLEAR_EFFECT_WITH_MILK)
                .criterion("clear_effect", CriteriaTriggers.CONSUME_ITEM.createCriterion(
                        new ConsumeItemTrigger.TriggerInstance(
                                Optional.of(ContextAwarePredicate.create(new HasAnyEffectCondition(LootContext.EntityTarget.THIS))),
                                Optional.of(ItemPredicate.Builder.item().of(items, Items.MILK_BUCKET).build())
                        )
                ))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.MILK, EnigmaticsBingoTags.TRIAL_CHAMBER, EnigmaticsBingoTags.GET_EFFECT)
                .name(Component.translatable("enigmaticsbingogoals.goal.clear_effect_with_milk"))
                .icon(IndicatorIcon.infer(getAllEffectsIcon(), Items.MILK_BUCKET))
        );
        addGoal(reachLevelsGoal(REACH_LEVELS, 10, 15));
        addGoal(obtainSomeItemsFromTagGoal(OBTAIN_SOME_SMALL_FLOWERS, BlockItemTags.SMALL_FLOWERS.item(), 5, 9)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY, EnigmaticsBingoTags.PLANT_BATCH)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_small_flowers", 0),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(BingoGoal.builder(DEAL_500_HEARTS_OF_DAMAGE)
                .criterion("deal", DamageExceptTeamTrigger.TriggerInstance.dealtDamage(MinMaxBounds.Ints.atLeast(10000)))
                .progress(new CriterionProgressTracker("deal", 0.05f))
                .name(Component.translatable("enigmaticsbingogoals.goal.deal_some_hearts_of_damage", 500))
                .tags(EnigmaticsBingoTags.OVERWORLD_ENTRY)
                .tooltip(Component.translatable("enigmaticsbingogoals.goal.deal_some_hearts_of_damage.tooltip"))
                .icon(IndicatorIcon.infer(EntityIcon.ofSpawnEgg(EntityTypes.COW, 500), ItemIcon.ofItem(Items.NETHERITE_SWORD)))
        );
        addGoal(dieToMobEntityGoal(DIE_TO_BEE, entityTypes, EntityTypes.BEE)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.BEEHIVE)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_bee",
                        EntityTypes.BEE.getDescription()))
        );
        addGoal(dieToDamageTypeGoal(DIE_TO_FIREWORKS, EnigmaticsBingoDamageTypeTags.FIREWORKS)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_fireworks", Component.translatable(Items.FIREWORK_ROCKET.getDescriptionId())))
                .catalyst(EnigmaticsBingoSynergies.EXPLOSION)
                .icon(IndicatorIcon.infer(Items.FIREWORK_ROCKET, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        addGoal(breakBlockGoal(BREAK_DIAMOND_ORE, blocks, Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING)
        );
        addGoal(obtainItemGoal(OBTAIN_REPEATER, items, Items.REPEATER)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING,
                        EnigmaticsBingoTags.REDSTONE, EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(obtainItemGoal(OBTAIN_POWERED_RAIL, items, Items.POWERED_RAIL)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING,
                        EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(obtainItemGoal(OBTAIN_DETECTOR_RAIL, items, Items.DETECTOR_RAIL)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(obtainItemGoal(OBTAIN_ACTIVATOR_RAIL, items, Items.ACTIVATOR_RAIL)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.IGLOO, EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(obtainItemGoal(OBTAIN_PISTON, items, Items.PISTON)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(tameAnimalGoal(TAME_CAT, entityTypes, EntityTypes.CAT)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.TAME_ANIMAL, EnigmaticsBingoTags.WITCH_HUT, EnigmaticsBingoTags.VILLAGE)
                .antisynergy(EnigmaticsBingoSynergies.CAT)
                .icon(IndicatorIcon.infer(EntityTypes.CAT, new ItemTagCycleIcon(ItemTags.CAT_FOOD)))
        );
        addGoal(tameAnimalGoal(TAME_WOLF, entityTypes, EntityTypes.WOLF)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.TAME_ANIMAL)
                .antisynergy(EnigmaticsBingoSynergies.WOLF)
                .icon(IndicatorIcon.infer(EntityTypes.WOLF, ItemIcon.ofItem(Items.BONE)))
        );
        addGoal(breedAnimalGoal(BREED_RABBIT, entityTypes, EntityTypes.RABBIT)
                .tags(EnigmaticsBingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.RABBIT)
                .catalyst(EnigmaticsBingoSynergies.BABY)
        );
        addGoal(killEntityGoal(KILL_GHAST, entityTypes, EntityTypes.GHAST)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_ghast", EntityTypes.GHAST.getDescription()))
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.GHAST)
                .icon(IndicatorIcon.infer(BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.GHAST), Items.NETHERITE_SWORD))
        );
        addGoal(killEntityGoal(KILL_SNOW_GOLEM, entityTypes, EntityTypes.SNOW_GOLEM)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_snow_golem", EntityTypes.SNOW_GOLEM.getDescription()))
                .tags(EnigmaticsBingoTags.OVERWORLD)
        );
        addGoal(killEntitiesFromTagGoal(KILL_50_MOBS, entityTypes, EnigmaticsBingoEntityTypeTags.MOBS, 50, 50, false)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.UNDEAD_MOB_BATCH, EnigmaticsBingoSynergies.ARTHROPOD_MOB_BATCH, EnigmaticsBingoSynergies.AQUATIC_MOB_BATCH)
        );
        addGoal(killEntitiesFromTagGoal(KILL_100_MOBS, entityTypes, EnigmaticsBingoEntityTypeTags.MOBS, 100, 100, false)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.UNDEAD_MOB_BATCH, EnigmaticsBingoSynergies.ARTHROPOD_MOB_BATCH, EnigmaticsBingoSynergies.AQUATIC_MOB_BATCH)
        );
        addGoal(killEntitiesFromTagGoal(KILL_15_ARTHROPODS, entityTypes, EntityTypeTags.ARTHROPOD, 15, 15, false)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_of_tag", 0,
                                Component.translatable(EntityTypeTags.ARTHROPOD.getTranslationKey())),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.ARTHROPOD_MOB_BATCH)
        );
        addGoal(killEntitiesFromTagGoal(KILL_30_ARTHROPODS, entityTypes, EntityTypeTags.ARTHROPOD, 30, 30, false)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_of_tag", 0,
                                Component.translatable(EntityTypeTags.ARTHROPOD.getTranslationKey())),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.ARTHROPOD_MOB_BATCH)
        );
        addGoal(killEntitiesFromTagGoal(KILL_20_AQUATIC_MOBS, entityTypes, EntityTypeTags.AQUATIC, 20, 20, false)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_of_tag", 0,
                                Component.translatable(EntityTypeTags.AQUATIC.getTranslationKey())),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.AQUATIC_MOB_BATCH)
        );
        addGoal(killEntitiesFromTagGoal(KILL_40_AQUATIC_MOBS, entityTypes, EntityTypeTags.AQUATIC, 40, 40, false)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_of_tag", 0,
                                Component.translatable(EntityTypeTags.AQUATIC.getTranslationKey())),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.AQUATIC_MOB_BATCH)
        );
        addGoal(killEntitiesFromTagGoal(KILL_30_UNDEAD_MOBS, entityTypes, EntityTypeTags.UNDEAD, 30, 30, false)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_of_tag", 0,
                                Component.translatable(EntityTypeTags.UNDEAD.getTranslationKey())),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.UNDEAD_MOB_BATCH)
        );
        addGoal(killEntitiesFromTagGoal(KILL_50_UNDEAD_MOBS, entityTypes, EntityTypeTags.UNDEAD, 50, 50, false)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_of_tag", 0,
                                Component.translatable(EntityTypeTags.UNDEAD.getTranslationKey())),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.UNDEAD_MOB_BATCH)
        );
        addGoal(killEntitiesFromTagGoal(KILL_SOME_UNIQUE_MOBS, entityTypes, EnigmaticsBingoEntityTypeTags.MOBS, 10, 15, true)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_unique_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.UNIQUE_NEUTRAL_MOBS, EnigmaticsBingoSynergies.UNIQUE_HOSTILE_MOBS)
        );
        addGoal(BingoGoal.builder(CLEAN_ARMOR_IN_CAULDRON)
                        .criterion("sign", CleanArmorInCauldronTrigger.TriggerInstance.cleanArmor())
                        .name(Component.translatable("enigmaticsbingogoals.goal.clean_armor_in_cauldron",
                                Component.translatable(Items.CAULDRON.getDescriptionId())))
                        .tags(EnigmaticsBingoTags.ITEM, EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.VILLAGE)
                        .icon(IndicatorIcon.infer(
                                BlockIcon.ofBlock(Blocks.WATER_CAULDRON),
                                new ItemTagCycleIcon(ItemTags.CAULDRON_CAN_REMOVE_DYE)
                        ))
        );
        addGoal(BingoGoal.builder(FILL_A_CHISELED_BOOKSHELF)
                .criterion("use", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                        LocationPredicate.Builder.location().setBlock(
                                BlockPredicate.Builder.block()
                                        .of(blocks, Blocks.CHISELED_BOOKSHELF)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(ChiseledBookShelfBlock.SLOT_OCCUPIED_PROPERTIES.get(0), true)
                                                .hasProperty(ChiseledBookShelfBlock.SLOT_OCCUPIED_PROPERTIES.get(1), true)
                                                .hasProperty(ChiseledBookShelfBlock.SLOT_OCCUPIED_PROPERTIES.get(2), true)
                                                .hasProperty(ChiseledBookShelfBlock.SLOT_OCCUPIED_PROPERTIES.get(3), true)
                                                .hasProperty(ChiseledBookShelfBlock.SLOT_OCCUPIED_PROPERTIES.get(4), true)
                                                .hasProperty(ChiseledBookShelfBlock.SLOT_OCCUPIED_PROPERTIES.get(5), true)
                                        )
                        ),
                        ItemPredicate.Builder.item().of(items, EnigmaticsBingoItemTags.BOOKS)
                ))
                .name(Component.translatable("enigmaticsbingogoals.goal.fill_chiseled_bookshelf",
                        Component.translatable(Items.CHISELED_BOOKSHELF.getDescriptionId())))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.VILLAGE)
                .antisynergy(EnigmaticsBingoSynergies.BOOK)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.CHISELED_BOOKSHELF), new ItemTagCycleIcon(EnigmaticsBingoItemTags.BOOKS)))
        );
        addGoal(advancementGoal(GET_ADVANCEMENT_WHAT_A_DEAL,
                Component.translatable("advancements.adventure.trade.title"),
                Identifier.withDefaultNamespace("adventure/trade"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.IGLOO, EnigmaticsBingoTags.VILLAGE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.EMERALD), ItemIcon.ofItem(Blocks.GOLD_BLOCK)))
        );
        addGoal(eatItemGoal(EAT_RABBIT_STEW, items, Items.RABBIT_STEW)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.STEW)
        );
        addGoal(BingoGoal.builder(ARMOR_STAND_FULL_ARMOR)
                        .criterion("full", ArmorStandSwapTrigger.TriggerInstance.fullArmor(items))
                        .name(Component.translatable("enigmaticsbingogoals.goal.armor_stand_full_armor",
                                Component.translatable(Items.ARMOR_STAND.getDescriptionId())))
                        .tags(EnigmaticsBingoTags.ITEM, EnigmaticsBingoTags.OVERWORLD)
                        .icon(IndicatorIcon.infer(
                                EntityIcon.of(EntityTypes.ARMOR_STAND, new ItemStackTemplate(Items.ARMOR_STAND)),
                                BingoGoalGeneratorUtils.createAllDifferentMaterialsIcon(registries)
                        ))
        );
        addGoal(wearArmorPiecesGoal(WEAR_FULL_LEATHER, entityTypes, items, Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE,
                Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.ARMOR)
                .name(Component.translatable("enigmaticsbingogoals.goal.wear_full_leather"))
        );
        addGoal(wearArmorPiecesGoal(WEAR_FULL_IRON, entityTypes, items, Items.IRON_HELMET, Items.IRON_CHESTPLATE,
                        Items.IRON_LEGGINGS, Items.IRON_BOOTS)
                        .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.ARMOR)
                        .name(Component.translatable("enigmaticsbingogoals.goal.wear_full_iron"))
        );
        addGoal(eatItemGoal(EAT_GLOW_BERRIES, items, Items.GLOW_BERRIES)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.ANCIENT_CITY, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .antisynergy(EnigmaticsBingoSynergies.LUSH_CAVE)
        );
        addGoal(BingoGoal.builder(USE_CARTOGRAPHY_TABLE)
                .criterion("use", UseCartographyTableTrigger.TriggerInstance.used())
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.USE_WORKSTATION)
                .antisynergy(EnigmaticsBingoSynergies.MAP)
                .name(Component.translatable("enigmaticsbingogoals.goal.use_cartography_table", Component.translatable(Items.CARTOGRAPHY_TABLE.getDescriptionId())))
                .icon(BlockIcon.ofBlock(Blocks.CARTOGRAPHY_TABLE))
        );
        addGoal(makeBannerWithPatternItemGoal(USE_FLOWER_PATTERN, items, Items.FLOWER_BANNER_PATTERN,
                BannerPatterns.FLOWER, "Flower Charge Pattern")
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY, EnigmaticsBingoTags.VILLAGE)
        );
        addGoal(advancementProgressGoal(EAT_SOME_UNIQUE_FOODS,
                Identifier.withDefaultNamespace("husbandry/balanced_diet"), 7, 13)
                .name(Component.translatable("enigmaticsbingogoals.goal.eat_some_unique_foods", 0),
                        subber -> subber.sub("with.0", "count")
                )
                .tooltip(Component.translatable("enigmaticsbingogoals.goal.eat_some_unique_foods.tooltip", Component.translatable(Items.CAKE.getDescriptionId())))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.UNIQUE_FOOD)
                .icon(
                        CycleIcon.infer(Arrays.stream(VanillaHusbandryAdvancements.EDIBLE_ITEMS)),
                        subber -> subber.sub("icons.*.item.count", "count")
                )
        );
        addGoal(
                BingoGoal.builder(SIGN_BOOK_AND_QUILL)
                        .criterion("sign", WriteBookTrigger.TriggerInstance.signer())
                        .name(Component.translatable("enigmaticsbingogoals.goal.sign_book_and_quill",
                                Component.translatable(Items.WRITABLE_BOOK.getDescriptionId())))
                        .tags(EnigmaticsBingoTags.ITEM, EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.WRITE_BOOK)
                        .antisynergy(EnigmaticsBingoSynergies.BOOK)
                        .icon(ItemIcon.ofItem(Items.WRITABLE_BOOK))
        );
        addGoal(
                BingoGoal.builder(MAKE_COPY_OF_COPY)
                        .criterion("clone", RecipeCraftedTrigger.TriggerInstance.craftedItem(
                                ResourceKey.create(Registries.RECIPE, Identifier.withDefaultNamespace("book_cloning")),
                                List.of(ItemPredicate.Builder.item().withComponents(
                                        DataComponentMatchers.Builder.components()
                                                .partial(
                                                        DataComponentPredicates.WRITTEN_BOOK,
                                                        new WrittenBookPredicate(
                                                                Optional.empty(),
                                                                Optional.empty(),
                                                                Optional.empty(),
                                                                MinMaxBounds.Ints.exactly(1),
                                                                Optional.empty()))
                                                .build()
                                        )
                                )
                        ))
                        .name(Component.translatable("enigmaticsbingogoals.goal.make_copy_of_copy",
                                Component.translatable("book.generation.2")))
                        .tags(EnigmaticsBingoTags.ITEM, EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.WRITE_BOOK)
                        .antisynergy(EnigmaticsBingoSynergies.BOOK)
                        .icon(new ItemIcon(new ItemStackTemplate(Items.WRITTEN_BOOK, 3)))
        );
        addGoal(BingoGoal.builder(WEAR_PUMPKIN)
                .tags(
                        EnigmaticsBingoTags.OVERWORLD,
                        EnigmaticsBingoTags.OUTPOST,
                        EnigmaticsBingoTags.WOODLAND_MANSION
                )
                .criterion("wear", WearPumpkinTrigger.TriggerInstance.wearPumpkin(MinMaxBounds.Ints.atLeast(300)))
                .name(Component.translatable("enigmaticsbingogoals.goal.wear_pumpkin_for_some_minutes", 5))
                .icon(ItemIcon.ofItem(Items.CARVED_PUMPKIN))
                .progress("wear")
        );
        addGoal(obtainItemGoal(OBTAIN_MOSSY_STONE_BRICK_WALL, items, Items.MOSSY_STONE_BRICK_WALL)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.WALL, EnigmaticsBingoTags.IGLOO,
                        EnigmaticsBingoTags.JUNGLE, EnigmaticsBingoTags.SHIPWRECK, EnigmaticsBingoTags.TRIAL_CHAMBER,
                        EnigmaticsBingoTags.MOSS)
        );
        addGoal(obtainItemGoal(OBTAIN_MOSSY_COBBLESTONE_WALL, items, Items.MOSSY_COBBLESTONE_WALL)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.WALL, EnigmaticsBingoTags.JUNGLE,
                        EnigmaticsBingoTags.SHIPWRECK, EnigmaticsBingoTags.TRIAL_CHAMBER, EnigmaticsBingoTags.MOSS)
        );
        addGoal(obtainItemGoal(OBTAIN_POLISHED_TUFF_WALL, items, Items.POLISHED_TUFF_WALL)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.WALL)
        );
        addGoal(obtainAllItemsFromTagGoal(OBTAIN_ALL_IRON_TOOLS, EnigmaticsBingoItemTags.IRON_TOOLS)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_material_tools",
                        Component.translatable(EnigmaticsBingoItemTags.IRON_TOOLS.getTranslationKey())))
        );
        addGoal(obtainAllItemsFromTagGoal(OBTAIN_ALL_COPPER_TOOLS, EnigmaticsBingoItemTags.COPPER_TOOLS)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_material_tools",
                        Component.translatable(EnigmaticsBingoItemTags.COPPER_TOOLS.getTranslationKey())))
        );
        Map<Identifier, Item> easyConcreteColors = Map.ofEntries(
                Map.entry(OBTAIN_STACK_OF_RED_CONCRETE, Items.CONCRETE.red()),
                Map.entry(OBTAIN_STACK_OF_YELLOW_CONCRETE, Items.CONCRETE.yellow()),
                Map.entry(OBTAIN_STACK_OF_ORANGE_CONCRETE, Items.CONCRETE.orange()),
                Map.entry(OBTAIN_STACK_OF_BLACK_CONCRETE, Items.CONCRETE.black()),
                Map.entry(OBTAIN_STACK_OF_WHITE_CONCRETE, Items.CONCRETE.white()),
                Map.entry(OBTAIN_STACK_OF_GRAY_CONCRETE, Items.CONCRETE.gray()),
                Map.entry(OBTAIN_STACK_OF_LIGHT_GRAY_CONCRETE, Items.CONCRETE.lightGray()),
                Map.entry(OBTAIN_STACK_OF_PINK_CONCRETE, Items.CONCRETE.pink()),
                Map.entry(OBTAIN_STACK_OF_MAGENTA_CONCRETE, Items.CONCRETE.magenta()),
                Map.entry(OBTAIN_STACK_OF_BLUE_CONCRETE, Items.CONCRETE.blue()),
                Map.entry(OBTAIN_STACK_OF_PURPLE_CONCRETE, Items.CONCRETE.purple())
        );
        for (Map.Entry<Identifier, Item> concreteColor : easyConcreteColors.entrySet()) {
            addGoal(obtainItemGoal(concreteColor.getKey(), items, concreteColor.getValue(), 64)
                    .tags(EnigmaticsBingoTags.OVERWORLD)
                    .antisynergy(EnigmaticsBingoSynergies.CONCRETE)
                    .infrequency(10)
                    .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                            Component.translatable(concreteColor.getValue().getDescriptionId())))
            );
        }
        Map<Identifier, Item> easyWoolColors = Map.ofEntries(
                Map.entry(OBTAIN_STACK_OF_RED_WOOL, Items.WOOL.red()),
                Map.entry(OBTAIN_STACK_OF_YELLOW_WOOL, Items.WOOL.yellow()),
                Map.entry(OBTAIN_STACK_OF_ORANGE_WOOL, Items.WOOL.orange()),
                Map.entry(OBTAIN_STACK_OF_BLACK_WOOL, Items.WOOL.black()),
                Map.entry(OBTAIN_STACK_OF_WHITE_WOOL, Items.WOOL.white()),
                Map.entry(OBTAIN_STACK_OF_GRAY_WOOL, Items.WOOL.gray()),
                Map.entry(OBTAIN_STACK_OF_LIGHT_GRAY_WOOL, Items.WOOL.lightGray()),
                Map.entry(OBTAIN_STACK_OF_PINK_WOOL, Items.WOOL.pink()),
                Map.entry(OBTAIN_STACK_OF_MAGENTA_WOOL, Items.WOOL.magenta()),
                Map.entry(OBTAIN_STACK_OF_BLUE_WOOL, Items.WOOL.blue()),
                Map.entry(OBTAIN_STACK_OF_PURPLE_WOOL, Items.WOOL.purple())
        );
        for (Map.Entry<Identifier, Item> woolColor : easyWoolColors.entrySet()) {
            addGoal(obtainItemGoal(woolColor.getKey(), items, woolColor.getValue(), 64)
                    .tags(EnigmaticsBingoTags.OVERWORLD)
                    .antisynergy(EnigmaticsBingoSynergies.WOOL)
                    .infrequency(12)
                    .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                            Component.translatable(woolColor.getValue().getDescriptionId())))
            );
        }
        addGoal(BingoGoal.builder(ANGER_ZOMBIFIED_PIGLIN)
                .criterion("anger", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(
                        Optional.of(EntityPredicate.Builder.entity().of(entityTypes, EntityTypes.ZOMBIFIED_PIGLIN).build())
                ))
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY)
                .name(Component.translatable("enigmaticsbingogoals.goal.anger_zombified_piglin",
                        EntityTypes.ZOMBIFIED_PIGLIN.getDescription()))
                .icon(IndicatorIcon.infer(
                        EntityTypes.ZOMBIFIED_PIGLIN,
                        BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.ANGRY_BIRD))
                )
        );
        addGoal(advancementGoal(GET_ADVANCEMENT_WE_NEED_TO_GO_DEEPER,
                Component.translatable("advancements.story.enter_the_nether.title"),
                Identifier.withDefaultNamespace("story/enter_the_nether"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.NETHER_ENTRY)
                .icon(IndicatorIcon.infer(ItemIcon.ofItem(Items.FLINT_AND_STEEL), ItemIcon.ofItem(Blocks.GOLD_BLOCK)))
        );
        addGoal(BingoGoal.builder(GROW_TREE_IN_NETHER)
                .criterion("grow", GrowFeatureTrigger.builder()
                        .feature(BingoFeatureTags.TREES)
                        .location(
                                LocationPredicate.Builder.inDimension(Level.NETHER).build()
                        ).build())
                .name(Component.translatable("enigmaticsbingogoals.goal.grow_tree_in_nether"))
                .icon(IndicatorIcon.infer(
                        new ItemTagCycleIcon(EnigmaticsBingoItemTags.SAPLINGS),
                        Blocks.NETHERRACK
                ))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY,
                        EnigmaticsBingoTags.GROW_TREE));
        addGoal(effectGoal(GET_GLOWING, MobEffects.GLOWING)
                .tags(EnigmaticsBingoTags.NETHER_ENTRY)
        );
        addGoal(advancementGoal(GET_ADVANCEMENT_OH_SHINY,
                Component.translatable("advancements.nether.distract_piglin.title"),
                Identifier.withDefaultNamespace("nether/distract_piglin"))
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.BARTERING)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.GOLD_INGOT), ItemIcon.ofItem(Blocks.GOLD_BLOCK)))
        );
        addGoal(dieToMobEntityGoal(DIE_TO_DOLPHIN, entityTypes, EntityTypes.DOLPHIN)
                .tags(EnigmaticsBingoTags.OVERWORLD)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_dolphin",
                        EntityTypes.DOLPHIN.getDescription()))
        );
        addGoal(dieToMobEntityGoal(DIE_TO_IRON_GOLEM, entityTypes, EntityTypes.IRON_GOLEM)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.VILLAGE)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_iron_golem",
                        EntityTypes.IRON_GOLEM.getDescription()))
        );
        addGoal(dieToDamageTypeGoal(DIE_TO_STALACTITE, EnigmaticsBingoDamageTypeTags.STALACTITE)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO, EnigmaticsBingoTags.CAVING)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_stalactite"))
                .icon(IndicatorIcon.infer(Items.POINTED_DRIPSTONE, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        addGoal(eatItemGoal(EAT_POISONOUS_POTATO, items, Items.POISONOUS_POTATO)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.SHIPWRECK)
                .antisynergy(EnigmaticsBingoSynergies.POISON)
        );
        addGoal(advancementGoal(GET_ADVANCEMENT_RETURN_TO_SENDER,
                Component.translatable("advancements.nether.return_to_sender.title"),
                Identifier.withDefaultNamespace("nether/return_to_sender"))
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.GHAST)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.FIRE_CHARGE), ItemIcon.ofItem(Blocks.GOLD_BLOCK)))
        );
        addGoal(killEntityGoal(KILL_ZOMBIE_VILLAGER, entityTypes, EntityTypes.ZOMBIE_VILLAGER)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_zombie_villager", EntityTypes.ZOMBIE_VILLAGER.getDescription()))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.IGLOO)
        );
        addGoal(neverDamageGoal(NEVER_50_DAMAGE, 50));
        addGoal(obtainAllItemsFromTagGoal(OBTAIN_ALL_GOLDEN_TOOLS, EnigmaticsBingoItemTags.GOLDEN_TOOLS)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_material_tools",
                        Component.translatable(EnigmaticsBingoItemTags.GOLDEN_TOOLS.getTranslationKey())))
        );
        addGoal(obtainAllItemsFromTagGoal(OBTAIN_ALL_RAW_ORE_BLOCKS, EnigmaticsBingoItemTags.RAW_ORE_BLOCKS)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_all_raw_ore_blocks",
                        Component.translatable(EnigmaticsBingoItemTags.RAW_ORE_BLOCKS.getTranslationKey())))
        );
        addGoal(obtainItemGoal(OBTAIN_CAKE, items, Items.CAKE)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.MILK, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .antisynergy(EnigmaticsBingoSynergies.CHICKEN)
        );
        addGoal(obtainItemGoal(OBTAIN_DAYLIGHT_DETECTOR, items, Items.DAYLIGHT_DETECTOR)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.REDSTONE, EnigmaticsBingoTags.NETHER_ENTRY)
        );
        addGoal(obtainItemGoal(OBTAIN_DISPENSER, items, Items.DISPENSER)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING,
                        EnigmaticsBingoTags.REDSTONE, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(obtainItemGoal(OBTAIN_FLOWERING_AZALEA, items, Items.FLOWERING_AZALEA)
                .tags(EnigmaticsBingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.LUSH_CAVE)
        );
        addGoal(obtainItemGoal(OBTAIN_GRASS_BLOCK, items, Items.GRASS_BLOCK)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.SILK_TOUCH)
        );
        addGoal(obtainItemGoal(OBTAIN_MUD_BRICK_WALL, items, Items.MUD_BRICK_WALL)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.WALL, EnigmaticsBingoTags.TRAIL_RUINS)
        );
        addGoal(obtainSomeItemsFromTagGoal(OBTAIN_SOME_MUSIC_DISCS, EnigmaticsBingoItemTags.MUSIC_DISCS, 1, 2)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.ANCIENT_CITY, EnigmaticsBingoTags.TRAIL_RUINS, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .antisynergy(EnigmaticsBingoSynergies.MUSIC_DISC)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_music_discs", 0),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(dieToEntityGoal(DIE_TO_TNT_MINECART, entityTypes, EntityTypes.TNT_MINECART, ItemIcon.ofItem(Items.TNT_MINECART))
                .tags(EnigmaticsBingoTags.OVERWORLD)
                .catalyst(EnigmaticsBingoSynergies.EXPLOSION)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_tnt_minecart",
                        EntityTypes.TNT_MINECART.getDescription()))
        );
        addGoal(killEntitiesFromTagGoal(KILL_SOME_UNIQUE_HOSTILE_MOBS, entityTypes, EnigmaticsBingoEntityTypeTags.HOSTILE, 7, 10, true)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_unique_hostile_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.UNIQUE_HOSTILE_MOBS)
        );
        addGoal(BingoGoal.builder(KILL_BAT_WITH_ARROW)
                .criterion("kill", KilledTrigger.TriggerInstance.playerKilledEntity(
                        EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityTypes, EntityTypes.BAT)),
                        DamageSourcePredicate.Builder.damageType()
                                .direct(EntityPredicate.Builder.entity().of(entityTypes, EntityTypeTags.ARROWS))
                ))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .icon(IndicatorIcon.infer(EntityTypes.BAT, Items.ARROW))
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_bat_with_arrow", EntityTypes.BAT.getDescription(), EntityTypes.ARROW.getDescription()))
        );
        addGoal(BingoGoal.builder(KILL_RABBIT_WITH_ARROW)
                .criterion("kill", KilledTrigger.TriggerInstance.playerKilledEntity(
                        EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityTypes, EntityTypes.RABBIT)),
                        DamageSourcePredicate.Builder.damageType()
                                .direct(EntityPredicate.Builder.entity().of(entityTypes, EntityTypeTags.ARROWS))
                ))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.RABBIT)
                .icon(IndicatorIcon.infer(EntityTypes.RABBIT, Items.ARROW))
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_rabbit_with_arrow", EntityTypes.RABBIT.getDescription(), EntityTypes.ARROW.getDescription()))
        );
        addGoal(obtainSomeItemsFromTagGoal(OBTAIN_HANGING_SIGN, EnigmaticsBingoItemTags.HANGING_SIGNS, 1, 1)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.SIGN)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_hanging_sign"))
        );
        addGoal(obtainItemGoal(OBTAIN_GLOW_ITEM_FRAME, items, Items.GLOW_ITEM_FRAME)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.GLOW_INK)
        );
        addGoal(obtainItemGoal(OBTAIN_TARGET, items, Items.TARGET)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.VILLAGE)
        );
        // todo: should this allow any weathering/waxed state?
        addGoal(obtainItemGoal(OBTAIN_CHISELED_COPPER, items, Items.CHISELED_COPPER.weathering().unaffected())
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(obtainItemGoal(OBTAIN_CHISELED_DEEPSLATE, items, Items.CHISELED_DEEPSLATE)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING)
        );
        addGoal(obtainItemGoal(OBTAIN_CHISELED_NETHER_BRICKS, items, Items.CHISELED_NETHER_BRICKS)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(obtainItemGoal(OBTAIN_CHISELED_POLISHED_BLACKSTONE, items, Items.CHISELED_POLISHED_BLACKSTONE)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY)
        );
        Map<Identifier, Item> easyStainedGlassPaneColors = Map.ofEntries(
                Map.entry(OBTAIN_RED_STAINED_GLASS_PANE, Items.STAINED_GLASS_PANE.red()),
                Map.entry(OBTAIN_YELLOW_STAINED_GLASS_PANE, Items.STAINED_GLASS_PANE.yellow()),
                Map.entry(OBTAIN_ORANGE_STAINED_GLASS_PANE, Items.STAINED_GLASS_PANE.orange()),
                Map.entry(OBTAIN_BLACK_STAINED_GLASS_PANE, Items.STAINED_GLASS_PANE.black()),
                Map.entry(OBTAIN_WHITE_STAINED_GLASS_PANE, Items.STAINED_GLASS_PANE.white()),
                Map.entry(OBTAIN_GRAY_STAINED_GLASS_PANE, Items.STAINED_GLASS_PANE.gray()),
                Map.entry(OBTAIN_LIGHT_GRAY_STAINED_GLASS_PANE, Items.STAINED_GLASS_PANE.lightGray()),
                Map.entry(OBTAIN_PINK_STAINED_GLASS_PANE, Items.STAINED_GLASS_PANE.pink()),
                Map.entry(OBTAIN_MAGENTA_STAINED_GLASS_PANE, Items.STAINED_GLASS_PANE.magenta()),
                Map.entry(OBTAIN_BLUE_STAINED_GLASS_PANE, Items.STAINED_GLASS_PANE.blue()),
                Map.entry(OBTAIN_PURPLE_STAINED_GLASS_PANE, Items.STAINED_GLASS_PANE.purple())
        );
        for (Map.Entry<Identifier, Item> stainerGlassColor:  easyStainedGlassPaneColors.entrySet()) {
            addGoal(obtainItemGoal(stainerGlassColor.getKey(), items, stainerGlassColor.getValue())
                    .tags(EnigmaticsBingoTags.OVERWORLD)
                    .antisynergy(EnigmaticsBingoSynergies.STAINED_GLASS_PANE)
                    .infrequency(10)
            );
        }
        addGoal(BingoGoal.builder(UNIQUE_FOODS_ON_CAMPFIRE)
                .criterion("use", CriteriaTriggers.ITEM_USED_ON_BLOCK.createCriterion(
                        new ItemUsedOnLocationTrigger.TriggerInstance(
                                Optional.empty(),
                                Optional.of(ContextAwarePredicate.create(new UniqueFoodsOnCampfireCondition(MinMaxBounds.Ints.atLeast(4))))
                        )
                ))
                .name(Component.translatable("enigmaticsbingogoals.goal.unique_foods_on_campfire",
                        Component.translatable(Items.CAMPFIRE.getDescriptionId())))
                .tags(EnigmaticsBingoTags.OVERWORLD)
                .icon(new ItemIcon(new ItemStackTemplate(Items.CAMPFIRE, 4)))
        );
        addGoal(obtainItemGoal(OBTAIN_BELL, items, Items.BELL)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.VILLAGE)
        );
        addGoal(BingoGoal.builder(USE_LOOM)
                .criterion("use", UseLoomTrigger.TriggerInstance.used())
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.USE_WORKSTATION)
                .antisynergy(EnigmaticsBingoSynergies.LOOM)
                .name(Component.translatable("enigmaticsbingogoals.goal.use_loom", Component.translatable(Items.LOOM.getDescriptionId())))
                .icon(BlockIcon.ofBlock(Blocks.LOOM))
        );
        addGoal(BingoGoal.builder(SURVIVE_EXPLOSION)
                .criterion("survive", CriteriaTriggers.ENTITY_HURT_PLAYER.createCriterion(
                        new EntityHurtPlayerTrigger.TriggerInstance(
                                Optional.of(ContextAwarePredicate.create(
                                    PlayerAliveCondition.INSTANCE
                                )),
                                Optional.of(DamagePredicate.Builder.damageInstance().type(
                                        DamageSourcePredicate.Builder.damageType()
                                                .tag(TagPredicate.is(DamageTypeTags.IS_EXPLOSION))
                                                .build()
                                ).build())
                        ))
                )
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY)
                .reactant(EnigmaticsBingoSynergies.EXPLOSION)
                .name(Component.translatable("enigmaticsbingogoals.goal.survive_explosion"))
                .icon(IndicatorIcon.infer(BlockIcon.ofBlock(Blocks.TNT), EffectIcon.of(MobEffects.REGENERATION)))
        );
        addGoal(BingoGoal.builder(NAME_A_SHEEP_JEB)
                .criterion("use", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
                        ItemPredicate.Builder.item().of(items, Items.NAME_TAG).withComponents(DataComponentMatchers.Builder
                                .components()
                                .exact(DataComponentExactPredicate.expect(DataComponents.CUSTOM_NAME, Component.literal("jeb_")))
                                .build()),
                        Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(entityTypes, EntityTypes.SHEEP)))
                ))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.WOODLAND_MANSION)
                .name(Component.translatable("enigmaticsbingogoals.goal.name_a_sheep_jeb", EntityTypes.SHEEP.getDescription()))
                .tooltip(Component.translatable("enigmaticsbingogoals.goal.name_a_sheep_jeb.tooltip", EntityTypes.SHEEP.getDescription(), "jeb_"))
                .icon(IndicatorIcon.infer(EntityTypes.SHEEP, Items.NAME_TAG))
        );
        addGoal(advancementProgressGoal(FEED_GOLDEN_DANDELION_TO_DIFFERENT_MOBS,
                EnigmaticsBingoAdvancements.FEED_GOLDEN_DANDELION_TO_DIFFERENT_MOBS, 3, 5)
                .name(Component.translatable("enigmaticsbingogoals.goal.feed_golden_dandelion_to_different_mobs",
                                Component.translatable(Items.GOLDEN_DANDELION.getDescriptionId()), 0
                        ),
                        subber -> subber.sub("with.1", "count"))
                .tags(EnigmaticsBingoTags.OVERWORLD)
                .icon(
                        IndicatorIcon.infer(BingoGoalGeneratorUtils.getAgeLockableEntitiesIcon(registries), ItemIcon.ofItem(Items.GOLDEN_DANDELION)),
                        subber -> subber.sub("base.icons.*.item.count", "count")
                )
        );
        addGoal(BingoGoal.builder(NEVER_CROUCH)
                .criterion("crouch", PoseChangeTrigger.TriggerInstance.crouch())
                .name(Component.translatable("enigmaticsbingogoals.goal.never_crouch"))
                .antisynergy(EnigmaticsBingoSynergies.CROUCH)
                .tags(EnigmaticsBingoTags.NEVER)
                .icon(IndicatorIcon.infer(Items.LEATHER_BOOTS, Items.BARRIER))
        );
    }
}
