package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoEntityTypeTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoItemTags;
import io.github.gaming32.bingo.data.BingoDifficulties;
import io.github.gaming32.bingo.data.BingoGoal;
import io.github.gaming32.bingo.data.BingoTags;
import io.github.gaming32.bingo.data.icons.*;
import net.minecraft.advancements.critereon.CuredZombieVillagerTrigger;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.advancements.critereon.SummonedEntityTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.advancements.packs.VanillaHusbandryAdvancements;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Blocks;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class EnigmaticsHardGoalProvider extends EnigmaticsDifficultyGoalProvider {
    public EnigmaticsHardGoalProvider(Consumer<BingoGoal.Holder> goalAdder, HolderLookup.Provider registries) {
        super(BingoDifficulties.HARD, goalAdder, registries);
    }

    @Override
    public void addGoals() {
        // TODO: Never open your inventory
        addGoal(advancementsGoal(id("get_advancements"), 29, 35));
        addGoal(BingoGoal.builder(id("cure_zombie_villager"))
                .criterion("transform", CuredZombieVillagerTrigger.TriggerInstance.curedZombieVillager())
                .name(Component.translatable("enigmaticsbingogoals.goal.cure_zombie_villager"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.GOLDEN_APPLE, EnigmaticsBingoTags.IGLOO)
                .icon(IndicatorIcon.infer(EntityType.ZOMBIE_VILLAGER, ItemIcon.ofItem(Items.GOLDEN_APPLE)))
        );
        addGoal(numberOfEffectsGoal(id("get_some_effects_many"), 8, 10));
        addGoal(breedAnimalGoal(id("breed_mule"), EntityType.MULE)
                .tags(BingoTags.OVERWORLD)
        );
        addGoal(advancementProgressGoal(id("breed_some_unique_mobs"),
                new ResourceLocation("minecraft", "husbandry/bred_all_animals"), 6, 15)
                .name(Component.translatable("enigmaticsbingogoals.goal.breed_some_unique_mobs", 0),
                        subber -> subber.sub("with.0", "count"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BREED_MOB)
                .icon(IndicatorIcon.infer(CycleIcon.infer(
                                Stream.concat(VanillaHusbandryAdvancements.BREEDABLE_ANIMALS.stream(),
                                        VanillaHusbandryAdvancements.INDIRECTLY_BREEDABLE_ANIMALS.stream()).toList()
                        ), EffectIcon.of(MobEffects.HEALTH_BOOST)),
                        subber -> subber.sub("base.icons.*.item.count", "count"))
        );
        addGoal(killEntitiesFromTagGoal(id("kill_some_unique_hostile_mobs_hard"), EnigmaticsBingoEntityTypeTags.HOSTILE, 11, 15, true)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_unique_hostile_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.UNIQUE_HOSTILE_MOBS, EnigmaticsBingoTags.KILL_MOB)
        );
        addGoal(killEntityGoal(id("kill_ender_dragon"), EntityType.ENDER_DRAGON)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_ender_dragon", EntityType.ENDER_DRAGON.getDescription()))
                .tags(BingoTags.END, EnigmaticsBingoTags.END_ENTRY)
                .icon(IndicatorIcon.infer(Items.DRAGON_HEAD, Items.NETHERITE_SWORD))
        );
        addGoal(killEntityGoal(id("kill_endermite"), EntityType.ENDERMITE)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_endermite", EntityType.ENDERMITE.getDescription()))
                .tags(BingoTags.OVERWORLD, BingoTags.NETHER, BingoTags.END, BingoTags.VILLAGE,
                        EnigmaticsBingoTags.WARPED_FOREST, EnigmaticsBingoTags.NETHER_LATE)
        );
        addGoal(killEntityGoal(id("kill_zoglin"), EntityType.ZOGLIN)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_zoglin", EntityType.ZOGLIN.getDescription()))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.CRIMSON_FOREST)
        );
        addGoal(wearArmorPiecesGoal(id("wear_full_diamond"), Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE,
                Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE, EnigmaticsBingoTags.ARMOR)
                .name(Component.translatable("enigmaticsbingogoals.goal.wear_full_diamond"))
        );
        // TODO: Grow huge crimson fungi in overworld
        // TODO: Grow huge warped fungi in overworld
        addGoal(obtainItemGoal(id("obtain_experience_bottle"), Items.EXPERIENCE_BOTTLE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OUTPOST, EnigmaticsBingoTags.ANCIENT_CITY,
                        BingoTags.VILLAGE)
        );
        // TODO: Use a Skull Banner Pattern
        addGoal(advancementProgressGoal(id("eat_some_unique_foods"),
                new ResourceLocation("minecraft", "husbandry/balanced_diet"), 21, 25)
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
        addGoal(advancementGoal(id("get_advancement_is_it_a_bird"),
                Component.translatable("advancements.adventure.spyglass_at_parrot.title"),
                new ResourceLocation("minecraft", "adventure/spyglass_at_parrot"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.AMETHYST, EnigmaticsBingoTags.JUNGLE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.SPYGLASS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_is_it_a_balloon"),
                Component.translatable("advancements.adventure.spyglass_at_ghast.title"),
                new ResourceLocation("minecraft", "adventure/spyglass_at_ghast"))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.AMETHYST, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.GHAST)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.SPYGLASS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_is_it_a_plane"),
                Component.translatable("advancements.adventure.spyglass_at_dragon.title"),
                new ResourceLocation("minecraft", "adventure/spyglass_at_dragon"))
                .tags(BingoTags.END, EnigmaticsBingoTags.AMETHYST, EnigmaticsBingoTags.END_ENTRY)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.SPYGLASS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_hero_of_the_village"),
                Component.translatable("advancements.adventure.hero_of_the_village.title"),
                new ResourceLocation("minecraft", "adventure/hero_of_the_village"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.OUTPOST, EnigmaticsBingoTags.RAID)
                .icon(new IndicatorIcon(new ItemIcon(BingoGoalGeneratorUtils.getOminousBanner(registries)), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_postmortal"),
                Component.translatable("advancements.adventure.totem_of_undying.title"),
                new ResourceLocation("minecraft", "adventure/totem_of_undying"))
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
                new ResourceLocation("minecraft", "adventure/craft_decorated_pot_using_only_sherds"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TRAIL_RUINS, EnigmaticsBingoTags.RARE_COLLECTIBLE_BATCH)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.DECORATED_POT), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(obtainAllItemsFromTagGoal(id("obtain_all_diamond_tools"), EnigmaticsBingoItemTags.DIAMOND_TOOLS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_material_tools",
                        Component.translatable(EnigmaticsBingoItemTags.DIAMOND_TOOLS.getTranslationKey())))
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
                new ResourceLocation("minecraft", "end/root"))
                .tags(BingoTags.END, EnigmaticsBingoTags.END_ENTRY)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.END_STONE), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(obtainItemGoal(id("obtain_dragon_egg"), Items.DRAGON_EGG)
                .tags(BingoTags.END, EnigmaticsBingoTags.END_ENTRY)
        );
        addGoal(obtainItemGoal(id("obtain_lingering_potion"), Items.LINGERING_POTION)
                .tags(BingoTags.END, EnigmaticsBingoTags.POTIONS, EnigmaticsBingoTags.END_ENTRY)
        );
        addGoal(advancementGoal(id("get_advancement_the_city_at_the_end_of_the_game"),
                Component.translatable("advancements.end.find_end_city.title"),
                new ResourceLocation("minecraft", "end/find_end_city"))
                .tags(BingoTags.END, EnigmaticsBingoTags.END_PROGRESS)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.PURPUR_PILLAR), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_great_view_from_up_here"),
                Component.translatable("advancements.end.levitate.title"),
                new ResourceLocation("minecraft", "end/levitate"))
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
    }
}
