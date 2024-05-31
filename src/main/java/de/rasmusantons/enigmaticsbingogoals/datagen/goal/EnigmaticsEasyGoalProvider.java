package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoTags;
import de.rasmusantons.enigmaticsbingogoals.conditions.FullUniqueInventoryCondition;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoDamageTypeTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoEntityTypeTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoItemTags;
import de.rasmusantons.enigmaticsbingogoals.triggers.DamageExceptTeamTrigger;
import de.rasmusantons.enigmaticsbingogoals.triggers.EmptyHungerTrigger;
import de.rasmusantons.enigmaticsbingogoals.triggers.WearPumpkinTrigger;
import io.github.gaming32.bingo.conditions.HasAnyEffectCondition;
import io.github.gaming32.bingo.data.BingoDifficulties;
import io.github.gaming32.bingo.data.BingoGoal;
import io.github.gaming32.bingo.data.BingoTags;
import io.github.gaming32.bingo.data.icons.*;
import io.github.gaming32.bingo.data.progresstrackers.CriterionProgressTracker;
import io.github.gaming32.bingo.triggers.ChickenHatchTrigger;
import io.github.gaming32.bingo.triggers.RelativeStatsTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.advancements.packs.VanillaHusbandryAdvancements;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;

public class EnigmaticsEasyGoalProvider extends EnigmaticsDifficultyGoalProvider {
    public EnigmaticsEasyGoalProvider(Consumer<BingoGoal.Holder> goalAdder, HolderLookup.Provider registries) {
        super(BingoDifficulties.EASY, goalAdder, registries);
    }

    @Override
    public void addGoals() {
        addGoal(BingoGoal.builder(id("never_seeds"))
                .criterion("obtain", InventoryChangeTrigger.TriggerInstance.hasItems(Items.WHEAT_SEEDS))
                .tags(
                        BingoTags.NEVER,
                        EnigmaticsBingoTags.SEEDS
                )
                .name(Component.translatable("enigmaticsbingogoals.goal.never_wheat_seeds",
                        Items.WHEAT_SEEDS.getDescription()))
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.WHEAT_SEEDS), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(BingoGoal.builder(id("never_touch_water"))
                .criterion("touch", EnterBlockTrigger.TriggerInstance.entersBlock(Blocks.WATER))
                .tags(BingoTags.NEVER)
                .name(Component.translatable("enigmaticsbingogoals.goal.never_touch_water",
                        Component.translatable(Blocks.WATER.getDescriptionId())))
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.WATER_BUCKET), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(BingoGoal.builder(id("never_damage"))
                .criterion("damage", EntityHurtPlayerTrigger.TriggerInstance.entityHurtPlayer())
                .tags(BingoTags.NEVER, EnigmaticsBingoTags.NEVER_TAKE_DAMAGE)
                .name(Component.translatable("enigmaticsbingogoals.goal.never_damage"))
                .icon(new IndicatorIcon(EffectIcon.of(MobEffects.HARM), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(BingoGoal.builder(id("never_fall_damage"))
                .criterion("damage", EntityHurtPlayerTrigger.TriggerInstance.entityHurtPlayer(
                        DamagePredicate.Builder.damageInstance().type(
                                DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(DamageTypeTags.IS_FALL))
                        )
                ))
                .tags(BingoTags.NEVER, EnigmaticsBingoTags.NEVER_TAKE_DAMAGE)
                .name(Component.translatable("enigmaticsbingogoals.goal.never_fall_damage"))
                .icon(new IndicatorIcon(EffectIcon.of(MobEffects.HARM), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(BingoGoal.builder(id("never_fire_damage"))
                .criterion("damage", EntityHurtPlayerTrigger.TriggerInstance.entityHurtPlayer(
                        DamagePredicate.Builder.damageInstance().type(
                                DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(DamageTypeTags.IS_FIRE))
                        )
                ))
                .tags(BingoTags.NEVER, EnigmaticsBingoTags.NEVER_TAKE_DAMAGE)
                .name(Component.translatable("enigmaticsbingogoals.goal.never_fire_damage"))
                .icon(new IndicatorIcon(BlockIcon.ofBlock(Blocks.FIRE), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(neverLevelsGoal(id("never_levels"), 1, 4));
        addGoal(advancementsGoal(id("get_advancements"), 15, 21));
        addGoal(obtainItemGoal(id("obtain_heart_of_the_sea"), Items.HEART_OF_THE_SEA)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BURIED_TREASURE, EnigmaticsBingoTags.SHIPWRECK)
        );
        addGoal(potionGoal(id("obtain_potion_of_water_breathing"),
                Potions.WATER_BREATHING, Potions.LONG_WATER_BREATHING)
                .tags(EnigmaticsBingoTags.BURIED_TREASURE, EnigmaticsBingoTags.SHIPWRECK)
        );
        // TODO (requires OVERTAKABLE): Have a higher level than the enemy
        // TODO (requires OVERTAKABLE): Eat more unique foods than the enemy
        addGoal(BingoGoal.builder(id("empty_hunger"))
                .criterion("empty_hunger", EmptyHungerTrigger.TriggerInstance.emptyHunger())
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY, EnigmaticsBingoTags.COVER_DISTANCE)
                .name(Component.translatable("enigmaticsbingogoals.goal.empty_hunger"))
                .icon(new IndicatorIcon(EffectIcon.of(MobEffects.HUNGER), ItemIcon.ofItem(Items.CLOCK)))
        );
        addGoal(BingoGoal.builder(id("sprint_1k_meters"))
                .criterion("sprint", RelativeStatsTrigger.builder()
                        .stat(Stats.SPRINT_ONE_CM, MinMaxBounds.Ints.atLeast(100000)).build())
                .progress(new CriterionProgressTracker("sprint", 0.01f))
                .name(Component.translatable("enigmaticsbingogoals.goal.sprint_distance", 1000))
                .tags(BingoTags.STAT, EnigmaticsBingoTags.COVER_DISTANCE)
                .icon(Items.GOLDEN_BOOTS));
        addGoal(BingoGoal.builder(id("crouch_500_meters"))
                .criterion("crouch", RelativeStatsTrigger.builder()
                        .stat(Stats.CROUCH_ONE_CM, MinMaxBounds.Ints.atLeast(50000)).build())
                .progress(new CriterionProgressTracker("crouch", 0.01f))
                .name(Component.translatable("bingo.goal.crouch_distance", 500))
                .tags(BingoTags.STAT, EnigmaticsBingoTags.COVER_DISTANCE)
                .icon(Items.LEATHER_BOOTS));
        addGoal(BingoGoal.builder(id("reach_world_center"))
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
        addGoal(numberOfEffectsGoal(id("get_some_effects"), 3, 4)
                .tags(EnigmaticsBingoTags.GOLDEN_APPLE, EnigmaticsBingoTags.PUFFER_FISH, EnigmaticsBingoTags.IGLOO,
                        EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.ANCIENT_CITY)
        );
        addGoal(effectGoal(id("get_poison"), MobEffects.POISON)
                .tags(EnigmaticsBingoTags.PUFFER_FISH, EnigmaticsBingoTags.POISON, EnigmaticsBingoTags.SUSPICIOUS_STEW,
                        EnigmaticsBingoTags.BEEHIVE, EnigmaticsBingoTags.MINESHAFT)
        );
        addGoal(effectGoal(id("get_weakness"), MobEffects.WEAKNESS)
                .tags(EnigmaticsBingoTags.WEAKNESS, EnigmaticsBingoTags.SUSPICIOUS_STEW, EnigmaticsBingoTags.IGLOO)
        );
        addGoal(effectGoal(id("get_leaping"), MobEffects.JUMP)
                .tags(EnigmaticsBingoTags.LEAPING, EnigmaticsBingoTags.SUSPICIOUS_STEW)
        );
        addGoal(effectGoal(id("get_blindness"), MobEffects.BLINDNESS)
                .tags(EnigmaticsBingoTags.SUSPICIOUS_STEW)
        );
        addGoal(effectGoal(id("get_saturation"), MobEffects.SATURATION)
                .tags(EnigmaticsBingoTags.SATURATION, EnigmaticsBingoTags.SUSPICIOUS_STEW)
        );
        addGoal(effectGoal(id("get_absorption"), MobEffects.ABSORPTION)
                .tags(EnigmaticsBingoTags.GOLDEN_APPLE, EnigmaticsBingoTags.IGLOO, EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(eatItemGoal(id("eat_suspicious_stew"), Items.SUSPICIOUS_STEW)
                .tags(BingoTags.END, EnigmaticsBingoTags.SUSPICIOUS_STEW, EnigmaticsBingoTags.SATURATION,
                        EnigmaticsBingoTags.WEAKNESS, EnigmaticsBingoTags.NIGHT_VISION, EnigmaticsBingoTags.LEAPING,
                        EnigmaticsBingoTags.POISON, EnigmaticsBingoTags.STEW)
        );
        addGoal(BingoGoal.builder(id("clear_effect_with_milk"))
                .criterion("clear_effect", CriteriaTriggers.CONSUME_ITEM.createCriterion(
                        new ConsumeItemTrigger.TriggerInstance(
                                Optional.of(ContextAwarePredicate.create(new HasAnyEffectCondition(LootContext.EntityTarget.THIS))),
                                Optional.of(ItemPredicate.Builder.item().of(Items.MILK_BUCKET).build())
                        )
                ))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MILK)
                .name(Component.translatable("enigmaticsbingogoals.goal.clear_effect_with_milk"))
                .icon(IndicatorIcon.infer(
                        CycleIcon.infer(BuiltInRegistries.MOB_EFFECT.holders().map(EffectIcon::of)),
                        Items.MILK_BUCKET
                ))
        );
        addGoal(reachLevelsGoal(id("reach_levels"), 10, 35));
        addGoal(obtainSomeItemsFromTagGoal(id("obtain_some_small_flowers"), ItemTags.SMALL_FLOWERS, 5, 9)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY, EnigmaticsBingoTags.PLANT_BATCH)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_small_flowers", 0),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(BingoGoal.builder(id("fill_a_composter"))
                .criterion("use", CriteriaTriggers.DEFAULT_BLOCK_USE.createCriterion(
                        new DefaultBlockInteractionTrigger.TriggerInstance(
                                Optional.empty(),
                                Optional.of(ContextAwarePredicate.create(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(Blocks.COMPOSTER))).build())))
                ))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY, EnigmaticsBingoTags.USE_WORKSTATION)
                .name(Component.translatable("enigmaticsbingogoals.goal.fill_composter"))
                .icon(ItemIcon.ofItem(Items.COMPOSTER))
        );
        addGoal(BingoGoal.builder(id("full_unique_inventory"))
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
        addGoal(BingoGoal.builder(id("deal_500_hearts_of_damage"))
                .criterion("deal", DamageExceptTeamTrigger.TriggerInstance.dealtDamage(MinMaxBounds.Ints.atLeast(5000)))
                .progress(new CriterionProgressTracker("deal", 0.1f))
                .name(Component.translatable("enigmaticsbingogoals.goal.deal_some_hearts_of_damage", 500))
                .tooltip(Component.translatable("enigmaticsbingogoals.goal.deal_some_hearts_of_damage.tooltip"))
                .icon(IndicatorIcon.infer(EntityType.COW, ItemIcon.ofItem(Items.NETHERITE_SWORD)))
        );
        addGoal(obtainAllItemsFromTagGoal(id("obtain_all_wooden_tools"), EnigmaticsBingoItemTags.WOODEN_TOOLS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET, EnigmaticsBingoTags.OVERWORLD_ENTRY)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_material_tools",
                        Component.translatable(EnigmaticsBingoItemTags.WOODEN_TOOLS.getTranslationKey())))
        );
        addGoal(obtainAllItemsFromTagGoal(id("obtain_all_stone_tools"), EnigmaticsBingoItemTags.STONE_TOOLS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET, EnigmaticsBingoTags.OVERWORLD_ENTRY)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_material_tools",
                        Component.translatable(EnigmaticsBingoItemTags.STONE_TOOLS.getTranslationKey())))
        );
        addGoal(BingoGoal.builder(id("stand_on_bedrock"))
                .criterion("stand_on", PlayerTrigger.TriggerInstance.located(
                        EntityPredicate.Builder.entity().steppingOn(
                                LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(
                                        Block.byItem(Items.BEDROCK)
                                ))
                        ))
                )
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY, EnigmaticsBingoTags.REACH_WORLD_LIMIT)
                .name(Component.translatable("enigmaticsbingogoals.goal.stand_on_bedrock"))
                .icon(ItemIcon.ofItem(Items.BEDROCK))
        );
        addGoal(BingoGoal.builder(id("hatch_baby_chicken"))
                .criterion("hatch", ChickenHatchTrigger.builder().build())
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY, EnigmaticsBingoTags.CHICKEN)
                .name(Component.translatable("enigmaticsbingogoals.goal.hatch_baby_chicken",
                        EntityType.CHICKEN.getDescription(),
                        Items.EGG.getDescription()
                ))
                .icon(IndicatorIcon.infer(EntityType.CHICKEN, Items.EGG))
        );
        addGoal(breedAnimalGoal(id("breed_chicken"), EntityType.CHICKEN)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY, EnigmaticsBingoTags.CHICKEN)
        );
        addGoal(BingoGoal.builder(id("reach_build_limit"))
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
        addGoal(dieToEntityGoal(id("die_to_llama"), EntityType.LLAMA)
                .tags(BingoTags.OVERWORLD)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_llama",
                        EntityType.LLAMA.getDescription()))
        );
        addGoal(dieToEntityGoal(id("die_to_bee"), EntityType.BEE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BEEHIVE)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_bee",
                        EntityType.BEE.getDescription()))
        );
        addGoal(dieToDamageTypeGoal(id("die_to_fireworks"), EnigmaticsBingoDamageTypeTags.FIREWORKS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_fireworks"))
                .icon(IndicatorIcon.infer(Items.FIREWORK_ROCKET, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        // TODO: Die to falling off vines
        addGoal(breakBlockGoal(id("break_diamond_ore"), Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING)
        );
        addGoal(obtainItemGoal(id("obtain_repeater"), Items.REPEATER)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING,
                        EnigmaticsBingoTags.REDSTONE, EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(obtainItemGoal(id("obtain_powered_rail"), Items.POWERED_RAIL)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING,
                        EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(obtainItemGoal(id("obtain_detector_rail"), Items.DETECTOR_RAIL)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(obtainItemGoal(id("obtain_activator_rail"), Items.ACTIVATOR_RAIL)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.IGLOO, EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(dieToEntityGoal(id("die_to_stray"), EntityType.STRAY)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_stray",
                        EntityType.STRAY.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_piston"), Items.PISTON)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(obtainItemGoal(id("obtain_powder_snow_bucket"), Items.POWDER_SNOW_BUCKET)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MOUNTAIN)
        );
        addGoal(tameAnimalGoal(id("tame_cat"), EntityType.CAT)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TAME_ANIMAL, EnigmaticsBingoTags.WITCH_HUT, BingoTags.VILLAGE)
        );
        addGoal(tameAnimalGoal(id("tame_wolf"), EntityType.WOLF)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TAME_ANIMAL)
        );
        addGoal(tameAnimalGoal(id("tame_parrot"), EntityType.PARROT)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TAME_ANIMAL, EnigmaticsBingoTags.JUNGLE)
        );
        addGoal(breedAnimalGoal(id("breed_rabbit"), EntityType.RABBIT)
                .tags(BingoTags.OVERWORLD)
        );
        addGoal(breedAnimalGoal(id("breed_hoglin"), EntityType.HOGLIN)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.CRIMSON_FOREST)
        );
        addGoal(breakBlockGoal(id("break_turtle_egg"), Blocks.TURTLE_EGG)
                .tags(BingoTags.OVERWORLD)
        );
        addGoal(killEntityGoal(id("kill_ghast"), EntityType.GHAST)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_ghast", EntityType.GHAST.getDescription()))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.GHAST)
                .icon(IndicatorIcon.infer(BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.GHAST), Items.NETHERITE_SWORD))
        );
        addGoal(killEntityGoal(id("kill_snow_golem"), EntityType.SNOW_GOLEM)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_snow_golem", EntityType.SNOW_GOLEM.getDescription()))
                .tags(BingoTags.OVERWORLD)
        );
        addGoal(killEntitiesFromTagGoal(id("kill_50_mobs"), EnigmaticsBingoEntityTypeTags.MOBS, 50, 50, false)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB, EnigmaticsBingoTags.KILL_MOB_BATCH)
        );
        addGoal(killEntitiesFromTagGoal(id("kill_100_mobs"), EnigmaticsBingoEntityTypeTags.MOBS, 100, 100, false)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB, EnigmaticsBingoTags.KILL_MOB_BATCH)
        );
        addGoal(killEntitiesFromTagGoal(id("kill_30_arthropods"), EntityTypeTags.ARTHROPOD, 30, 30, false)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_of_tag", 0,
                                Component.translatable(EntityTypeTags.ARTHROPOD.getTranslationKey())),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB, EnigmaticsBingoTags.KILL_MOB_BATCH)
        );
        addGoal(killEntitiesFromTagGoal(id("kill_30_undead_mobs"), EntityTypeTags.UNDEAD, 30, 30, false)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_of_tag", 0,
                                Component.translatable(EntityTypeTags.UNDEAD.getTranslationKey())),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB, EnigmaticsBingoTags.KILL_MOB_BATCH)
        );
        addGoal(killEntitiesFromTagGoal(id("kill_50_undead_mobs"), EntityTypeTags.UNDEAD, 50, 50, false)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_of_tag", 0,
                                Component.translatable(EntityTypeTags.UNDEAD.getTranslationKey())),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB, EnigmaticsBingoTags.KILL_MOB_BATCH)
        );
        // TODO: Wash something in a Cauldron
        // TODO: Wear 4 different armor materials
        // TODO: Fill a Chiseled Bookshelf with books
        addGoal(advancementGoal(id("get_advancement_what_a_deal"),
                Component.translatable("advancements.adventure.trade.title"),
                new ResourceLocation("minecraft", "adventure/trade"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.IGLOO, BingoTags.VILLAGE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.EMERALD), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(eatItemGoal(id("eat_rabbit_stew"), Items.RABBIT_STEW)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE, EnigmaticsBingoTags.STEW)
        );
        addGoal(obtainItemGoal(id("obtain_bell"), Items.BELL)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE)
        );
        // TODO: Give an armor stand 4 pieces of armor
        addGoal(wearArmorPiecesGoal(id("wear_full_leather"), Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE,
                Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.ARMOR)
                .name(Component.translatable("enigmaticsbingogoals.goal.wear_full_leather"))
        );
        addGoal(wearArmorPiecesGoal(id("wear_full_iron"), Items.IRON_HELMET, Items.IRON_CHESTPLATE,
                        Items.IRON_LEGGINGS, Items.IRON_BOOTS)
                        .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.ARMOR)
                        .name(Component.translatable("enigmaticsbingogoals.goal.wear_full_iron"))
        );
        addGoal(eatItemGoal(id("eat_glow_berries"), Items.GLOW_BERRIES)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.LUSH_CAVE, EnigmaticsBingoTags.ANCIENT_CITY)
        );
        // TODO: Use a Loom
        // TODO: Use a Cartography Table
        // TODO: Use a Flower Banner Pattern
        // TODO: Use a Masoned Banner Pattern
        // TODO: Use a Bordure Banner Pattern
        addGoal(advancementProgressGoal(id("eat_some_unique_foods"),
                new ResourceLocation("minecraft", "husbandry/balanced_diet"), 7, 13)
                .name(Component.translatable("enigmaticsbingogoals.goal.eat_some_unique_foods", 0),
                        subber -> subber.sub("with.0", "count")
                )
                .tooltip(Component.translatable("enigmaticsbingogoals.goal.eat_some_unique_foods.tooltip", Items.CAKE.getDescription()))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.UNIQUE_FOOD)
                .icon(
                        CycleIcon.infer(Arrays.stream(VanillaHusbandryAdvancements.EDIBLE_ITEMS)),
                        subber -> subber.sub("icons.*.item.count", "count")
                )
        );
        // TODO: Sign a Book and Quill
        addGoal(obtainItemGoal(id("obtain_bookshelf"), Items.BOOKSHELF)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BOOK, EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(BingoGoal.builder(id("wear_pumpkin"))
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
        addGoal(obtainItemGoal(id("obtain_mossy_stone_brick_wall"), Items.MOSSY_STONE_BRICK_WALL)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WALL, EnigmaticsBingoTags.IGLOO,
                        EnigmaticsBingoTags.LUSH_CAVE, EnigmaticsBingoTags.JUNGLE)
        );
        addGoal(obtainItemGoal(id("obtain_mossy_cobblestone_wall"), Items.MOSSY_COBBLESTONE_WALL)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WALL, EnigmaticsBingoTags.LUSH_CAVE, EnigmaticsBingoTags.JUNGLE)
        );
        addGoal(obtainAllItemsFromTagGoal(id("obtain_all_iron_tools"), EnigmaticsBingoItemTags.IRON_TOOLS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_material_tools",
                        Component.translatable(EnigmaticsBingoItemTags.IRON_TOOLS.getTranslationKey())))
        );
        addGoal(obtainItemGoal(id("obtain_orange_glazed_terracotta"), Items.ORANGE_GLAZED_TERRACOTTA)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TRAIL_RUINS, EnigmaticsBingoTags.TERRACOTTA)
        );
        addGoal(obtainItemGoal(id("obtain_blue_glazed_terracotta"), Items.BLUE_GLAZED_TERRACOTTA)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TRAIL_RUINS, EnigmaticsBingoTags.TERRACOTTA)
        );
        addGoal(obtainItemGoal(id("obtain_black_glazed_terracotta"), Items.BLACK_GLAZED_TERRACOTTA)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TRAIL_RUINS, EnigmaticsBingoTags.TERRACOTTA)
        );
        addGoal(obtainItemGoal(id("obtain_gray_glazed_terracotta"), Items.GRAY_GLAZED_TERRACOTTA)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TRAIL_RUINS, EnigmaticsBingoTags.TERRACOTTA)
        );
        addGoal(obtainItemGoal(id("obtain_white_glazed_terracotta"), Items.WHITE_GLAZED_TERRACOTTA)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TRAIL_RUINS, EnigmaticsBingoTags.TERRACOTTA)
        );
        addGoal(obtainItemGoal(id("obtain_green_glazed_terracotta"), Items.GREEN_GLAZED_TERRACOTTA)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.IGLOO, EnigmaticsBingoTags.TERRACOTTA)
        );
        addGoal(obtainItemGoal(id("obtain_lime_glazed_terracotta"), Items.LIME_GLAZED_TERRACOTTA)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TERRACOTTA)
        );
        addGoal(obtainItemGoal(id("obtain_honey_bottle"), Items.HONEY_BOTTLE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BEEHIVE)
        );
        addGoal(obtainSomeItemsFromTagGoal(id("obtain_colored_candle"), ItemTags.CANDLES, 1, 1)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BEEHIVE, EnigmaticsBingoTags.ANCIENT_CITY)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_colored_candle"))
        );
        addGoal(eatItemGoal(id("eat_cookie"), Items.COOKIE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.JUNGLE)
        );
        // TODO: Use an Anvil
        addGoal(obtainItemGoal(id("obtain_stack_of_red_concrete"), Items.RED_CONCRETE, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CONCRETE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.RED_CONCRETE.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_yellow_concrete"), Items.YELLOW_CONCRETE, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CONCRETE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.YELLOW_CONCRETE.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_orange_concrete"), Items.ORANGE_CONCRETE, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CONCRETE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.ORANGE_CONCRETE.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_black_concrete"), Items.BLACK_CONCRETE, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CONCRETE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.BLACK_CONCRETE.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_white_concrete"), Items.WHITE_CONCRETE, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CONCRETE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.WHITE_CONCRETE.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_gray_concrete"), Items.GRAY_CONCRETE, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CONCRETE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.GRAY_CONCRETE.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_light_gray_concrete"), Items.LIGHT_GRAY_CONCRETE, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CONCRETE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.LIGHT_GRAY_CONCRETE.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_pink_concrete"), Items.PINK_CONCRETE, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CONCRETE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.PINK_CONCRETE.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_magenta_concrete"), Items.MAGENTA_CONCRETE, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CONCRETE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.MAGENTA_CONCRETE.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_blue_concrete"), Items.BLUE_CONCRETE, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CONCRETE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.BLUE_CONCRETE.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_purple_concrete"), Items.PURPLE_CONCRETE, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CONCRETE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.PURPLE_CONCRETE.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_red_wool"), Items.RED_WOOL, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOOL)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.RED_WOOL.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_yellow_wool"), Items.YELLOW_WOOL, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOOL)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.YELLOW_WOOL.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_orange_wool"), Items.ORANGE_WOOL, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOOL)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.ORANGE_WOOL.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_black_wool"), Items.BLACK_WOOL, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOOL)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.BLACK_WOOL.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_white_wool"), Items.WHITE_WOOL, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOOL)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.WHITE_WOOL.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_gray_wool"), Items.GRAY_WOOL, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOOL, EnigmaticsBingoTags.ANCIENT_CITY)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.GRAY_WOOL.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_light_gray_wool"), Items.LIGHT_GRAY_WOOL, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOOL)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.LIGHT_GRAY_WOOL.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_pink_wool"), Items.PINK_WOOL, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOOL)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.PINK_WOOL.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_magenta_wool"), Items.MAGENTA_WOOL, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOOL)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.MAGENTA_WOOL.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_blue_wool"), Items.BLUE_WOOL, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOOL)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.BLUE_WOOL.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_purple_wool"), Items.PURPLE_WOOL, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOOL)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.PURPLE_WOOL.getDescription()))
        );
        // TODO: Anger a Zombified Piglin
        addGoal(advancementGoal(id("get_advancement_we_need_to_go_deeper"),
                Component.translatable("advancements.story.enter_the_nether.title"),
                new ResourceLocation("minecraft", "nether/enter_the_nether"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.NETHER_ENTRY)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.FLINT_AND_STEEL), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(effectGoal(id("get_glowing"), MobEffects.GLOWING)
                .tags(EnigmaticsBingoTags.NETHER_ENTRY)
        );
        addGoal(advancementGoal(id("get_advancement_oh_shiny"),
                Component.translatable("advancements.nether.distract_piglin.title"),
                new ResourceLocation("minecraft", "nether/distract_piglin"))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.BARTERING)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.GOLD_INGOT), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
    }
}
