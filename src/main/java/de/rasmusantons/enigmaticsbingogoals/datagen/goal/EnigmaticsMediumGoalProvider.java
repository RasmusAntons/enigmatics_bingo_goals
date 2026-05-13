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
import io.github.gaming32.bingo.data.BingoTags;
import io.github.gaming32.bingo.data.goal.BingoGoal;
import io.github.gaming32.bingo.data.icons.*;
import io.github.gaming32.bingo.data.progresstrackers.CriterionProgressTracker;
import io.github.gaming32.bingo.data.tags.bingo.BingoItemTags;
import io.github.gaming32.bingo.triggers.*;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.criterion.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentExactPredicate;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.predicates.DataComponentPredicates;
import net.minecraft.core.component.predicates.EnchantmentsPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.packs.VanillaHusbandryAdvancements;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStackTemplate;
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
import static de.rasmusantons.enigmaticsbingogoals.datagen.goal.EnigmaticsBingoGoalIds.Medium.*;

public class EnigmaticsMediumGoalProvider extends EnigmaticsDifficultyGoalProvider {
    public EnigmaticsMediumGoalProvider(BiConsumer<Identifier, BingoGoal> goalAdder, HolderLookup.Provider registries) {
        super(EnigmaticsBingoDifficulties.MEDIUM, goalAdder, registries);
    }

    @Override
    public void addGoals() {
        final var entityTypes = registries.lookupOrThrow(Registries.ENTITY_TYPE);
        final var items = registries.lookupOrThrow(Registries.ITEM);
        final var blocks = registries.lookupOrThrow(Registries.BLOCK);

        addGoal(BingoGoal.builder(NEVER_OBTAIN_CRAFTING_TABLE)
                .criterion("obtain", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .tags(
                        EnigmaticsBingoTags.NEVER,
                        BingoTags.LOCKOUT_INFLICTABLE,
                        EnigmaticsBingoTags.VILLAGE,
                        EnigmaticsBingoTags.OUTPOST,
                        EnigmaticsBingoTags.IGLOO,
                        EnigmaticsBingoTags.WITCH_HUT,
                        EnigmaticsBingoTags.TRAIL_RUINS
                )
                .name(Component.translatable("enigmaticsbingogoals.goal.never_obtain_crafting_table",
                        Component.translatable(Items.CRAFTING_TABLE.getDescriptionId())))
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.CRAFTING_TABLE), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(neverDamageGoal(NEVER_25_DAMAGE, 25));
        addGoal(BingoGoal.builder(NEVER_DIE)
                .criterion("die", BingoTriggers.DEATH.get().createCriterion(
                        DeathTrigger.TriggerInstance.death(null)
                ))
                .tags(EnigmaticsBingoTags.NEVER, BingoTags.LOCKOUT_INFLICTABLE, EnigmaticsBingoTags.NEVER_TAKE_DAMAGE, EnigmaticsBingoTags.PLAYER_KILL)
                .catalyst(EnigmaticsBingoSynergies.DIE)
                .name(Component.translatable("enigmaticsbingogoals.goal.never_die"))
                .icon(IndicatorIcon.infer(BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD), Items.BARRIER))
        );
        addGoal(BingoGoal.builder(KILL_ENEMY_PLAYER)
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
        addGoal(advancementsGoal(GET_ADVANCEMENTS, 21, 25));
        addGoal(obtainItemGoal(OBTAIN_DARK_PRISMARINE, items, Items.DARK_PRISMARINE)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.BURIED_TREASURE,
                        EnigmaticsBingoTags.OCEAN_MONUMENT, EnigmaticsBingoTags.SHIPWRECK)
        );
        addGoal(BingoGoal.builder(GIVE_EFFECT_TO_OTHER_TEAM)
                .criterion("give", GiveEffectToOtherTeamTrigger.TriggerInstance.anyEffect())
                .tags(EnigmaticsBingoTags.PVP, EnigmaticsBingoTags.GET_EFFECT, EnigmaticsBingoTags.STRAY,
                        EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.POTIONS, EnigmaticsBingoTags.TRIAL_CHAMBER,
                        EnigmaticsBingoTags.SWAMP)
                .icon(IndicatorIcon.infer(getAllEffectsIcon(), Items.PLAYER_HEAD))
                .name(Component.translatable("enigmaticsbingogoals.goal.give_effect_to_other_team"))
        );
        addGoal(BingoGoal.builder(PLAY_MUSIC_TO_OTHER_TEAM)
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
        addGoal(BingoGoal.builder(HIT_OTHER_TEAM_WITH_SNOWBALL)
                .criterion("hit", HitOtherTeamWithProjectileTrigger.TriggerInstance.ofType(entityTypes, EntityType.SNOWBALL))
                .tags(EnigmaticsBingoTags.PVP)
                .name(Component.translatable("enigmaticsbingogoals.goal.hit_player_with_snowball", EntityType.SNOWBALL.getDescription()))
                .icon(IndicatorIcon.infer(Items.SNOWBALL, Items.PLAYER_HEAD))
        );
        addGoal(BingoGoal.builder(HIT_OTHER_TEAM_WITH_WIND_CHARGE)
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
        addGoal(advancementProgressGoal(VISIT_SOME_UNIQUE_OVERWORLD_BIOMES,
                Identifier.withDefaultNamespace("adventure/adventuring_time"), 15, 25)
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
        addGoal(numberOfEffectsGoal(GET_SOME_EFFECTS, 6, 12));
        addGoal(effectGoal(GET_SLOWNESS, MobEffects.SLOWNESS)
                .tags(EnigmaticsBingoTags.TRIAL_CHAMBER)
                .antisynergy(EnigmaticsBingoSynergies.SLOWNESS)
        );
        addGoal(effectGoal(GET_MINING_FATIGUE, MobEffects.MINING_FATIGUE)
                .tags(EnigmaticsBingoTags.OCEAN_MONUMENT)
                .antisynergy(EnigmaticsBingoSynergies.SATURATION)
        );
        addGoal(effectGoal(GET_NAUSEA, MobEffects.NAUSEA)
                .tags(EnigmaticsBingoTags.PUFFER_FISH)
        );
        addGoal(dieToDamageTypeGoal(DIE_TO_INTENTIONAL_GAME_DESIGN, EnigmaticsBingoDamageTypeTags.INTENTIONAL_GAME_DESIGN)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.DIE_TO)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_intentional_game_design"))
                .catalyst(EnigmaticsBingoSynergies.EXPLOSION)
                .icon(IndicatorIcon.infer(CycleIcon.infer(Items.RED_BED, Items.RESPAWN_ANCHOR), BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        addGoal(dieToMobEntityGoal(DIE_TO_GOAT, entityTypes, EntityType.GOAT)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.MOUNTAIN, EnigmaticsBingoTags.GOAT)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_goat",
                        EntityType.GOAT.getDescription()))
        );
        addGoal(dieToDamageTypeGoal(DIE_TO_MAGIC, EnigmaticsBingoDamageTypeTags.MAGIC)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO, EnigmaticsBingoTags.WITCH_HUT,
                        EnigmaticsBingoTags.OCEAN_MONUMENT, EnigmaticsBingoTags.INSTANT_DAMAGE,
                        EnigmaticsBingoTags.RAID, EnigmaticsBingoTags.OUTPOST, EnigmaticsBingoTags.WOODLAND_MANSION)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_magic"))
                .icon(IndicatorIcon.infer(
                                new ItemStackTemplate(Items.SPLASH_POTION, DataComponentPatch.builder()
                                        .set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.STRONG_HARMING))
                                        .build()
                                ),
                                BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)
                        )
                )
        );
        addGoal(dieToDamageTypeGoal(DIE_TO_ANVIL, EnigmaticsBingoDamageTypeTags.ANVIL)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_anvil", Component.translatable(Items.ANVIL.getDescriptionId())))
                .icon(IndicatorIcon.infer(Items.ANVIL, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        addGoal(obtainItemGoal(OBTAIN_OBSERVER, items, Items.OBSERVER)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.NETHER_ENTRY)
        );
        addGoal(obtainItemGoal(OBTAIN_STICKY_PISTON, items, Items.STICKY_PISTON)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.SLIME, EnigmaticsBingoTags.JUNGLE, EnigmaticsBingoTags.ANCIENT_CITY, EnigmaticsBingoTags.SWAMP,
                        EnigmaticsBingoTags.SEEDFIND_BIOME_SWAMP)
        );
        addGoal(obtainItemGoal(OBTAIN_REDSTONE_LAMP, items, Items.REDSTONE_LAMP)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.ANCIENT_CITY)
        );
        addGoal(obtainItemGoal(OBTAIN_COMPARATOR, items, Items.COMPARATOR)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.ANCIENT_CITY)
        );
        addGoal(breakBlockGoal(BREAK_EMERALD_ORE, blocks, Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.MOUNTAIN,
                        EnigmaticsBingoTags.SEEDFIND_BIOMETAG_IS_MOUNTAIN)
        );
        addGoal(killEntityGoal(KILL_SILVERFISH, entityTypes, EntityType.SILVERFISH)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_silverfish", EntityType.SILVERFISH.getDescription()))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.STRONGHOLD, EnigmaticsBingoTags.MOUNTAIN,
                        EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.SILVERFISH, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(killEntityGoal(KILL_BREEZE, entityTypes, EntityType.BREEZE)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_breeze", EntityType.BREEZE.getDescription()))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.TRIAL_CHAMBER, EnigmaticsBingoTags.BREEZE)
        );
        addGoal(killEntityGoal(KILL_BOGGED, entityTypes, EntityType.BOGGED)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_bogged", EntityType.BOGGED.getDescription()))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.TRIAL_CHAMBER, EnigmaticsBingoTags.SWAMP)
        );
        addGoal(tameAnimalGoal(TAME_OCELOT, entityTypes, EntityType.OCELOT)
                .name(Component.translatable("enigmaticsbingogoals.goal.tame_ocelot", EntityType.OCELOT.getDescription()))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.TAME_ANIMAL, EnigmaticsBingoTags.JUNGLE,
                        EnigmaticsBingoTags.SEEDFIND_BIOMETAG_IS_JUNGLE)
                .icon(IndicatorIcon.infer(EntityType.OCELOT, new ItemTagCycleIcon(ItemTags.OCELOT_FOOD)))
        );
        addGoal(breedAnimalGoal(BREED_PIG, entityTypes, EntityType.PIG)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.VILLAGE)
                .catalyst(EnigmaticsBingoSynergies.BABY)
        );
        addGoal(breedAnimalGoal(BREED_FOX, entityTypes, EntityType.FOX)
                .tags(EnigmaticsBingoTags.OVERWORLD)
                .catalyst(EnigmaticsBingoSynergies.BABY)
        );
        addGoal(breedAnimalGoal(BREED_ARMADILLO, entityTypes, EntityType.ARMADILLO)
                .tags(EnigmaticsBingoTags.OVERWORLD)
        );
        addGoal(breedAnimalGoal(BREED_HORSE, entityTypes, EntityType.HORSE)
                .tags(EnigmaticsBingoTags.OVERWORLD)
        );
        addGoal(breedAnimalGoal(BREED_AXOLOTL, entityTypes, EntityType.AXOLOTL)
                .tags(EnigmaticsBingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.LUSH_CAVE)
        );
        addGoal(breedAnimalGoal(BREED_STRIDER, entityTypes, EntityType.STRIDER)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.STRIDER)
        );
        addGoal(killEntitiesFromTagGoal(KILL_SOME_UNIQUE_MOBS, EnigmaticsBingoEntityTypeTags.MOBS, 16, 25, true)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_unique_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.UNIQUE_HOSTILE_MOBS)
        );
        addGoal(killEntitiesFromTagGoal(KILL_SOME_UNIQUE_HOSTILE_MOBS, EnigmaticsBingoEntityTypeTags.HOSTILE, 11, 14, true)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_unique_hostile_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.UNIQUE_HOSTILE_MOBS)
        );
        addGoal(BingoGoal.builder(KILL_MOB_WHILE_DEAD)
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
        addGoal(killEntityGoal(KILL_WITCH, entityTypes, EntityType.WITCH)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_witch", EntityType.WITCH.getDescription()))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.RAID, EnigmaticsBingoTags.WITCH_HUT)
        );
        addGoal(killEntityGoal(KILL_VINDICATOR, entityTypes, EntityType.VINDICATOR)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_vindicator", EntityType.VINDICATOR.getDescription()))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.RAID, EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(killEntityGoal(KILL_ELDER_GUARDIAN, entityTypes, EntityType.ELDER_GUARDIAN)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_elder_guardian", EntityType.ELDER_GUARDIAN.getDescription()))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.OCEAN_MONUMENT)
                .icon(IndicatorIcon.infer(BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.ELDER_GUARDIAN), Items.NETHERITE_SWORD))
        );
        addGoal(obtainItemGoal(OBTAIN_MUSHROOM_STEM, items, Items.MUSHROOM_STEM)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.SILK_TOUCH)
        );
        addGoal(obtainItemGoal(OBTAIN_WARPED_NYLIUM, items, Items.WARPED_NYLIUM)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.SILK_TOUCH,
                        EnigmaticsBingoTags.WARPED_FOREST, EnigmaticsBingoTags.NETHER_ENTRY,
                        EnigmaticsBingoTags.SEEDFIND_BIOME_WARPED_FOREST)
        );
        addGoal(obtainSomeItemsFromTagGoal(OBTAIN_CHAINMAIL_ARMOR, EnigmaticsBingoItemTags.CHAINMAIL_ARMOR, 1, 1)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.RAID,
                        EnigmaticsBingoTags.OUTPOST, EnigmaticsBingoTags.WOODLAND_MANSION)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_chainmail_armor",
                        Component.translatable(EnigmaticsBingoItemTags.CHAINMAIL_ARMOR.getTranslationKey())))
        );
        addGoal(obtainSomeItemsFromTagGoal(OBTAIN_4_DIFFERENT_SEEDS, EnigmaticsBingoItemTags.SEEDS, 4, 4)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.WOODLAND_MANSION)
                .antisynergy(EnigmaticsBingoSynergies.SEEDS)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_seeds",
                                0, Component.translatable(EnigmaticsBingoItemTags.SEEDS.getTranslationKey())),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(eatItemGoal(EAT_BEETROOT_SOUP, items, Items.BEETROOT_SOUP)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.STEW)
        );
        addGoal(obtainItemGoal(OBTAIN_TROPICAL_FISH_BUCKET, items, Items.TROPICAL_FISH_BUCKET)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.BUCKET_WITH_MOB)
        );
        addGoal(obtainItemGoal(OBTAIN_TADPOLE_BUCKET, items, Items.TADPOLE_BUCKET)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.BUCKET_WITH_MOB, EnigmaticsBingoTags.SLIME, EnigmaticsBingoTags.SWAMP)
                .antisynergy(EnigmaticsBingoSynergies.FROG)
        );
        addGoal(advancementGoal(GET_ADVANCEMENT_SOUND_OF_MUSIC,
                Component.translatable("advancements.adventure.play_jukebox_in_meadows.title"),
                Identifier.withDefaultNamespace("adventure/play_jukebox_in_meadows"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.ANCIENT_CITY, EnigmaticsBingoTags.WOODLAND_MANSION,
                        EnigmaticsBingoTags.TRAIL_RUINS, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .antisynergy(EnigmaticsBingoSynergies.MUSIC_DISC)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.JUKEBOX), ItemIcon.ofItem(Blocks.GOLD_BLOCK)))
        );
        addGoal(BingoGoal.builder(EQUIP_WOLF_ARMOR)
                .criterion("equip", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
                        ItemPredicate.Builder.item().of(items, Items.WOLF_ARMOR),
                        Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(entityTypes, EntityType.WOLF)))
                ))
                .tags(EnigmaticsBingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.WOLF)
                .name(Component.translatable("enigmaticsbingogoals.goal.equip_a_wolf_with_armor", EntityType.WOLF.getDescription()))
                .icon(IndicatorIcon.infer(EntityType.WOLF, Items.WOLF_ARMOR))
        );
        addGoal(wearArmorPiecesGoal(WEAR_FULL_GOLD, entityTypes, items, Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE,
                Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.ARMOR)
                .name(Component.translatable("enigmaticsbingogoals.goal.wear_full_gold"))
        );
        addGoal(obtainSomeItemsFromTagGoal(OBTAIN_SOME_SAPLINGS, EnigmaticsBingoItemTags.SAPLINGS, 4, 5)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.PLANT_BATCH)
                .antisynergy(EnigmaticsBingoSynergies.SAPLING)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_saplings", 0,
                                Component.translatable(EnigmaticsBingoItemTags.SAPLINGS.getTranslationKey())),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(obtainSomeItemsFromTagGoal(OBTAIN_SOME_BONEMEALABLE_BLOCKS, BingoItemTags.BONEMEALABLE, 10, 20)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.PLANT_BATCH)
                .antisynergy(EnigmaticsBingoSynergies.SAPLING, EnigmaticsBingoSynergies.SEEDS)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_bonemealable_blocks", 0),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(advancementProgressGoal(EAT_SOME_UNIQUE_FOODS,
                Identifier.withDefaultNamespace("husbandry/balanced_diet"), 14, 24)
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
        addGoal(advancementGoal(GET_ADVANCEMENT_SNIPER_DUEL,
                Component.translatable("advancements.adventure.sniper_duel.title"),
                Identifier.withDefaultNamespace("adventure/sniper_duel"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.BOW)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.ARROW), ItemIcon.ofItem(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(GET_ADVANCEMENT_BULLSEYE,
                Component.translatable("advancements.adventure.bullseye.title"),
                Identifier.withDefaultNamespace("adventure/bullseye"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.BOW)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.TARGET), ItemIcon.ofItem(Blocks.GOLD_BLOCK)))
        );
        addGoal(BingoGoal.builder(RIDE_PIG_FOR_300_METERS)
                .criterion("ride", RelativeStatsTrigger.builder()
                        .stat(Stats.PIG_ONE_CM, MinMaxBounds.Ints.atLeast(30000)).build())
                .progress(new CriterionProgressTracker("ride", 0.01f))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.SADDLE, EnigmaticsBingoTags.PIG)
                .name(Component.translatable("enigmaticsbingogoals.goal.ride_pig_distance",
                        EntityType.PIG.getDescription(), 300))
                .icon(IndicatorIcon.infer(EntityType.PIG, EffectIcon.of(MobEffects.SPEED)))
        );
        addGoal(BingoGoal.builder(RIDE_PIG_LAVA)
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
        addGoal(BingoGoal.builder(USE_CARROT_ON_A_STICK)
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
                        EntityType.PIG.getDescription(), Component.translatable(Items.CARROT_ON_A_STICK.getDescriptionId())))
                .icon(IndicatorIcon.infer(EntityType.PIG, Items.CARROT_ON_A_STICK))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.PIG, EnigmaticsBingoTags.SADDLE)
        );
        addGoal(rideAbstractHorseWithSaddleGoal(RIDE_HORSE, entityTypes, items, EntityType.HORSE)
                .name(Component.translatable("enigmaticsbingogoals.goal.ride_horse",
                        EntityType.HORSE.getDescription(), Component.translatable(Items.SADDLE.getDescriptionId())))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.SADDLE)
        );
        addGoal(advancementGoal(GET_ANY_SPYGLASS_ADVANCEMENT, null,
                Identifier.withDefaultNamespace("adventure/spyglass_at_parrot"),
                Identifier.withDefaultNamespace("adventure/spyglass_at_ghast"),
                Identifier.withDefaultNamespace("adventure/spyglass_at_dragon"))
                .name(Component.translatable("enigmaticsbingogoals.goal.get_any_spyglass_advancement"))
                .tags(EnigmaticsBingoTags.AMETHYST, EnigmaticsBingoTags.JUNGLE, EnigmaticsBingoTags.NETHER_ENTRY,
                        EnigmaticsBingoTags.NETHER_ENTRY)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.SPYGLASS), ItemIcon.ofItem(Blocks.GOLD_BLOCK)))
        );
        addGoal(obtainItemGoal(OBTAIN_SPONGE, items, Items.SPONGE)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.OCEAN_MONUMENT)
        );
        addGoal(obtainSomeItemsFromTagGoal(OBTAIN_SOME_TRIM_TEMPLATES, BingoItemTags.TRIM_TEMPLATES, 2, 3)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.RARE_COLLECTIBLE_BATCH,
                        EnigmaticsBingoTags.TRAIL_RUINS, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_trim_templates", 0),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(obtainItemGoal(OBTAIN_COBWEB, items, Items.COBWEB)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.IGLOO,
                        EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(breakBlockGoal(BREAK_MOB_SPAWNER, blocks, Blocks.SPAWNER)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.FORTRESS,
                        EnigmaticsBingoTags.STRONGHOLD, EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(obtainItemGoal(OBTAIN_CYAN_GLAZED_TERRACOTTA, items, Items.CYAN_GLAZED_TERRACOTTA)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.TRAIL_RUINS)
                .antisynergy(EnigmaticsBingoSynergies.TERRACOTTA)
                .infrequency(7)
        );
        addGoal(obtainSomeItemsFromTagGoal(OBTAIN_SOME_DIFFERENT_COLORS_OF_TERRACOTTA, ItemTags.TERRACOTTA, 6, 11)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.TRAIL_RUINS)
                .antisynergy(EnigmaticsBingoSynergies.TERRACOTTA)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_colors_of_terracotta", 0),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(obtainItemGoal(OBTAIN_SLIME_BLOCK, items, Items.SLIME_BLOCK)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.SLIME, EnigmaticsBingoTags.SWAMP)
        );
        addGoal(obtainItemGoal(OBTAIN_HONEY_BLOCK, items, Items.HONEY_BLOCK)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.BEEHIVE, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(obtainItemGoal(OBTAIN_SCAFFOLDING, items, Items.SCAFFOLDING)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.JUNGLE, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(advancementGoal(GET_ADVANCEMENT_ENCHANTER,
                Component.translatable("advancements.story.enchant_item.title"),
                Identifier.withDefaultNamespace("story/enchant_item"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.WOODLAND_MANSION)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.ENCHANTED_BOOK), ItemIcon.ofItem(Blocks.GOLD_BLOCK)))
        );
        addGoal(obtainItemGoal(OBTAIN_STACK_OF_CYAN_WOOL, items, Items.CYAN_WOOL, 64)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.IGLOO)
                .antisynergy(EnigmaticsBingoSynergies.WOOL)
                .infrequency(12)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Component.translatable(Items.CYAN_WOOL.getDescriptionId())))
        );
        addGoal(obtainItemGoal(OBTAIN_STACK_OF_GREEN_WOOL, items, Items.GREEN_WOOL, 64)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.IGLOO, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .antisynergy(EnigmaticsBingoSynergies.WOOL)
                .infrequency(12)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Component.translatable(Items.GREEN_WOOL.getDescriptionId())))
        );
        // TODO: Show an Egg to the World | Tooltip: Visit 10-20 biomes with an Egg in your off-hand
        addGoal(BingoGoal.builder(USE_GLOW_INK_ON_CRIMSON_SIGN)
                .criterion("use", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                        LocationPredicate.Builder.location().setBlock(
                                BlockPredicate.Builder.block()
                                        .of(blocks, Blocks.CRIMSON_SIGN, Blocks.CRIMSON_WALL_SIGN)
                        ),
                        ItemPredicate.Builder.item().of(items, Items.GLOW_INK_SAC)
                ))
                .name(Component.translatable("enigmaticsbingogoals.goal.use_item_on_block",
                        Component.translatable(Items.GLOW_INK_SAC.getDescriptionId()), Component.translatable(Items.CRIMSON_SIGN.getDescriptionId())))
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.CRIMSON_FOREST, EnigmaticsBingoTags.SIGN,
                        EnigmaticsBingoTags.GLOW_INK, EnigmaticsBingoTags.SEEDFIND_BIOME_CRIMSON_FOREST)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.CRIMSON_SIGN), ItemIcon.ofItem(Items.GLOW_INK_SAC)))
        );
        addGoal(BingoGoal.builder(USE_GLOW_INK_ON_WARPED_SIGN)
                .criterion("use", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                        LocationPredicate.Builder.location().setBlock(
                                BlockPredicate.Builder.block()
                                        .of(blocks, Blocks.WARPED_SIGN, Blocks.WARPED_WALL_SIGN)
                        ),
                        ItemPredicate.Builder.item().of(items, Items.GLOW_INK_SAC)
                ))
                .name(Component.translatable("enigmaticsbingogoals.goal.use_item_on_block",
                        Component.translatable(Items.GLOW_INK_SAC.getDescriptionId()), Component.translatable(Items.WARPED_SIGN.getDescriptionId())))
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.WARPED_FOREST, EnigmaticsBingoTags.SIGN,
                        EnigmaticsBingoTags.GLOW_INK, EnigmaticsBingoTags.SEEDFIND_BIOME_WARPED_FOREST)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.WARPED_SIGN), ItemIcon.ofItem(Items.GLOW_INK_SAC)))
        );
        addGoal(advancementGoal(GET_ADVANCEMENT_A_TERRIBLE_FORTRESS,
                Component.translatable("advancements.nether.find_fortress.title"),
                Identifier.withDefaultNamespace("nether/find_fortress"))
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.NETHER_EXPLORE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.NETHER_BRICKS), ItemIcon.ofItem(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(GET_ADVANCEMENT_THOSE_WERE_THE_DAYS,
                Component.translatable("advancements.nether.find_bastion.title"),
                Identifier.withDefaultNamespace("nether/find_bastion"))
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.NETHER_EXPLORE, EnigmaticsBingoTags.BASTION)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.POLISHED_BLACKSTONE_BRICKS), ItemIcon.ofItem(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(GET_ADVANCEMENT_NOT_QUITE_NINE_LIVES,
                Component.translatable("advancements.nether.charge_respawn_anchor.title"),
                Identifier.withDefaultNamespace("nether/charge_respawn_anchor"))
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.RESPAWN_ANCHOR), ItemIcon.ofItem(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(GET_ADVANCEMENT_HOT_TOURIST_DESTINATIONS,
                Component.translatable("advancements.nether.explore_nether.title"),
                Identifier.withDefaultNamespace("nether/explore_nether"))
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BIOMES, EnigmaticsBingoTags.NETHER_EXPLORE,
                        EnigmaticsBingoTags.NETHER_LATE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.NETHERITE_BOOTS), ItemIcon.ofItem(Blocks.GOLD_BLOCK)))
        );
        addGoal(obtainItemGoal(OBTAIN_END_CRYSTAL, items, Items.END_CRYSTAL)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.EYE_OF_ENDER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(obtainItemGoal(OBTAIN_ENDER_EYE, items, Items.ENDER_EYE)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.EYE_OF_ENDER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(obtainItemGoal(OBTAIN_ENDER_CHEST, items, Items.ENDER_CHEST)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.EYE_OF_ENDER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(potionGoal(OBTAIN_POTION_OF_OOZING, items, Potions.OOZING)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.SLIME,
                        EnigmaticsBingoTags.SWAMP, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(potionGoal(OBTAIN_POTION_OF_INFESTATION, items, Potions.INFESTED)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.SILVERFISH)
        );
        addGoal(potionGoal(OBTAIN_POTION_OF_WEAVING, items, Potions.WEAVING)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.MINESHAFT,
                        EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(potionGoal(OBTAIN_POTION_OF_WIND_CHARGING, items, Potions.WIND_CHARGED)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.TRIAL_CHAMBER,
                        EnigmaticsBingoTags.BREEZE)
        );
        addGoal(potionGoal(OBTAIN_POTION_OF_STRENGTH, items, Potions.STRENGTH, Potions.LONG_STRENGTH, Potions.STRONG_STRENGTH)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(potionGoal(OBTAIN_POTION_OF_REGENERATION, items, Potions.REGENERATION, Potions.LONG_REGENERATION, Potions.STRONG_REGENERATION)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(potionGoal(OBTAIN_POTION_OF_HEALING, items, Potions.HEALING, Potions.STRONG_HEALING)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(potionGoal(OBTAIN_POTION_OF_SLOWNESS, items, Potions.SLOWNESS, Potions.STRONG_SLOWNESS, Potions.LONG_SLOWNESS)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
                .antisynergy(EnigmaticsBingoSynergies.SLOWNESS)
        );
        addGoal(potionGoal(OBTAIN_POTION_OF_HARMING, items, Potions.HARMING, Potions.LONG_STRENGTH, Potions.STRONG_STRENGTH)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.INSTANT_DAMAGE)
        );
        addGoal(potionGoal(OBTAIN_POTION_OF_POISON, items, Potions.POISON, Potions.LONG_POISON, Potions.STRONG_POISON)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
                .antisynergy(EnigmaticsBingoSynergies.POISON)
        );
        addGoal(potionGoal(OBTAIN_POTION_OF_NIGHT_VISION, items, Potions.NIGHT_VISION, Potions.LONG_NIGHT_VISION)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(potionGoal(OBTAIN_POTION_OF_SWIFTNESS, items, Potions.SWIFTNESS, Potions.LONG_SWIFTNESS, Potions.STRONG_SWIFTNESS)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.TRAIL_RUINS)
        );
        addGoal(obtainSomeItemsGoal(OBTAIN_SOME_FIRE_CHARGES, items, Items.FIRE_CHARGE, 4, 10)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.BARTERING, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(obtainItemGoal(OBTAIN_NETHERITE_SCRAP, items, Items.NETHERITE_SCRAP)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_LATE, EnigmaticsBingoTags.NETHERITE)
        );
        addGoal(breakBlockGoal(BREAK_TURTLE_EGG, blocks, Blocks.TURTLE_EGG)
                .tags(EnigmaticsBingoTags.OVERWORLD)
        );
        addGoal(breedAnimalGoal(BREED_HOGLIN, entityTypes, EntityType.HOGLIN)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.CRIMSON_FOREST,
                        EnigmaticsBingoTags.SEEDFIND_BIOME_CRIMSON_FOREST)
        );
        addGoal(dieToMobEntityGoal(DIE_TO_LLAMA, entityTypes, EntityType.LLAMA)
                .tags(EnigmaticsBingoTags.OVERWORLD)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_llama",
                        EntityType.LLAMA.getDescription()))
        );
        addGoal(dieToMobEntityGoal(DIE_TO_STRAY, entityTypes, EntityType.STRAY)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_stray",
                        EntityType.STRAY.getDescription()))
        );
        addGoal(eatItemGoal(EAT_COOKIE, items, Items.COOKIE)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.JUNGLE,
                        EnigmaticsBingoTags.SEEDFIND_BIOMETAG_IS_JUNGLE)
        );
        addGoal(effectGoal(GET_WEAKNESS, MobEffects.WEAKNESS)
                .tags(EnigmaticsBingoTags.IGLOO)
                .antisynergy(EnigmaticsBingoSynergies.WEAKNESS)
                .reactant(EnigmaticsBingoSynergies.SUSPICIOUS_STEW)
        );
        addGoal(obtainSomeItemsFromTagGoal(OBTAIN_COLORED_CANDLE, EnigmaticsBingoItemTags.COLORED_CANDLES, 1, 1)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.BEEHIVE, EnigmaticsBingoTags.ANCIENT_CITY, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_colored_candle"))
        );
        addGoal(obtainItemGoal(OBTAIN_GREEN_GLAZED_TERRACOTTA, items, Items.GREEN_GLAZED_TERRACOTTA)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.IGLOO, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .antisynergy(EnigmaticsBingoSynergies.TERRACOTTA)
                .infrequency(7)
        );
        addGoal(obtainItemGoal(OBTAIN_LIME_GLAZED_TERRACOTTA, items, Items.LIME_GLAZED_TERRACOTTA)
                .tags(EnigmaticsBingoTags.OVERWORLD)
                .antisynergy(EnigmaticsBingoSynergies.TERRACOTTA)
                .infrequency(7)
        );
        addGoal(obtainItemGoal(OBTAIN_HONEY_BOTTLE, items, Items.HONEY_BOTTLE)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.BEEHIVE, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(obtainItemGoal(OBTAIN_POWDER_SNOW_BUCKET, items, Items.POWDER_SNOW_BUCKET)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.MOUNTAIN, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(reachLevelsGoal(REACH_LEVELS, 16, 25));
        addGoal(tameAnimalGoal(TAME_PARROT, entityTypes, EntityType.PARROT)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.TAME_ANIMAL, EnigmaticsBingoTags.JUNGLE,
                        EnigmaticsBingoTags.SEEDFIND_BIOMETAG_IS_JUNGLE)
                .icon(IndicatorIcon.infer(EntityType.PARROT, new ItemTagCycleIcon(ItemTags.PARROT_FOOD)))
        );
        addGoal(tameSomeCatsGoal(TAME_SOME_CATS, registries.lookupOrThrow(Registries.CAT_VARIANT), 2, 4));
        addGoal(tameSomeWolvesGoal(TAME_SOME_WOLVES, 2, 2));
        // TODO (requires OVERTAKABLE): Eat more unique foods than the enemy
        addGoal(wearDifferentMaterialsGoal(WEAR_4_DIFFERENT_MATERIALS, 4));
        {
            HolderLookup.RegistryLookup<Enchantment> enchantmentRegistry = registries.lookupOrThrow(Registries.ENCHANTMENT);
            List<Holder.Reference<Enchantment>> enchantmentNoCurses = enchantmentRegistry
                    .listElements()
                    .filter(hr -> !StringUtils.containsIgnoreCase(hr.toString(), "curse"))
                    .toList();
            HolderSet<Enchantment> enchantmentHolderSetNoCurses = HolderSet.direct(enchantmentNoCurses);

            addGoal(BingoGoal.builder(USE_GRINDSTONE_TO_DISENCHANT)
                    .criterion("disenchant_enchant_slot_1", UseGrindstoneTrigger.builder().firstItem(
                            ItemPredicate.Builder.item().withComponents(
                                    DataComponentMatchers.Builder.components().partial(
                                            DataComponentPredicates.ENCHANTMENTS,
                                            EnchantmentsPredicate.enchantments(List.of(
                                                    new EnchantmentPredicate(enchantmentHolderSetNoCurses, MinMaxBounds.Ints.atLeast(0))
                                            ))
                                    ).build()
                            ).build()).build()
                    )
                    .criterion("disenchant_stored_enchant_slot_1", UseGrindstoneTrigger.builder().firstItem(
                            ItemPredicate.Builder.item().withComponents(
                                    DataComponentMatchers.Builder.components().partial(
                                            DataComponentPredicates.STORED_ENCHANTMENTS,
                                            EnchantmentsPredicate.storedEnchantments(List.of(
                                                    new EnchantmentPredicate(enchantmentHolderSetNoCurses, MinMaxBounds.Ints.atLeast(0))
                                            ))
                                    ).build()
                            ).build()).build()
                    )
                    .criterion("disenchant_enchant_slot_2", UseGrindstoneTrigger.builder().secondItem(
                            ItemPredicate.Builder.item().withComponents(
                                    DataComponentMatchers.Builder.components().partial(
                                            DataComponentPredicates.ENCHANTMENTS,
                                            EnchantmentsPredicate.enchantments(List.of(
                                                    new EnchantmentPredicate(enchantmentHolderSetNoCurses, MinMaxBounds.Ints.atLeast(0))
                                            ))
                                    ).build()
                            ).build()).build()
                    )
                    .criterion("disenchant_stored_enchant_slot_2", UseGrindstoneTrigger.builder().secondItem(
                            ItemPredicate.Builder.item().withComponents(
                                    DataComponentMatchers.Builder.components().partial(
                                            DataComponentPredicates.STORED_ENCHANTMENTS,
                                            EnchantmentsPredicate.storedEnchantments(List.of(
                                                    new EnchantmentPredicate(enchantmentHolderSetNoCurses, MinMaxBounds.Ints.atLeast(0))
                                            ))
                                    ).build()
                            ).build()).build()
                    )
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.USE_WORKSTATION)
                    .name(Component.translatable("enigmaticsbingogoals.goal.use_grindstone_to_disenchant",
                            Component.translatable(Items.GRINDSTONE.getDescriptionId()))
                    )
                    .icon(new IndicatorIcon(BlockIcon.ofBlock(Blocks.GRINDSTONE), ItemIcon.ofItem(Items.ENCHANTED_BOOK)))
            );
        }
        addGoal(BingoGoal.builder(USE_ANVIL)
                .criterion("use", UseAnvilTrigger.TriggerInstance.used())
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.ANVIL, EnigmaticsBingoTags.USE_WORKSTATION)
                .name(Component.translatable("enigmaticsbingogoals.goal.use_anvil",
                        Component.translatable(Items.ANVIL.getDescriptionId()))
                )
                .icon(BlockIcon.ofBlock(Blocks.ANVIL))
        );
        addGoal(advancementProgressGoal(BREED_SOME_UNIQUE_MOBS,
                Identifier.withDefaultNamespace("husbandry/bred_all_animals"), 6, 10)
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
        addGoal(advancementGoal(GET_ADVANCEMENT_SUBSPACE_BUBBLE,
                Component.translatable("advancements.nether.fast_travel.title"),
                Identifier.withDefaultNamespace("nether/fast_travel"))
                .tags(EnigmaticsBingoTags.NETHER)
                .icon(new IndicatorIcon(BlockIcon.ofBlock(Blocks.NETHER_PORTAL), ItemIcon.ofItem(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(GET_ADVANCEMENT_IS_IT_A_BIRD,
                Component.translatable("advancements.adventure.spyglass_at_parrot.title"),
                Identifier.withDefaultNamespace("adventure/spyglass_at_parrot"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.AMETHYST, EnigmaticsBingoTags.JUNGLE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.SPYGLASS), ItemIcon.ofItem(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(GET_ADVANCEMENT_IS_IT_A_BALLOON,
                Component.translatable("advancements.adventure.spyglass_at_ghast.title"),
                Identifier.withDefaultNamespace("adventure/spyglass_at_ghast"))
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.AMETHYST, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.GHAST)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.SPYGLASS), ItemIcon.ofItem(Blocks.GOLD_BLOCK)))
        );
        addGoal(BingoGoal.builder(HUGE_WARPED_FUNGUS_IN_OVERWORLD)
                .criterion("grow", GrowFeatureTrigger.builder()
                        .feature(EnigmaticsBingoFeatureTags.HUGE_WARPED_FUNGI)
                        .location(
                                LocationPredicate.Builder.inDimension(Level.OVERWORLD).build()
                        ).build())
                .name(Component.translatable("enigmaticsbingogoals.goal.huge_fungus_in_overworld",
                        Component.translatable(Items.WARPED_FUNGUS.getDescriptionId()))
                )
                .icon(IndicatorIcon.infer(
                        Items.WARPED_FUNGUS,
                        Blocks.GRASS_BLOCK
                ))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.VILLAGE, EnigmaticsBingoTags.SILK_TOUCH,
                        EnigmaticsBingoTags.WARPED_FOREST, EnigmaticsBingoTags.NETHER_LATE, EnigmaticsBingoTags.GROW_TREE,
                        EnigmaticsBingoTags.SEEDFIND_BIOME_WARPED_FOREST)
        );
        addGoal(killEntityGoal(KILL_ENDERMITE, entityTypes, EntityType.ENDERMITE)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_endermite", EntityType.ENDERMITE.getDescription()))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.END, EnigmaticsBingoTags.VILLAGE,
                        EnigmaticsBingoTags.WARPED_FOREST, EnigmaticsBingoTags.NETHER_LATE)
        );
        addGoal(killEntityGoal(KILL_ZOGLIN, entityTypes, EntityType.ZOGLIN)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_zoglin", EntityType.ZOGLIN.getDescription()))
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.CRIMSON_FOREST,
                        EnigmaticsBingoTags.SEEDFIND_BIOME_CRIMSON_FOREST)
        );
        addGoal(obtainAllItemsFromTagGoal(OBTAIN_ALL_DIAMOND_TOOLS, EnigmaticsBingoItemTags.DIAMOND_TOOLS)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_material_tools",
                        Component.translatable(EnigmaticsBingoItemTags.DIAMOND_TOOLS.getTranslationKey())))
        );
        addGoal(obtainItemGoal(OBTAIN_EXPERIENCE_BOTTLE, items, Items.EXPERIENCE_BOTTLE)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.OUTPOST, EnigmaticsBingoTags.ANCIENT_CITY,
                        EnigmaticsBingoTags.VILLAGE)
        );
        addGoal(obtainSomeItemsFromTagGoal(OBTAIN_COPPER_BULB, EnigmaticsBingoItemTags.COPPER_BULBS, 1, 1)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_copper_bulb",
                        Component.translatable(Items.COPPER_BULB.getDescriptionId()))
                )
        );
        addGoal(advancementGoal(GET_ADVANCEMENT_MINECRAFT_TRIALS_EDITION,
                Component.translatable("advancements.adventure.minecraft_trials_edition.title"),
                Identifier.withDefaultNamespace("adventure/minecraft_trials_edition"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.CHISELED_TUFF), ItemIcon.ofItem(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(GET_ADVANCEMENT_CRAFTERS_CRAFTING_CRAFTERS,
                Component.translatable("advancements.adventure.crafters_crafting_crafters.title"),
                Identifier.withDefaultNamespace("adventure/crafters_crafting_crafters"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.REDSTONE, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.WOODLAND_MANSION)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.CRAFTER), ItemIcon.ofItem(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(GET_ADVANCEMENT_WHO_NEEDS_ROCKETS,
                Component.translatable("advancements.adventure.who_needs_rockets.title"),
                Identifier.withDefaultNamespace("adventure/who_needs_rockets"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.TRIAL_CHAMBER, EnigmaticsBingoTags.BREEZE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.WIND_CHARGE), ItemIcon.ofItem(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(GET_ADVANCEMENT_BLOWBACK,
                Component.translatable("advancements.adventure.blowback.title"),
                Identifier.withDefaultNamespace("adventure/blowback"))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.TRIAL_CHAMBER, EnigmaticsBingoTags.BREEZE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.WIND_CHARGE), ItemIcon.ofItem(Blocks.GOLD_BLOCK)))
        );
        addGoal(obtainItemGoal(OBTAIN_LINGERING_POTION, items, Items.LINGERING_POTION)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.END, EnigmaticsBingoTags.POTIONS, EnigmaticsBingoTags.TRIAL_CHAMBER,
                        EnigmaticsBingoTags.END_ENTRY)
        );
        addGoal(obtainItemGoal(OBTAIN_GILDED_BLACKSTONE, items, Items.GILDED_BLACKSTONE)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.NETHER_EXPLORE, EnigmaticsBingoTags.BASTION)
        );
        addGoal(obtainItemGoal(OBTAIN_SEA_LANTERN, items, Items.SEA_LANTERN)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.OCEAN_MONUMENT)
        );
        addGoal(obtainItemGoal(OBTAIN_SOUL_LANTERN, items, Items.SOUL_LANTERN)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.SOUL_SAND, EnigmaticsBingoTags.ANCIENT_CITY)
        );
        addGoal(obtainItemGoal(OBTAIN_SOUL_CAMPFIRE, items, Items.SOUL_CAMPFIRE)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.SOUL_SAND)
        );
        addGoal(obtainItemGoal(OBTAIN_BAMBOO_MOSAIC,items, Items.BAMBOO_MOSAIC)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.JUNGLE, EnigmaticsBingoTags.TRIAL_CHAMBER)
        );
        addGoal(obtainItemGoal(OBTAIN_TINTED_GLASS, items, Items.TINTED_GLASS)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.AMETHYST)
        );
        addGoal(obtainSomeItemsFromTagGoal(OBTAIN_SOME_MUSIC_DISCS, EnigmaticsBingoItemTags.MUSIC_DISCS, 3, 5)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.ANCIENT_CITY, EnigmaticsBingoTags.TRAIL_RUINS, EnigmaticsBingoTags.TRIAL_CHAMBER)
                .antisynergy(EnigmaticsBingoSynergies.MUSIC_DISC)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_music_discs", 0),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(obtainItemGoal(OBTAIN_GOAT_HORN, items, Items.GOAT_HORN)
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.MOUNTAIN, EnigmaticsBingoTags.GOAT, EnigmaticsBingoTags.OUTPOST)
        );
        addGoal(BingoGoal.builder(DIE_TO_VINES)
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
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_vines", Component.translatable(Items.VINE.getDescriptionId())))
                .icon(IndicatorIcon.infer(
                        Items.VINE,
                        BingoGoalGeneratorUtils.getCustomPLayerHead((BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD))
                ))
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.JUNGLE, EnigmaticsBingoTags.DIE_TO,
                        EnigmaticsBingoTags.SEEDFIND_BIOMETAG_IS_JUNGLE)
        );
        addGoal(advancementGoal(GET_ADVANCEMENT_STAY_HYDRATED,
                Component.translatable("advancements.husbandry.place_dried_ghast_in_water.title"),
                Identifier.withDefaultNamespace("husbandry/place_dried_ghast_in_water"))
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.HAPPY_GHAST)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.DRIED_GHAST), ItemIcon.ofItem(Blocks.GOLD_BLOCK)))
        );
    }
}
