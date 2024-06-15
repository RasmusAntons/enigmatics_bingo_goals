package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoFeatureTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoEntityTypeTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoItemTags;
import io.github.gaming32.bingo.data.BingoDifficulties;
import io.github.gaming32.bingo.data.BingoGoal;
import io.github.gaming32.bingo.data.BingoTags;
import io.github.gaming32.bingo.data.icons.*;
import io.github.gaming32.bingo.triggers.GrowFeatureTrigger;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
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

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class EnigmaticsHardGoalProvider extends EnigmaticsDifficultyGoalProvider {
    public EnigmaticsHardGoalProvider(BiConsumer<ResourceLocation, BingoGoal> goalAdder, HolderLookup.Provider registries) {
        super(BingoDifficulties.HARD, goalAdder, registries);
    }

    @Override
    public void addGoals() {
        // TODO: Never open your inventory
        addGoal(advancementsGoal(id("get_advancements"), 26, 35));
        addGoal(BingoGoal.builder(id("cure_zombie_villager"))
                .criterion("transform", CuredZombieVillagerTrigger.TriggerInstance.curedZombieVillager())
                .name(Component.translatable("enigmaticsbingogoals.goal.cure_zombie_villager"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.GOLDEN_APPLE, EnigmaticsBingoTags.IGLOO)
                .icon(IndicatorIcon.infer(EntityType.ZOMBIE_VILLAGER, ItemIcon.ofItem(Items.GOLDEN_APPLE)))
        );
        addGoal(numberOfEffectsGoal(id("get_some_effects"), 15, 20));
        addGoal(breedAnimalGoal(id("breed_mule"), EntityType.MULE)
                .tags(BingoTags.OVERWORLD)
        );
        addGoal(advancementProgressGoal(id("visit_some_unique_overworld_biomes"),
                ResourceLocation.withDefaultNamespace("adventure/adventuring_time"), 26, 35)
                .name(Component.translatable("enigmaticsbingogoals.goal.visit_some_unique_overworld_biomes", 0),
                        subber -> subber.sub("with.0", "count")
                )
                .tooltip(Component.translatable("enigmaticsbingogoals.goal.visit_some_unique_overworld_biomes.tooltip",
                        Component.translatable("advancements.adventure.adventuring_time.title")))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BIOMES, EnigmaticsBingoTags.OVERWORLD_EXPLORE)
                .icon(
                        Items.GOLDEN_BOOTS,
                        subber -> subber.sub("item.count", "count")
                )
        );
        addGoal(advancementProgressGoal(id("breed_some_unique_mobs"),
                ResourceLocation.withDefaultNamespace("husbandry/bred_all_animals"), 11, 15)
                .name(Component.translatable("enigmaticsbingogoals.goal.breed_some_unique_mobs", 0),
                        subber -> subber.sub("with.0", "count"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BREED_MOB)
                .icon(IndicatorIcon.infer(CycleIcon.infer(
                                Stream.concat(VanillaHusbandryAdvancements.BREEDABLE_ANIMALS.stream(),
                                        VanillaHusbandryAdvancements.INDIRECTLY_BREEDABLE_ANIMALS.stream()).toList()
                        ), EffectIcon.of(MobEffects.HEALTH_BOOST)),
                        subber -> subber.sub("base.icons.*.item.count", "count"))
        );
        addGoal(killEntitiesFromTagGoal(id("kill_some_unique_hostile_mobs"), EnigmaticsBingoEntityTypeTags.HOSTILE, 15, 20, true)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_unique_hostile_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.UNIQUE_HOSTILE_MOBS, EnigmaticsBingoTags.KILL_MOB)
        );
        addGoal(killEntityGoal(id("kill_ender_dragon"), EntityType.ENDER_DRAGON)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_ender_dragon", EntityType.ENDER_DRAGON.getDescription()))
                .tags(BingoTags.END, EnigmaticsBingoTags.END_ENTRY)
                .icon(IndicatorIcon.infer(Items.DRAGON_HEAD, Items.NETHERITE_SWORD))
        );
        addGoal(wearArmorPiecesGoal(id("wear_full_diamond"), Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE,
                Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE, EnigmaticsBingoTags.ARMOR)
                .name(Component.translatable("enigmaticsbingogoals.goal.wear_full_diamond"))
        );
        addGoal(BingoGoal.builder(id("huge_crimson_fungus_in_overworld"))
                .criterion("grow", GrowFeatureTrigger.builder()
                        .feature(EnigmaticsBingoFeatureTags.HUGE_CRIMSON_FUNGI)
                        .location(
                                LocationPredicate.Builder.inDimension(Level.OVERWORLD).build()
                        ).build())
                .name(Component.translatable("enigmaticsbingogoals.goal.huge_fungus_in_overworld", Items.CRIMSON_FUNGUS.getDescription()))
                .icon(IndicatorIcon.infer(
                        Items.CRIMSON_FUNGUS,
                        Blocks.GRASS_BLOCK
                ))
                .tags(BingoTags.OVERWORLD, BingoTags.NETHER, BingoTags.VILLAGE, EnigmaticsBingoTags.SILK_TOUCH,
                        EnigmaticsBingoTags.CRIMSON_FOREST, EnigmaticsBingoTags.NETHER_LATE, EnigmaticsBingoTags.GROW_TREE)
        );
        // TODO: Use a Skull Banner Pattern
        addGoal(advancementProgressGoal(id("eat_some_unique_foods"),
                ResourceLocation.withDefaultNamespace("husbandry/balanced_diet"), 25, 32)
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
        addGoal(advancementGoal(id("get_advancement_is_it_a_plane"),
                Component.translatable("advancements.adventure.spyglass_at_dragon.title"),
                ResourceLocation.withDefaultNamespace("adventure/spyglass_at_dragon"))
                .tags(BingoTags.END, EnigmaticsBingoTags.AMETHYST, EnigmaticsBingoTags.END_ENTRY)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.SPYGLASS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_hero_of_the_village"),
                Component.translatable("advancements.adventure.hero_of_the_village.title"),
                ResourceLocation.withDefaultNamespace("adventure/hero_of_the_village"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.OUTPOST, EnigmaticsBingoTags.RAID)
                .icon(new IndicatorIcon(new ItemIcon(BingoGoalGeneratorUtils.getOminousBanner(registries)), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_postmortal"),
                Component.translatable("advancements.adventure.totem_of_undying.title"),
                ResourceLocation.withDefaultNamespace("adventure/totem_of_undying"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.OUTPOST, EnigmaticsBingoTags.RAID)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.TOTEM_OF_UNDYING), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(obtainAllItemsFromTagGoal(id("obtain_all_horse_armors"), EnigmaticsBingoItemTags.HORSE_ARMORS)
                .tags(BingoTags.OVERWORLD, BingoTags.NETHER, BingoTags.END, EnigmaticsBingoTags.RARE_COLLECTIBLE_BATCH,
                        EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.MINESHAFT, BingoTags.VILLAGE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_all_horse_armor",
                        Component.translatable(EnigmaticsBingoItemTags.HORSE_ARMORS.getTranslationKey())))
        );
        addGoal(advancementGoal(id("get_advancement_careful_restoration"),
                Component.translatable("advancements.adventure.craft_decorated_pot_using_only_sherds.title"),
                ResourceLocation.withDefaultNamespace("adventure/craft_decorated_pot_using_only_sherds"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TRAIL_RUINS, EnigmaticsBingoTags.RARE_COLLECTIBLE_BATCH,
                        EnigmaticsBingoTags.TRIAL_CHAMBER)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.DECORATED_POT), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(potionGoal(id("obtain_potion_of_leaping"),
                Potions.LEAPING, Potions.LONG_LEAPING, Potions.STRONG_LEAPING)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(potionGoal(id("obtain_potion_of_slow_falling"),
                Potions.SLOW_FALLING, Potions.LONG_SLOW_FALLING)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(potionGoal(id("obtain_potion_of_the_turtle_master"),
                Potions.TURTLE_MASTER, Potions.LONG_TURTLE_MASTER, Potions.STRONG_TURTLE_MASTER)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(BingoGoal.builder(id("summon_the_wither"))
                .criterion("summon", SummonedEntityTrigger.TriggerInstance.summonedEntity(
                        EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(EntityType.WITHER)))
                )
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.WITHER_SKULL, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.NETHER_LATE)
                .name(Component.translatable("enigmaticsbingogoals.goal.summon_the_wither", EntityType.WITHER.getDescription()))
                .icon(IndicatorIcon.infer(EntityType.WITHER, Items.WITHER_SKELETON_SKULL))
        );
        addGoal(obtainItemGoal(id("obtain_nether_star"), Items.NETHER_STAR)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.WITHER_SKULL, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.NETHER_LATE)
        );
        addGoal(advancementGoal(id("get_advancement_the_end"),
                Component.translatable("advancements.end.root.title"),
                ResourceLocation.withDefaultNamespace("end/root"))
                .tags(BingoTags.END, EnigmaticsBingoTags.END_ENTRY)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.END_STONE), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(obtainItemGoal(id("obtain_dragon_egg"), Items.DRAGON_EGG)
                .tags(BingoTags.END, EnigmaticsBingoTags.END_ENTRY)
        );
        addGoal(advancementGoal(id("get_advancement_the_city_at_the_end_of_the_game"),
                Component.translatable("advancements.end.find_end_city.title"),
                ResourceLocation.withDefaultNamespace("end/find_end_city"))
                .tags(BingoTags.END, EnigmaticsBingoTags.END_PROGRESS)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.PURPUR_PILLAR), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_great_view_from_up_here"),
                Component.translatable("advancements.end.levitate.title"),
                ResourceLocation.withDefaultNamespace("end/levitate"))
                .tags(BingoTags.END, EnigmaticsBingoTags.END_PROGRESS)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.SHULKER_SHELL), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(eatItemGoal(id("eat_chorus_fruit"), Items.CHORUS_FRUIT)
                .tags(BingoTags.END, EnigmaticsBingoTags.END_PROGRESS)
        );
        addGoal(obtainItemGoal(id("obtain_elytra"), Items.ELYTRA)
                .tags(BingoTags.END, EnigmaticsBingoTags.END_PROGRESS, EnigmaticsBingoTags.END_SHIP)
        );
        addGoal(obtainItemGoal(id("obtain_dragon_head"), Items.DRAGON_HEAD)
                .tags(BingoTags.END, EnigmaticsBingoTags.END_PROGRESS, EnigmaticsBingoTags.END_SHIP)
        );
        addGoal(obtainItemGoal(id("obtain_purpur_block"), Items.PURPUR_BLOCK)
                .tags(BingoTags.END, EnigmaticsBingoTags.END_PROGRESS)
        );
        addGoal(BingoGoal.builder(id("never_damage"))
                .criterion("damage", EntityHurtPlayerTrigger.TriggerInstance.entityHurtPlayer())
                .tags(BingoTags.NEVER, EnigmaticsBingoTags.NEVER_TAKE_DAMAGE)
                .name(Component.translatable("enigmaticsbingogoals.goal.never_damage"))
                .icon(new IndicatorIcon(EffectIcon.of(MobEffects.HARM), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(neverLevelsGoal(id("never_levels"), 1, 1));
        addGoal(reachLevelsGoal(id("reach_levels"), 26, 35));
        addGoal(dieToDamageTypeGoal(id("fall_out_of_world"), DamageTypeTags.ALWAYS_MOST_SIGNIFICANT_FALL)
                .tags(BingoTags.END, EnigmaticsBingoTags.END_ENTRY, EnigmaticsBingoTags.DIE_TO)
                .name(Component.translatable("enigmaticsbingogoals.goal.fall_out_of_world"))
                .icon(IndicatorIcon.infer(Blocks.END_PORTAL, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        addGoal(advancementGoal(id("get_advancement_eye_spy"),
                Component.translatable("advancements.story.follow_ender_eye.title"),
                ResourceLocation.withDefaultNamespace("story/follow_ender_eye"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.END_ENTRY, EnigmaticsBingoTags.STRONGHOLD)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.ENDER_EYE), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_this_boat_has_legs"),
                Component.translatable("advancements.nether.ride_strider.title"),
                ResourceLocation.withDefaultNamespace("nether/ride_strider"))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.STRIDER)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.WARPED_FUNGUS_ON_A_STICK), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(obtainItemGoal(id("obtain_crimson_nylium"), Items.CRIMSON_NYLIUM)
                .tags(BingoTags.NETHER, BingoTags.VILLAGE, EnigmaticsBingoTags.SILK_TOUCH,
                        EnigmaticsBingoTags.CRIMSON_FOREST, EnigmaticsBingoTags.NETHER_LATE)
        );
        addGoal(obtainItemGoal(id("obtain_lodestone"), Items.LODESTONE)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_LATE, EnigmaticsBingoTags.NETHERITE)
        );
        addGoal(obtainItemGoal(id("obtain_netherite_ingot"), Items.NETHERITE_INGOT)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_LATE, EnigmaticsBingoTags.NETHERITE)
        );
        addGoal(obtainItemGoal(id("obtain_wither_skeleton_skull"), Items.WITHER_SKELETON_SKULL)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.WITHER_SKULL, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(breedFrogVariantGoal(id("breed_white_frog"), FrogVariant.WARM)
                .name(Component.translatable("enigmaticsbingogoals.goal.breed_white_frog", EntityType.FROG.getDescription()))
        );
        addGoal(breedFrogVariantGoal(id("breed_orange_frog"), FrogVariant.TEMPERATE)
                .name(Component.translatable("enigmaticsbingogoals.goal.breed_orange_frog", EntityType.FROG.getDescription()))
        );
        addGoal(breedFrogVariantGoal(id("breed_green_frog"), FrogVariant.COLD)
                .name(Component.translatable("enigmaticsbingogoals.goal.breed_green_frog", EntityType.FROG.getDescription()))
        );
    }
}
