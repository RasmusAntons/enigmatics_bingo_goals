package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

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
import io.github.gaming32.bingo.util.ResourceLocations;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.packs.VanillaHusbandryAdvancements;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.minecraft.world.level.storage.loot.LootContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import static de.rasmusantons.enigmaticsbingogoals.datagen.goal.BingoGoalGeneratorUtils.getAllEffectsIcon;

public class EnigmaticsEasyGoalProvider extends EnigmaticsDifficultyGoalProvider {
    public EnigmaticsEasyGoalProvider(BiConsumer<ResourceLocation, BingoGoal> goalAdder, HolderLookup.Provider registries) {
        super(EnigmaticsBingoDifficulties.EASY, goalAdder, registries);
    }

    @Override
    public void addGoals() {
        final var entityTypes = registries.lookupOrThrow(Registries.ENTITY_TYPE);
        final var items = registries.lookupOrThrow(Registries.ITEM);
        final var blocks = registries.lookupOrThrow(Registries.BLOCK);

        addGoal(BingoGoal.builder(eid("never_seeds"))
                .criterion("obtain", InventoryChangeTrigger.TriggerInstance.hasItems(Items.WHEAT_SEEDS))
                .tags(BingoTags.NEVER)
                .antisynergy(EnigmaticsBingoSynergies.SEEDS)
                .name(Component.translatable("enigmaticsbingogoals.goal.never_wheat_seeds",
                        Items.WHEAT_SEEDS.getName()))
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.WHEAT_SEEDS), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(BingoGoal.builder(eid("never_touch_water"))
                .criterion("touch", EnterBlockTrigger.TriggerInstance.entersBlock(Blocks.WATER))
                .tags(BingoTags.NEVER)
                .name(Component.translatable("enigmaticsbingogoals.goal.never_touch_water",
                        Component.translatable(Blocks.WATER.getDescriptionId())))
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.WATER_BUCKET), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(BingoGoal.builder(eid("never_fall_damage"))
                .criterion("damage", EntityHurtPlayerTrigger.TriggerInstance.entityHurtPlayer(
                        DamagePredicate.Builder.damageInstance().type(
                                DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(DamageTypeTags.IS_FALL))
                        )
                ))
                .tags(BingoTags.NEVER, EnigmaticsBingoTags.NEVER_TAKE_DAMAGE)
                .name(Component.translatable("enigmaticsbingogoals.goal.never_fall_damage"))
                .icon(new IndicatorIcon(EffectIcon.of(MobEffects.HARM), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(BingoGoal.builder(eid("never_fire_damage"))
                .criterion("damage", EntityHurtPlayerTrigger.TriggerInstance.entityHurtPlayer(
                        DamagePredicate.Builder.damageInstance().type(
                                DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(DamageTypeTags.IS_FIRE))
                        )
                ))
                .tags(BingoTags.NEVER, EnigmaticsBingoTags.NEVER_TAKE_DAMAGE)
                .name(Component.translatable("enigmaticsbingogoals.goal.never_fire_damage"))
                .icon(new IndicatorIcon(BlockIcon.ofBlock(Blocks.FIRE), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(neverLevelsGoal(eid("never_levels"), 2, 4));
        addGoal(advancementsGoal(eid("get_advancements"), 15, 20));
        addGoal(obtainItemGoal(eid("obtain_heart_of_the_sea"), items, Items.HEART_OF_THE_SEA)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BURIED_TREASURE, EnigmaticsBingoTags.SHIPWRECK)
        );
        addGoal(potionGoal(eid("obtain_potion_of_water_breathing"), items,
                Potions.WATER_BREATHING, Potions.LONG_WATER_BREATHING)
                .tags(EnigmaticsBingoTags.BURIED_TREASURE, EnigmaticsBingoTags.SHIPWRECK)
        );
        // TODO (requires OVERTAKABLE): Have a higher level than the enemy
        addGoal(numberOfEffectsGoal(eid("get_some_effects"), 3, 4)
                .tags(EnigmaticsBingoTags.PUFFER_FISH, EnigmaticsBingoTags.IGLOO,
                        EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.ANCIENT_CITY)
        );
        addGoal(effectGoal(eid("get_poison"), MobEffects.POISON)
                .tags(EnigmaticsBingoTags.PUFFER_FISH,
                        EnigmaticsBingoTags.BEEHIVE, EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.SWAMP,
                        EnigmaticsBingoTags.TRIAL_CHAMBER)
                .antisynergy(EnigmaticsBingoSynergies.POISON)
                .reactant(EnigmaticsBingoSynergies.SUSPICIOUS_STEW)
        );
        addGoal(effectGoal(eid("get_jump_boost"), MobEffects.JUMP)
                .antisynergy(EnigmaticsBingoSynergies.JUMP_BOOST)
                .reactant(EnigmaticsBingoSynergies.SUSPICIOUS_STEW)
        );
        addGoal(effectGoal(eid("get_blindness"), MobEffects.BLINDNESS)
                .reactant(EnigmaticsBingoSynergies.BLINDNESS)
        );
        addGoal(effectGoal(eid("get_saturation"), MobEffects.SATURATION)
                .antisynergy(EnigmaticsBingoSynergies.SATURATION)
                .reactant(EnigmaticsBingoSynergies.SATURATION)
        );
        addGoal(effectGoal(eid("get_absorption"), MobEffects.ABSORPTION)
                .tags(EnigmaticsBingoTags.IGLOO, EnigmaticsBingoTags.WOODLAND_MANSION,
                        EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(eatItemGoal(eid("eat_suspicious_stew"), items, Items.SUSPICIOUS_STEW)
                .tags(BingoTags.END, EnigmaticsBingoTags.STEW)
                .catalyst(EnigmaticsBingoSynergies.SUSPICIOUS_STEW)
                .antisynergy(EnigmaticsBingoSynergies.GET_EFFECT_BATCH)
        );
        addGoal(BingoGoal.builder(eid("clear_effect_with_milk"))
                .criterion("clear_effect", CriteriaTriggers.CONSUME_ITEM.createCriterion(
                        new ConsumeItemTrigger.TriggerInstance(
                                Optional.of(ContextAwarePredicate.create(new HasAnyEffectCondition(LootContext.EntityTarget.THIS))),
                                Optional.of(ItemPredicate.Builder.item().of(items, Items.MILK_BUCKET).build())
                        )
                ))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MILK, EnigmaticsBingoTags.TRIAL_CHAMBER, EnigmaticsBingoTags.GET_EFFECT)
                .name(Component.translatable("enigmaticsbingogoals.goal.clear_effect_with_milk"))
                .icon(IndicatorIcon.infer(getAllEffectsIcon(), Items.MILK_BUCKET))
        );
        addGoal(reachLevelsGoal(eid("reach_levels"), 10, 15));
        addGoal(obtainSomeItemsFromTagGoal(eid("obtain_some_small_flowers"), ItemTags.SMALL_FLOWERS, 5, 9)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY, EnigmaticsBingoTags.PLANT_BATCH)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_small_flowers", 0),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(BingoGoal.builder(eid("deal_500_hearts_of_damage"))
                .criterion("deal", DamageExceptTeamTrigger.TriggerInstance.dealtDamage(MinMaxBounds.Ints.atLeast(10000)))
                .progress(new CriterionProgressTracker("deal", 0.05f))
                .name(Component.translatable("enigmaticsbingogoals.goal.deal_some_hearts_of_damage", 500))
                .tags(EnigmaticsBingoTags.OVERWORLD_ENTRY)
                .tooltip(Component.translatable("enigmaticsbingogoals.goal.deal_some_hearts_of_damage.tooltip"))
                .icon(IndicatorIcon.infer(EntityIcon.ofSpawnEgg(EntityType.COW, 500), ItemIcon.ofItem(Items.NETHERITE_SWORD)))
        );
        addGoal(dieToMobEntityGoal(eid("die_to_bee"), entityTypes, EntityType.BEE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BEEHIVE)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_bee",
                        EntityType.BEE.getDescription()))
        );
        addGoal(dieToDamageTypeGoal(eid("die_to_fireworks"), EnigmaticsBingoDamageTypeTags.FIREWORKS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_fireworks", Items.FIREWORK_ROCKET.getName()))
                .catalyst(EnigmaticsBingoSynergies.EXPLOSION)
                .icon(IndicatorIcon.infer(Items.FIREWORK_ROCKET, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        addGoal(breakBlockGoal(eid("break_diamond_ore"), blocks, Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING)
        );
        addGoal(obtainItemGoal(eid("obtain_repeater"), items, Items.REPEATER)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING,
                        EnigmaticsBingoTags.REDSTONE, EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(obtainItemGoal(eid("obtain_powered_rail"), items, Items.POWERED_RAIL)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING,
                        EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(obtainItemGoal(eid("obtain_detector_rail"), items, Items.DETECTOR_RAIL)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(obtainItemGoal(eid("obtain_activator_rail"), items, Items.ACTIVATOR_RAIL)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.IGLOO, EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(obtainItemGoal(eid("obtain_piston"), items, Items.PISTON)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(tameAnimalGoal(eid("tame_cat"), entityTypes, EntityType.CAT)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TAME_ANIMAL, EnigmaticsBingoTags.WITCH_HUT, BingoTags.VILLAGE)
                .antisynergy(EnigmaticsBingoSynergies.CAT)
        );
        addGoal(tameAnimalGoal(eid("tame_wolf"), entityTypes, EntityType.WOLF)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TAME_ANIMAL)
                .antisynergy(EnigmaticsBingoSynergies.WOLF)
        );
        addGoal(breedAnimalGoal(eid("breed_rabbit"), entityTypes, EntityType.RABBIT)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.RABBIT)
                .catalyst(EnigmaticsBingoSynergies.BABY)
        );
        addGoal(killEntityGoal(eid("kill_ghast"), entityTypes, EntityType.GHAST)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_ghast", EntityType.GHAST.getDescription()))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.GHAST)
                .icon(IndicatorIcon.infer(BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.GHAST), Items.NETHERITE_SWORD))
        );
        addGoal(killEntityGoal(eid("kill_snow_golem"), entityTypes, EntityType.SNOW_GOLEM)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_snow_golem", EntityType.SNOW_GOLEM.getDescription()))
                .tags(BingoTags.OVERWORLD)
        );
        addGoal(killEntitiesFromTagGoal(eid("kill_50_mobs"), EnigmaticsBingoEntityTypeTags.MOBS, 50, 50, false)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.UNDEAD_MOB_BATCH, EnigmaticsBingoSynergies.ARTHROPOD_MOB_BATCH, EnigmaticsBingoSynergies.AQUATIC_MOB_BATCH)
        );
        addGoal(killEntitiesFromTagGoal(eid("kill_100_mobs"), EnigmaticsBingoEntityTypeTags.MOBS, 100, 100, false)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.UNDEAD_MOB_BATCH, EnigmaticsBingoSynergies.ARTHROPOD_MOB_BATCH, EnigmaticsBingoSynergies.AQUATIC_MOB_BATCH)
        );
        addGoal(killEntitiesFromTagGoal(eid("kill_15_arthropods"), EntityTypeTags.ARTHROPOD, 15, 15, false)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_of_tag", 0,
                                Component.translatable(EntityTypeTags.ARTHROPOD.getTranslationKey())),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.ARTHROPOD_MOB_BATCH)
        );
        addGoal(killEntitiesFromTagGoal(eid("kill_30_arthropods"), EntityTypeTags.ARTHROPOD, 30, 30, false)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_of_tag", 0,
                                Component.translatable(EntityTypeTags.ARTHROPOD.getTranslationKey())),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.ARTHROPOD_MOB_BATCH)
        );
        addGoal(killEntitiesFromTagGoal(eid("kill_20_aquatic_mobs"), EntityTypeTags.AQUATIC, 20, 20, false)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_of_tag", 0,
                                Component.translatable(EntityTypeTags.AQUATIC.getTranslationKey())),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.AQUATIC_MOB_BATCH)
        );
        addGoal(killEntitiesFromTagGoal(eid("kill_40_aquatic_mobs"), EntityTypeTags.AQUATIC, 40, 40, false)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_of_tag", 0,
                                Component.translatable(EntityTypeTags.AQUATIC.getTranslationKey())),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.AQUATIC_MOB_BATCH)
        );
        addGoal(killEntitiesFromTagGoal(eid("kill_30_undead_mobs"), EntityTypeTags.UNDEAD, 30, 30, false)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_of_tag", 0,
                                Component.translatable(EntityTypeTags.UNDEAD.getTranslationKey())),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.UNDEAD_MOB_BATCH)
        );
        addGoal(killEntitiesFromTagGoal(eid("kill_50_undead_mobs"), EntityTypeTags.UNDEAD, 50, 50, false)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_of_tag", 0,
                                Component.translatable(EntityTypeTags.UNDEAD.getTranslationKey())),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.UNDEAD_MOB_BATCH)
        );
        addGoal(killEntitiesFromTagGoal(eid("kill_some_unique_mobs"), EnigmaticsBingoEntityTypeTags.MOBS, 10, 15, true)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_unique_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.UNIQUE_NEUTRAL_MOBS, EnigmaticsBingoSynergies.UNIQUE_HOSTILE_MOBS)
        );
        addGoal(BingoGoal.builder(eid("clean_armor_in_cauldron"))
                        .criterion("sign", CleanArmorInCauldronTrigger.TriggerInstance.cleanArmor())
                        .name(Component.translatable("enigmaticsbingogoals.goal.clean_armor_in_cauldron",
                                Items.CAULDRON.getName()))
                        .tags(BingoTags.ITEM, BingoTags.OVERWORLD, BingoTags.VILLAGE)
                        .icon(IndicatorIcon.infer(
                                BlockIcon.ofBlock(Blocks.WATER_CAULDRON),
                                new ItemTagCycleIcon(ItemTags.DYEABLE)
                        ))
        );
        addGoal(BingoGoal.builder(eid("fill_a_chiseled_bookshelf"))
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
                        Items.CHISELED_BOOKSHELF.getName()))
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE)
                .antisynergy(EnigmaticsBingoSynergies.BOOK)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.CHISELED_BOOKSHELF), new ItemTagCycleIcon(EnigmaticsBingoItemTags.BOOKS)))
        );
        addGoal(advancementGoal(eid("get_advancement_what_a_deal"),
                Component.translatable("advancements.adventure.trade.title"),
                ResourceLocation.withDefaultNamespace("adventure/trade"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.IGLOO, BingoTags.VILLAGE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.EMERALD), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(eatItemGoal(eid("eat_rabbit_stew"), items, Items.RABBIT_STEW)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE, EnigmaticsBingoTags.STEW)
        );
        addGoal(BingoGoal.builder(eid("armor_stand_full_armor"))
                        .criterion("full", ArmorStandSwapTrigger.TriggerInstance.fullArmor(items))
                        .name(Component.translatable("enigmaticsbingogoals.goal.armor_stand_full_armor",
                                Items.ARMOR_STAND.getName()))
                        .tags(BingoTags.ITEM, BingoTags.OVERWORLD)
                        .icon(IndicatorIcon.infer(
                                EntityIcon.of(EntityType.ARMOR_STAND, Items.ARMOR_STAND.getDefaultInstance()),
                                BingoGoalGeneratorUtils.createAllDifferentMaterialsIcon(registries)
                        ))
        );
        addGoal(wearArmorPiecesGoal(eid("wear_full_leather"), entityTypes, items, Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE,
                Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.ARMOR)
                .name(Component.translatable("enigmaticsbingogoals.goal.wear_full_leather"))
        );
        addGoal(wearArmorPiecesGoal(eid("wear_full_iron"), entityTypes, items, Items.IRON_HELMET, Items.IRON_CHESTPLATE,
                        Items.IRON_LEGGINGS, Items.IRON_BOOTS)
                        .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.ARMOR)
                        .name(Component.translatable("enigmaticsbingogoals.goal.wear_full_iron"))
        );
        addGoal(eatItemGoal(eid("eat_glow_berries"), items, Items.GLOW_BERRIES)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.ANCIENT_CITY, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .antisynergy(EnigmaticsBingoSynergies.LUSH_CAVE)
        );
        addGoal(BingoGoal.builder(eid("use_cartography_table"))
                .criterion("use", UseCartographyTableTrigger.TriggerInstance.used())
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY, BingoTags.VILLAGE, EnigmaticsBingoTags.USE_WORKSTATION)
                .antisynergy(EnigmaticsBingoSynergies.MAP)
                .name(Component.translatable("enigmaticsbingogoals.goal.use_cartography_table", Items.CARTOGRAPHY_TABLE.getName()))
                .icon(BlockIcon.ofBlock(Blocks.CARTOGRAPHY_TABLE))
        );
        addGoal(makeBannerWithPatternItemGoal(eid("use_flower_pattern"), items, Items.FLOWER_BANNER_PATTERN,
                BannerPatterns.FLOWER, "Flower Charge Pattern")
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY, BingoTags.VILLAGE)
        );
        addGoal(advancementProgressGoal(eid("eat_some_unique_foods"),
                ResourceLocation.withDefaultNamespace("husbandry/balanced_diet"), 7, 13)
                .name(Component.translatable("enigmaticsbingogoals.goal.eat_some_unique_foods", 0),
                        subber -> subber.sub("with.0", "count")
                )
                .tooltip(Component.translatable("enigmaticsbingogoals.goal.eat_some_unique_foods.tooltip", Items.CAKE.getName()))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.UNIQUE_FOOD)
                .icon(
                        CycleIcon.infer(Arrays.stream(VanillaHusbandryAdvancements.EDIBLE_ITEMS)),
                        subber -> subber.sub("icons.*.item.count", "count")
                )
        );
        addGoal(
                BingoGoal.builder(eid("sign_book_and_quill"))
                        .criterion("sign", WriteBookTrigger.TriggerInstance.signer())
                        .name(Component.translatable("enigmaticsbingogoals.goal.sign_book_and_quill",
                                Items.WRITABLE_BOOK.getName()))
                        .tags(BingoTags.ITEM, BingoTags.OVERWORLD, EnigmaticsBingoTags.WRITE_BOOK)
                        .antisynergy(EnigmaticsBingoSynergies.BOOK)
                        .icon(ItemIcon.ofItem(Items.WRITABLE_BOOK))
        );
        addGoal(
                BingoGoal.builder(eid("make_copy_of_copy"))
                        .criterion("clone", RecipeCraftedTrigger.TriggerInstance.craftedItem(
                                ResourceKey.create(Registries.RECIPE, ResourceLocations.minecraft("book_cloning")),
                                List.of(ItemPredicate.Builder.item().withSubPredicate(
                                        ItemSubPredicates.WRITTEN_BOOK,
                                        new ItemWrittenBookPredicate(
                                                Optional.empty(),
                                                Optional.empty(),
                                                Optional.empty(),
                                                MinMaxBounds.Ints.exactly(1),
                                                Optional.empty()
                                        )
                                ))
                        ))
                        .name(Component.translatable("enigmaticsbingogoals.goal.make_copy_of_copy",
                                Component.translatable("book.generation.2")))
                        .tags(BingoTags.ITEM, BingoTags.OVERWORLD, EnigmaticsBingoTags.WRITE_BOOK)
                        .antisynergy(EnigmaticsBingoSynergies.BOOK)
                        .icon(new ItemIcon(new ItemStack(Items.WRITTEN_BOOK, 3)))
        );
        addGoal(BingoGoal.builder(eid("wear_pumpkin"))
                .tags(
                        BingoTags.OVERWORLD,
                        EnigmaticsBingoTags.OUTPOST,
                        EnigmaticsBingoTags.WOODLAND_MANSION
                )
                .criterion("wear", WearPumpkinTrigger.TriggerInstance.wearPumpkin(MinMaxBounds.Ints.atLeast(300)))
                .name(Component.translatable("enigmaticsbingogoals.goal.wear_pumpkin_for_some_minutes", 5))
                .icon(ItemIcon.ofItem(Items.CARVED_PUMPKIN))
                .progress("wear")
        );
        addGoal(obtainItemGoal(eid("obtain_mossy_stone_brick_wall"), items, Items.MOSSY_STONE_BRICK_WALL)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WALL, EnigmaticsBingoTags.IGLOO,
                        EnigmaticsBingoTags.JUNGLE, EnigmaticsBingoTags.SHIPWRECK, EnigmaticsBingoTags.TRIAL_CHAMBER,
                        EnigmaticsBingoTags.MOSS)
        );
        addGoal(obtainItemGoal(eid("obtain_mossy_cobblestone_wall"), items, Items.MOSSY_COBBLESTONE_WALL)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WALL, EnigmaticsBingoTags.JUNGLE,
                        EnigmaticsBingoTags.SHIPWRECK, EnigmaticsBingoTags.TRIAL_CHAMBER, EnigmaticsBingoTags.MOSS)
        );
        addGoal(obtainItemGoal(eid("obtain_polished_tuff_wall"), items, Items.POLISHED_TUFF_WALL)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WALL)
        );
        addGoal(obtainAllItemsFromTagGoal(eid("obtain_all_iron_tools"), EnigmaticsBingoItemTags.IRON_TOOLS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_material_tools",
                        Component.translatable(EnigmaticsBingoItemTags.IRON_TOOLS.getTranslationKey())))
        );
        addGoal(obtainItemGoal(eid("obtain_stack_of_red_concrete"), items, Items.RED_CONCRETE, 64)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.CONCRETE)
                .infrequency(10)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.RED_CONCRETE.getName()))
        );
        addGoal(obtainItemGoal(eid("obtain_stack_of_yellow_concrete"), items, Items.YELLOW_CONCRETE, 64)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.CONCRETE)
                .infrequency(10)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.YELLOW_CONCRETE.getName()))
        );
        addGoal(obtainItemGoal(eid("obtain_stack_of_orange_concrete"), items, Items.ORANGE_CONCRETE, 64)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.CONCRETE)
                .infrequency(10)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.ORANGE_CONCRETE.getName()))
        );
        addGoal(obtainItemGoal(eid("obtain_stack_of_black_concrete"), items, Items.BLACK_CONCRETE, 64)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.CONCRETE)
                .infrequency(10)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.BLACK_CONCRETE.getName()))
        );
        addGoal(obtainItemGoal(eid("obtain_stack_of_white_concrete"), items, Items.WHITE_CONCRETE, 64)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.CONCRETE)
                .infrequency(10)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.WHITE_CONCRETE.getName()))
        );
        addGoal(obtainItemGoal(eid("obtain_stack_of_gray_concrete"), items, Items.GRAY_CONCRETE, 64)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.CONCRETE)
                .infrequency(10)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.GRAY_CONCRETE.getName()))
        );
        addGoal(obtainItemGoal(eid("obtain_stack_of_light_gray_concrete"), items, Items.LIGHT_GRAY_CONCRETE, 64)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.CONCRETE)
                .infrequency(10)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.LIGHT_GRAY_CONCRETE.getName()))
        );
        addGoal(obtainItemGoal(eid("obtain_stack_of_pink_concrete"), items, Items.PINK_CONCRETE, 64)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.CONCRETE)
                .infrequency(10)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.PINK_CONCRETE.getName()))
        );
        addGoal(obtainItemGoal(eid("obtain_stack_of_magenta_concrete"), items, Items.MAGENTA_CONCRETE, 64)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.CONCRETE)
                .infrequency(10)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.MAGENTA_CONCRETE.getName()))
        );
        addGoal(obtainItemGoal(eid("obtain_stack_of_blue_concrete"), items, Items.BLUE_CONCRETE, 64)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.CONCRETE)
                .infrequency(10)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.BLUE_CONCRETE.getName()))
        );
        addGoal(obtainItemGoal(eid("obtain_stack_of_purple_concrete"), items, Items.PURPLE_CONCRETE, 64)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.CONCRETE)
                .infrequency(10)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.PURPLE_CONCRETE.getName()))
        );
        addGoal(obtainItemGoal(eid("obtain_stack_of_red_wool"), items, Items.RED_WOOL, 64)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.WOOL)
                .infrequency(12)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.RED_WOOL.getName()))
        );
        addGoal(obtainItemGoal(eid("obtain_stack_of_yellow_wool"), items, Items.YELLOW_WOOL, 64)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.WOOL)
                .infrequency(12)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.YELLOW_WOOL.getName()))
        );
        addGoal(obtainItemGoal(eid("obtain_stack_of_orange_wool"), items, Items.ORANGE_WOOL, 64)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.WOOL)
                .infrequency(12)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.ORANGE_WOOL.getName()))
        );
        addGoal(obtainItemGoal(eid("obtain_stack_of_black_wool"), items, Items.BLACK_WOOL, 64)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.WOOL)
                .infrequency(12)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.BLACK_WOOL.getName()))
        );
        addGoal(obtainItemGoal(eid("obtain_stack_of_white_wool"), items, Items.WHITE_WOOL, 64)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.WOOL)
                .infrequency(12)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.WHITE_WOOL.getName()))
        );
        addGoal(obtainItemGoal(eid("obtain_stack_of_gray_wool"), items, Items.GRAY_WOOL, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.ANCIENT_CITY)
                .antisynergy(EnigmaticsBingoSynergies.WOOL)
                .infrequency(12)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.GRAY_WOOL.getName()))
        );
        addGoal(obtainItemGoal(eid("obtain_stack_of_light_gray_wool"), items, Items.LIGHT_GRAY_WOOL, 64)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.WOOL)
                .infrequency(12)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.LIGHT_GRAY_WOOL.getName()))
        );
        addGoal(obtainItemGoal(eid("obtain_stack_of_pink_wool"), items, Items.PINK_WOOL, 64)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.WOOL)
                .infrequency(12)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.PINK_WOOL.getName()))
        );
        addGoal(obtainItemGoal(eid("obtain_stack_of_magenta_wool"), items, Items.MAGENTA_WOOL, 64)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.WOOL)
                .infrequency(12)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.MAGENTA_WOOL.getName()))
        );
        addGoal(obtainItemGoal(eid("obtain_stack_of_blue_wool"), items, Items.BLUE_WOOL, 64)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.WOOL)
                .infrequency(12)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.BLUE_WOOL.getName()))
        );
        addGoal(obtainItemGoal(eid("obtain_stack_of_purple_wool"), items, Items.PURPLE_WOOL, 64)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.WOOL)
                .infrequency(12)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.PURPLE_WOOL.getName()))
        );
        addGoal(BingoGoal.builder(eid("anger_zombified_piglin"))
                .criterion("anger", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(
                        Optional.of(EntityPredicate.Builder.entity().of(entityTypes, EntityType.ZOMBIFIED_PIGLIN).build())
                ))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY)
                .name(Component.translatable("enigmaticsbingogoals.goal.anger_zombified_piglin",
                        EntityType.ZOMBIFIED_PIGLIN.getDescription()))
                .icon(IndicatorIcon.infer(
                        EntityType.ZOMBIFIED_PIGLIN,
                        BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.ANGRY_BIRD))
                )
        );
        addGoal(advancementGoal(eid("get_advancement_we_need_to_go_deeper"),
                Component.translatable("advancements.story.enter_the_nether.title"),
                ResourceLocation.withDefaultNamespace("story/enter_the_nether"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.NETHER_ENTRY)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.FLINT_AND_STEEL), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(BingoGoal.builder(eid("grow_tree_in_nether"))
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
                .tags(BingoTags.OVERWORLD, BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY,
                        EnigmaticsBingoTags.GROW_TREE));
        addGoal(effectGoal(eid("get_glowing"), MobEffects.GLOWING)
                .tags(EnigmaticsBingoTags.NETHER_ENTRY)
        );
        addGoal(advancementGoal(eid("get_advancement_oh_shiny"),
                Component.translatable("advancements.nether.distract_piglin.title"),
                ResourceLocation.withDefaultNamespace("nether/distract_piglin"))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.BARTERING)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.GOLD_INGOT), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(dieToMobEntityGoal(eid("die_to_dolphin"), entityTypes, EntityType.DOLPHIN)
                .tags(BingoTags.OVERWORLD)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_dolphin",
                        EntityType.DOLPHIN.getDescription()))
        );
        addGoal(dieToMobEntityGoal(eid("die_to_iron_golem"), entityTypes, EntityType.IRON_GOLEM)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_iron_golem",
                        EntityType.IRON_GOLEM.getDescription()))
        );
        addGoal(dieToDamageTypeGoal(eid("die_to_stalactite"), EnigmaticsBingoDamageTypeTags.STALACTITE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO, EnigmaticsBingoTags.CAVING)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_stalactite"))
                .icon(IndicatorIcon.infer(Items.POINTED_DRIPSTONE, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        addGoal(eatItemGoal(eid("eat_poisonous_potato"), items, Items.POISONOUS_POTATO)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE, EnigmaticsBingoTags.SHIPWRECK)
                .antisynergy(EnigmaticsBingoSynergies.POISON)
        );
        addGoal(advancementGoal(eid("get_advancement_return_to_sender"),
                Component.translatable("advancements.nether.return_to_sender.title"),
                ResourceLocation.withDefaultNamespace("nether/return_to_sender"))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.GHAST)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.FIRE_CHARGE), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(killEntityGoal(eid("kill_zombie_villager"), entityTypes, EntityType.ZOMBIE_VILLAGER)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_zombie_villager", EntityType.ZOMBIE_VILLAGER.getDescription()))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.IGLOO)
        );
        addGoal(neverDamageGoal(eid("never_50_damage"), 50));
        addGoal(obtainAllItemsFromTagGoal(eid("obtain_all_golden_tools"), EnigmaticsBingoItemTags.GOLDEN_TOOLS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_material_tools",
                        Component.translatable(EnigmaticsBingoItemTags.GOLDEN_TOOLS.getTranslationKey())))
        );
        addGoal(obtainAllItemsFromTagGoal(eid("obtain_all_raw_ore_blocks"), EnigmaticsBingoItemTags.RAW_ORE_BLOCKS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_all_raw_ore_blocks",
                        Component.translatable(EnigmaticsBingoItemTags.RAW_ORE_BLOCKS.getTranslationKey())))
        );
        addGoal(obtainItemGoal(eid("obtain_cake"), items, Items.CAKE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MILK, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .antisynergy(EnigmaticsBingoSynergies.CHICKEN)
        );
        addGoal(obtainItemGoal(eid("obtain_daylight_detector"), items, Items.DAYLIGHT_DETECTOR)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.REDSTONE, EnigmaticsBingoTags.NETHER_ENTRY)
        );
        addGoal(obtainItemGoal(eid("obtain_dispenser"), items, Items.DISPENSER)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING,
                        EnigmaticsBingoTags.REDSTONE, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(obtainItemGoal(eid("obtain_flowering_azalea"), items, Items.FLOWERING_AZALEA)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.LUSH_CAVE)
        );
        addGoal(obtainItemGoal(eid("obtain_grass_block"), items, Items.GRASS_BLOCK)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE, EnigmaticsBingoTags.SILK_TOUCH)
        );
        addGoal(obtainItemGoal(eid("obtain_mud_brick_wall"), items, Items.MUD_BRICK_WALL)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WALL, EnigmaticsBingoTags.TRAIL_RUINS)
        );
        addGoal(obtainSomeItemsFromTagGoal(eid("obtain_some_music_discs"), EnigmaticsBingoItemTags.MUSIC_DISCS, 1, 2)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.ANCIENT_CITY, EnigmaticsBingoTags.TRAIL_RUINS, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .antisynergy(EnigmaticsBingoSynergies.MUSIC_DISC)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_music_discs", 0),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(dieToEntityGoal(eid("die_to_tnt_minecart"), entityTypes, EntityType.TNT_MINECART, ItemIcon.ofItem(Items.TNT_MINECART))
                .tags(BingoTags.OVERWORLD)
                .catalyst(EnigmaticsBingoSynergies.EXPLOSION)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_tnt_minecart",
                        EntityType.TNT_MINECART.getDescription()))
        );
        addGoal(killEntitiesFromTagGoal(eid("kill_some_unique_hostile_mobs"), EnigmaticsBingoEntityTypeTags.HOSTILE, 7, 10, true)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_unique_hostile_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.UNIQUE_HOSTILE_MOBS)
        );
        addGoal(BingoGoal.builder(eid("kill_bat_with_arrow"))
                .criterion("kill", KilledTrigger.TriggerInstance.playerKilledEntity(
                        EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityTypes, EntityType.BAT)),
                        DamageSourcePredicate.Builder.damageType()
                                .direct(EntityPredicate.Builder.entity().of(entityTypes, EntityTypeTags.ARROWS))
                ))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .icon(IndicatorIcon.infer(EntityType.BAT, Items.ARROW))
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_bat_with_arrow", EntityType.BAT.getDescription(), EntityType.ARROW.getDescription()))
        );
        addGoal(BingoGoal.builder(eid("kill_rabbit_with_arrow"))
                .criterion("kill", KilledTrigger.TriggerInstance.playerKilledEntity(
                        EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityTypes, EntityType.RABBIT)),
                        DamageSourcePredicate.Builder.damageType()
                                .direct(EntityPredicate.Builder.entity().of(entityTypes, EntityTypeTags.ARROWS))
                ))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.RABBIT)
                .icon(IndicatorIcon.infer(EntityType.RABBIT, Items.ARROW))
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_rabbit_with_arrow", EntityType.RABBIT.getDescription(), EntityType.ARROW.getDescription()))
        );
        addGoal(obtainSomeItemsFromTagGoal(eid("obtain_hanging_sign"), EnigmaticsBingoItemTags.HANGING_SIGNS, 1, 1)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.SIGN)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_hanging_sign"))
        );
        addGoal(obtainItemGoal(eid("obtain_glow_item_frame"), items, Items.GLOW_ITEM_FRAME)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.GLOW_INK)
        );
        addGoal(obtainItemGoal(eid("obtain_target"), items, Items.TARGET)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE)
        );
        addGoal(obtainItemGoal(eid("obtain_chiseled_copper"), items, Items.CHISELED_COPPER)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(obtainItemGoal(eid("obtain_chiseled_deepslate"), items, Items.CHISELED_DEEPSLATE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING)
        );
        addGoal(obtainItemGoal(eid("obtain_chiseled_nether_bricks"), items, Items.CHISELED_NETHER_BRICKS)
                .tags(BingoTags.OVERWORLD, BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(obtainItemGoal(eid("obtain_chiseled_polished_blackstone"), items, Items.CHISELED_POLISHED_BLACKSTONE)
                .tags(BingoTags.OVERWORLD, BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY)
        );
        addGoal(obtainItemGoal(eid("obtain_black_stained_glass_pane"), items, Items.BLACK_STAINED_GLASS_PANE)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.STAINED_GLASS_PANE)
                .infrequency(10)
        );
        addGoal(obtainItemGoal(eid("obtain_blue_stained_glass_pane"), items, Items.BLUE_STAINED_GLASS_PANE)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.STAINED_GLASS_PANE)
                .infrequency(10)
        );
        addGoal(obtainItemGoal(eid("obtain_gray_stained_glass_pane"), items, Items.GRAY_STAINED_GLASS_PANE)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.STAINED_GLASS_PANE)
                .infrequency(10)
        );
        addGoal(obtainItemGoal(eid("obtain_light_gray_stained_glass_pane"), items, Items.LIGHT_GRAY_STAINED_GLASS_PANE)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.STAINED_GLASS_PANE)
                .infrequency(10)
        );
        addGoal(obtainItemGoal(eid("obtain_magenta_stained_glass_pane"), items, Items.MAGENTA_STAINED_GLASS_PANE)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.STAINED_GLASS_PANE)
                .infrequency(10)
        );
        addGoal(obtainItemGoal(eid("obtain_orange_stained_glass_pane"), items, Items.ORANGE_STAINED_GLASS_PANE)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.STAINED_GLASS_PANE)
                .infrequency(10)
        );
        addGoal(obtainItemGoal(eid("obtain_pink_stained_glass_pane"), items, Items.PINK_STAINED_GLASS_PANE)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.STAINED_GLASS_PANE)
                .infrequency(10)
        );
        addGoal(obtainItemGoal(eid("obtain_purple_stained_glass_pane"), items, Items.PURPLE_STAINED_GLASS_PANE)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.STAINED_GLASS_PANE)
                .infrequency(10)
        );
        addGoal(obtainItemGoal(eid("obtain_red_stained_glass_pane"), items, Items.RED_STAINED_GLASS_PANE)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.STAINED_GLASS_PANE)
                .infrequency(10)
        );
        addGoal(obtainItemGoal(eid("obtain_white_stained_glass_pane"), items, Items.WHITE_STAINED_GLASS_PANE)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.STAINED_GLASS_PANE)
                .infrequency(10)
        );
        addGoal(obtainItemGoal(eid("obtain_yellow_stained_glass_pane"), items, Items.YELLOW_STAINED_GLASS_PANE)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.STAINED_GLASS_PANE)
                .infrequency(10)
        );
        addGoal(BingoGoal.builder(eid("unique_foods_on_campfire"))
                .criterion("use", CriteriaTriggers.ITEM_USED_ON_BLOCK.createCriterion(
                        new ItemUsedOnLocationTrigger.TriggerInstance(
                                Optional.empty(),
                                Optional.of(ContextAwarePredicate.create(new UniqueFoodsOnCampfireCondition(MinMaxBounds.Ints.atLeast(4))))
                        )
                ))
                .name(Component.translatable("enigmaticsbingogoals.goal.unique_foods_on_campfire",
                        Items.CAMPFIRE.getName()))
                .tags(BingoTags.OVERWORLD)
                .icon(new ItemIcon(new ItemStack(Items.CAMPFIRE, 4)))
        );
        addGoal(obtainItemGoal(eid("obtain_bell"), items, Items.BELL)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE)
        );
        addGoal(BingoGoal.builder(eid("use_loom"))
                .criterion("use", UseLoomTrigger.TriggerInstance.used())
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY, BingoTags.VILLAGE, EnigmaticsBingoTags.USE_WORKSTATION)
                .antisynergy(EnigmaticsBingoSynergies.LOOM)
                .name(Component.translatable("enigmaticsbingogoals.goal.use_loom", Items.LOOM.getName()))
                .icon(BlockIcon.ofBlock(Blocks.LOOM))
        );
        addGoal(BingoGoal.builder(eid("survive_explosion"))
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
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY)
                .reactant(EnigmaticsBingoSynergies.EXPLOSION)
                .name(Component.translatable("enigmaticsbingogoals.goal.survive_explosion"))
                .icon(IndicatorIcon.infer(BlockIcon.ofBlock(Blocks.TNT), EffectIcon.of(MobEffects.REGENERATION)))
        );
    }
}
