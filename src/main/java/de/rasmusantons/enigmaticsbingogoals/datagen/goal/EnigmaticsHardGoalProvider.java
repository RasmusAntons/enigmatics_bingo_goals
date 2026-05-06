package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoDifficulties;
import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoTags;
import de.rasmusantons.enigmaticsbingogoals.datagen.EnigmaticsBingoSynergies;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoEntityTypeTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoFeatureTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoItemTags;
import io.github.gaming32.bingo.data.BingoTags;
import io.github.gaming32.bingo.data.goal.BingoGoal;
import io.github.gaming32.bingo.data.icons.*;
import io.github.gaming32.bingo.triggers.GrowFeatureTrigger;
import net.minecraft.advancements.criterion.CuredZombieVillagerTrigger;
import net.minecraft.advancements.criterion.EntityHurtPlayerTrigger;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.packs.VanillaHusbandryAdvancements;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.frog.FrogVariants;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerPatterns;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static de.rasmusantons.enigmaticsbingogoals.datagen.goal.EnigmaticsBingoGoalIds.Hard.*;

public class EnigmaticsHardGoalProvider extends EnigmaticsDifficultyGoalProvider {
    public EnigmaticsHardGoalProvider(BiConsumer<Identifier, BingoGoal> goalAdder, HolderLookup.Provider registries) {
        super(EnigmaticsBingoDifficulties.HARD, goalAdder, registries);
    }

    @Override
    public void addGoals() {
        final var entityTypes = registries.lookupOrThrow(Registries.ENTITY_TYPE);
        final var items = registries.lookupOrThrow(Registries.ITEM);

        // TODO: Never open your inventory
        addGoal(advancementsGoal(GET_ADVANCEMENTS, 26, 35));
        addGoal(BingoGoal.builder(CURE_ZOMBIE_VILLAGER)
                .criterion("transform", CuredZombieVillagerTrigger.TriggerInstance.curedZombieVillager())
                .name(Component.translatable("enigmaticsbingogoals.goal.cure_zombie_villager"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.IGLOO)
                .icon(IndicatorIcon.infer(EntityType.ZOMBIE_VILLAGER, ItemIcon.ofItem(Items.GOLDEN_APPLE)))
        );
        addGoal(numberOfEffectsGoal(GET_SOME_EFFECTS, 13, 19));
        addGoal(breedAnimalGoal(BREED_MULE, entityTypes, EntityType.MULE)
                .tags(EnigmaticsBingoTags.OVERWORLD)
        );
        addGoal(advancementProgressGoal(VISIT_SOME_UNIQUE_OVERWORLD_BIOMES,
                Identifier.withDefaultNamespace("adventure/adventuring_time"), 26, 35)
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
        addGoal(tameSomeCatsGoal(TAME_SOME_CATS, registries.lookupOrThrow(Registries.CAT_VARIANT), 5, 6));
        addGoal(tameSomeWolvesGoal(TAME_SOME_WOLVES, 3, 4));
        addGoal(advancementProgressGoal(BREED_SOME_UNIQUE_MOBS,
                Identifier.withDefaultNamespace("husbandry/bred_all_animals"), 11, 15)
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
        addGoal(killEntitiesFromTagGoal(KILL_SOME_UNIQUE_HOSTILE_MOBS, EnigmaticsBingoEntityTypeTags.HOSTILE, 15, 19, true)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_unique_hostile_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.UNIQUE_HOSTILE_MOBS)
        );
        addGoal(killEntityGoal(KILL_ENDER_DRAGON, entityTypes, EntityType.ENDER_DRAGON)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_ender_dragon", EntityType.ENDER_DRAGON.getDescription()))
                .tags(EnigmaticsBingoTags.END, EnigmaticsBingoTags.END_ENTRY)
                .icon(IndicatorIcon.infer(Items.DRAGON_HEAD, Items.NETHERITE_SWORD))
        );
        addGoal(wearArmorPiecesGoal(WEAR_FULL_DIAMOND, entityTypes, items, Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE,
                Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.ARMOR)
                .name(Component.translatable("enigmaticsbingogoals.goal.wear_full_diamond"))
        );
        addGoal(BingoGoal.builder(HUGE_CRIMSON_FUNGUS_IN_OVERWORLD)
                .criterion("grow", GrowFeatureTrigger.builder()
                        .feature(EnigmaticsBingoFeatureTags.HUGE_CRIMSON_FUNGI)
                        .location(
                                LocationPredicate.Builder.inDimension(Level.OVERWORLD).build()
                        ).build())
                .name(Component.translatable("enigmaticsbingogoals.goal.huge_fungus_in_overworld",
                        Component.translatable(Items.CRIMSON_FUNGUS.getDescriptionId()))
                )
                .icon(IndicatorIcon.infer(
                        Items.CRIMSON_FUNGUS,
                        Blocks.GRASS_BLOCK
                ))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.SILK_TOUCH,
                        EnigmaticsBingoTags.CRIMSON_FOREST, EnigmaticsBingoTags.NETHER_LATE, EnigmaticsBingoTags.GROW_TREE)
        );
        addGoal(makeBannerWithPatternItemGoal(USE_SKULL_PATTERN, items, Items.SKULL_BANNER_PATTERN,
                BannerPatterns.SKULL, "Skull Charge Pattern")
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.WITHER_SKULL, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(advancementProgressGoal(EAT_SOME_UNIQUE_FOODS,
                Identifier.withDefaultNamespace("husbandry/balanced_diet"), 25, 32)
                .name(Component.translatable("enigmaticsbingogoals.goal.eat_some_unique_foods", 0),
                        subber -> subber.sub("with.0", "count")
                )
                .tooltip(Component.translatable("enigmaticsbingogoals.goal.eat_some_unique_foods.tooltip",
                        Component.translatable(Items.CAKE.getDescriptionId()))
                )
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.UNIQUE_FOOD)
                .icon(
                        CycleIcon.infer(Arrays.stream(VanillaHusbandryAdvancements.EDIBLE_ITEMS)),
                        subber -> subber.sub("icons.*.item.count", "count")
                )
        );
        addGoal(killEntitiesFromTagGoal(KILL_SOME_UNIQUE_MOBS, EnigmaticsBingoEntityTypeTags.MOBS, 26, 35, true)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_unique_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.UNIQUE_NEUTRAL_MOBS, EnigmaticsBingoSynergies.UNIQUE_HOSTILE_MOBS)
        );
        addGoal(advancementGoal(GET_ADVANCEMENT_IS_IT_A_PLANE,
                Component.translatable("advancements.adventure.spyglass_at_dragon.title"),
                Identifier.withDefaultNamespace("adventure/spyglass_at_dragon"))
                .tags(EnigmaticsBingoTags.END, EnigmaticsBingoTags.AMETHYST, EnigmaticsBingoTags.END_ENTRY)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.SPYGLASS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(effectGoal(GET_HERO_OF_THE_VILLAGE, MobEffects.HERO_OF_THE_VILLAGE)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.OUTPOST, EnigmaticsBingoTags.RAID)
        );
        addGoal(advancementGoal(GET_POSTMORTAL,
                Component.translatable("advancements.adventure.totem_of_undying.title"),
                Identifier.withDefaultNamespace("adventure/totem_of_undying"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.OUTPOST, EnigmaticsBingoTags.RAID)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.TOTEM_OF_UNDYING), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(obtainAllItemsFromTagGoal(OBTAIN_ALL_HORSE_ARMORS, EnigmaticsBingoItemTags.HORSE_ARMORS)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.END, EnigmaticsBingoTags.RARE_COLLECTIBLE_BATCH,
                        EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.VILLAGE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_all_horse_armor",
                        Component.translatable(EnigmaticsBingoItemTags.HORSE_ARMORS.getTranslationKey())))
        );
        addGoal(advancementGoal(GET_ADVANCEMENT_CAREFUL_RESTORATION,
                Component.translatable("advancements.adventure.craft_decorated_pot_using_only_sherds.title"),
                Identifier.withDefaultNamespace("adventure/craft_decorated_pot_using_only_sherds"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.TRAIL_RUINS, EnigmaticsBingoTags.RARE_COLLECTIBLE_BATCH,
                        EnigmaticsBingoTags.TRIAL_CHAMBER)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.DECORATED_POT), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(potionGoal(OBTAIN_POTION_OF_LEAPING, items,
                Potions.LEAPING, Potions.LONG_LEAPING, Potions.STRONG_LEAPING)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(potionGoal(OBTAIN_POTION_OF_SLOW_FALLING, items,
                Potions.SLOW_FALLING, Potions.LONG_SLOW_FALLING)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(potionGoal(OBTAIN_POTION_OF_THE_TURTLE_MASTER, items,
                Potions.TURTLE_MASTER, Potions.LONG_TURTLE_MASTER, Potions.STRONG_TURTLE_MASTER)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(advancementGoal(GET_ADVANCEMENT_THE_END,
                Component.translatable("advancements.end.root.title"),
                Identifier.withDefaultNamespace("end/root"))
                .tags(EnigmaticsBingoTags.END, EnigmaticsBingoTags.END_ENTRY)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.END_STONE), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(obtainItemGoal(OBTAIN_DRAGON_EGG, items, Items.DRAGON_EGG)
                .tags(EnigmaticsBingoTags.END, EnigmaticsBingoTags.END_ENTRY)
        );
        addGoal(advancementGoal(GET_ADVANCEMENT_THE_CITY_AT_THE_END_OF_THE_GAME,
                Component.translatable("advancements.end.find_end_city.title"),
                Identifier.withDefaultNamespace("end/find_end_city"))
                .tags(EnigmaticsBingoTags.END, EnigmaticsBingoTags.END_PROGRESS)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.PURPUR_PILLAR), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(GET_ADVANCEMENT_GREAT_VIEW_FROM_UP_HERE,
                Component.translatable("advancements.end.levitate.title"),
                Identifier.withDefaultNamespace("end/levitate"))
                .tags(EnigmaticsBingoTags.END, EnigmaticsBingoTags.END_PROGRESS)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.SHULKER_SHELL), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(eatItemGoal(EAT_CHORUS_FRUIT, items, Items.CHORUS_FRUIT)
                .tags(EnigmaticsBingoTags.END, EnigmaticsBingoTags.END_PROGRESS)
        );
        addGoal(obtainItemGoal(OBTAIN_ELYTRA, items, Items.ELYTRA)
                .tags(EnigmaticsBingoTags.END, EnigmaticsBingoTags.END_PROGRESS, EnigmaticsBingoTags.END_SHIP)
        );
        addGoal(obtainItemGoal(OBTAIN_DRAGON_HEAD, items, Items.DRAGON_HEAD)
                .tags(EnigmaticsBingoTags.END, EnigmaticsBingoTags.END_PROGRESS, EnigmaticsBingoTags.END_SHIP)
        );
        addGoal(obtainItemGoal(OBTAIN_PURPUR_BLOCK, items, Items.PURPUR_BLOCK)
                .tags(EnigmaticsBingoTags.END, EnigmaticsBingoTags.END_PROGRESS)
        );
        addGoal(BingoGoal.builder(NEVER_DAMAGE)
                .criterion("damage", EntityHurtPlayerTrigger.TriggerInstance.entityHurtPlayer())
                .tags(EnigmaticsBingoTags.NEVER, BingoTags.LOCKOUT_INFLICTABLE, EnigmaticsBingoTags.NEVER_TAKE_DAMAGE)
                .catalyst(EnigmaticsBingoSynergies.TAKE_DAMAGE)
                .name(Component.translatable("enigmaticsbingogoals.goal.never_damage"))
                .icon(new IndicatorIcon(EffectIcon.of(MobEffects.INSTANT_DAMAGE), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(neverLevelsGoal(NEVER_LEVELS, 1, 1));
        addGoal(reachLevelsGoal(REACH_LEVELS, 26, 35));
        addGoal(dieToDamageTypeGoal(FALL_OUT_OF_WORLD, DamageTypeTags.ALWAYS_MOST_SIGNIFICANT_FALL)
                .tags(EnigmaticsBingoTags.END, EnigmaticsBingoTags.END_ENTRY, EnigmaticsBingoTags.DIE_TO)
                .name(Component.translatable("enigmaticsbingogoals.goal.fall_out_of_world"))
                .icon(IndicatorIcon.infer(Blocks.END_PORTAL, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        addGoal(advancementGoal(GET_ADVANCEMENT_EYE_SPY,
                Component.translatable("advancements.story.follow_ender_eye.title"),
                Identifier.withDefaultNamespace("story/follow_ender_eye"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.END_ENTRY, EnigmaticsBingoTags.STRONGHOLD)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.ENDER_EYE), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(GET_ADVANCEMENT_THIS_BOAT_HAS_LEGS,
                Component.translatable("advancements.nether.ride_strider.title"),
                Identifier.withDefaultNamespace("nether/ride_strider"))
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.STRIDER)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.WARPED_FUNGUS_ON_A_STICK), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(obtainItemGoal(OBTAIN_CRIMSON_NYLIUM, items, Items.CRIMSON_NYLIUM)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.SILK_TOUCH,
                        EnigmaticsBingoTags.CRIMSON_FOREST, EnigmaticsBingoTags.NETHER_LATE)
        );
        addGoal(obtainItemGoal(OBTAIN_LODESTONE, items, Items.LODESTONE)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_LATE, EnigmaticsBingoTags.NETHERITE)
        );
        addGoal(obtainItemGoal(OBTAIN_NETHERITE_INGOT, items, Items.NETHERITE_INGOT)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_LATE, EnigmaticsBingoTags.NETHERITE)
        );
        addGoal(obtainItemGoal(OBTAIN_WITHER_SKELETON_SKULL, items, Items.WITHER_SKELETON_SKULL)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.WITHER_SKULL, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(breedFrogVariantGoal(BREED_WHITE_FROG, FrogVariants.WARM)
                .name(Component.translatable("enigmaticsbingogoals.goal.breed_white_frog", EntityType.FROG.getDescription()))
        );
        addGoal(breedFrogVariantGoal(BREED_ORANGE_FROG, FrogVariants.TEMPERATE)
                .name(Component.translatable("enigmaticsbingogoals.goal.breed_orange_frog", EntityType.FROG.getDescription()))
        );
        addGoal(breedFrogVariantGoal(BREED_GREEN_FROG, FrogVariants.COLD)
                .name(Component.translatable("enigmaticsbingogoals.goal.breed_green_frog", EntityType.FROG.getDescription()))
        );
        addGoal(obtainSomeItemsFromTagGoal(OBTAIN_SOME_SAPLINGS, EnigmaticsBingoItemTags.SAPLINGS, 6, 7)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.PLANT_BATCH)
                .antisynergy(EnigmaticsBingoSynergies.SAPLING)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_saplings", 0,
                                Component.translatable(EnigmaticsBingoItemTags.SAPLINGS.getTranslationKey())),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(makeBannerWithPatternItemGoal(USE_GLOBE_PATTERN, items, Items.GLOBE_BANNER_PATTERN,
                BannerPatterns.GLOBE, "Globe Pattern")
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.VILLAGE)
        );
    }
}
