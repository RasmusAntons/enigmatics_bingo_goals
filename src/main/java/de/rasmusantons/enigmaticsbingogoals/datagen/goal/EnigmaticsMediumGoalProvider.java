package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoDifficulties;
import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoTags;
import de.rasmusantons.enigmaticsbingogoals.conditions.KillEnemyPlayerCondition;
import de.rasmusantons.enigmaticsbingogoals.conditions.PlayerAliveCondition;
import de.rasmusantons.enigmaticsbingogoals.datagen.EnigmaticsBingoSynergies;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoDamageTypeTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoEntityTypeTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoFeatureTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoItemTags;
import de.rasmusantons.enigmaticsbingogoals.triggers.*;
import io.github.gaming32.bingo.data.goal.BingoGoal;
import io.github.gaming32.bingo.data.icons.*;
import io.github.gaming32.bingo.data.progresstrackers.CriterionProgressTracker;
import io.github.gaming32.bingo.data.tags.bingo.BingoItemTags;
import io.github.gaming32.bingo.triggers.*;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
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
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static de.rasmusantons.enigmaticsbingogoals.datagen.goal.BingoGoalGeneratorUtils.getAllEffectsIcon;

public class EnigmaticsMediumGoalProvider extends EnigmaticsDifficultyGoalProvider {
    public EnigmaticsMediumGoalProvider(BiConsumer<ResourceLocation, BingoGoal> goalAdder, HolderLookup.Provider registries) {
        super(EnigmaticsBingoDifficulties.MEDIUM, goalAdder, registries);
    }

    @Override
    public void addGoals() {
        final var entityTypes = registries.lookupOrThrow(Registries.ENTITY_TYPE);
        final var items = registries.lookupOrThrow(Registries.ITEM);
        final var blocks = registries.lookupOrThrow(Registries.BLOCK);

        addGoal(BingoGoal.builder(eid("never_obtain_crafting_table"))
                .criterion("obtain", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .tags(
                        EnigmaticsBingoTags.NEVER,
                        EnigmaticsBingoTags.VILLAGE,
                        EnigmaticsBingoTags.OUTPOST,
                        EnigmaticsBingoTags.IGLOO,
                        EnigmaticsBingoTags.WITCH_HUT,
                        EnigmaticsBingoTags.TRAIL_RUINS
                )
                .name(Component.translatable("enigmaticsbingogoals.goal.never_obtain_crafting_table",
                        Items.CRAFTING_TABLE.getName()))
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.CRAFTING_TABLE), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(neverDamageGoal(eid("never_25_damage"), 25));
        addGoal(BingoGoal.builder(eid("never_die"))
                .criterion("die", BingoTriggers.DEATH.get().createCriterion(
                        DeathTrigger.TriggerInstance.death(null)
                ))
                .tags(EnigmaticsBingoTags.NEVER, EnigmaticsBingoTags.NEVER_TAKE_DAMAGE, EnigmaticsBingoTags.PLAYER_KILL)
                .catalyst(EnigmaticsBingoSynergies.DIE)
                .name(Component.translatable("enigmaticsbingogoals.goal.never_die"))
                .icon(IndicatorIcon.infer(BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD), Items.BARRIER))
        );
        addGoal(BingoGoal.builder(eid("kill_enemy_player"))
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
        addGoal(advancementsGoal(eid("get_advancements"), 21, 25));
        addGoal(obtainItemGoal(eid("obtain_dark_prismarine"), items, Items.DARK_PRISMARINE)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.BURIED_TREASURE,
                        EnigmaticsBingoTags.OCEAN_MONUMENT, EnigmaticsBingoTags.SHIPWRECK)
        );
        addGoal(BingoGoal.builder(eid("give_effect_to_other_team"))
                .criterion("give", GiveEffectToOtherTeamTrigger.TriggerInstance.anyEffect())
                .tags(EnigmaticsBingoTags.PVP, EnigmaticsBingoTags.GET_EFFECT, EnigmaticsBingoTags.STRAY,
                        EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.POTIONS, EnigmaticsBingoTags.TRIAL_CHAMBER,
                        EnigmaticsBingoTags.SWAMP)
                .icon(IndicatorIcon.infer(getAllEffectsIcon(), Items.PLAYER_HEAD))
                .name(Component.translatable("enigmaticsbingogoals.goal.give_effect_to_other_team"))
        );
        addGoal(BingoGoal.builder(eid("play_music_to_other_team"))
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
        addGoal(BingoGoal.builder(eid("hit_other_team_with_snowball"))
                .criterion("hit", HitOtherTeamWithProjectileTrigger.TriggerInstance.ofType(entityTypes, EntityType.SNOWBALL))
                .tags(EnigmaticsBingoTags.PVP)
                .name(Component.translatable("enigmaticsbingogoals.goal.hit_player_with_snowball", EntityType.SNOWBALL.getDescription()))
                .icon(IndicatorIcon.infer(Items.SNOWBALL, Items.PLAYER_HEAD))
        );
        addGoal(BingoGoal.builder(eid("hit_other_team_with_wind_charge"))
                .criterion("hit", HitOtherTeamWithProjectileTrigger.TriggerInstance.ofType(entityTypes, EntityType.WIND_CHARGE))
                .tags(EnigmaticsBingoTags.PVP, EnigmaticsBingoTags.TRIAL_CHAMBER, EnigmaticsBingoTags.BREEZE)
                .name(Component.translatable("enigmaticsbingogoals.goal.hit_player_with_wind_charge", EntityType.WIND_CHARGE.getDescription()))
                .icon(IndicatorIcon.infer(Items.WIND_CHARGE, Items.PLAYER_HEAD))
        );
        // TODO (requires OVERTAKABLE): Kill more unique mobs than the enemy
        // TODO (requires OVERTAKABLE): Kill more unique hostile mobs than the enemy
        // TODO (requires OVERTAKABLE): Kill more unique neutral mobs than the enemy
        // TODO (requires OVERTAKABLE): Visit more unique Overworld Biomes than enemy team
        // TODO (requires OVERTAKABLE): Take less damage than the enemy
        addGoal(advancementProgressGoal(eid("visit_some_unique_overworld_biomes"),
                ResourceLocation.withDefaultNamespace("adventure/adventuring_time"), 15, 25)
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
        addGoal(numberOfEffectsGoal(eid("get_some_effects"), 6, 12));
        addGoal(effectGoal(eid("get_slowness"), MobEffects.MOVEMENT_SLOWDOWN)
                .tags(EnigmaticsBingoTags.TRIAL_CHAMBER)
                .antisynergy(EnigmaticsBingoSynergies.SLOWNESS)
        );
        addGoal(effectGoal(eid("get_mining_fatigue"), MobEffects.DIG_SLOWDOWN)
                .tags(EnigmaticsBingoTags.OCEAN_MONUMENT)
                .antisynergy(EnigmaticsBingoSynergies.SATURATION)
        );
        addGoal(effectGoal(eid("get_nausea"), MobEffects.CONFUSION)
                .tags(EnigmaticsBingoTags.PUFFER_FISH)
        );
        addGoal(dieToDamageTypeGoal(eid("die_to_intentional_game_design"), EnigmaticsBingoDamageTypeTags.INTENTIONAL_GAME_DESIGN)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.DIE_TO)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_intentional_game_design"))
                .catalyst(EnigmaticsBingoSynergies.EXPLOSION)
                .icon(IndicatorIcon.infer(CycleIcon.infer(Items.RED_BED, Items.RESPAWN_ANCHOR), BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        addGoal(dieToMobEntityGoal(eid("die_to_goat"), entityTypes, EntityType.GOAT)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.MOUNTAIN, EnigmaticsBingoTags.GOAT)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_goat",
                        EntityType.GOAT.getDescription()))
        );
        addGoal(dieToDamageTypeGoal(eid("die_to_magic"), EnigmaticsBingoDamageTypeTags.MAGIC)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO, EnigmaticsBingoTags.WITCH_HUT,
                        EnigmaticsBingoTags.OCEAN_MONUMENT, EnigmaticsBingoTags.INSTANT_DAMAGE,
                        EnigmaticsBingoTags.RAID, EnigmaticsBingoTags.OUTPOST, EnigmaticsBingoTags.WOODLAND_MANSION)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_magic"))
                .icon(IndicatorIcon.infer(
                                PotionContents.createItemStack(Items.SPLASH_POTION, Potions.STRONG_HARMING),
                                BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)
                        )
                )
        );
        addGoal(dieToDamageTypeGoal(eid("die_to_anvil"), EnigmaticsBingoDamageTypeTags.ANVIL)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_anvil", Items.ANVIL.getName()))
                .icon(IndicatorIcon.infer(Items.ANVIL, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        addGoal(obtainItemGoal(eid("obtain_observer"), items, Items.OBSERVER)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.NETHER_ENTRY)
        );
        addGoal(obtainItemGoal(eid("obtain_sticky_piston"), items, Items.STICKY_PISTON)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.SLIME, EnigmaticsBingoTags.JUNGLE, EnigmaticsBingoTags.ANCIENT_CITY, EnigmaticsBingoTags.SWAMP)
        );
        addGoal(obtainItemGoal(eid("obtain_redstone_lamp"), items, Items.REDSTONE_LAMP)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.ANCIENT_CITY)
        );
        addGoal(obtainItemGoal(eid("obtain_comparator"), items, Items.COMPARATOR)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.ANCIENT_CITY)
        );
        addGoal(breakBlockGoal(eid("break_emerald_ore"), blocks, Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.MOUNTAIN)
        );
        addGoal(killEntityGoal(eid("kill_silverfish"), entityTypes, EntityType.SILVERFISH)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_silverfish", EntityType.SILVERFISH.getDescription()))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.STRONGHOLD, EnigmaticsBingoTags.MOUNTAIN,
                        EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.SILVERFISH, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(killEntityGoal(eid("kill_breeze"), entityTypes, EntityType.BREEZE)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_breeze", EntityType.BREEZE.getDescription()))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.TRIAL_CHAMBER, EnigmaticsBingoTags.BREEZE)
        );
        addGoal(killEntityGoal(eid("kill_bogged"), entityTypes, EntityType.BOGGED)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_bogged", EntityType.BOGGED.getDescription()))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.TRIAL_CHAMBER, EnigmaticsBingoTags.SWAMP)
        );
        addGoal(tameAnimalGoal(eid("tame_ocelot"), entityTypes, EntityType.OCELOT)
                .name(Component.translatable("enigmaticsbingogoals.goal.tame_ocelot", EntityType.OCELOT.getDescription()))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.TAME_ANIMAL, EnigmaticsBingoTags.JUNGLE)
        );
        addGoal(breedAnimalGoal(eid("breed_pig"), entityTypes, EntityType.PIG)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.VILLAGE)
                .catalyst(EnigmaticsBingoSynergies.BABY)
        );
        addGoal(breedAnimalGoal(eid("breed_fox"), entityTypes, EntityType.FOX)
                .tags(EnigmaticsBingoTags.OVERWORLD)
                .catalyst(EnigmaticsBingoSynergies.BABY)
        );
        addGoal(breedAnimalGoal(eid("breed_armadillo"), entityTypes, EntityType.ARMADILLO)
                .tags(EnigmaticsBingoTags.OVERWORLD)
        );
        addGoal(breedAnimalGoal(eid("breed_horse"), entityTypes, EntityType.HORSE)
                .tags(EnigmaticsBingoTags.OVERWORLD)
        );
        addGoal(breedAnimalGoal(eid("breed_axolotl"), entityTypes, EntityType.AXOLOTL)
                .tags(EnigmaticsBingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.LUSH_CAVE)
        );
        addGoal(breedAnimalGoal(eid("breed_strider"), entityTypes, EntityType.STRIDER)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.STRIDER)
        );
        addGoal(killEntitiesFromTagGoal(eid("kill_some_unique_mobs"), EnigmaticsBingoEntityTypeTags.MOBS, 16, 25, true)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_unique_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.UNIQUE_HOSTILE_MOBS)
        );
        addGoal(killEntitiesFromTagGoal(eid("kill_some_unique_hostile_mobs"), EnigmaticsBingoEntityTypeTags.HOSTILE, 11, 14, true)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_unique_hostile_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.UNIQUE_HOSTILE_MOBS)
        );
        addGoal(BingoGoal.builder(eid("kill_mob_while_dead"))
                .criterion("kill", CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
                        new KilledTrigger.TriggerInstance(
                                Optional.of(ContextAwarePredicate.create(new InvertedLootItemCondition(PlayerAliveCondition.INSTANCE))),
                                Optional.of(ContextAwarePredicate.create(
                                        InvertedLootItemCondition.invert(
                                                LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                                                        EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityTypes, EntityType.PLAYER))
                                                )
                                        ).build()
                                )),
                                Optional.empty()
                        )
                ))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY, EnigmaticsBingoTags.KILL_MOB)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_mob_while_dead"))
                .tooltip(Component.translatable("enigmaticsbingogoals.goal.kill_mob_while_dead.tooltip"))
                .icon(IndicatorIcon.infer(Items.WOODEN_SWORD, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        addGoal(killEntityGoal(eid("kill_witch"), entityTypes, EntityType.WITCH)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_witch", EntityType.WITCH.getDescription()))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.RAID, EnigmaticsBingoTags.WITCH_HUT)
        );
        addGoal(killEntityGoal(eid("kill_vindicator"), entityTypes, EntityType.VINDICATOR)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_vindicator", EntityType.VINDICATOR.getDescription()))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.RAID, EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(killEntityGoal(eid("kill_elder_guardian"), entityTypes, EntityType.ELDER_GUARDIAN)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_elder_guardian", EntityType.ELDER_GUARDIAN.getDescription()))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.OCEAN_MONUMENT)
                .icon(IndicatorIcon.infer(BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.ELDER_GUARDIAN), Items.NETHERITE_SWORD))
        );
        addGoal(obtainItemGoal(eid("obtain_mushroom_stem"), items, Items.MUSHROOM_STEM)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.SILK_TOUCH)
        );
        addGoal(obtainItemGoal(eid("obtain_warped_nylium"), items, Items.WARPED_NYLIUM)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.SILK_TOUCH,
                        EnigmaticsBingoTags.WARPED_FOREST, EnigmaticsBingoTags.NETHER_ENTRY)
        );
        addGoal(obtainSomeItemsFromTagGoal(eid("obtain_chainmail_armor"), EnigmaticsBingoItemTags.CHAINMAIL_ARMOR, 1, 1)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.RAID,
                        EnigmaticsBingoTags.OUTPOST, EnigmaticsBingoTags.WOODLAND_MANSION)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_chainmail_armor",
                        Component.translatable(EnigmaticsBingoItemTags.CHAINMAIL_ARMOR.getTranslationKey())))
        );
        addGoal(obtainSomeItemsFromTagGoal(eid("obtain_4_different_seeds"), EnigmaticsBingoItemTags.SEEDS, 4, 4)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.WOODLAND_MANSION)
                .antisynergy(EnigmaticsBingoSynergies.SEEDS)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_seeds",
                                0, Component.translatable(EnigmaticsBingoItemTags.SEEDS.getTranslationKey())),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(eatItemGoal(eid("eat_beetroot_soup"), items, Items.BEETROOT_SOUP)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.STEW)
        );
        addGoal(obtainItemGoal(eid("obtain_tropical_fish_bucket"), items, Items.TROPICAL_FISH_BUCKET)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.BUCKET_WITH_MOB)
        );
        addGoal(obtainItemGoal(eid("obtain_tadpole_bucket"), items, Items.TADPOLE_BUCKET)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.BUCKET_WITH_MOB, EnigmaticsBingoTags.SLIME, EnigmaticsBingoTags.SWAMP)
                .antisynergy(EnigmaticsBingoSynergies.FROG)
        );
        addGoal(advancementGoal(eid("get_advancement_sound_of_music"),
                Component.translatable("advancements.adventure.play_jukebox_in_meadows.title"),
                ResourceLocation.withDefaultNamespace("adventure/play_jukebox_in_meadows"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.ANCIENT_CITY, EnigmaticsBingoTags.WOODLAND_MANSION,
                        EnigmaticsBingoTags.TRAIL_RUINS, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .antisynergy(EnigmaticsBingoSynergies.MUSIC_DISC)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.JUKEBOX), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(BingoGoal.builder(eid("equip_wolf_armor"))
                .criterion("equip", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
                        ItemPredicate.Builder.item().of(items, Items.WOLF_ARMOR),
                        Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(entityTypes, EntityType.WOLF)))
                ))
                .tags(EnigmaticsBingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.WOLF)
                .name(Component.translatable("enigmaticsbingogoals.goal.equip_a_wolf_with_armor", EntityType.WOLF.getDescription()))
                .icon(IndicatorIcon.infer(EntityType.WOLF, Items.WOLF_ARMOR))
        );
        addGoal(wearArmorPiecesGoal(eid("wear_full_gold"), entityTypes, items, Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE,
                Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.ARMOR)
                .name(Component.translatable("enigmaticsbingogoals.goal.wear_full_gold"))
        );
        addGoal(obtainSomeItemsFromTagGoal(eid("obtain_some_saplings"), EnigmaticsBingoItemTags.SAPLINGS, 4, 5)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.PLANT_BATCH)
                .antisynergy(EnigmaticsBingoSynergies.SAPLING)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_saplings", 0,
                                Component.translatable(EnigmaticsBingoItemTags.SAPLINGS.getTranslationKey())),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(obtainSomeItemsFromTagGoal(eid("obtain_some_bonemealable_blocks"), BingoItemTags.BONEMEALABLE, 10, 20)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.PLANT_BATCH)
                .antisynergy(EnigmaticsBingoSynergies.SAPLING, EnigmaticsBingoSynergies.SEEDS)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_bonemealable_blocks", 0),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(advancementProgressGoal(eid("eat_some_unique_foods"),
                ResourceLocation.withDefaultNamespace("husbandry/balanced_diet"), 14, 24)
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
        addGoal(advancementGoal(eid("get_advancement_sniper_duel"),
                Component.translatable("advancements.adventure.sniper_duel.title"),
                ResourceLocation.withDefaultNamespace("adventure/sniper_duel"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.BOW)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.ARROW), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(eid("get_advancement_bullseye"),
                Component.translatable("advancements.adventure.bullseye.title"),
                ResourceLocation.withDefaultNamespace("adventure/bullseye"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.BOW)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.TARGET), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(BingoGoal.builder(eid("ride_pig_for_300_meters"))
                .criterion("ride", RelativeStatsTrigger.builder()
                        .stat(Stats.PIG_ONE_CM, MinMaxBounds.Ints.atLeast(30000)).build())
                .progress(new CriterionProgressTracker("ride", 0.01f))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.SADDLE, EnigmaticsBingoTags.PIG)
                .name(Component.translatable("enigmaticsbingogoals.goal.ride_pig_distance",
                        EntityType.PIG.getDescription(), 300))
                .icon(IndicatorIcon.infer(EntityType.PIG, EffectIcon.of(MobEffects.MOVEMENT_SPEED)))
        );
        addGoal(BingoGoal.builder(eid("ride_pig_lava"))
                .criterion("ride", CriteriaTriggers.RIDE_ENTITY_IN_LAVA_TRIGGER.createCriterion(
                        new DistanceTrigger.TriggerInstance(
                                Optional.of(ContextAwarePredicate.create(
                                        LootItemEntityPropertyCondition.hasProperties(
                                                LootContext.EntityTarget.THIS,
                                                EntityPredicate.Builder.entity()
                                                        .entityType(EntityTypePredicate.of(entityTypes, EntityType.PLAYER)).vehicle(
                                                                EntityPredicate.Builder.entity().of(entityTypes, EntityType.PIG)
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
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.PIG, EnigmaticsBingoTags.SADDLE)
        );
        addGoal(BingoGoal.builder(eid("use_carrot_on_a_stick"))
                .criterion("use", BingoTriggers.TRY_USE_ITEM.get().createCriterion(
                        new TryUseItemTrigger.TriggerInstance(
                                Optional.of(ContextAwarePredicate.create(
                                        LootItemEntityPropertyCondition.hasProperties(
                                                LootContext.EntityTarget.THIS,
                                                EntityPredicate.Builder.entity()
                                                        .entityType(EntityTypePredicate.of(entityTypes, EntityType.PLAYER)).vehicle(
                                                                EntityPredicate.Builder.entity().of(entityTypes, EntityType.PIG)
                                                        )
                                        ).build()
                                )),
                                Optional.of(ItemPredicate.Builder.item().of(items, Items.CARROT_ON_A_STICK).build()),
                                Optional.empty()
                        )
                ))
                .name(Component.translatable("enigmaticsbingogoals.goal.use_carrot_on_a_stick",
                        EntityType.PIG.getDescription(), Items.CARROT_ON_A_STICK.getName()))
                .icon(IndicatorIcon.infer(EntityType.PIG, Items.CARROT_ON_A_STICK))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.PIG, EnigmaticsBingoTags.SADDLE)
        );
        addGoal(rideAbstractHorseWithSaddleGoal(eid("ride_horse"), entityTypes, items, EntityType.HORSE)
                .name(Component.translatable("enigmaticsbingogoals.goal.ride_horse",
                        EntityType.HORSE.getDescription(), Items.SADDLE.getName()))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.SADDLE)
        );
        addGoal(advancementGoal(eid("get_any_spyglass_advancement"), null,
                ResourceLocation.withDefaultNamespace("adventure/spyglass_at_parrot"),
                ResourceLocation.withDefaultNamespace("adventure/spyglass_at_ghast"),
                ResourceLocation.withDefaultNamespace("adventure/spyglass_at_dragon"))
                .name(Component.translatable("enigmaticsbingogoals.goal.get_any_spyglass_advancement"))
                .tags(EnigmaticsBingoTags.AMETHYST, EnigmaticsBingoTags.JUNGLE, EnigmaticsBingoTags.NETHER_ENTRY,
                        EnigmaticsBingoTags.NETHER_ENTRY)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.SPYGLASS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(obtainItemGoal(eid("obtain_sponge"), items, Items.SPONGE)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.OCEAN_MONUMENT)
        );
        addGoal(obtainSomeItemsFromTagGoal(eid("obtain_some_trim_templates"), BingoItemTags.TRIM_TEMPLATES, 2, 3)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.RARE_COLLECTIBLE_BATCH,
                        EnigmaticsBingoTags.TRAIL_RUINS, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_trim_templates", 0),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(obtainItemGoal(eid("obtain_cobweb"), items, Items.COBWEB)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.IGLOO,
                        EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(breakBlockGoal(eid("break_mob_spawner"), blocks, Blocks.SPAWNER)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.FORTRESS,
                        EnigmaticsBingoTags.STRONGHOLD, EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(BingoGoal.builder(eid("name_a_sheep_jeb"))
                .criterion("use", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
                        ItemPredicate.Builder.item().of(items, Items.NAME_TAG).hasComponents(DataComponentPredicate.builder()
                                .expect(DataComponents.CUSTOM_NAME, Component.literal("jeb_"))
                                .build()),
                        Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(entityTypes, EntityType.SHEEP)))
                ))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.WOODLAND_MANSION)
                .name(Component.translatable("enigmaticsbingogoals.goal.name_a_sheep_jeb", EntityType.SHEEP.getDescription()))
                .tooltip(Component.translatable("enigmaticsbingogoals.goal.name_a_sheep_jeb.tooltip", EntityType.SHEEP.getDescription(), "jeb_"))
                .icon(IndicatorIcon.infer(EntityType.SHEEP, Items.NAME_TAG))
        );
        addGoal(obtainItemGoal(eid("obtain_cyan_glazed_terracotta"), items, Items.CYAN_GLAZED_TERRACOTTA)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.TRAIL_RUINS)
                .antisynergy(EnigmaticsBingoSynergies.TERRACOTTA)
                .infrequency(7)
        );
        addGoal(obtainSomeItemsFromTagGoal(eid("obtain_some_different_colors_of_terracotta"), ItemTags.TERRACOTTA, 6, 11)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.TRAIL_RUINS)
                .antisynergy(EnigmaticsBingoSynergies.TERRACOTTA)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_colors_of_terracotta", 0),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(obtainItemGoal(eid("obtain_slime_block"), items, Items.SLIME_BLOCK)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.SLIME, EnigmaticsBingoTags.SWAMP)
        );
        addGoal(obtainItemGoal(eid("obtain_honey_block"), items, Items.HONEY_BLOCK)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.BEEHIVE, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(obtainItemGoal(eid("obtain_scaffolding"), items, Items.SCAFFOLDING)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.JUNGLE, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(advancementGoal(eid("get_advancement_enchanter"),
                Component.translatable("advancements.story.enchant_item.title"),
                ResourceLocation.withDefaultNamespace("story/enchant_item"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.WOODLAND_MANSION)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.ENCHANTED_BOOK), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(obtainItemGoal(eid("obtain_stack_of_cyan_wool"), items, Items.CYAN_WOOL, 64)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.IGLOO)
                .antisynergy(EnigmaticsBingoSynergies.WOOL)
                .infrequency(12)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.CYAN_WOOL.getName()))
        );
        addGoal(obtainItemGoal(eid("obtain_stack_of_green_wool"), items, Items.GREEN_WOOL, 64)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.IGLOO, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .antisynergy(EnigmaticsBingoSynergies.WOOL)
                .infrequency(12)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.GREEN_WOOL.getName()))
        );
        // TODO: Show an Egg to the World | Tooltip: Visit 10-20 biomes with an Egg in your off-hand
        addGoal(BingoGoal.builder(eid("use_glow_ink_on_crimson_sign"))
                .criterion("use", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                        LocationPredicate.Builder.location().setBlock(
                                BlockPredicate.Builder.block()
                                        .of(blocks, Blocks.CRIMSON_SIGN, Blocks.CRIMSON_WALL_SIGN)
                        ),
                        ItemPredicate.Builder.item().of(items, Items.GLOW_INK_SAC)
                ))
                .name(Component.translatable("enigmaticsbingogoals.goal.use_item_on_block",
                        Items.GLOW_INK_SAC.getName(), Items.CRIMSON_SIGN.getName()))
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.CRIMSON_FOREST, EnigmaticsBingoTags.SIGN,
                        EnigmaticsBingoTags.GLOW_INK)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.CRIMSON_SIGN), ItemIcon.ofItem(Items.GLOW_INK_SAC)))
        );
        addGoal(BingoGoal.builder(eid("use_glow_ink_on_warped_sign"))
                .criterion("use", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                        LocationPredicate.Builder.location().setBlock(
                                BlockPredicate.Builder.block()
                                        .of(blocks, Blocks.WARPED_SIGN, Blocks.WARPED_WALL_SIGN)
                        ),
                        ItemPredicate.Builder.item().of(items, Items.GLOW_INK_SAC)
                ))
                .name(Component.translatable("enigmaticsbingogoals.goal.use_item_on_block",
                        Items.GLOW_INK_SAC.getName(), Items.WARPED_SIGN.getName()))
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.WARPED_FOREST, EnigmaticsBingoTags.SIGN,
                        EnigmaticsBingoTags.GLOW_INK)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.WARPED_SIGN), ItemIcon.ofItem(Items.GLOW_INK_SAC)))
        );
        addGoal(advancementGoal(eid("get_advancement_a_terrible_fortress"),
                Component.translatable("advancements.nether.find_fortress.title"),
                ResourceLocation.withDefaultNamespace("nether/find_fortress"))
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.NETHER_EXPLORE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.NETHER_BRICKS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(eid("get_advancement_those_were_the_days"),
                Component.translatable("advancements.nether.find_bastion.title"),
                ResourceLocation.withDefaultNamespace("nether/find_bastion"))
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.NETHER_EXPLORE, EnigmaticsBingoTags.BASTION)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.POLISHED_BLACKSTONE_BRICKS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(eid("get_advancement_not_quite_nine_lives"),
                Component.translatable("advancements.nether.charge_respawn_anchor.title"),
                ResourceLocation.withDefaultNamespace("nether/charge_respawn_anchor"))
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.RESPAWN_ANCHOR), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(eid("get_advancement_hot_tourist_destinations"),
                Component.translatable("advancements.nether.explore_nether.title"),
                ResourceLocation.withDefaultNamespace("nether/explore_nether"))
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BIOMES, EnigmaticsBingoTags.NETHER_EXPLORE,
                        EnigmaticsBingoTags.NETHER_LATE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.NETHERITE_BOOTS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(obtainItemGoal(eid("obtain_end_crystal"), items, Items.END_CRYSTAL)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.EYE_OF_ENDER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(obtainItemGoal(eid("obtain_ender_eye"), items, Items.ENDER_EYE)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.EYE_OF_ENDER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(obtainItemGoal(eid("obtain_ender_chest"), items, Items.ENDER_CHEST)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.EYE_OF_ENDER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(potionGoal(eid("obtain_potion_of_oozing"), items, Potions.OOZING)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.SLIME,
                        EnigmaticsBingoTags.SWAMP, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(potionGoal(eid("obtain_potion_of_infestation"), items, Potions.INFESTED)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.SILVERFISH)
        );
        addGoal(potionGoal(eid("obtain_potion_of_weaving"), items, Potions.WEAVING)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.MINESHAFT,
                        EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(potionGoal(eid("obtain_potion_of_wind_charging"), items, Potions.OOZING)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.TRIAL_CHAMBER,
                        EnigmaticsBingoTags.BREEZE)
        );
        addGoal(potionGoal(eid("obtain_potion_of_strength"), items, Potions.STRENGTH, Potions.LONG_STRENGTH, Potions.STRONG_STRENGTH)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(potionGoal(eid("obtain_potion_of_regeneration"), items, Potions.REGENERATION, Potions.LONG_REGENERATION, Potions.STRONG_REGENERATION)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(potionGoal(eid("obtain_potion_of_healing"), items, Potions.HEALING, Potions.STRONG_HEALING)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(potionGoal(eid("obtain_potion_of_slowness"), items, Potions.SLOWNESS, Potions.STRONG_SLOWNESS, Potions.LONG_SLOWNESS)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
                .antisynergy(EnigmaticsBingoSynergies.SLOWNESS)
        );
        addGoal(potionGoal(eid("obtain_potion_of_harming"), items, Potions.HARMING, Potions.LONG_STRENGTH, Potions.STRONG_STRENGTH)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.INSTANT_DAMAGE)
        );
        addGoal(potionGoal(eid("obtain_potion_of_poison"), items, Potions.POISON, Potions.LONG_POISON, Potions.STRONG_POISON)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
                .antisynergy(EnigmaticsBingoSynergies.POISON)
        );
        addGoal(potionGoal(eid("obtain_potion_of_night_vision"), items, Potions.NIGHT_VISION, Potions.LONG_NIGHT_VISION)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(potionGoal(eid("obtain_potion_of_swiftness"), items, Potions.SWIFTNESS, Potions.LONG_SWIFTNESS, Potions.STRONG_SWIFTNESS)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.TRAIL_RUINS)
        );
        addGoal(obtainSomeItemsGoal(eid("obtain_some_fire_charges"), items, Items.FIRE_CHARGE, 4, 10)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.BARTERING, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(obtainItemGoal(eid("obtain_netherite_scrap"), items, Items.NETHERITE_SCRAP)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_LATE, EnigmaticsBingoTags.NETHERITE)
        );
        addGoal(breakBlockGoal(eid("break_turtle_egg"), blocks, Blocks.TURTLE_EGG)
                .tags(EnigmaticsBingoTags.OVERWORLD)
        );
        addGoal(breedAnimalGoal(eid("breed_hoglin"), entityTypes, EntityType.HOGLIN)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.CRIMSON_FOREST)
        );
        addGoal(dieToMobEntityGoal(eid("die_to_llama"), entityTypes, EntityType.LLAMA)
                .tags(EnigmaticsBingoTags.OVERWORLD)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_llama",
                        EntityType.LLAMA.getDescription()))
        );
        addGoal(dieToMobEntityGoal(eid("die_to_stray"), entityTypes, EntityType.STRAY)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_stray",
                        EntityType.STRAY.getDescription()))
        );
        addGoal(eatItemGoal(eid("eat_cookie"), items, Items.COOKIE)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.JUNGLE)
        );
        addGoal(effectGoal(eid("get_weakness"), MobEffects.WEAKNESS)
                .tags(EnigmaticsBingoTags.IGLOO)
                .antisynergy(EnigmaticsBingoSynergies.WEAKNESS)
                .reactant(EnigmaticsBingoSynergies.SUSPICIOUS_STEW)
        );
        addGoal(obtainSomeItemsFromTagGoal(eid("obtain_colored_candle"), ItemTags.CANDLES, 1, 1)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.BEEHIVE, EnigmaticsBingoTags.ANCIENT_CITY, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_colored_candle"))
        );
        addGoal(obtainItemGoal(eid("obtain_green_glazed_terracotta"), items, Items.GREEN_GLAZED_TERRACOTTA)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.IGLOO, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .antisynergy(EnigmaticsBingoSynergies.TERRACOTTA)
                .infrequency(7)
        );
        addGoal(obtainItemGoal(eid("obtain_lime_glazed_terracotta"), items, Items.LIME_GLAZED_TERRACOTTA)
                .tags(EnigmaticsBingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.TERRACOTTA)
                .infrequency(7)
        );
        addGoal(obtainItemGoal(eid("obtain_honey_bottle"), items, Items.HONEY_BOTTLE)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.BEEHIVE, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(obtainItemGoal(eid("obtain_powder_snow_bucket"), items, Items.POWDER_SNOW_BUCKET)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.MOUNTAIN, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(reachLevelsGoal(eid("reach_levels"), 16, 25));
        addGoal(tameAnimalGoal(eid("tame_parrot"), entityTypes, EntityType.PARROT)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.TAME_ANIMAL, EnigmaticsBingoTags.JUNGLE)
        );
        addGoal(tameSomeCatsGoal(eid("tame_some_cats"), 2, 4));
        addGoal(tameSomeWolvesGoal(eid("tame_some_wolves"), 2, 2));
        // TODO (requires OVERTAKABLE): Eat more unique foods than the enemy
        addGoal(wearDifferentMaterialsGoal(eid("wear_4_different_materials"), 4));
        {
            HolderLookup.RegistryLookup<Enchantment> enchantmentRegistry = registries.lookupOrThrow(Registries.ENCHANTMENT);
            List<Holder.Reference<Enchantment>> enchantmentNoCurses = enchantmentRegistry
                    .listElements()
                    .filter(hr -> !StringUtils.containsIgnoreCase(hr.toString(), "curse"))
                    .toList();
            HolderSet<Enchantment> enchantmentHolderSetNoCurses = HolderSet.direct(enchantmentNoCurses);

            addGoal(BingoGoal.builder(eid("use_grindstone_to_disenchant"))
                    .criterion("disenchant_enchant_slot_1", UseGrindstoneTrigger.builder().firstItem(
                            ItemPredicate.Builder.item().withSubPredicate(
                                    ItemSubPredicates.ENCHANTMENTS,
                                    ItemEnchantmentsPredicate.enchantments(List.of(
                                            new EnchantmentPredicate(Optional.of(enchantmentHolderSetNoCurses), MinMaxBounds.Ints.atLeast(0))
                                    ))
                            ).build()).build()
                    )
                    .criterion("disenchant_stored_enchant_slot_1", UseGrindstoneTrigger.builder().firstItem(
                            ItemPredicate.Builder.item().withSubPredicate(
                                    ItemSubPredicates.STORED_ENCHANTMENTS,
                                    ItemEnchantmentsPredicate.storedEnchantments(List.of(
                                            new EnchantmentPredicate(Optional.of(enchantmentHolderSetNoCurses), MinMaxBounds.Ints.atLeast(0))
                                    ))
                            ).build()).build()
                    )
                    .criterion("disenchant_enchant_slot_2", UseGrindstoneTrigger.builder().secondItem(
                            ItemPredicate.Builder.item().withSubPredicate(
                                    ItemSubPredicates.ENCHANTMENTS,
                                    ItemEnchantmentsPredicate.enchantments(List.of(
                                            new EnchantmentPredicate(Optional.of(enchantmentHolderSetNoCurses), MinMaxBounds.Ints.atLeast(0))
                                    ))
                            ).build()).build()
                    )
                    .criterion("disenchant_stored_enchant_slot_2", UseGrindstoneTrigger.builder().secondItem(
                            ItemPredicate.Builder.item().withSubPredicate(
                                    ItemSubPredicates.STORED_ENCHANTMENTS,
                                    ItemEnchantmentsPredicate.storedEnchantments(List.of(
                                            new EnchantmentPredicate(Optional.of(enchantmentHolderSetNoCurses), MinMaxBounds.Ints.atLeast(0))
                                    ))
                            ).build()).build()
                    )
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.USE_WORKSTATION)
                    .name(Component.translatable("enigmaticsbingogoals.goal.use_grindstone_to_disenchant", Items.GRINDSTONE.getName()))
                    .icon(new IndicatorIcon(BlockIcon.ofBlock(Blocks.GRINDSTONE), ItemIcon.ofItem(Items.ENCHANTED_BOOK)))
            );
        }
        addGoal(BingoGoal.builder(eid("use_anvil"))
                .criterion("use", UseAnvilTrigger.TriggerInstance.used())
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.ANVIL, EnigmaticsBingoTags.USE_WORKSTATION)
                .name(Component.translatable("enigmaticsbingogoals.goal.use_anvil", Items.ANVIL.getName()))
                .icon(BlockIcon.ofBlock(Blocks.ANVIL))
        );
        addGoal(advancementProgressGoal(eid("breed_some_unique_mobs"),
                ResourceLocation.withDefaultNamespace("husbandry/bred_all_animals"), 6, 10)
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
        addGoal(advancementGoal(eid("get_advancement_subspace_bubble"),
                Component.translatable("advancements.nether.fast_travel.title"),
                ResourceLocation.withDefaultNamespace("nether/fast_travel"))
                .tags(EnigmaticsBingoTags.NETHER)
                .icon(new IndicatorIcon(BlockIcon.ofBlock(Blocks.NETHER_PORTAL), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(eid("get_advancement_is_it_a_bird"),
                Component.translatable("advancements.adventure.spyglass_at_parrot.title"),
                ResourceLocation.withDefaultNamespace("adventure/spyglass_at_parrot"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.AMETHYST, EnigmaticsBingoTags.JUNGLE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.SPYGLASS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(eid("get_advancement_is_it_a_balloon"),
                Component.translatable("advancements.adventure.spyglass_at_ghast.title"),
                ResourceLocation.withDefaultNamespace("adventure/spyglass_at_ghast"))
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.AMETHYST, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.GHAST)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.SPYGLASS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(BingoGoal.builder(eid("huge_warped_fungus_in_overworld"))
                .criterion("grow", GrowFeatureTrigger.builder()
                        .feature(EnigmaticsBingoFeatureTags.HUGE_WARPED_FUNGI)
                        .location(
                                LocationPredicate.Builder.inDimension(Level.OVERWORLD).build()
                        ).build())
                .name(Component.translatable("enigmaticsbingogoals.goal.huge_fungus_in_overworld", Items.WARPED_FUNGUS.getName()))
                .icon(IndicatorIcon.infer(
                        Items.WARPED_FUNGUS,
                        Blocks.GRASS_BLOCK
                ))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.SILK_TOUCH,
                        EnigmaticsBingoTags.WARPED_FOREST, EnigmaticsBingoTags.NETHER_LATE, EnigmaticsBingoTags.GROW_TREE)
        );
        addGoal(killEntityGoal(eid("kill_endermite"), entityTypes, EntityType.ENDERMITE)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_endermite", EntityType.ENDERMITE.getDescription()))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.END, EnigmaticsBingoTags.VILLAGE,
                        EnigmaticsBingoTags.WARPED_FOREST, EnigmaticsBingoTags.NETHER_LATE)
        );
        addGoal(killEntityGoal(eid("kill_zoglin"), entityTypes, EntityType.ZOGLIN)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_zoglin", EntityType.ZOGLIN.getDescription()))
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.CRIMSON_FOREST)
        );
        addGoal(obtainAllItemsFromTagGoal(eid("obtain_all_diamond_tools"), EnigmaticsBingoItemTags.DIAMOND_TOOLS)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_material_tools",
                        Component.translatable(EnigmaticsBingoItemTags.DIAMOND_TOOLS.getTranslationKey())))
        );
        addGoal(obtainItemGoal(eid("obtain_experience_bottle"), items, Items.EXPERIENCE_BOTTLE)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.OUTPOST, EnigmaticsBingoTags.ANCIENT_CITY,
                        EnigmaticsBingoTags.VILLAGE)
        );
        addGoal(obtainSomeItemsFromTagGoal(eid("obtain_copper_bulb"), EnigmaticsBingoItemTags.COPPER_BULBS, 1, 1)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_copper_bulb", Items.COPPER_BULB.getName()))
        );
        addGoal(advancementGoal(eid("get_advancement_minecraft_trials_edition"),
                Component.translatable("advancements.adventure.minecraft_trials_edition.title"),
                ResourceLocation.withDefaultNamespace("adventure/minecraft_trials_edition"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.CHISELED_TUFF), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(eid("get_advancement_crafters_crafting_crafters"),
                Component.translatable("advancements.adventure.crafters_crafting_crafters.title"),
                ResourceLocation.withDefaultNamespace("adventure/crafters_crafting_crafters"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.REDSTONE, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.WOODLAND_MANSION)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.CRAFTER), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(eid("get_advancement_who_needs_rockets"),
                Component.translatable("advancements.adventure.who_needs_rockets.title"),
                ResourceLocation.withDefaultNamespace("adventure/who_needs_rockets"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.TRIAL_CHAMBER, EnigmaticsBingoTags.BREEZE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.WIND_CHARGE), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(eid("get_advancement_blowback"),
                Component.translatable("advancements.adventure.blowback.title"),
                ResourceLocation.withDefaultNamespace("adventure/blowback"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.TRIAL_CHAMBER, EnigmaticsBingoTags.BREEZE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.WIND_CHARGE), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(obtainItemGoal(eid("obtain_lingering_potion"), items, Items.LINGERING_POTION)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.END, EnigmaticsBingoTags.POTIONS, EnigmaticsBingoTags.TRIAL_CHAMBER,
                        EnigmaticsBingoTags.END_ENTRY)
        );
        addGoal(obtainItemGoal(eid("obtain_gilded_blackstone"), items, Items.GILDED_BLACKSTONE)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.NETHER_EXPLORE, EnigmaticsBingoTags.BASTION)
        );
        addGoal(obtainItemGoal(eid("obtain_sea_lantern"), items, Items.SEA_LANTERN)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.OCEAN_MONUMENT)
        );
        addGoal(obtainItemGoal(eid("obtain_soul_lantern"), items, Items.SOUL_LANTERN)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.SOUL_SAND, EnigmaticsBingoTags.ANCIENT_CITY)
        );
        addGoal(obtainItemGoal(eid("obtain_soul_campfire"), items, Items.SOUL_CAMPFIRE)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.SOUL_SAND)
        );
        addGoal(obtainItemGoal(eid("obtain_bamboo_mosaic"),items, Items.BAMBOO_MOSAIC)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.JUNGLE, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(obtainItemGoal(eid("obtain_tinted_glass"), items, Items.TINTED_GLASS)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.AMETHYST)
        );
        addGoal(obtainSomeItemsFromTagGoal(eid("obtain_some_music_discs"), EnigmaticsBingoItemTags.MUSIC_DISCS, 3, 5)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.ANCIENT_CITY, EnigmaticsBingoTags.TRAIL_RUINS, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .antisynergy(EnigmaticsBingoSynergies.MUSIC_DISC)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_music_discs", 0),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(obtainItemGoal(eid("obtain_goat_horn"), items, Items.GOAT_HORN)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.MOUNTAIN, EnigmaticsBingoTags.GOAT, EnigmaticsBingoTags.OUTPOST)
        );
        addGoal(BingoGoal.builder(eid("die_to_vines"))
                .criterion("die", EnigmaticsBingoGoalsTriggers.FALL_FROM_BLOCK.get().createCriterion(
                                new FallFromSolidBlockOrClimbableTrigger.TriggerInstance(
                                        Optional.of(ContextAwarePredicate.create(
                                                new InvertedLootItemCondition(
                                                        PlayerAliveCondition.INSTANCE
                                                )
                                        )),
                                        Optional.of(BlockPredicate.Builder.block().of(blocks, Blocks.VINE).build()),
                                        Optional.empty()
                                )
                        )
                )
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_vines", Items.VINE.getName()))
                .icon(IndicatorIcon.infer(
                        Items.VINE,
                        BingoGoalGeneratorUtils.getCustomPLayerHead((BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD))
                ))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.JUNGLE, EnigmaticsBingoTags.DIE_TO)
        );
    }
}
