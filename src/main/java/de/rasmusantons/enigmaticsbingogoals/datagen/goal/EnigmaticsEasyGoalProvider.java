package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoDamageTypeTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoEntityTypeTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoItemTags;
import de.rasmusantons.enigmaticsbingogoals.triggers.DamageExceptTeamTrigger;
import de.rasmusantons.enigmaticsbingogoals.triggers.WearPumpkinTrigger;
import de.rasmusantons.enigmaticsbingogoals.triggers.WriteBookTrigger;
import io.github.gaming32.bingo.conditions.HasAnyEffectCondition;
import io.github.gaming32.bingo.data.BingoDifficulties;
import io.github.gaming32.bingo.data.BingoGoal;
import io.github.gaming32.bingo.data.BingoTags;
import io.github.gaming32.bingo.data.icons.*;
import io.github.gaming32.bingo.data.progresstrackers.CriterionProgressTracker;
import io.github.gaming32.bingo.data.tags.BingoFeatureTags;
import io.github.gaming32.bingo.triggers.GrowFeatureTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.advancements.packs.VanillaHusbandryAdvancements;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.storage.loot.LootContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public class EnigmaticsEasyGoalProvider extends EnigmaticsDifficultyGoalProvider {
    public EnigmaticsEasyGoalProvider(BiConsumer<ResourceLocation, BingoGoal> goalAdder, HolderLookup.Provider registries) {
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
        addGoal(neverLevelsGoal(id("never_levels"), 2, 4));
        addGoal(advancementsGoal(id("get_advancements"), 15, 20));
        addGoal(obtainItemGoal(id("obtain_heart_of_the_sea"), Items.HEART_OF_THE_SEA)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BURIED_TREASURE, EnigmaticsBingoTags.SHIPWRECK)
        );
        addGoal(potionGoal(id("obtain_potion_of_water_breathing"),
                Potions.WATER_BREATHING, Potions.LONG_WATER_BREATHING)
                .tags(EnigmaticsBingoTags.BURIED_TREASURE, EnigmaticsBingoTags.SHIPWRECK)
        );
        // TODO (requires OVERTAKABLE): Have a higher level than the enemy
        addGoal(numberOfEffectsGoal(id("get_some_effects"), 3, 4)
                .tags(EnigmaticsBingoTags.GOLDEN_APPLE, EnigmaticsBingoTags.PUFFER_FISH, EnigmaticsBingoTags.IGLOO,
                        EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.ANCIENT_CITY)
        );
        addGoal(effectGoal(id("get_poison"), MobEffects.POISON)
                .tags(EnigmaticsBingoTags.PUFFER_FISH, EnigmaticsBingoTags.POISON, EnigmaticsBingoTags.SUSPICIOUS_STEW,
                        EnigmaticsBingoTags.BEEHIVE, EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.SWAMP,
                        EnigmaticsBingoTags.TRIAL_CHAMBER)
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
                .tags(EnigmaticsBingoTags.GOLDEN_APPLE, EnigmaticsBingoTags.IGLOO, EnigmaticsBingoTags.WOODLAND_MANSION,
                        EnigmaticsBingoTags.TRIAL_CHAMBER)
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
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MILK, EnigmaticsBingoTags.TRIAL_CHAMBER, EnigmaticsBingoTags.GET_EFFECT)
                .name(Component.translatable("enigmaticsbingogoals.goal.clear_effect_with_milk"))
                .icon(IndicatorIcon.infer(
                        CycleIcon.infer(BuiltInRegistries.MOB_EFFECT.holders().map(EffectIcon::of)),
                        Items.MILK_BUCKET
                ))
        );
        addGoal(reachLevelsGoal(id("reach_levels"), 10, 15));
        addGoal(obtainSomeItemsFromTagGoal(id("obtain_some_small_flowers"), ItemTags.SMALL_FLOWERS, 5, 9)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY, EnigmaticsBingoTags.PLANT_BATCH)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_small_flowers", 0),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(BingoGoal.builder(id("deal_500_hearts_of_damage"))
                .criterion("deal", DamageExceptTeamTrigger.TriggerInstance.dealtDamage(MinMaxBounds.Ints.atLeast(5000)))
                .progress(new CriterionProgressTracker("deal", 0.1f))
                .name(Component.translatable("enigmaticsbingogoals.goal.deal_some_hearts_of_damage", 500))
                .tags(EnigmaticsBingoTags.OVERWORLD_ENTRY)
                .tooltip(Component.translatable("enigmaticsbingogoals.goal.deal_some_hearts_of_damage.tooltip"))
                .icon(IndicatorIcon.infer(EntityIcon.ofSpawnEgg(EntityType.COW, 500), ItemIcon.ofItem(Items.NETHERITE_SWORD)))
        );
        addGoal(dieToMobEntityGoal(id("die_to_bee"), EntityType.BEE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BEEHIVE)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_bee",
                        EntityType.BEE.getDescription()))
        );
        addGoal(dieToDamageTypeGoal(id("die_to_fireworks"), EnigmaticsBingoDamageTypeTags.FIREWORKS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_fireworks", Items.FIREWORK_ROCKET.getDescription()))
                .icon(IndicatorIcon.infer(Items.FIREWORK_ROCKET, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
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
        addGoal(obtainItemGoal(id("obtain_piston"), Items.PISTON)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(tameAnimalGoal(id("tame_cat"), EntityType.CAT)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TAME_ANIMAL, EnigmaticsBingoTags.WITCH_HUT, BingoTags.VILLAGE)
        );
        addGoal(breedAnimalGoal(id("breed_rabbit"), EntityType.RABBIT)
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
        addGoal(BingoGoal.builder(id("fill_a_chiseled_bookshelf"))
                .criterion("use", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                        LocationPredicate.Builder.location().setBlock(
                                BlockPredicate.Builder.block()
                                        .of(Blocks.CHISELED_BOOKSHELF)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(ChiseledBookShelfBlock.SLOT_OCCUPIED_PROPERTIES.get(0), true)
                                                .hasProperty(ChiseledBookShelfBlock.SLOT_OCCUPIED_PROPERTIES.get(1), true)
                                                .hasProperty(ChiseledBookShelfBlock.SLOT_OCCUPIED_PROPERTIES.get(2), true)
                                                .hasProperty(ChiseledBookShelfBlock.SLOT_OCCUPIED_PROPERTIES.get(3), true)
                                                .hasProperty(ChiseledBookShelfBlock.SLOT_OCCUPIED_PROPERTIES.get(4), true)
                                                .hasProperty(ChiseledBookShelfBlock.SLOT_OCCUPIED_PROPERTIES.get(5), true)
                                        )
                        ),
                        ItemPredicate.Builder.item().of(EnigmaticsBingoItemTags.BOOKS)
                ))
                .name(Component.translatable("enigmaticsbingogoals.goal.fill_chiseled_bookshelf",
                        Items.CHISELED_BOOKSHELF.getDescription()))
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE, EnigmaticsBingoTags.BOOK, EnigmaticsBingoTags.WOODLAND_MANSION)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.CHISELED_BOOKSHELF), new ItemTagCycleIcon(EnigmaticsBingoItemTags.BOOKS)))
        );
        addGoal(advancementGoal(id("get_advancement_what_a_deal"),
                Component.translatable("advancements.adventure.trade.title"),
                ResourceLocation.withDefaultNamespace("adventure/trade"))
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
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.LUSH_CAVE, EnigmaticsBingoTags.ANCIENT_CITY,
                        EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        // TODO: Use a Loom
        // TODO: Use a Cartography Table
        // TODO: Use a Flower Banner Pattern
        // TODO: Use a Masoned Banner Pattern
        addGoal(advancementProgressGoal(id("eat_some_unique_foods"),
                ResourceLocation.withDefaultNamespace("husbandry/balanced_diet"), 7, 13)
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
        addGoal(
                BingoGoal.builder(id("sign_book_and_quill"))
                        .criterion("sign", WriteBookTrigger.TriggerInstance.signer())
                        .name(Component.translatable("enigmaticsbingogoals.goal.sign_book_and_quill",
                                Items.WRITABLE_BOOK.getDescription()))
                        .tags(BingoTags.ITEM, BingoTags.OVERWORLD, EnigmaticsBingoTags.WRITE_BOOK, EnigmaticsBingoTags.BOOK)
                        .icon(ItemIcon.ofItem(Items.WRITABLE_BOOK))
        );
        addGoal(
                BingoGoal.builder(id("make_copy_of_copy"))
                        .criterion("clone", RecipeCraftedTrigger.TriggerInstance.craftedItem(
                                ResourceLocation.withDefaultNamespace("book_cloning"),
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
                        .tags(BingoTags.ITEM, BingoTags.OVERWORLD, EnigmaticsBingoTags.WRITE_BOOK, EnigmaticsBingoTags.BOOK)
                        .icon(new ItemIcon(new ItemStack(Items.WRITTEN_BOOK, 3)))
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
                        EnigmaticsBingoTags.LUSH_CAVE, EnigmaticsBingoTags.JUNGLE, EnigmaticsBingoTags.SHIPWRECK,
                        EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(obtainItemGoal(id("obtain_mossy_cobblestone_wall"), Items.MOSSY_COBBLESTONE_WALL)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WALL, EnigmaticsBingoTags.LUSH_CAVE,
                        EnigmaticsBingoTags.JUNGLE, EnigmaticsBingoTags.SHIPWRECK, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(obtainItemGoal(id("obtain_polished_tuff_wall"), Items.POLISHED_TUFF_WALL)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WALL)
        );
        addGoal(obtainAllItemsFromTagGoal(id("obtain_all_iron_tools"), EnigmaticsBingoItemTags.IRON_TOOLS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_material_tools",
                        Component.translatable(EnigmaticsBingoItemTags.IRON_TOOLS.getTranslationKey())))
        );
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
        addGoal(BingoGoal.builder(id("anger_zombified_piglin"))
                .criterion("anger", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(
                        Optional.of(EntityPredicate.Builder.entity().of(EntityType.ZOMBIFIED_PIGLIN).build())
                ))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY)
                .name(Component.translatable("enigmaticsbingogoals.goal.anger_zombified_piglin",
                        EntityType.ZOMBIFIED_PIGLIN.getDescription()))
                .icon(IndicatorIcon.infer(
                        EntityType.ZOMBIFIED_PIGLIN,
                        BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.ANGRY_BIRD))
                )
        );
        addGoal(advancementGoal(id("get_advancement_we_need_to_go_deeper"),
                Component.translatable("advancements.story.enter_the_nether.title"),
                ResourceLocation.withDefaultNamespace("nether/enter_the_nether"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.NETHER_ENTRY)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.FLINT_AND_STEEL), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(BingoGoal.builder(id("grow_tree_in_nether"))
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
        addGoal(effectGoal(id("get_glowing"), MobEffects.GLOWING)
                .tags(EnigmaticsBingoTags.NETHER_ENTRY)
        );
        addGoal(advancementGoal(id("get_advancement_oh_shiny"),
                Component.translatable("advancements.nether.distract_piglin.title"),
                ResourceLocation.withDefaultNamespace("nether/distract_piglin"))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.BARTERING)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.GOLD_INGOT), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(dieToMobEntityGoal(id("die_to_dolphin"), EntityType.DOLPHIN)
                .tags(BingoTags.OVERWORLD)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_dolphin",
                        EntityType.DOLPHIN.getDescription()))
        );
        addGoal(dieToMobEntityGoal(id("die_to_iron_golem"), EntityType.IRON_GOLEM)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_iron_golem",
                        EntityType.IRON_GOLEM.getDescription()))
        );
        addGoal(dieToDamageTypeGoal(id("die_to_stalactite"), EnigmaticsBingoDamageTypeTags.STALACTITE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO, EnigmaticsBingoTags.CAVING)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_stalactite"))
                .icon(IndicatorIcon.infer(Items.POINTED_DRIPSTONE, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        addGoal(eatItemGoal(id("eat_poisonous_potato"), Items.POISONOUS_POTATO)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE, EnigmaticsBingoTags.POISON, EnigmaticsBingoTags.SHIPWRECK)
        );
        addGoal(advancementGoal(id("get_advancement_return_to_sender"),
                Component.translatable("advancements.nether.return_to_sender.title"),
                ResourceLocation.withDefaultNamespace("nether/return_to_sender"))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.GHAST)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.FIRE_CHARGE), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(killEntityGoal(id("kill_zombie_villager"), EntityType.ZOMBIE_VILLAGER)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_zombie_villager", EntityType.ZOMBIE_VILLAGER.getDescription()))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.IGLOO, EnigmaticsBingoTags.GOLDEN_APPLE)
        );
        addGoal(neverDamageGoal(id("never_50_damage"), 50));
        addGoal(obtainAllItemsFromTagGoal(id("obtain_all_golden_tools"), EnigmaticsBingoItemTags.GOLDEN_TOOLS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_material_tools",
                        Component.translatable(EnigmaticsBingoItemTags.GOLDEN_TOOLS.getTranslationKey())))
        );
        addGoal(obtainAllItemsFromTagGoal(id("obtain_all_raw_ore_blocks"), EnigmaticsBingoItemTags.RAW_ORE_BLOCKS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_all_raw_ore_blocks",
                        Component.translatable(EnigmaticsBingoItemTags.RAW_ORE_BLOCKS.getTranslationKey())))
        );
        addGoal(obtainItemGoal(id("obtain_cake"), Items.CAKE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MILK, EnigmaticsBingoTags.CHICKEN)
        );
        addGoal(obtainItemGoal(id("obtain_daylight_detector"), Items.DAYLIGHT_DETECTOR)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.REDSTONE, EnigmaticsBingoTags.NETHER_ENTRY)
        );
        addGoal(obtainItemGoal(id("obtain_dispenser"), Items.DISPENSER)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING,
                        EnigmaticsBingoTags.REDSTONE, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(obtainItemGoal(id("obtain_flowering_azalea"), Items.FLOWERING_AZALEA)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.LUSH_CAVE)
        );
        addGoal(obtainItemGoal(id("obtain_grass_block"), Items.GRASS_BLOCK)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE, EnigmaticsBingoTags.SILK_TOUCH)
        );
        addGoal(obtainItemGoal(id("obtain_mud_brick_wall"), Items.MUD_BRICK_WALL)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WALL, EnigmaticsBingoTags.TRAIL_RUINS)
        );
        addGoal(obtainSomeItemsFromTagGoal(id("obtain_some_music_discs"), EnigmaticsBingoItemTags.MUSIC_DISCS, 2, 5)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MUSIC_DISC, EnigmaticsBingoTags.ANCIENT_CITY,
                        EnigmaticsBingoTags.TRAIL_RUINS, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_music_discs", 0),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(dieToEntityGoal(id("die_to_tnt_minecart"), EntityType.TNT_MINECART, ItemIcon.ofItem(Items.TNT_MINECART))
                .tags(BingoTags.OVERWORLD)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_tnt_minecart",
                        EntityType.TNT_MINECART.getDescription()))
        );
        addGoal(killEntitiesFromTagGoal(id("kill_some_unique_hostile_mobs"), EnigmaticsBingoEntityTypeTags.HOSTILE, 7, 10, true)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_unique_hostile_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.UNIQUE_HOSTILE_MOBS, EnigmaticsBingoTags.KILL_MOB)
        );
    }
}
