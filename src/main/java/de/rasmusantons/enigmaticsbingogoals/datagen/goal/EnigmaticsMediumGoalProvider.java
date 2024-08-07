package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoTags;
import de.rasmusantons.enigmaticsbingogoals.conditions.KillEnemyPlayerCondition;
import de.rasmusantons.enigmaticsbingogoals.conditions.PlayerAliveCondition;
import de.rasmusantons.enigmaticsbingogoals.datagen.EnigmaticsBingoSynergies;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoDamageTypeTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoEntityTypeTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoFeatureTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoItemTags;
import de.rasmusantons.enigmaticsbingogoals.triggers.GiveEffectToOtherTeamTrigger;
import de.rasmusantons.enigmaticsbingogoals.triggers.HitOtherTeamWithProjectileTrigger;
import de.rasmusantons.enigmaticsbingogoals.triggers.PlayMusicToOtherTeamTrigger;
import io.github.gaming32.bingo.data.BingoDifficulties;
import io.github.gaming32.bingo.data.BingoGoal;
import io.github.gaming32.bingo.data.BingoTags;
import io.github.gaming32.bingo.data.icons.*;
import io.github.gaming32.bingo.data.progresstrackers.CriterionProgressTracker;
import io.github.gaming32.bingo.data.tags.BingoItemTags;
import io.github.gaming32.bingo.triggers.*;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.advancements.packs.VanillaHusbandryAdvancements;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class EnigmaticsMediumGoalProvider extends EnigmaticsDifficultyGoalProvider {
    public EnigmaticsMediumGoalProvider(BiConsumer<ResourceLocation, BingoGoal> goalAdder, HolderLookup.Provider registries) {
        super(BingoDifficulties.MEDIUM, goalAdder, registries);
    }

    @Override
    public void addGoals() {
        addGoal(BingoGoal.builder(id("never_obtain_crafting_table"))
                .criterion("obtain", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .tags(
                        BingoTags.NEVER,
                        BingoTags.VILLAGE,
                        EnigmaticsBingoTags.OUTPOST,
                        EnigmaticsBingoTags.IGLOO,
                        EnigmaticsBingoTags.WITCH_HUT,
                        EnigmaticsBingoTags.TRAIL_RUINS
                )
                .name(Component.translatable("enigmaticsbingogoals.goal.never_obtain_crafting_table",
                        Items.CRAFTING_TABLE.getDescription()))
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.CRAFTING_TABLE), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(neverDamageGoal(id("never_25_damage"), 25));
        addGoal(BingoGoal.builder(id("never_die"))
                .criterion("die", BingoTriggers.DEATH.get().createCriterion(
                        DeathTrigger.TriggerInstance.death(null)
                ))
                .tags(BingoTags.NEVER, EnigmaticsBingoTags.NEVER_TAKE_DAMAGE, EnigmaticsBingoTags.PLAYER_KILL)
                .catalyst(EnigmaticsBingoSynergies.DIE)
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
        addGoal(advancementsGoal(id("get_advancements"), 21, 25));
        addGoal(obtainItemGoal(id("obtain_dark_prismarine"), Items.DARK_PRISMARINE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BURIED_TREASURE,
                        EnigmaticsBingoTags.OCEAN_MONUMENT, EnigmaticsBingoTags.SHIPWRECK)
        );
        addGoal(BingoGoal.builder(id("give_effect_to_other_team"))
                .criterion("give", GiveEffectToOtherTeamTrigger.TriggerInstance.anyEffect())
                .tags(EnigmaticsBingoTags.PVP, EnigmaticsBingoTags.GET_EFFECT, EnigmaticsBingoTags.STRAY,
                        EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.POTIONS, EnigmaticsBingoTags.TRIAL_CHAMBER,
                        EnigmaticsBingoTags.SWAMP)
                .icon(IndicatorIcon.infer(
                        CycleIcon.infer(BuiltInRegistries.MOB_EFFECT.holders().map(EffectIcon::of)),
                        Items.PLAYER_HEAD))
                .name(Component.translatable("enigmaticsbingogoals.goal.give_effect_to_other_team"))
        );
        addGoal(BingoGoal.builder(id("play_music_to_other_team"))
                .criterion("music", PlayMusicToOtherTeamTrigger.TriggerInstance.playMusic())
                .tags(
                        EnigmaticsBingoTags.PVP,
                        EnigmaticsBingoTags.ANCIENT_CITY,
                        EnigmaticsBingoTags.WOODLAND_MANSION,
                        EnigmaticsBingoTags.TRAIL_RUINS,
                        EnigmaticsBingoTags.TRAIL_RUINS
                )
                .antisynergy(EnigmaticsBingoSynergies.MUSIC_DISC)
                .name(Component.translatable("enigmaticsbingogoals.goal.play_music_to_other_team"))
                .icon(IndicatorIcon.infer(Items.JUKEBOX, Items.PLAYER_HEAD))
        );
        addGoal(BingoGoal.builder(id("hit_other_team_with_snowball"))
                .criterion("hit", HitOtherTeamWithProjectileTrigger.TriggerInstance.ofType(EntityType.SNOWBALL))
                .tags(EnigmaticsBingoTags.PVP)
                .name(Component.translatable("enigmaticsbingogoals.goal.hit_player_with_snowball", EntityType.SNOWBALL.getDescription()))
                .icon(IndicatorIcon.infer(Items.SNOWBALL, Items.PLAYER_HEAD))
        );
        addGoal(BingoGoal.builder(id("hit_other_team_with_wind_charge"))
                .criterion("hit", HitOtherTeamWithProjectileTrigger.TriggerInstance.ofType(EntityType.WIND_CHARGE))
                .tags(EnigmaticsBingoTags.PVP, EnigmaticsBingoTags.TRIAL_CHAMBER, EnigmaticsBingoTags.BREEZE)
                .name(Component.translatable("enigmaticsbingogoals.goal.hit_player_with_wind_charge", EntityType.WIND_CHARGE.getDescription()))
                .icon(IndicatorIcon.infer(Items.WIND_CHARGE, Items.PLAYER_HEAD))
        );
        // TODO (requires OVERTAKABLE): Kill more unique mobs than the enemy
        // TODO (requires OVERTAKABLE): Kill more unique hostile mobs than the enemy
        // TODO (requires OVERTAKABLE): Kill more unique neutral mobs than the enemy
        // TODO (requires OVERTAKABLE): Visit more unique Overworld Biomes than enemy team
        // TODO (requires OVERTAKABLE): Take less damage than the enemy
        // TODO: Visit 15-25 unique Biomes
        addGoal(advancementProgressGoal(id("visit_some_unique_overworld_biomes"),
                ResourceLocation.withDefaultNamespace("adventure/adventuring_time"), 15, 25)
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
        addGoal(numberOfEffectsGoal(id("get_some_effects"), 6, 12));
        addGoal(effectGoal(id("get_slowness"), MobEffects.MOVEMENT_SLOWDOWN)
                .tags(EnigmaticsBingoTags.TRIAL_CHAMBER)
                .antisynergy(EnigmaticsBingoSynergies.SLOWNESS)
        );
        addGoal(effectGoal(id("get_mining_fatigue"), MobEffects.DIG_SLOWDOWN)
                .tags(EnigmaticsBingoTags.OCEAN_MONUMENT)
                .antisynergy(EnigmaticsBingoSynergies.SATURATION)
        );
        addGoal(effectGoal(id("get_nausea"), MobEffects.CONFUSION)
                .tags(EnigmaticsBingoTags.PUFFER_FISH)
        );
        addGoal(dieToDamageTypeGoal(id("die_to_intentional_game_design"), EnigmaticsBingoDamageTypeTags.INTENTIONAL_GAME_DESIGN)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.DIE_TO)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_intentional_game_design"))
                .icon(IndicatorIcon.infer(CycleIcon.infer(Items.RED_BED, Items.RESPAWN_ANCHOR), BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        addGoal(dieToMobEntityGoal(id("die_to_goat"), EntityType.GOAT)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MOUNTAIN, EnigmaticsBingoTags.GOAT)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_goat",
                        EntityType.GOAT.getDescription()))
        );
        addGoal(dieToDamageTypeGoal(id("die_to_magic"), EnigmaticsBingoDamageTypeTags.MAGIC)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO, EnigmaticsBingoTags.WITCH_HUT,
                        EnigmaticsBingoTags.OCEAN_MONUMENT, EnigmaticsBingoTags.INSTANT_DAMAGE,
                        EnigmaticsBingoTags.RAID, EnigmaticsBingoTags.OUTPOST, EnigmaticsBingoTags.WOODLAND_MANSION)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_magic"))
                .icon(IndicatorIcon.infer(
                                PotionContents.createItemStack(Items.SPLASH_POTION, Potions.STRONG_HARMING),
                                BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)
                        )
                )
        );
        addGoal(dieToDamageTypeGoal(id("die_to_anvil"), EnigmaticsBingoDamageTypeTags.ANVIL)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_anvil", Items.ANVIL.getDescription()))
                .icon(IndicatorIcon.infer(Items.ANVIL, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        addGoal(obtainItemGoal(id("obtain_observer"), Items.OBSERVER)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.NETHER_ENTRY)
        );
        addGoal(obtainItemGoal(id("obtain_sticky_piston"), Items.STICKY_PISTON)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.SLIME, EnigmaticsBingoTags.JUNGLE, EnigmaticsBingoTags.ANCIENT_CITY, EnigmaticsBingoTags.SWAMP)
        );
        addGoal(obtainItemGoal(id("obtain_redstone_lamp"), Items.REDSTONE_LAMP)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.ANCIENT_CITY)
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
                        EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.SILVERFISH, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(killEntityGoal(id("kill_breeze"), EntityType.BREEZE)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_breeze", EntityType.BREEZE.getDescription()))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TRIAL_CHAMBER, EnigmaticsBingoTags.BREEZE)
        );
        addGoal(killEntityGoal(id("kill_bogged"), EntityType.BOGGED)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_bogged", EntityType.BOGGED.getDescription()))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TRIAL_CHAMBER, EnigmaticsBingoTags.SWAMP)
        );
        addGoal(tameAnimalGoal(id("tame_ocelot"), EntityType.OCELOT)
                .name(Component.translatable("enigmaticsbingogoals.goal.tame_ocelot", EntityType.OCELOT.getDescription()))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TAME_ANIMAL, EnigmaticsBingoTags.JUNGLE)
        );
        addGoal(breedAnimalGoal(id("breed_pig"), EntityType.PIG)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE)
                .catalyst(EnigmaticsBingoSynergies.BABY)
        );
        addGoal(breedAnimalGoal(id("breed_fox"), EntityType.FOX)
                .tags(BingoTags.OVERWORLD)
                .catalyst(EnigmaticsBingoSynergies.BABY)
        );
        addGoal(breedAnimalGoal(id("breed_armadillo"), EntityType.ARMADILLO)
                .tags(BingoTags.OVERWORLD)
        );
        addGoal(breedAnimalGoal(id("breed_horse"), EntityType.HORSE)
                .tags(BingoTags.OVERWORLD)
        );
        addGoal(breedAnimalGoal(id("breed_axolotl"), EntityType.AXOLOTL)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.LUSH_CAVE)
        );
        addGoal(breedAnimalGoal(id("breed_strider"), EntityType.STRIDER)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.STRIDER)
        );
        addGoal(killEntitiesFromTagGoal(id("kill_some_unique_mobs"), EnigmaticsBingoEntityTypeTags.MOBS, 16, 25, true)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_unique_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.UNIQUE_NEUTRAL_MOBS, EnigmaticsBingoSynergies.UNIQUE_HOSTILE_MOBS)
        );
        // TODO: Kill (5-7) unique Neutral Mobs
        // TODO: Kill (7-10) unique Neutral Mobs
        addGoal(killEntitiesFromTagGoal(id("kill_some_unique_hostile_mobs"), EnigmaticsBingoEntityTypeTags.HOSTILE, 11, 14, true)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_unique_hostile_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.UNIQUE_HOSTILE_MOBS)
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
        addGoal(killEntityGoal(id("kill_elder_guardian"), EntityType.ELDER_GUARDIAN)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_elder_guardian", EntityType.ELDER_GUARDIAN.getDescription()))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OCEAN_MONUMENT)
                .icon(IndicatorIcon.infer(BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.ELDER_GUARDIAN), Items.NETHERITE_SWORD))
        );
        addGoal(obtainItemGoal(id("obtain_mushroom_stem"), Items.MUSHROOM_STEM)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE, EnigmaticsBingoTags.SILK_TOUCH)
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
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE, EnigmaticsBingoTags.WOODLAND_MANSION)
                .antisynergy(EnigmaticsBingoSynergies.SEEDS)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_seeds",
                                0, Component.translatable(EnigmaticsBingoItemTags.SEEDS.getTranslationKey())),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(eatItemGoal(id("eat_beetroot_soup"), Items.BEETROOT_SOUP)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE, EnigmaticsBingoTags.STEW)
        );
        addGoal(obtainItemGoal(id("obtain_tropical_fish_bucket"), Items.TROPICAL_FISH_BUCKET)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BUCKET_WITH_MOB)
        );
        addGoal(obtainItemGoal(id("obtain_tadpole_bucket"), Items.TADPOLE_BUCKET)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BUCKET_WITH_MOB, EnigmaticsBingoTags.SLIME, EnigmaticsBingoTags.SWAMP)
                .antisynergy(EnigmaticsBingoSynergies.FROG)
        );
        addGoal(advancementGoal(id("get_advancement_sound_of_music"),
                Component.translatable("advancements.adventure.play_jukebox_in_meadows.title"),
                ResourceLocation.withDefaultNamespace("adventure/play_jukebox_in_meadows"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.ANCIENT_CITY, EnigmaticsBingoTags.WOODLAND_MANSION,
                        EnigmaticsBingoTags.TRAIL_RUINS, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .antisynergy(EnigmaticsBingoSynergies.MUSIC_DISC)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.JUKEBOX), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(BingoGoal.builder(id("equip_wolf_armor"))
                .criterion("equip", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
                        ItemPredicate.Builder.item().of(Items.WOLF_ARMOR),
                        Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(EntityType.WOLF)))
                ))
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.WOLF)
                .name(Component.translatable("enigmaticsbingogoals.goal.equip_a_wolf_with_armor", EntityType.WOLF.getDescription()))
                .icon(IndicatorIcon.infer(EntityType.WOLF, Items.WOLF_ARMOR))
        );
        addGoal(wearArmorPiecesGoal(id("wear_full_gold"), Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE,
                Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS)
                .tags(BingoTags.OVERWORLD, BingoTags.NETHER, BingoTags.VILLAGE, EnigmaticsBingoTags.ARMOR)
                .name(Component.translatable("enigmaticsbingogoals.goal.wear_full_gold"))
        );
        addGoal(obtainSomeItemsFromTagGoal(id("obtain_some_saplings"), EnigmaticsBingoItemTags.SAPLINGS, 4, 5)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.PLANT_BATCH)
                .antisynergy(EnigmaticsBingoSynergies.SAPLING)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_saplings", 0,
                                Component.translatable(EnigmaticsBingoItemTags.SAPLINGS.getTranslationKey())),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(obtainSomeItemsFromTagGoal(id("obtain_some_bonemealable_blocks"), BingoItemTags.BONEMEALABLE, 10, 20)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.PLANT_BATCH)
                .antisynergy(EnigmaticsBingoSynergies.SAPLING, EnigmaticsBingoSynergies.SEEDS)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_bonemealable_blocks", 0),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(advancementProgressGoal(id("eat_some_unique_foods"),
                ResourceLocation.withDefaultNamespace("husbandry/balanced_diet"), 14, 24)
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
                ResourceLocation.withDefaultNamespace("adventure/sniper_duel"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BOW)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.ARROW), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_bullseye"),
                Component.translatable("advancements.adventure.bullseye.title"),
                ResourceLocation.withDefaultNamespace("adventure/bullseye"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BOW)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.TARGET), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(BingoGoal.builder(id("ride_pig_for_300_meters"))
                .criterion("ride", RelativeStatsTrigger.builder()
                        .stat(Stats.PIG_ONE_CM, MinMaxBounds.Ints.atLeast(30000)).build())
                .progress(new CriterionProgressTracker("ride", 0.01f))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.SADDLE, EnigmaticsBingoTags.PIG)
                .name(Component.translatable("enigmaticsbingogoals.goal.ride_pig_distance",
                        EntityType.PIG.getDescription(), 300))
                .icon(IndicatorIcon.infer(EntityType.PIG, EffectIcon.of(MobEffects.MOVEMENT_SPEED)))
        );
        addGoal(BingoGoal.builder(id("ride_pig_lava"))
                .criterion("ride", CriteriaTriggers.RIDE_ENTITY_IN_LAVA_TRIGGER.createCriterion(
                        new DistanceTrigger.TriggerInstance(
                                Optional.of(ContextAwarePredicate.create(
                                        LootItemEntityPropertyCondition.hasProperties(
                                                LootContext.EntityTarget.THIS,
                                                EntityPredicate.Builder.entity()
                                                        .entityType(EntityTypePredicate.of(EntityType.PLAYER)).vehicle(
                                                                EntityPredicate.Builder.entity().of(EntityType.PIG)
                                                        )
                                        ).build()
                                )),
                                Optional.empty(),
                                Optional.empty()
                        )
                ))
                .name(Component.translatable("enigmaticsbingogoals.goal.ride_pig_lava"))
                .tooltip(Component.translatable("enigmaticsbingogoals.goal.ride_pig_lava.tooltip",
                        EntityType.PIG.getDescription(),  Component.translatable(Blocks.LAVA.getDescriptionId())))
                .icon(IndicatorIcon.infer(EntityType.PIG, Items.LAVA_BUCKET))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.PIG, EnigmaticsBingoTags.SADDLE)
        );
        addGoal(BingoGoal.builder(id("use_carrot_on_a_stick"))
                .criterion("use", BingoTriggers.TRY_USE_ITEM.get().createCriterion(
                        new TryUseItemTrigger.TriggerInstance(
                                Optional.of(ContextAwarePredicate.create(
                                        LootItemEntityPropertyCondition.hasProperties(
                                                LootContext.EntityTarget.THIS,
                                                EntityPredicate.Builder.entity()
                                                        .entityType(EntityTypePredicate.of(EntityType.PLAYER)).vehicle(
                                                                EntityPredicate.Builder.entity().of(EntityType.PIG)
                                                        )
                                        ).build()
                                )),
                                Optional.of(ItemPredicate.Builder.item().of(Items.CARROT_ON_A_STICK).build()),
                                Optional.empty()
                        )
                ))
                .name(Component.translatable("enigmaticsbingogoals.goal.use_carrot_on_a_stick",
                        EntityType.PIG.getDescription(), Items.CARROT_ON_A_STICK.getDescription()))
                .icon(IndicatorIcon.infer(EntityType.PIG, Items.CARROT_ON_A_STICK))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.PIG, EnigmaticsBingoTags.SADDLE)
        );
        addGoal(rideAbstractHorseWithSaddleGoal(id("ride_horse"), EntityType.HORSE)
                .name(Component.translatable("enigmaticsbingogoals.goal.ride_horse",
                        EntityType.HORSE.getDescription(), Items.SADDLE.getDescription()))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.SADDLE)
        );
        addGoal(advancementGoal(id("get_any_spyglass_advancement"), null,
                ResourceLocation.withDefaultNamespace("adventure/spyglass_at_parrot"),
                ResourceLocation.withDefaultNamespace("adventure/spyglass_at_ghast"),
                ResourceLocation.withDefaultNamespace("adventure/spyglass_at_dragon"))
                .name(Component.translatable("enigmaticsbingogoals.goal.get_any_spyglass_advancement"))
                .tags(EnigmaticsBingoTags.AMETHYST, EnigmaticsBingoTags.JUNGLE, EnigmaticsBingoTags.NETHER_ENTRY,
                        EnigmaticsBingoTags.NETHER_ENTRY)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.SPYGLASS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(obtainItemGoal(id("obtain_sponge"), Items.SPONGE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OCEAN_MONUMENT)
        );
        addGoal(obtainSomeItemsFromTagGoal(id("obtain_some_trim_templates"), ItemTags.TRIM_TEMPLATES, 2, 3)
                .tags(BingoTags.OVERWORLD, BingoTags.NETHER, EnigmaticsBingoTags.RARE_COLLECTIBLE_BATCH,
                        EnigmaticsBingoTags.TRAIL_RUINS, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_trim_templates", 0),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(obtainItemGoal(id("obtain_cobweb"), Items.COBWEB)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.IGLOO,
                        EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(breakBlockGoal(id("break_mob_spawner"), Blocks.SPAWNER)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.FORTRESS,
                        EnigmaticsBingoTags.STRONGHOLD, EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(BingoGoal.builder(id("name_a_sheep_jeb"))
                .criterion("use", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
                        ItemPredicate.Builder.item().of(Items.NAME_TAG).hasComponents(DataComponentPredicate.builder()
                                .expect(DataComponents.CUSTOM_NAME, Component.literal("jeb_"))
                                .build()),
                        Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(EntityType.SHEEP)))
                ))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.WOODLAND_MANSION)
                .name(Component.translatable("enigmaticsbingogoals.goal.name_a_sheep_jeb", EntityType.SHEEP.getDescription()))
                .tooltip(Component.translatable("enigmaticsbingogoals.goal.name_a_sheep_jeb.tooltip", EntityType.SHEEP.getDescription(), "jeb_"))
                .icon(IndicatorIcon.infer(EntityType.SHEEP, Items.NAME_TAG))
        );
        addGoal(obtainItemGoal(id("obtain_cyan_glazed_terracotta"), Items.CYAN_GLAZED_TERRACOTTA)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TRAIL_RUINS)
                .antisynergy(EnigmaticsBingoSynergies.TERRACOTTA)
                .infrequency(7)
        );
        addGoal(obtainSomeItemsFromTagGoal(id("obtain_some_different_colors_of_terracotta"), ItemTags.TERRACOTTA, 6, 11)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TRAIL_RUINS)
                .antisynergy(EnigmaticsBingoSynergies.TERRACOTTA)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_colors_of_terracotta", 0),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(obtainItemGoal(id("obtain_slime_block"), Items.SLIME_BLOCK)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.SLIME, EnigmaticsBingoTags.SWAMP)
        );
        addGoal(obtainItemGoal(id("obtain_honey_block"), Items.HONEY_BLOCK)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BEEHIVE, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(obtainItemGoal(id("obtain_scaffolding"), Items.SCAFFOLDING)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.JUNGLE, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(advancementGoal(id("get_advancement_enchanter"),
                Component.translatable("advancements.story.enchant_item.title"),
                ResourceLocation.withDefaultNamespace("story/enchant_item"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOODLAND_MANSION)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.ENCHANTED_BOOK), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_cyan_wool"), Items.CYAN_WOOL, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.IGLOO)
                .antisynergy(EnigmaticsBingoSynergies.WOOL)
                .infrequency(12)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.CYAN_WOOL.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_green_wool"), Items.GREEN_WOOL, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.IGLOO, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .antisynergy(EnigmaticsBingoSynergies.WOOL)
                .infrequency(12)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.GREEN_WOOL.getDescription()))
        );
        // TODO: Show an Egg to the World | Tooltip: Visit 10-20 biomes with an Egg in your off-hand
        addGoal(BingoGoal.builder(id("use_glow_ink_on_crimson_sign"))
                .criterion("use", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                        LocationPredicate.Builder.location().setBlock(
                                BlockPredicate.Builder.block()
                                        .of(Blocks.CRIMSON_SIGN, Blocks.CRIMSON_WALL_SIGN)
                        ),
                        ItemPredicate.Builder.item().of(Items.GLOW_INK_SAC)
                ))
                .name(Component.translatable("enigmaticsbingogoals.goal.use_item_on_block",
                        Items.GLOW_INK_SAC.getDescription(), Items.CRIMSON_SIGN.getDescription()))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.CRIMSON_FOREST, EnigmaticsBingoTags.SIGN,
                        EnigmaticsBingoTags.GLOW_INK)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.CRIMSON_SIGN), ItemIcon.ofItem(Items.GLOW_INK_SAC)))
        );
        addGoal(BingoGoal.builder(id("use_glow_ink_on_warped_sign"))
                .criterion("use", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                        LocationPredicate.Builder.location().setBlock(
                                BlockPredicate.Builder.block()
                                        .of(Blocks.WARPED_SIGN, Blocks.WARPED_WALL_SIGN)
                        ),
                        ItemPredicate.Builder.item().of(Items.GLOW_INK_SAC)
                ))
                .name(Component.translatable("enigmaticsbingogoals.goal.use_item_on_block",
                        Items.GLOW_INK_SAC.getDescription(), Items.WARPED_SIGN.getDescription()))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.WARPED_FOREST, EnigmaticsBingoTags.SIGN,
                        EnigmaticsBingoTags.GLOW_INK)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.WARPED_SIGN), ItemIcon.ofItem(Items.GLOW_INK_SAC)))
        );
        addGoal(advancementGoal(id("get_advancement_a_terrible_fortress"),
                Component.translatable("advancements.nether.find_fortress.title"),
                ResourceLocation.withDefaultNamespace("nether/find_fortress"))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.NETHER_EXPLORE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.NETHER_BRICKS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_those_were_the_days"),
                Component.translatable("advancements.nether.find_bastion.title"),
                ResourceLocation.withDefaultNamespace("nether/find_bastion"))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.NETHER_EXPLORE, EnigmaticsBingoTags.BASTION)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.POLISHED_BLACKSTONE_BRICKS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_not_quite_nine_lives"),
                Component.translatable("advancements.nether.charge_respawn_anchor.title"),
                ResourceLocation.withDefaultNamespace("nether/charge_respawn_anchor"))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.RESPAWN_ANCHOR), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_hot_tourist_destinations"),
                Component.translatable("advancements.nether.explore_nether.title"),
                ResourceLocation.withDefaultNamespace("nether/explore_nether"))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BIOMES, EnigmaticsBingoTags.NETHER_EXPLORE,
                        EnigmaticsBingoTags.NETHER_LATE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.NETHERITE_BOOTS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
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
        addGoal(potionGoal(id("obtain_potion_of_oozing"),
                Potions.OOZING)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.SLIME,
                        EnigmaticsBingoTags.SWAMP, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(potionGoal(id("obtain_potion_of_infestation"),
                Potions.INFESTED)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.SILVERFISH)
        );
        addGoal(potionGoal(id("obtain_potion_of_weaving"),
                Potions.WEAVING)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.MINESHAFT,
                        EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(potionGoal(id("obtain_potion_of_wind_charging"),
                Potions.OOZING)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.TRIAL_CHAMBER,
                        EnigmaticsBingoTags.BREEZE)
        );
        addGoal(potionGoal(id("obtain_potion_of_strength"),
                Potions.STRENGTH, Potions.LONG_STRENGTH, Potions.STRONG_STRENGTH)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(potionGoal(id("obtain_potion_of_regeneration"),
                Potions.REGENERATION, Potions.LONG_REGENERATION, Potions.STRONG_REGENERATION)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(potionGoal(id("obtain_potion_of_healing"), Potions.HEALING, Potions.STRONG_HEALING)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(potionGoal(id("obtain_potion_of_slowness"),
                Potions.SLOWNESS, Potions.STRONG_SLOWNESS, Potions.LONG_SLOWNESS)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
                .antisynergy(EnigmaticsBingoSynergies.SLOWNESS)
        );
        addGoal(potionGoal(id("obtain_potion_of_harming"),
                Potions.HARMING, Potions.LONG_STRENGTH, Potions.STRONG_STRENGTH)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER,
                        EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.INSTANT_DAMAGE)
        );
        addGoal(potionGoal(id("obtain_potion_of_poison"), Potions.POISON, Potions.LONG_POISON, Potions.STRONG_POISON)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
                .antisynergy(EnigmaticsBingoSynergies.POISON)
        );
        addGoal(potionGoal(id("obtain_potion_of_night_vision"), Potions.NIGHT_VISION, Potions.LONG_NIGHT_VISION)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(potionGoal(id("obtain_potion_of_swiftness"),
                Potions.SWIFTNESS, Potions.LONG_SWIFTNESS, Potions.STRONG_SWIFTNESS)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.TRAIL_RUINS)
        );
        addGoal(obtainSomeItemsGoal(id("obtain_some_fire_charges"), Items.FIRE_CHARGE, 4, 10)
                .tags(BingoTags.OVERWORLD, BingoTags.NETHER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.BARTERING,
                        EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(obtainItemGoal(id("obtain_netherite_scrap"), Items.NETHERITE_SCRAP)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_LATE, EnigmaticsBingoTags.NETHERITE)
        );
        addGoal(breakBlockGoal(id("break_turtle_egg"), Blocks.TURTLE_EGG)
                .tags(BingoTags.OVERWORLD)
        );
        addGoal(breedAnimalGoal(id("breed_hoglin"), EntityType.HOGLIN)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.CRIMSON_FOREST)
        );
        addGoal(dieToMobEntityGoal(id("die_to_llama"), EntityType.LLAMA)
                .tags(BingoTags.OVERWORLD)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_llama",
                        EntityType.LLAMA.getDescription()))
        );
        addGoal(dieToMobEntityGoal(id("die_to_stray"), EntityType.STRAY)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_stray",
                        EntityType.STRAY.getDescription()))
        );
        addGoal(eatItemGoal(id("eat_cookie"), Items.COOKIE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.JUNGLE)
        );
        addGoal(effectGoal(id("get_weakness"), MobEffects.WEAKNESS)
                .tags(EnigmaticsBingoTags.IGLOO)
                .antisynergy(EnigmaticsBingoSynergies.WEAKNESS)
                .reactant(EnigmaticsBingoSynergies.SUSPICIOUS_STEW)
        );
        addGoal(obtainSomeItemsFromTagGoal(id("obtain_colored_candle"), ItemTags.CANDLES, 1, 1)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BEEHIVE, EnigmaticsBingoTags.ANCIENT_CITY, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_colored_candle"))
        );
        addGoal(obtainItemGoal(id("obtain_green_glazed_terracotta"), Items.GREEN_GLAZED_TERRACOTTA)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.IGLOO, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .antisynergy(EnigmaticsBingoSynergies.TERRACOTTA)
                .infrequency(7)
        );
        addGoal(obtainItemGoal(id("obtain_lime_glazed_terracotta"), Items.LIME_GLAZED_TERRACOTTA)
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.TERRACOTTA)
                .infrequency(7)
        );
        addGoal(obtainItemGoal(id("obtain_honey_bottle"), Items.HONEY_BOTTLE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BEEHIVE, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(obtainItemGoal(id("obtain_powder_snow_bucket"), Items.POWDER_SNOW_BUCKET)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MOUNTAIN, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(reachLevelsGoal(id("reach_levels"), 16, 25));
        addGoal(tameAnimalGoal(id("tame_parrot"), EntityType.PARROT)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TAME_ANIMAL, EnigmaticsBingoTags.JUNGLE)
        );
        addGoal(tameSomeCatsGoal(id("tame_some_cats"), 2, 4));
        addGoal(tameSomeWolvesGoal(id("tame_some_wolves"), 2, 2));
        // TODO (requires OVERTAKABLE): Eat more unique foods than the enemy
        // TODO: Die to falling off vines
        // TODO: Wear 4 different armor materials
        // TODO: Use a Bordure Banner Pattern
        // TODO: Use an Anvil
        addGoal(advancementProgressGoal(id("breed_some_unique_mobs"),
                ResourceLocation.withDefaultNamespace("husbandry/bred_all_animals"), 6, 10)
                .name(Component.translatable("enigmaticsbingogoals.goal.breed_some_unique_mobs", 0),
                        subber -> subber.sub("with.0", "count"))
                .tags(BingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.BREED_MOB_BATCH)
                .catalyst(EnigmaticsBingoSynergies.BREED_MOB)
                .icon(IndicatorIcon.infer(CycleIcon.infer(
                                Stream.concat(VanillaHusbandryAdvancements.BREEDABLE_ANIMALS.stream(),
                                        VanillaHusbandryAdvancements.INDIRECTLY_BREEDABLE_ANIMALS.stream()).toList()
                        ), EffectIcon.of(MobEffects.HEALTH_BOOST)),
                        subber -> subber.sub("base.icons.*.item.count", "count"))
        );
        addGoal(advancementGoal(id("get_advancement_subspace_bubble"),
                Component.translatable("advancements.nether.fast_travel.title"),
                ResourceLocation.withDefaultNamespace("nether/fast_travel"))
                .tags(BingoTags.NETHER)
                .icon(new IndicatorIcon(BlockIcon.ofBlock(Blocks.NETHER_PORTAL), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_is_it_a_bird"),
                Component.translatable("advancements.adventure.spyglass_at_parrot.title"),
                ResourceLocation.withDefaultNamespace("adventure/spyglass_at_parrot"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.AMETHYST, EnigmaticsBingoTags.JUNGLE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.SPYGLASS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_is_it_a_balloon"),
                Component.translatable("advancements.adventure.spyglass_at_ghast.title"),
                ResourceLocation.withDefaultNamespace("adventure/spyglass_at_ghast"))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.AMETHYST, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.GHAST)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.SPYGLASS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(BingoGoal.builder(id("huge_warped_fungus_in_overworld"))
                .criterion("grow", GrowFeatureTrigger.builder()
                        .feature(EnigmaticsBingoFeatureTags.HUGE_WARPED_FUNGI)
                        .location(
                                LocationPredicate.Builder.inDimension(Level.OVERWORLD).build()
                        ).build())
                .name(Component.translatable("enigmaticsbingogoals.goal.huge_fungus_in_overworld", Items.WARPED_FUNGUS.getDescription()))
                .icon(IndicatorIcon.infer(
                        Items.WARPED_FUNGUS,
                        Blocks.GRASS_BLOCK
                ))
                .tags(BingoTags.OVERWORLD, BingoTags.NETHER, BingoTags.VILLAGE, EnigmaticsBingoTags.SILK_TOUCH,
                        EnigmaticsBingoTags.WARPED_FOREST, EnigmaticsBingoTags.NETHER_LATE, EnigmaticsBingoTags.GROW_TREE)
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
        addGoal(obtainAllItemsFromTagGoal(id("obtain_all_diamond_tools"), EnigmaticsBingoItemTags.DIAMOND_TOOLS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_material_tools",
                        Component.translatable(EnigmaticsBingoItemTags.DIAMOND_TOOLS.getTranslationKey())))
        );
        addGoal(obtainItemGoal(id("obtain_experience_bottle"), Items.EXPERIENCE_BOTTLE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OUTPOST, EnigmaticsBingoTags.ANCIENT_CITY,
                        BingoTags.VILLAGE)
        );
        addGoal(obtainSomeItemsFromTagGoal(id("obtain_copper_bulb"), EnigmaticsBingoItemTags.COPPER_BULBS, 1, 1)
                .tags(BingoTags.OVERWORLD, BingoTags.NETHER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_copper_bulb", Items.COPPER_BULB.getDescription()))
        );
        addGoal(advancementGoal(id("get_advancement_minecraft_trials_edition"),
                Component.translatable("advancements.adventure.minecraft_trials_edition.title"),
                ResourceLocation.withDefaultNamespace("adventure/minecraft_trials_edition"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.CHISELED_TUFF), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_crafters_crafting_crafters"),
                Component.translatable("advancements.adventure.crafters_crafting_crafters.title"),
                ResourceLocation.withDefaultNamespace("adventure/crafters_crafting_crafters"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.REDSTONE, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.WOODLAND_MANSION)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.CRAFTER), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_who_needs_rockets"),
                Component.translatable("advancements.adventure.who_needs_rockets.title"),
                ResourceLocation.withDefaultNamespace("adventure/who_needs_rockets"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TRIAL_CHAMBER, EnigmaticsBingoTags.BREEZE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.WIND_CHARGE), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_blowback"),
                Component.translatable("advancements.adventure.blowback.title"),
                ResourceLocation.withDefaultNamespace("adventure/blowback"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TRIAL_CHAMBER, EnigmaticsBingoTags.BREEZE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.WIND_CHARGE), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(obtainItemGoal(id("obtain_lingering_potion"), Items.LINGERING_POTION)
                .tags(BingoTags.OVERWORLD, BingoTags.END, EnigmaticsBingoTags.POTIONS, EnigmaticsBingoTags.TRIAL_CHAMBER,
                        EnigmaticsBingoTags.END_ENTRY)
        );
        addGoal(obtainItemGoal(id("obtain_gilded_blackstone"), Items.GILDED_BLACKSTONE)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.NETHER_EXPLORE, EnigmaticsBingoTags.BASTION)
        );
        addGoal(obtainItemGoal(id("obtain_sea_lantern"), Items.SEA_LANTERN)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OCEAN_MONUMENT)
        );
        addGoal(obtainItemGoal(id("obtain_soul_lantern"), Items.SOUL_LANTERN)
                .tags(BingoTags.OVERWORLD, BingoTags.NETHER, EnigmaticsBingoTags.SOUL_SAND, EnigmaticsBingoTags.ANCIENT_CITY)
        );
        addGoal(obtainItemGoal(id("obtain_soul_campfire"), Items.SOUL_CAMPFIRE)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.SOUL_SAND)
        );
        addGoal(obtainItemGoal(id("obtain_bamboo_mosaic"), Items.BAMBOO_MOSAIC)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.JUNGLE, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(obtainItemGoal(id("obtain_tinted_glass"), Items.TINTED_GLASS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.AMETHYST)
        );
        addGoal(obtainSomeItemsFromTagGoal(id("obtain_some_music_discs"), EnigmaticsBingoItemTags.MUSIC_DISCS, 3, 5)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.ANCIENT_CITY, EnigmaticsBingoTags.TRAIL_RUINS, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .antisynergy(EnigmaticsBingoSynergies.MUSIC_DISC)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_music_discs", 0),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(obtainItemGoal(id("obtain_goat_horn"), Items.GOAT_HORN)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MOUNTAIN, EnigmaticsBingoTags.GOAT, EnigmaticsBingoTags.OUTPOST)
        );
    }
}
