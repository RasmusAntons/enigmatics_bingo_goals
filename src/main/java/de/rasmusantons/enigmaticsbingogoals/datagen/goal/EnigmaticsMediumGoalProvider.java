package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoTags;
import de.rasmusantons.enigmaticsbingogoals.conditions.KillEnemyPlayerCondition;
import de.rasmusantons.enigmaticsbingogoals.conditions.PlayerAliveCondition;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoDamageTypeTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoEntityTypeTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoItemTags;
import de.rasmusantons.enigmaticsbingogoals.triggers.GiveEffectToOtherTeamTrigger;
import de.rasmusantons.enigmaticsbingogoals.triggers.HitOtherTeamWithProjectileTrigger;
import de.rasmusantons.enigmaticsbingogoals.triggers.PlayMusicToOtherTeamTrigger;
import io.github.gaming32.bingo.data.BingoDifficulties;
import io.github.gaming32.bingo.data.BingoGoal;
import io.github.gaming32.bingo.data.BingoTags;
import io.github.gaming32.bingo.data.icons.*;
import io.github.gaming32.bingo.triggers.BingoTriggers;
import io.github.gaming32.bingo.triggers.DeathTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.advancements.packs.VanillaHusbandryAdvancements;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiConsumer;

public class EnigmaticsMediumGoalProvider extends EnigmaticsDifficultyGoalProvider {
    public EnigmaticsMediumGoalProvider(BiConsumer<ResourceLocation, BingoGoal> goalAdder, HolderLookup.Provider registries) {
        super(BingoDifficulties.MEDIUM, goalAdder, registries);
    }

    @Override
    public void addGoals() {
        addGoal(BingoGoal.builder(id("never_crafting_table"))
                .criterion("obtain", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .tags(
                        BingoTags.NEVER,
                        BingoTags.VILLAGE,
                        EnigmaticsBingoTags.OUTPOST,
                        EnigmaticsBingoTags.IGLOO,
                        EnigmaticsBingoTags.WITCH_HUT,
                        EnigmaticsBingoTags.TRAIL_RUINS
                )
                .name(Component.translatable("enigmaticsbingogoals.goal.never_crafting_table",
                        Items.CRAFTING_TABLE.getDescription()))
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.CRAFTING_TABLE), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(neverDamageGoal(id("never_50_damage"), 50));
        addGoal(neverDamageGoal(id("never_100_damage"), 100));
        addGoal(BingoGoal.builder(id("never_die"))
                .criterion("die", BingoTriggers.DEATH.get().createCriterion(
                        DeathTrigger.TriggerInstance.death(null)
                ))
                .tags(BingoTags.NEVER, EnigmaticsBingoTags.NEVER_TAKE_DAMAGE, EnigmaticsBingoTags.PLAYER_KILL)
                .name(Component.translatable("enigmaticsbingogoals.goal.never_die"))
                .icon(IndicatorIcon.infer(BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD), Items.BARRIER))
        );
        addGoal(BingoGoal.builder(id("kill_enemy_player"))
                .criterion("kill", CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
                        new KilledTrigger.TriggerInstance(
                                Optional.empty(),
                                Optional.of(ContextAwarePredicate.create(KillEnemyPlayerCondition.INSTANCE)),
                                Optional.empty()
                        )
                ))
                .tags(
                        EnigmaticsBingoTags.PVP,
                        EnigmaticsBingoTags.PLAYER_KILL
                )
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_enemy_player"))
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.PLAYER_HEAD), ItemIcon.ofItem(Items.NETHERITE_SWORD)))
        );
        addGoal(advancementsGoal(id("get_advancements"), 22, 28));
        addGoal(obtainItemGoal(id("obtain_dark_prismarine"), Items.DARK_PRISMARINE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BURIED_TREASURE,
                        EnigmaticsBingoTags.OCEAN_MONUMENT, EnigmaticsBingoTags.SHIPWRECK)
        );
        addGoal(BingoGoal.builder(id("give_effect_to_other_team"))
                .criterion("give", GiveEffectToOtherTeamTrigger.TriggerInstance.anyEffect())
                .tags(EnigmaticsBingoTags.PVP, EnigmaticsBingoTags.GET_EFFECT, EnigmaticsBingoTags.STRAY,
                        EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.POTIONS)
                .icon(IndicatorIcon.infer(
                        CycleIcon.infer(BuiltInRegistries.MOB_EFFECT.holders().map(EffectIcon::of)),
                        Items.PLAYER_HEAD))
                .name(Component.translatable("enigmaticsbingogoals.goal.give_effect_to_other_team"))
        );
        addGoal(BingoGoal.builder(id("play_music_to_other_team"))
                .criterion("music", PlayMusicToOtherTeamTrigger.TriggerInstance.playMusic())
                .tags(
                        EnigmaticsBingoTags.PVP,
                        EnigmaticsBingoTags.MUSIC_DISC,
                        EnigmaticsBingoTags.ANCIENT_CITY,
                        EnigmaticsBingoTags.WOODLAND_MANSION,
                        EnigmaticsBingoTags.TRAIL_RUINS
                )
                .name(Component.translatable("enigmaticsbingogoals.goal.play_music_to_other_team"))
                .icon(IndicatorIcon.infer(Items.JUKEBOX, Items.PLAYER_HEAD))
        );
        addGoal(BingoGoal.builder(id("hit_other_team_with_snowball"))
                .criterion("hit", HitOtherTeamWithProjectileTrigger.TriggerInstance.ofType(EntityType.SNOWBALL))
                .tags(EnigmaticsBingoTags.PVP)
                .name(Component.translatable("enigmaticsbingogoals.goal.hit_player_with_snowball", EntityType.SNOWBALL.getDescription()))
                .icon(IndicatorIcon.infer(Items.SNOWBALL, Items.PLAYER_HEAD))
        );
        // TODO (requires OVERTAKABLE): Kill more unique mobs than the enemy
        // TODO (requires OVERTAKABLE): Kill more unique hostile mobs than the enemy
        // TODO (requires OVERTAKABLE): Kill more unique neutral mobs than the enemy
        // TODO: Visit 15-25 unique Biomes
        addGoal(advancementProgressGoal(id("visit_some_unique_overworld_biomes"),
                new ResourceLocation("minecraft", "adventure/adventuring_time"), 10, 25)
                .name(Component.translatable("enigmaticsbingogoals.goal.visit_some_unique_overworld_biomes", 0),
                        subber -> subber.sub("with.0", "count")
                )
                .tooltip(Component.translatable("enigmaticsbingogoals.goal.visit_some_unique_overworld_biomes.tooltip"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BIOMES, EnigmaticsBingoTags.OVERWORLD_EXPLORE)
                .icon(
                        Items.GOLDEN_BOOTS,
                        subber -> subber.sub("item.count", "count")
                )
        );
        addGoal(numberOfEffectsGoal(id("get_some_effects"), 5, 7));
        addGoal(effectGoal(id("get_slowness"), MobEffects.MOVEMENT_SLOWDOWN)
                .tags(EnigmaticsBingoTags.SLOWNESS)
        );
        addGoal(effectGoal(id("get_mining_fatigue"), MobEffects.DIG_SLOWDOWN)
                .tags(EnigmaticsBingoTags.SATURATION, EnigmaticsBingoTags.OCEAN_MONUMENT)
        );
        addGoal(effectGoal(id("get_nausea"), MobEffects.CONFUSION)
                .tags(EnigmaticsBingoTags.PUFFER_FISH)
        );
        addGoal(obtainItemGoal(id("obtain_cake"), Items.CAKE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MILK, EnigmaticsBingoTags.CHICKEN)
        );
        addGoal(dieToDamageTypeGoal(id("die_to_intentional_game_design"), EnigmaticsBingoDamageTypeTags.INTENTIONAL_GAME_DESIGN)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.DIE_TO)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_intentional_game_design"))
                .icon(IndicatorIcon.infer(Items.RED_BED, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        addGoal(dieToDamageTypeGoal(id("fall_out_of_world"), DamageTypeTags.ALWAYS_MOST_SIGNIFICANT_FALL)
                .tags(BingoTags.END, EnigmaticsBingoTags.END_ENTRY, EnigmaticsBingoTags.DIE_TO)
                .name(Component.translatable("enigmaticsbingogoals.goal.fall_out_of_world"))
                .icon(IndicatorIcon.infer(Blocks.END_PORTAL, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        addGoal(dieToEntityGoal(id("die_to_iron_golem"), EntityType.IRON_GOLEM)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_iron_golem",
                        EntityType.IRON_GOLEM.getDescription()))
        );
        addGoal(dieToEntityGoal(id("die_to_dolphin"), EntityType.DOLPHIN)
                .tags(BingoTags.OVERWORLD)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_dolphin",
                        EntityType.DOLPHIN.getDescription()))
        );
        addGoal(dieToEntityGoal(id("die_to_goat"), EntityType.GOAT)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MOUNTAIN)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_goat",
                        EntityType.GOAT.getDescription()))
        );
        addGoal(dieToDamageTypeGoal(id("die_to_magic"), EnigmaticsBingoDamageTypeTags.MAGIC)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO, EnigmaticsBingoTags.WITCH_HUT,
                        EnigmaticsBingoTags.OCEAN_MONUMENT, EnigmaticsBingoTags.INSTANT_DAMAGE,
                        EnigmaticsBingoTags.RAID, EnigmaticsBingoTags.OUTPOST, EnigmaticsBingoTags.WOODLAND_MANSION)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_magic"))
                .icon(IndicatorIcon.infer(EffectIcon.of(MobEffects.HARM), BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        addGoal(dieToDamageTypeGoal(id("die_to_anvil"), EnigmaticsBingoDamageTypeTags.ANVIL)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_anvil"))
                .icon(IndicatorIcon.infer(Items.ANVIL, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        addGoal(dieToDamageTypeGoal(id("die_to_stalactite"), EnigmaticsBingoDamageTypeTags.STALACTITE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO, EnigmaticsBingoTags.CAVING)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_stalactite"))
                .icon(IndicatorIcon.infer(Items.POINTED_DRIPSTONE, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        // TODO: Die to a TNT Minecart
        addGoal(obtainAllItemsFromTagGoal(id("obtain_all_raw_ore_blocks"), EnigmaticsBingoItemTags.RAW_ORE_BLOCKS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_all_raw_ore_blocks",
                        Component.translatable(EnigmaticsBingoItemTags.RAW_ORE_BLOCKS.getTranslationKey())))
        );
        addGoal(obtainItemGoal(id("obtain_dispenser"), Items.DISPENSER)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING,
                        EnigmaticsBingoTags.REDSTONE)
        );
        addGoal(obtainItemGoal(id("obtain_observer"), Items.OBSERVER)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.NETHER_ENTRY)
        );
        addGoal(obtainItemGoal(id("obtain_sticky_piston"), Items.STICKY_PISTON)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.SLIME, EnigmaticsBingoTags.JUNGLE, EnigmaticsBingoTags.ANCIENT_CITY)
        );
        addGoal(obtainItemGoal(id("obtain_redstone_lamp"), Items.REDSTONE_LAMP)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.ANCIENT_CITY)
        );
        addGoal(obtainItemGoal(id("obtain_daylight_detector"), Items.DAYLIGHT_DETECTOR)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.REDSTONE, EnigmaticsBingoTags.NETHER_ENTRY)
        );
        addGoal(obtainItemGoal(id("obtain_comparator"), Items.COMPARATOR)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.ANCIENT_CITY)
        );
        addGoal(breakBlockGoal(id("break_emerald_ore"), Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MOUNTAIN)
        );
        addGoal(killEntityGoal(id("kill_silverfish"), EntityType.SILVERFISH)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_silverfish", EntityType.SILVERFISH.getDescription()))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.STRONGHOLD, EnigmaticsBingoTags.MOUNTAIN,
                        EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(tameAnimalGoal(id("tame_ocelot"), EntityType.OCELOT)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TAME_ANIMAL, EnigmaticsBingoTags.JUNGLE)
        );
        addGoal(breedAnimalGoal(id("breed_pig"), EntityType.PIG)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE)
        );
        addGoal(breedAnimalGoal(id("breed_fox"), EntityType.FOX)
                .tags(BingoTags.OVERWORLD)
        );
        addGoal(breedAnimalGoal(id("breed_armadillo"), EntityType.ARMADILLO)
                .tags(BingoTags.OVERWORLD)
        );
        addGoal(breedAnimalGoal(id("breed_horse"), EntityType.HORSE)
                .tags(BingoTags.OVERWORLD)
        );
        addGoal(breedAnimalGoal(id("breed_axolotl"), EntityType.AXOLOTL)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.LUSH_CAVE)
        );
        addGoal(breedAnimalGoal(id("breed_strider"), EntityType.STRIDER)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.STRIDER)
        );
        addGoal(killEntitiesFromTagGoal(id("kill_some_unique_mobs"), EnigmaticsBingoEntityTypeTags.MOBS, 10, 25, true)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_unique_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.UNIQUE_MOBS, EnigmaticsBingoTags.KILL_MOB)
        );
        // TODO: Kill (5-7) unique Neutral Mobs
        // TODO: Kill (7-10) unique Neutral Mobs
        addGoal(killEntitiesFromTagGoal(id("kill_some_unique_hostile_mobs_medium"), EnigmaticsBingoEntityTypeTags.HOSTILE, 7, 10, true)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_unique_hostile_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.UNIQUE_HOSTILE_MOBS, EnigmaticsBingoTags.KILL_MOB)
        );
        addGoal(BingoGoal.builder(id("kill_mob_while_dead"))
                .criterion("kill", CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
                        new KilledTrigger.TriggerInstance(
                                Optional.of(ContextAwarePredicate.create(new InvertedLootItemCondition(PlayerAliveCondition.INSTANCE))),
                                Optional.of(ContextAwarePredicate.create(
                                        InvertedLootItemCondition.invert(
                                                LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                                                        EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(EntityType.PLAYER))
                                                )
                                        ).build()
                                )),
                                Optional.empty()
                        )
                ))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY, EnigmaticsBingoTags.KILL_MOB)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_mob_while_dead"))
                .tooltip(Component.translatable("enigmaticsbingogoals.goal.kill_mob_while_dead.tooltip"))
                .icon(IndicatorIcon.infer(Items.WOODEN_SWORD, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        addGoal(killEntityGoal(id("kill_witch"), EntityType.WITCH)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_witch", EntityType.WITCH.getDescription()))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.RAID, EnigmaticsBingoTags.WITCH_HUT)
        );
        addGoal(killEntityGoal(id("kill_vindicator"), EntityType.VINDICATOR)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_vindicator", EntityType.VINDICATOR.getDescription()))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.RAID, EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(killEntityGoal(id("kill_zombie_villager"), EntityType.ZOMBIE_VILLAGER)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_zombie_villager", EntityType.ZOMBIE_VILLAGER.getDescription()))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.IGLOO, EnigmaticsBingoTags.GOLDEN_APPLE)
        );
        addGoal(killEntityGoal(id("kill_elder_guardian"), EntityType.ELDER_GUARDIAN)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_elder_guardian", EntityType.ELDER_GUARDIAN.getDescription()))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OCEAN_MONUMENT)
                .icon(IndicatorIcon.infer(BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.ELDER_GUARDIAN), Items.NETHERITE_SWORD))
        );
        // TODO: Kill 5 baby neutral/hostile mobs
        addGoal(obtainItemGoal(id("obtain_grass_block"), Items.GRASS_BLOCK)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE, EnigmaticsBingoTags.SILK_TOUCH)
        );
        addGoal(obtainItemGoal(id("obtain_mushroom_stem"), Items.MUSHROOM_STEM)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE, EnigmaticsBingoTags.SILK_TOUCH)
        );
        addGoal(obtainItemGoal(id("obtain_crimson_nylium"), Items.CRIMSON_NYLIUM)
                .tags(BingoTags.NETHER, BingoTags.VILLAGE, EnigmaticsBingoTags.SILK_TOUCH,
                        EnigmaticsBingoTags.CRIMSON_FOREST, EnigmaticsBingoTags.NETHER_LATE)
        );
        addGoal(obtainItemGoal(id("obtain_warped_nylium"), Items.WARPED_NYLIUM)
                .tags(BingoTags.NETHER, BingoTags.VILLAGE, EnigmaticsBingoTags.SILK_TOUCH,
                        EnigmaticsBingoTags.WARPED_FOREST, EnigmaticsBingoTags.NETHER_ENTRY)
        );
        addGoal(obtainSomeItemsFromTagGoal(id("obtain_chainmail_armor"), EnigmaticsBingoItemTags.CHAINMAIL_ARMOR, 1, 1)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE, EnigmaticsBingoTags.RAID,
                        EnigmaticsBingoTags.OUTPOST, EnigmaticsBingoTags.WOODLAND_MANSION)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_chainmail_armor",
                        Component.translatable(EnigmaticsBingoItemTags.CHAINMAIL_ARMOR.getTranslationKey())))
        );
        addGoal(obtainSomeItemsFromTagGoal(id("obtain_4_different_seeds"), EnigmaticsBingoItemTags.SEEDS, 4, 4)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE, EnigmaticsBingoTags.SEEDS,
                        EnigmaticsBingoTags.WOODLAND_MANSION)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_seeds",
                                Component.translatable(EnigmaticsBingoItemTags.SEEDS.getTranslationKey()), 0),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(eatItemGoal(id("eat_beetroot_soup"), Items.BEETROOT_SOUP)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE, EnigmaticsBingoTags.STEW)
        );
        addGoal(eatItemGoal(id("eat_poisonous_potato"), Items.POISONOUS_POTATO)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE, EnigmaticsBingoTags.POISON, EnigmaticsBingoTags.SHIPWRECK)
        );
        addGoal(obtainItemGoal(id("obtain_tropical_fish_bucket"), Items.TROPICAL_FISH_BUCKET)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BUCKET_WITH_MOB)
        );
        // TODO: Hatch a White Frog
        // TODO: Hatch an Orange Frog
        // TODO: Hatch a Green Frog
        addGoal(obtainItemGoal(id("obtain_tadpole_bucket"), Items.TADPOLE_BUCKET)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BUCKET_WITH_MOB, EnigmaticsBingoTags.FROG)
        );
        addGoal(obtainSomeItemsFromTagGoal(id("obtain_some_music_discs"), ItemTags.MUSIC_DISCS, 2, 5)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MUSIC_DISC, EnigmaticsBingoTags.ANCIENT_CITY,
                        EnigmaticsBingoTags.TRAIL_RUINS)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_music_discs", 0),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(advancementGoal(id("get_advancement_sound_of_music"),
                Component.translatable("advancements.adventure.play_jukebox_in_meadows.title"),
                new ResourceLocation("minecraft", "adventure/play_jukebox_in_meadows"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MUSIC_DISC, EnigmaticsBingoTags.ANCIENT_CITY,
                        EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.TRAIL_RUINS)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.JUKEBOX), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        // TODO: Equip Wolf Armor
        addGoal(wearArmorPiecesGoal(id("wear_full_gold"), Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE,
                Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS)
                .tags(BingoTags.OVERWORLD, BingoTags.NETHER, BingoTags.VILLAGE, EnigmaticsBingoTags.ARMOR)
                .name(Component.translatable("enigmaticsbingogoals.goal.wear_full_gold"))
        );
        addGoal(obtainSomeItemsFromTagGoal(id("obtain_some_saplings"), EnigmaticsBingoItemTags.SAPLINGS, 5, 7)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.PLANT_BATCH)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_saplings", 0,
                                Component.translatable(EnigmaticsBingoItemTags.SAPLINGS.getTranslationKey())),
                        subber -> subber.sub("with.0", "count")
                )
        );
        // TODO: Obtain (5-10) bonemeal-able blocks
        addGoal(obtainItemGoal(id("obtain_flowering_azalea"), Items.FLOWERING_AZALEA)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.LUSH_CAVE)
        );
        addGoal(advancementProgressGoal(id("eat_some_unique_foods"),
                new ResourceLocation("minecraft", "husbandry/balanced_diet"), 14, 20)
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
        addGoal(advancementGoal(id("get_advancement_sniper_duel"),
                Component.translatable("advancements.adventure.sniper_duel.title"),
                new ResourceLocation("minecraft", "adventure/sniper_duel"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BOW)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.ARROW), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_bullseye"),
                Component.translatable("advancements.adventure.bullseye.title"),
                new ResourceLocation("minecraft", "adventure/bullseye"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BOW)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.TARGET), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        // TODO: Travel 300 meters on pig | Use minecraft:pig_one_cm to see statistics
        // TODO: This boat does not have legs TOOLTIP: Use Carrot on a Stick to Ride a Pig into Lava
        // TODO: Use Carrot on a Stick to Ride a Pig
        addGoal(rideAbstractHorseWithSaddleGoal(id("ride_horse"), EntityType.HORSE)
                .name(Component.translatable("enigmaticsbingogoals.goal.ride_horse",
                        EntityType.HORSE.getDescription(), Items.SADDLE.getDescription()))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.SADDLE)
        );
        addGoal(advancementGoal(id("get_any_spyglass_advancement"), null,
                new ResourceLocation("minecraft", "adventure/spyglass_at_parrot"),
                new ResourceLocation("minecraft", "adventure/spyglass_at_ghast"),
                new ResourceLocation("minecraft", "adventure/spyglass_at_dragon"))
                .name(Component.translatable("enigmaticsbingogoals.goal.get_any_spyglass_advancement"))
                .tags(EnigmaticsBingoTags.AMETHYST, EnigmaticsBingoTags.JUNGLE, EnigmaticsBingoTags.NETHER_ENTRY,
                        EnigmaticsBingoTags.NETHER_ENTRY)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.SPYGLASS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(obtainItemGoal(id("obtain_sponge"), Items.SPONGE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OCEAN_MONUMENT)
        );
        addGoal(obtainSomeItemsFromTagGoal(id("obtain_some_trim_templates"), ItemTags.TRIM_TEMPLATES, 1, 3)
                .tags(BingoTags.OVERWORLD, BingoTags.NETHER, EnigmaticsBingoTags.RARE_COLLECTIBLE_BATCH,
                        EnigmaticsBingoTags.TRAIL_RUINS)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_trim_templates", 0),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(obtainItemGoal(id("obtain_mud_brick_wall"), Items.MUD_BRICK_WALL)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WALL, EnigmaticsBingoTags.TRAIL_RUINS)
        );
        addGoal(obtainAllItemsFromTagGoal(id("obtain_all_golden_tools"), EnigmaticsBingoItemTags.GOLDEN_TOOLS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_material_tools",
                        Component.translatable(EnigmaticsBingoItemTags.GOLDEN_TOOLS.getTranslationKey())))
        );
        addGoal(obtainItemGoal(id("obtain_cobweb"), Items.COBWEB)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.IGLOO,
                        EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(breakBlockGoal(id("break_mob_spawner"), Blocks.SPAWNER)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.FORTRESS,
                        EnigmaticsBingoTags.STRONGHOLD, EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        // TODO: Rename a sheep to "jeb_"
        addGoal(obtainItemGoal(id("obtain_cyan_glazed_terracotta"), Items.CYAN_GLAZED_TERRACOTTA)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TRAIL_RUINS, EnigmaticsBingoTags.TERRACOTTA)
        );
        addGoal(obtainSomeItemsFromTagGoal(id("obtain_some_different_colors_of_terracotta"), ItemTags.TERRACOTTA, 6, 11)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TERRACOTTA, EnigmaticsBingoTags.TRAIL_RUINS)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_colors_of_terracotta", 0),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(obtainItemGoal(id("obtain_slime_block"), Items.SLIME_BLOCK)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.SLIME)
        );
        addGoal(obtainItemGoal(id("obtain_honey_block"), Items.HONEY_BLOCK)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BEEHIVE)
        );
        addGoal(obtainItemGoal(id("obtain_scaffolding"), Items.SCAFFOLDING)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.JUNGLE)
        );
        addGoal(advancementGoal(id("get_advancement_enchanter"),
                Component.translatable("advancements.story.enchant_item.title"),
                new ResourceLocation("minecraft", "story/enchant_item"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOODLAND_MANSION)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.ENCHANTED_BOOK), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_cyan_wool"), Items.CYAN_WOOL, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOOL, EnigmaticsBingoTags.IGLOO)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.CYAN_WOOL.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_green_wool"), Items.GREEN_WOOL, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOOL, EnigmaticsBingoTags.IGLOO)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.GREEN_WOOL.getDescription()))
        );
        // TODO: Show an Egg to the World | Tooltip: Visit 10-20 biomes with an Egg in your off-hand
        // TODO: Apply Glow Ink to a Crimson Sign
        // TODO: Apply Glow Ink to a Warped Sign
        addGoal(advancementGoal(id("get_advancement_a_terrible_fortress"),
                Component.translatable("advancements.nether.find_fortress.title"),
                new ResourceLocation("minecraft", "nether/find_fortress"))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.NETHER_EXPLORE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.NETHER_BRICKS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_those_were_the_days"),
                Component.translatable("advancements.nether.find_bastion.title"),
                new ResourceLocation("minecraft", "nether/find_bastion"))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.NETHER_EXPLORE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.POLISHED_BLACKSTONE_BRICKS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_not_quite_nine_lives"),
                Component.translatable("advancements.nether.charge_respawn_anchor.title"),
                new ResourceLocation("minecraft", "nether/charge_respawn_anchor"))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.RESPAWN_ANCHOR), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_hot_tourist_destinations"),
                Component.translatable("advancements.nether.explore_nether.title"),
                new ResourceLocation("minecraft", "nether/explore_nether"))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BIOMES, EnigmaticsBingoTags.NETHER_EXPLORE,
                        EnigmaticsBingoTags.NETHER_LATE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.NETHERITE_BOOTS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_return_to_sender"),
                Component.translatable("advancements.nether.return_to_sender.title"),
                new ResourceLocation("minecraft", "nether/return_to_sender"))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.GHAST)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.FIRE_CHARGE), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_this_boat_has_legs"),
                Component.translatable("advancements.nether.ride_strider.title"),
                new ResourceLocation("minecraft", "nether/ride_strider"))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.STRIDER)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.WARPED_FUNGUS_ON_A_STICK), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(obtainItemGoal(id("obtain_end_crystal"), Items.END_CRYSTAL)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.EYE_OF_ENDER, EnigmaticsBingoTags.BLAZE_POWDER,
                        EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(obtainItemGoal(id("obtain_ender_eye"), Items.ENDER_EYE)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.EYE_OF_ENDER, EnigmaticsBingoTags.BLAZE_POWDER,
                        EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(obtainItemGoal(id("obtain_ender_chest"), Items.ENDER_CHEST)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.EYE_OF_ENDER, EnigmaticsBingoTags.BLAZE_POWDER,
                        EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(potionGoal(id("obtain_potion_of_strength"),
                Potions.STRENGTH, Potions.LONG_STRENGTH, Potions.STRONG_STRENGTH)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(potionGoal(id("obtain_potion_of_regeneration"),
                Potions.REGENERATION, Potions.LONG_REGENERATION, Potions.STRONG_REGENERATION)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER)
        );
        addGoal(potionGoal(id("obtain_potion_of_healing"), Potions.HEALING, Potions.STRONG_HEALING)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(potionGoal(id("obtain_potion_of_slowness"),
                Potions.SLOWNESS, Potions.STRONG_SLOWNESS, Potions.LONG_SLOWNESS)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(potionGoal(id("obtain_potion_of_harming"),
                Potions.HARMING, Potions.LONG_STRENGTH, Potions.STRONG_STRENGTH)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER,
                        EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.INSTANT_DAMAGE)
        );
        addGoal(potionGoal(id("obtain_potion_of_poison"), Potions.POISON, Potions.LONG_POISON, Potions.STRONG_POISON)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(potionGoal(id("obtain_potion_of_night_vision"), Potions.NIGHT_VISION, Potions.LONG_NIGHT_VISION)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(potionGoal(id("obtain_potion_of_swiftness"),
                Potions.SWIFTNESS, Potions.LONG_SWIFTNESS, Potions.STRONG_SWIFTNESS)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(obtainItemGoal(id("obtain_wither_skeleton_skull"), Items.WITHER_SKELETON_SKULL)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.WITHER_SKULL, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(obtainSomeItemsGoal(id("obtain_some_fire_charges"), Items.FIRE_CHARGE, 4, 10)
                .tags(BingoTags.OVERWORLD, BingoTags.NETHER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.BARTERING)
        );
        addGoal(obtainItemGoal(id("obtain_lodestone"), Items.LODESTONE)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_LATE, EnigmaticsBingoTags.NETHERITE)
        );
        addGoal(obtainItemGoal(id("obtain_netherite_scrap"), Items.NETHERITE_SCRAP)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_LATE, EnigmaticsBingoTags.NETHERITE)
        );
        addGoal(obtainItemGoal(id("obtain_netherite_ingot"), Items.NETHERITE_INGOT)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_LATE, EnigmaticsBingoTags.NETHERITE)
        );
        addGoal(advancementGoal(id("get_advancement_eye_spy"),
                Component.translatable("advancements.story.follow_ender_eye.title"),
                new ResourceLocation("minecraft", "story/follow_ender_eye"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.END_ENTRY, EnigmaticsBingoTags.STRONGHOLD)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.ENDER_EYE), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
    }
}
