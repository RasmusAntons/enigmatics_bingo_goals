package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoDifficulties;
import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoTags;
import de.rasmusantons.enigmaticsbingogoals.datagen.EnigmaticsBingoSynergies;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoEntityTypeTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoFeatureTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoItemTags;
import io.github.gaming32.bingo.data.goal.BingoGoal;
import io.github.gaming32.bingo.data.icons.*;
import io.github.gaming32.bingo.triggers.GrowFeatureTrigger;
import net.minecraft.advancements.critereon.CuredZombieVillagerTrigger;
import net.minecraft.advancements.critereon.EntityHurtPlayerTrigger;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.packs.VanillaHusbandryAdvancements;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerPatterns;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class EnigmaticsHardGoalProvider extends EnigmaticsDifficultyGoalProvider {
    public EnigmaticsHardGoalProvider(BiConsumer<ResourceLocation, BingoGoal> goalAdder, HolderLookup.Provider registries) {
        super(EnigmaticsBingoDifficulties.HARD, goalAdder, registries);
    }

    @Override
    public void addGoals() {
        final var entityTypes = registries.lookupOrThrow(Registries.ENTITY_TYPE);
        final var items = registries.lookupOrThrow(Registries.ITEM);

        // TODO: Never open your inventory
        addGoal(advancementsGoal(eid("get_advancements"), 26, 35));
        addGoal(BingoGoal.builder(eid("cure_zombie_villager"))
                .criterion("transform", CuredZombieVillagerTrigger.TriggerInstance.curedZombieVillager())
                .name(Component.translatable("enigmaticsbingogoals.goal.cure_zombie_villager"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.IGLOO)
                .icon(IndicatorIcon.infer(EntityType.ZOMBIE_VILLAGER, ItemIcon.ofItem(Items.GOLDEN_APPLE)))
        );
        addGoal(numberOfEffectsGoal(eid("get_some_effects"), 13, 19));
        addGoal(breedAnimalGoal(eid("breed_mule"), entityTypes, EntityType.MULE)
                .tags(EnigmaticsBingoTags.OVERWORLD)
        );
        addGoal(advancementProgressGoal(eid("visit_some_unique_overworld_biomes"),
                ResourceLocation.withDefaultNamespace("adventure/adventuring_time"), 26, 35)
                .name(Component.translatable("enigmaticsbingogoals.goal.visit_some_unique_overworld_biomes", 0),
                        subber -> subber.sub("with.0", "count")
                )
                .tooltip(Component.translatable("enigmaticsbingogoals.goal.visit_some_unique_overworld_biomes.tooltip",
                        Component.translatable("advancements.adventure.adventuring_time.title")))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.BIOMES, EnigmaticsBingoTags.OVERWORLD_EXPLORE)
                .icon(
                        Items.GOLDEN_BOOTS,
                        subber -> subber.sub("item.count", "count")
                )
        );
        addGoal(tameSomeCatsGoal(eid("tame_some_cats"), 5, 6));
        addGoal(tameSomeWolvesGoal(eid("tame_some_wolves"), 3, 4));
        addGoal(advancementProgressGoal(eid("breed_some_unique_mobs"),
                ResourceLocation.withDefaultNamespace("husbandry/bred_all_animals"), 11, 15)
                .name(Component.translatable("enigmaticsbingogoals.goal.breed_some_unique_mobs", 0),
                        subber -> subber.sub("with.0", "count"))
                .tags(EnigmaticsBingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.BREED_MOB_BATCH)
                .catalyst(EnigmaticsBingoSynergies.BREED_MOB)
                .icon(IndicatorIcon.infer(CycleIcon.infer(
                                Stream.concat(VanillaHusbandryAdvancements.BREEDABLE_ANIMALS.stream(),
                                        VanillaHusbandryAdvancements.INDIRECTLY_BREEDABLE_ANIMALS.stream()).toList()
                        ), EffectIcon.of(MobEffects.HEALTH_BOOST)),
                        subber -> subber.sub("base.icons.*.item.count", "count"))
        );
        addGoal(killEntitiesFromTagGoal(eid("kill_some_unique_hostile_mobs"), EnigmaticsBingoEntityTypeTags.HOSTILE, 15, 19, true)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_unique_hostile_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.UNIQUE_HOSTILE_MOBS)
        );
        addGoal(killEntityGoal(eid("kill_ender_dragon"), entityTypes, EntityType.ENDER_DRAGON)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_ender_dragon", EntityType.ENDER_DRAGON.getDescription()))
                .tags(EnigmaticsBingoTags.END, EnigmaticsBingoTags.END_ENTRY)
                .icon(IndicatorIcon.infer(Items.DRAGON_HEAD, Items.NETHERITE_SWORD))
        );
        addGoal(wearArmorPiecesGoal(eid("wear_full_diamond"), entityTypes, items, Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE,
                Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.ARMOR)
                .name(Component.translatable("enigmaticsbingogoals.goal.wear_full_diamond"))
        );
        addGoal(BingoGoal.builder(eid("huge_crimson_fungus_in_overworld"))
                .criterion("grow", GrowFeatureTrigger.builder()
                        .feature(EnigmaticsBingoFeatureTags.HUGE_CRIMSON_FUNGI)
                        .location(
                                LocationPredicate.Builder.inDimension(Level.OVERWORLD).build()
                        ).build())
                .name(Component.translatable("enigmaticsbingogoals.goal.huge_fungus_in_overworld", Items.CRIMSON_FUNGUS.getName()))
                .icon(IndicatorIcon.infer(
                        Items.CRIMSON_FUNGUS,
                        Blocks.GRASS_BLOCK
                ))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.SILK_TOUCH,
                        EnigmaticsBingoTags.CRIMSON_FOREST, EnigmaticsBingoTags.NETHER_LATE, EnigmaticsBingoTags.GROW_TREE)
        );
        addGoal(makeBannerWithPatternItemGoal(eid("use_skull_pattern"), items, Items.SKULL_BANNER_PATTERN,
                BannerPatterns.SKULL, "Skull Charge Pattern")
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.WITHER_SKULL, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(advancementProgressGoal(eid("eat_some_unique_foods"),
                ResourceLocation.withDefaultNamespace("husbandry/balanced_diet"), 25, 32)
                .name(Component.translatable("enigmaticsbingogoals.goal.eat_some_unique_foods", 0),
                        subber -> subber.sub("with.0", "count")
                )
                .tooltip(Component.translatable("enigmaticsbingogoals.goal.eat_some_unique_foods.tooltip", Items.CAKE.getName()))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.UNIQUE_FOOD)
                .icon(
                        CycleIcon.infer(Arrays.stream(VanillaHusbandryAdvancements.EDIBLE_ITEMS)),
                        subber -> subber.sub("icons.*.item.count", "count")
                )
        );
        addGoal(killEntitiesFromTagGoal(eid("kill_some_unique_mobs"), EnigmaticsBingoEntityTypeTags.MOBS, 26, 35, true)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_unique_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.UNIQUE_NEUTRAL_MOBS, EnigmaticsBingoSynergies.UNIQUE_HOSTILE_MOBS)
        );
        addGoal(advancementGoal(eid("get_advancement_is_it_a_plane"),
                Component.translatable("advancements.adventure.spyglass_at_dragon.title"),
                ResourceLocation.withDefaultNamespace("adventure/spyglass_at_dragon"))
                .tags(EnigmaticsBingoTags.END, EnigmaticsBingoTags.AMETHYST, EnigmaticsBingoTags.END_ENTRY)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.SPYGLASS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(effectGoal(eid("get_hero_of_the_village"), MobEffects.HERO_OF_THE_VILLAGE)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.OUTPOST, EnigmaticsBingoTags.RAID)
        );
        addGoal(advancementGoal(eid("get_postmortal"),
                Component.translatable("advancements.adventure.totem_of_undying.title"),
                ResourceLocation.withDefaultNamespace("adventure/totem_of_undying"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.OUTPOST, EnigmaticsBingoTags.RAID)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.TOTEM_OF_UNDYING), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(obtainAllItemsFromTagGoal(eid("obtain_all_horse_armors"), EnigmaticsBingoItemTags.HORSE_ARMORS)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.END, EnigmaticsBingoTags.RARE_COLLECTIBLE_BATCH,
                        EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.VILLAGE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_all_horse_armor",
                        Component.translatable(EnigmaticsBingoItemTags.HORSE_ARMORS.getTranslationKey())))
        );
        addGoal(advancementGoal(eid("get_advancement_careful_restoration"),
                Component.translatable("advancements.adventure.craft_decorated_pot_using_only_sherds.title"),
                ResourceLocation.withDefaultNamespace("adventure/craft_decorated_pot_using_only_sherds"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.TRAIL_RUINS, EnigmaticsBingoTags.RARE_COLLECTIBLE_BATCH,
                        EnigmaticsBingoTags.TRIAL_CHAMBER)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.DECORATED_POT), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(potionGoal(eid("obtain_potion_of_leaping"), items,
                Potions.LEAPING, Potions.LONG_LEAPING, Potions.STRONG_LEAPING)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(potionGoal(eid("obtain_potion_of_slow_falling"), items,
                Potions.SLOW_FALLING, Potions.LONG_SLOW_FALLING)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(potionGoal(eid("obtain_potion_of_the_turtle_master"), items,
                Potions.TURTLE_MASTER, Potions.LONG_TURTLE_MASTER, Potions.STRONG_TURTLE_MASTER)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(advancementGoal(eid("get_advancement_the_end"),
                Component.translatable("advancements.end.root.title"),
                ResourceLocation.withDefaultNamespace("end/root"))
                .tags(EnigmaticsBingoTags.END, EnigmaticsBingoTags.END_ENTRY)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.END_STONE), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(obtainItemGoal(eid("obtain_dragon_egg"), items, Items.DRAGON_EGG)
                .tags(EnigmaticsBingoTags.END, EnigmaticsBingoTags.END_ENTRY)
        );
        addGoal(advancementGoal(eid("get_advancement_the_city_at_the_end_of_the_game"),
                Component.translatable("advancements.end.find_end_city.title"),
                ResourceLocation.withDefaultNamespace("end/find_end_city"))
                .tags(EnigmaticsBingoTags.END, EnigmaticsBingoTags.END_PROGRESS)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.PURPUR_PILLAR), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(eid("get_advancement_great_view_from_up_here"),
                Component.translatable("advancements.end.levitate.title"),
                ResourceLocation.withDefaultNamespace("end/levitate"))
                .tags(EnigmaticsBingoTags.END, EnigmaticsBingoTags.END_PROGRESS)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.SHULKER_SHELL), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(eatItemGoal(eid("eat_chorus_fruit"), items, Items.CHORUS_FRUIT)
                .tags(EnigmaticsBingoTags.END, EnigmaticsBingoTags.END_PROGRESS)
        );
        addGoal(obtainItemGoal(eid("obtain_elytra"), items, Items.ELYTRA)
                .tags(EnigmaticsBingoTags.END, EnigmaticsBingoTags.END_PROGRESS, EnigmaticsBingoTags.END_SHIP)
        );
        addGoal(obtainItemGoal(eid("obtain_dragon_head"), items, Items.DRAGON_HEAD)
                .tags(EnigmaticsBingoTags.END, EnigmaticsBingoTags.END_PROGRESS, EnigmaticsBingoTags.END_SHIP)
        );
        addGoal(obtainItemGoal(eid("obtain_purpur_block"), items, Items.PURPUR_BLOCK)
                .tags(EnigmaticsBingoTags.END, EnigmaticsBingoTags.END_PROGRESS)
        );
        addGoal(BingoGoal.builder(eid("never_damage"))
                .criterion("damage", EntityHurtPlayerTrigger.TriggerInstance.entityHurtPlayer())
                .tags(EnigmaticsBingoTags.NEVER, EnigmaticsBingoTags.NEVER_TAKE_DAMAGE)
                .catalyst(EnigmaticsBingoSynergies.TAKE_DAMAGE)
                .name(Component.translatable("enigmaticsbingogoals.goal.never_damage"))
                .icon(new IndicatorIcon(EffectIcon.of(MobEffects.HARM), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(neverLevelsGoal(eid("never_levels"), 1, 1));
        addGoal(reachLevelsGoal(eid("reach_levels"), 26, 35));
        addGoal(dieToDamageTypeGoal(eid("fall_out_of_world"), DamageTypeTags.ALWAYS_MOST_SIGNIFICANT_FALL)
                .tags(EnigmaticsBingoTags.END, EnigmaticsBingoTags.END_ENTRY, EnigmaticsBingoTags.DIE_TO)
                .name(Component.translatable("enigmaticsbingogoals.goal.fall_out_of_world"))
                .icon(IndicatorIcon.infer(Blocks.END_PORTAL, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        addGoal(advancementGoal(eid("get_advancement_eye_spy"),
                Component.translatable("advancements.story.follow_ender_eye.title"),
                ResourceLocation.withDefaultNamespace("story/follow_ender_eye"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.END_ENTRY, EnigmaticsBingoTags.STRONGHOLD)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.ENDER_EYE), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(eid("get_advancement_this_boat_has_legs"),
                Component.translatable("advancements.nether.ride_strider.title"),
                ResourceLocation.withDefaultNamespace("nether/ride_strider"))
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.STRIDER)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.WARPED_FUNGUS_ON_A_STICK), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(obtainItemGoal(eid("obtain_crimson_nylium"), items, Items.CRIMSON_NYLIUM)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.SILK_TOUCH,
                        EnigmaticsBingoTags.CRIMSON_FOREST, EnigmaticsBingoTags.NETHER_LATE)
        );
        addGoal(obtainItemGoal(eid("obtain_lodestone"), items, Items.LODESTONE)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_LATE, EnigmaticsBingoTags.NETHERITE)
        );
        addGoal(obtainItemGoal(eid("obtain_netherite_ingot"), items, Items.NETHERITE_INGOT)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_LATE, EnigmaticsBingoTags.NETHERITE)
        );
        addGoal(obtainItemGoal(eid("obtain_wither_skeleton_skull"), items, Items.WITHER_SKELETON_SKULL)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.WITHER_SKULL, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(breedFrogVariantGoal(eid("breed_white_frog"), FrogVariant.WARM)
                .name(Component.translatable("enigmaticsbingogoals.goal.breed_white_frog", EntityType.FROG.getDescription()))
        );
        addGoal(breedFrogVariantGoal(eid("breed_orange_frog"), FrogVariant.TEMPERATE)
                .name(Component.translatable("enigmaticsbingogoals.goal.breed_orange_frog", EntityType.FROG.getDescription()))
        );
        addGoal(breedFrogVariantGoal(eid("breed_green_frog"), FrogVariant.COLD)
                .name(Component.translatable("enigmaticsbingogoals.goal.breed_green_frog", EntityType.FROG.getDescription()))
        );
        addGoal(obtainSomeItemsFromTagGoal(eid("obtain_some_saplings"), EnigmaticsBingoItemTags.SAPLINGS, 6, 7)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.PLANT_BATCH)
                .antisynergy(EnigmaticsBingoSynergies.SAPLING)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_saplings", 0,
                                Component.translatable(EnigmaticsBingoItemTags.SAPLINGS.getTranslationKey())),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(makeBannerWithPatternItemGoal(eid("use_globe_pattern"), items, Items.GLOBE_BANNER_PATTERN,
                BannerPatterns.GLOBE, "Globe Pattern")
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.VILLAGE)
        );
    }
}
