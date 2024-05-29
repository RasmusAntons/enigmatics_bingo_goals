package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoTags;
import de.rasmusantons.enigmaticsbingogoals.conditions.FullUniqueInventoryCondition;
import de.rasmusantons.enigmaticsbingogoals.conditions.KillEnemyPlayerCondition;
import de.rasmusantons.enigmaticsbingogoals.conditions.PlayerAliveCondition;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoDamageTypeTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoItemTags;
import de.rasmusantons.enigmaticsbingogoals.triggers.*;
import io.github.gaming32.bingo.conditions.HasAnyEffectCondition;
import io.github.gaming32.bingo.data.BingoGoal;
import io.github.gaming32.bingo.data.BingoTags;
import io.github.gaming32.bingo.data.icons.*;
import io.github.gaming32.bingo.data.progresstrackers.CriterionProgressTracker;
import io.github.gaming32.bingo.triggers.BingoTriggers;
import io.github.gaming32.bingo.triggers.ChickenHatchTrigger;
import io.github.gaming32.bingo.triggers.DeathTrigger;
import io.github.gaming32.bingo.triggers.RelativeStatsTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;

import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;

public class EnigmaticsGoalProvider extends EnigmaticsDifficultyGoalProvider {
    public EnigmaticsGoalProvider(Consumer<BingoGoal.Holder> goalAdder, HolderLookup.Provider registries) {
        super(EnigmaticsDifficultyGoalProvider.ENIGMATICS, goalAdder, registries);
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
        addGoal(neverDamageGoal(id("never_50_damage"), 50));
        addGoal(neverDamageGoal(id("never_100_damage"), 100));
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
        addGoal(neverLevelsGoal(id("never_levels"), 1, 3));
        addGoal(BingoGoal.builder(id("never_die"))
                .criterion("die", BingoTriggers.DEATH.get().createCriterion(
                        DeathTrigger.TriggerInstance.death(null)
                ))
                .tags(BingoTags.NEVER, EnigmaticsBingoTags.NEVER_TAKE_DAMAGE, EnigmaticsBingoTags.PLAYER_KILL)
                .name(Component.translatable("enigmaticsbingogoals.goal.never_die"))
                .icon(IndicatorIcon.infer(BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD), Items.BARRIER))
        );
        // TODO: Never open your inventory
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
        addGoal(advancementsGoal(id("get_advancements"), 15, 35));
        addGoal(obtainItemGoal(id("obtain_heart_of_the_sea"), Items.HEART_OF_THE_SEA)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BURIED_TREASURE, EnigmaticsBingoTags.SHIPWRECK)
        );
        addGoal(obtainItemGoal(id("obtain_dark_prismarine"), Items.DARK_PRISMARINE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BURIED_TREASURE,
                        EnigmaticsBingoTags.OCEAN_MONUMENT, EnigmaticsBingoTags.SHIPWRECK)
        );
        addGoal(potionGoal(id("obtain_potion_of_water_breathing"),
                Potions.WATER_BREATHING, Potions.LONG_WATER_BREATHING)
                .tags(EnigmaticsBingoTags.BURIED_TREASURE, EnigmaticsBingoTags.SHIPWRECK)
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
        addGoal(BingoGoal.builder(id("give_effect_to_other_team"))
                .criterion("give", GiveEffectToOtherTeamTrigger.TriggerInstance.anyEffect())
                .tags(EnigmaticsBingoTags.PVP, EnigmaticsBingoTags.GET_EFFECT, EnigmaticsBingoTags.STRAY,
                        EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.POTIONS)
                .icon(IndicatorIcon.infer(
                        CycleIcon.infer(BuiltInRegistries.MOB_EFFECT.holders().map(EffectIcon::of)),
                        Items.PLAYER_HEAD))
                .name(Component.translatable("enigmaticsbingogoals.goal.give_effect_to_other_team"))
        );
        addGoal(BingoGoal.builder(id("hit_other_team_with_snowball"))
                .criterion("hit", HitOtherTeamWithProjectileTrigger.TriggerInstance.ofType(EntityType.SNOWBALL))
                .tags(EnigmaticsBingoTags.PVP)
                .name(Component.translatable("enigmaticsbingogoals.goal.hit_player_with_snowball", EntityType.SNOWBALL.getDescription()))
                .icon(IndicatorIcon.infer(Items.SNOWBALL, Items.PLAYER_HEAD))
        );
        // TODO: Have a higher level than the enemy
        // TODO: Eat more unique foods than the enemy
        // TODO: Kill more unique mobs than the enemy
        // TODO: Kill more unique hostile mobs than the enemy
        // TODO: Kill more unique neutral mobs than the enemy
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
        addGoal(BingoGoal.builder(id("cure_zombie_villager"))
                .criterion("transform", CuredZombieVillagerTrigger.TriggerInstance.curedZombieVillager())
                .name(Component.translatable("enigmaticsbingogoals.goal.cure_zombie_villager"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.GOLDEN_APPLE, EnigmaticsBingoTags.IGLOO)
                .icon(IndicatorIcon.infer(EntityType.ZOMBIE_VILLAGER, ItemIcon.ofItem(Items.GOLDEN_APPLE)))
        );
        addGoal(numberOfEffectsGoal(id("get_some_effects_few"), 3, 5)
                .tags(EnigmaticsBingoTags.GOLDEN_APPLE, EnigmaticsBingoTags.PUFFER_FISH, EnigmaticsBingoTags.IGLOO,
                        EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.ANCIENT_CITY)
        );
        addGoal(numberOfEffectsGoal(id("get_some_effects_many"), 6, 10));
        addGoal(effectGoal(id("get_absorption"), MobEffects.ABSORPTION)
                .tags(EnigmaticsBingoTags.GOLDEN_APPLE, EnigmaticsBingoTags.IGLOO, EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(effectGoal(id("get_nausea"), MobEffects.CONFUSION)
                .tags(EnigmaticsBingoTags.PUFFER_FISH)
        );
        addGoal(effectGoal(id("get_poison"), MobEffects.POISON)
                .tags(EnigmaticsBingoTags.PUFFER_FISH, EnigmaticsBingoTags.POISON, EnigmaticsBingoTags.SUSPICIOUS_STEW,
                        EnigmaticsBingoTags.BEEHIVE, EnigmaticsBingoTags.MINESHAFT)
        );
        addGoal(effectGoal(id("get_weakness"), MobEffects.WEAKNESS)
                .tags(EnigmaticsBingoTags.WEAKNESS, EnigmaticsBingoTags.SUSPICIOUS_STEW, EnigmaticsBingoTags.IGLOO)
        );
        addGoal(effectGoal(id("get_slowness"), MobEffects.MOVEMENT_SLOWDOWN)
                .tags(EnigmaticsBingoTags.SLOWNESS)
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
        addGoal(effectGoal(id("get_mining_fatigue"), MobEffects.DIG_SLOWDOWN)
                .tags(EnigmaticsBingoTags.SATURATION, EnigmaticsBingoTags.OCEAN_MONUMENT)
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
        addGoal(obtainItemGoal(id("obtain_cake"), Items.CAKE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MILK, EnigmaticsBingoTags.CHICKEN)
        );
        addGoal(reachLevelsGoal(id("reach_levels"), 10, 35));
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
        // TODO: Deal 500 hearts of damage
        addGoal(obtainAllItemsFromTagGoal(id("obtain_all_wooden_tools"), EnigmaticsBingoItemTags.WOODEN_TOOLS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET, EnigmaticsBingoTags.OVERWORLD_ENTRY)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_material_tools"))
        );
        addGoal(obtainAllItemsFromTagGoal(id("obtain_all_stone_tools"), EnigmaticsBingoItemTags.STONE_TOOLS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET, EnigmaticsBingoTags.OVERWORLD_ENTRY)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_material_tools"))
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
        addGoal(dieToEntityGoal(id("die_to_iron_golem"), EntityType.IRON_GOLEM)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_iron_golem",
                        EntityType.IRON_GOLEM.getDescription()))
        );
        addGoal(dieToEntityGoal(id("die_to_bee"), EntityType.BEE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BEEHIVE)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_bee",
                        EntityType.BEE.getDescription()))
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
        addGoal(dieToEntityGoal(id("die_to_stray"), EntityType.STRAY)
                .tags(BingoTags.OVERWORLD)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_stray",
                        EntityType.STRAY.getDescription()))
        );
        addGoal(dieToDamageTypeGoal(id("die_to_anvil"), EnigmaticsBingoDamageTypeTags.ANVIL)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_anvil"))
                .icon(IndicatorIcon.infer(Items.ANVIL, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        addGoal(dieToDamageTypeGoal(id("die_to_stalactite"), EnigmaticsBingoDamageTypeTags.STALACTITE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO, EnigmaticsBingoTags.CAVING)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_stalactite"))
                .icon(IndicatorIcon.infer(Items.FIREWORK_ROCKET, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        // TODO: Die to a TNT Minecart
        addGoal(dieToDamageTypeGoal(id("die_to_fireworks"), EnigmaticsBingoDamageTypeTags.FIREWORKS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_fireworks"))
                .icon(IndicatorIcon.infer(Items.FIREWORK_ROCKET, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        addGoal(dieToDamageTypeGoal(id("die_to_magic"), EnigmaticsBingoDamageTypeTags.MAGIC)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO, EnigmaticsBingoTags.WITCH_HUT,
                        EnigmaticsBingoTags.OCEAN_MONUMENT, EnigmaticsBingoTags.INSTANT_DAMAGE,
                        EnigmaticsBingoTags.RAID, EnigmaticsBingoTags.OUTPOST, EnigmaticsBingoTags.WOODLAND_MANSION)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_magic"))
                .icon(IndicatorIcon.infer(EffectIcon.of(MobEffects.HARM), BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        // TODO: Die to falling off vines
        addGoal(breakBlockGoal(id("break_diamond_ore"), Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING)
        );
        addGoal(obtainAllItemsFromTagGoal(id("obtain_all_raw_ore_blocks"), EnigmaticsBingoItemTags.RAW_ORE_BLOCKS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_all_raw_ore_blocks",
                        Component.translatable(EnigmaticsBingoItemTags.RAW_ORE_BLOCKS.getTranslationKey())))
        );
        addGoal(obtainItemGoal(id("obtain_repeater"), Items.REPEATER)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING,
                        EnigmaticsBingoTags.REDSTONE, EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(obtainItemGoal(id("obtain_dispenser"), Items.DISPENSER)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING,
                        EnigmaticsBingoTags.REDSTONE)
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
        addGoal(obtainItemGoal(id("obtain_observer"), Items.OBSERVER)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.NETHER_ENTRY)
        );
        addGoal(obtainItemGoal(id("obtain_piston"), Items.PISTON)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(obtainItemGoal(id("obtain_redstone_lamp"), Items.REDSTONE_LAMP)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.ANCIENT_CITY)
        );
        addGoal(obtainItemGoal(id("obtain_comparator"), Items.COMPARATOR)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.ANCIENT_CITY)
        );
        addGoal(obtainItemGoal(id("obtain_daylight_detector"), Items.DAYLIGHT_DETECTOR)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.REDSTONE, EnigmaticsBingoTags.NETHER_ENTRY)
        );
        addGoal(breakBlockGoal(id("break_emerald_ore"), Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MOUNTAIN)
        );
        addGoal(killEntityGoal(id("kill_silverfish"), EntityType.SILVERFISH)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_silverfish", EntityType.SILVERFISH.getDescription()))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.STRONGHOLD, EnigmaticsBingoTags.MOUNTAIN,
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
        addGoal(tameAnimalGoal(id("tame_ocelot"), EntityType.OCELOT)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TAME_ANIMAL, EnigmaticsBingoTags.JUNGLE)
        );
        // TODO: Hatch a White Frog
        // TODO: Hatch an Orange Frog
        // TODO: Hatch a Green Frog
        addGoal(breedAnimalGoal(id("breed_pig"), EntityType.PIG)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE)
        );
        addGoal(breedAnimalGoal(id("breed_mule"), EntityType.MULE)
                .tags(BingoTags.OVERWORLD)
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
        addGoal(breedAnimalGoal(id("breed_rabbit"), EntityType.RABBIT)
                .tags(BingoTags.OVERWORLD)
        );
        addGoal(breedAnimalGoal(id("breed_strider"), EntityType.STRIDER)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.STRIDER)
        );
        addGoal(breedAnimalGoal(id("breed_hoglin"), EntityType.HOGLIN)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.CRIMSON_FOREST)
        );
        addGoal(breakBlockGoal(id("break_turtle_egg"), Blocks.TURTLE_EGG)
                .tags(BingoTags.OVERWORLD)
        );
        // TODO: Breed (6-15) unique mobs
        // TODO: Kill (10-25) unique Mobs
        // TODO: Kill (5-7) unique Neutral Mobs
        // TODO: Kill (7-10) unique Neutral Mobs
        // TODO: Kill (7-10) unique Hostile Mobs
        // TODO: Kill (11-15) unique Hostile Mobs
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
        addGoal(killEntityGoal(id("kill_endermite"), EntityType.ENDERMITE)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_endermite", EntityType.ENDERMITE.getDescription()))
                .tags(BingoTags.OVERWORLD, BingoTags.NETHER, BingoTags.END, BingoTags.VILLAGE,
                        EnigmaticsBingoTags.WARPED_FOREST, EnigmaticsBingoTags.NETHER_LATE)
        );
        addGoal(killEntityGoal(id("kill_zoglin"), EntityType.ZOGLIN)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_zoglin", EntityType.ZOGLIN.getDescription()))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.CRIMSON_FOREST)
        );
        addGoal(killEntityGoal(id("kill_snow_golem"), EntityType.SNOW_GOLEM)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_snow_golem", EntityType.SNOW_GOLEM.getDescription()))
                .tags(BingoTags.OVERWORLD)
        );
        addGoal(killEntityGoal(id("kill_elder_guardian"), EntityType.ELDER_GUARDIAN)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_elder_guardian", EntityType.ELDER_GUARDIAN.getDescription()))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OCEAN_MONUMENT)
                .icon(IndicatorIcon.infer(BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.ELDER_GUARDIAN), Items.NETHERITE_SWORD))
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
        // TODO: Kill 50 mobs
        // TODO: Kill 100 mobs
        // TODO: Kill 30 Arthropods
        // TODO: Kill 30 Undead Mobs
        // TODO: Kill 50 Undead Mobs
        // TODO: Kill 5 baby neutral/hostile mobs
        // TODO: Wash something in a Cauldron
        // TODO: Wear 4 different armor materials
        // TODO: Wear a full set of Diamond Armor
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
        // TODO: Grow huge crimson fungi in overworld
        // TODO: Grow huge warped fungi in overworld
        // TODO: Fill a Chiseled Bookshelf with books
        addGoal(advancementGoal(id("get_advancement_what_a_deal"),
                new ResourceLocation("minecraft", "adventure/trade"),
                        Component.translatable("advancements.adventure.trade.title"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.IGLOO, BingoTags.VILLAGE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.EMERALD), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
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
        addGoal(eatItemGoal(id("eat_rabbit_stew"), Items.RABBIT_STEW)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE, EnigmaticsBingoTags.STEW)
        );
        addGoal(obtainItemGoal(id("obtain_bell"), Items.BELL)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE)
        );
        addGoal(eatItemGoal(id("eat_poisonous_potato"), Items.POISONOUS_POTATO)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE, EnigmaticsBingoTags.POISON, EnigmaticsBingoTags.SHIPWRECK)
        );
        addGoal(obtainItemGoal(id("obtain_tropical_fish_bucket"), Items.TROPICAL_FISH_BUCKET)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BUCKET_WITH_MOB)
        );
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
                new ResourceLocation("minecraft", "adventure/play_jukebox_in_meadows"),
                Component.translatable("advancements.adventure.play_jukebox_in_meadows.title"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MUSIC_DISC, EnigmaticsBingoTags.ANCIENT_CITY,
                        EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.TRAIL_RUINS)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.JUKEBOX), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        // TODO: Equip Wolf Armor
        // TODO: Give an armor stand 4 pieces of armor
        // TODO: Wear a full set of Leather Armor
        // TODO: Wear a full set of Iron Armor
        // TODO: Wear a full set of Gold Armor
        addGoal(obtainSomeItemsFromTagGoal(id("obtain_some_saplings"), EnigmaticsBingoItemTags.SAPLINGS, 5, 7)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.PLANT_BATCH)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_saplings", 0,
                                Component.translatable(EnigmaticsBingoItemTags.SAPLINGS.getTranslationKey())),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(obtainSomeItemsFromTagGoal(id("obtain_some_small_flowers"), ItemTags.SMALL_FLOWERS, 5, 9)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY, EnigmaticsBingoTags.PLANT_BATCH)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_different_small_flowers", 0),
                        subber -> subber.sub("with.0", "count")
                )
        );
        // TODO: Obtain (5-10) bonemeal-able blocks
        addGoal(eatItemGoal(id("eat_glow_berries"), Items.GLOW_BERRIES)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.LUSH_CAVE, EnigmaticsBingoTags.ANCIENT_CITY)
        );
        addGoal(obtainItemGoal(id("obtain_flowering_azalea"), Items.FLOWERING_AZALEA)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.LUSH_CAVE)
        );
        // TODO: Use a Loom
        // TODO: Use a Cartography Table
        // TODO: Use a Flower Banner Pattern
        // TODO: Use a Masoned Banner Pattern
        // TODO: Use a Bordure Banner Pattern
        // TODO: Use a Skull Banner Pattern
        // TODO: Eat 7 unique foods
        // TODO: Eat 10 unique foods
        // TODO: Eat 15 unique foods
        // TODO: Eat 20 unique foods
        // TODO: Eat 25 unique foods
        addGoal(advancementGoal(id("get_advancement_sniper_duel"),
                new ResourceLocation("minecraft", "adventure/sniper_duel"),
                        Component.translatable("advancements.adventure.sniper_duel.title"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BOW)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.ARROW), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_bullseye"),
                new ResourceLocation("minecraft", "adventure/bullseye"),
                        Component.translatable("advancements.adventure.bullseye.title"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BOW)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.TARGET), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        // TODO: This boat does not have legs TOOLTIP: Use Carrot on a Stick to Ride a Pig into Lava
        // TODO: Use Carrot on a Stick to Ride a Pig
        addGoal(rideAbstractHorseWithSaddleGoal(id("ride_horse"), EntityType.HORSE)
                .name(Component.translatable("enigmaticsbingogoals.goal.ride_horse",
                        EntityType.HORSE.getDescription(), Items.SADDLE.getDescription()))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.SADDLE)
        );
        addGoal(advancementGoal(id("get_advancement_is_it_a_bird"),
                new ResourceLocation("minecraft", "adventure/spyglass_at_parrot"),
                        Component.translatable("advancements.adventure.spyglass_at_parrot.title"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.AMETHYST, EnigmaticsBingoTags.JUNGLE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.SPYGLASS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_is_it_a_balloon"),
                new ResourceLocation("minecraft", "adventure/spyglass_at_ghast"),
                        Component.translatable("advancements.adventure.spyglass_at_ghast.title"))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.AMETHYST, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.GHAST)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.SPYGLASS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_is_it_a_plane"),
                new ResourceLocation("minecraft", "adventure/spyglass_at_dragon"),
                        Component.translatable("advancements.adventure.spyglass_at_dragon.title"))
                .tags(BingoTags.END, EnigmaticsBingoTags.AMETHYST, EnigmaticsBingoTags.END_ENTRY)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.SPYGLASS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        // TODO: Get any Spyglass advancement
        // TODO: Sign a Book and Quill
        addGoal(obtainItemGoal(id("obtain_bookshelf"), Items.BOOKSHELF)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BOOK, EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(advancementGoal(id("get_advancement_hero_of_the_village"),
                new ResourceLocation("minecraft", "adventure/hero_of_the_village"),
                        Component.translatable("advancements.adventure.hero_of_the_village.title"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.OUTPOST, EnigmaticsBingoTags.RAID)
                .icon(new IndicatorIcon(new ItemIcon(BingoGoalGeneratorUtils.getOminousBanner(registries)), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_postmortal"),
                new ResourceLocation("minecraft", "adventure/totem_of_undying"),
                        Component.translatable("advancements.adventure.totem_of_undying.title"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.OUTPOST, EnigmaticsBingoTags.RAID)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.TOTEM_OF_UNDYING), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
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
        addGoal(obtainItemGoal(id("obtain_experience_bottle"), Items.EXPERIENCE_BOTTLE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OUTPOST, EnigmaticsBingoTags.ANCIENT_CITY,
                        BingoTags.VILLAGE)
        );
        addGoal(obtainItemGoal(id("obtain_sponge"), Items.SPONGE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OCEAN_MONUMENT)
        );
        addGoal(obtainSomeItemsFromTagGoal(id("obtain_some_trim_templates"), ItemTags.TRIM_TEMPLATES, 1, 3)
                .tags(BingoTags.OVERWORLD, BingoTags.NETHER, EnigmaticsBingoTags.RARE_COLLECTIBLE_BATCH
                        ,EnigmaticsBingoTags.TRAIL_RUINS)
                .name(
                        Component.translatable("enigmaticsbingogoals.goal.obtain_some_trim_templates", 0),
                        subber -> subber.sub("with.0", "count")
                )
        );
        addGoal(obtainAllItemsFromTagGoal(id("obtain_all_horse_armors"), EnigmaticsBingoItemTags.HORSE_ARMORS)
                .tags(BingoTags.OVERWORLD, BingoTags.NETHER, BingoTags.END, EnigmaticsBingoTags.RARE_COLLECTIBLE_BATCH,
                        EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.MINESHAFT, BingoTags.VILLAGE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_all_horse_armor",
                        Component.translatable(EnigmaticsBingoItemTags.HORSE_ARMORS.getTranslationKey())))
        );
        addGoal(advancementGoal(id("get_advancement_careful_restoration"),
                new ResourceLocation("minecraft", "adventure/craft_decorated_pot_using_only_sherds"),
                        Component.translatable("advancements.adventure.craft_decorated_pot_using_only_sherds.title"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TRAIL_RUINS, EnigmaticsBingoTags.RARE_COLLECTIBLE_BATCH)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.DECORATED_POT), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(obtainItemGoal(id("obtain_mud_brick_wall"), Items.MUD_BRICK_WALL)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WALL, EnigmaticsBingoTags.TRAIL_RUINS)
        );
        addGoal(obtainItemGoal(id("obtain_mossy_stone_brick_wall"), Items.MOSSY_STONE_BRICK_WALL)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WALL, EnigmaticsBingoTags.IGLOO
                        ,EnigmaticsBingoTags.LUSH_CAVE ,EnigmaticsBingoTags.JUNGLE)
        );
        addGoal(eatItemGoal(id("eat_suspicious_stew"), Items.SUSPICIOUS_STEW)
                .tags(BingoTags.END, EnigmaticsBingoTags.SUSPICIOUS_STEW, EnigmaticsBingoTags.SATURATION,
                        EnigmaticsBingoTags.WEAKNESS, EnigmaticsBingoTags.NIGHT_VISION, EnigmaticsBingoTags.LEAPING,
                        EnigmaticsBingoTags.POISON)
        );
        addGoal(obtainItemGoal(id("obtain_mossy_cobblestone_wall"), Items.MOSSY_COBBLESTONE_WALL)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WALL,  EnigmaticsBingoTags.LUSH_CAVE, EnigmaticsBingoTags.JUNGLE)
        );
        addGoal(obtainAllItemsFromTagGoal(id("obtain_all_iron_tools"), EnigmaticsBingoItemTags.IRON_TOOLS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_material_tools"))
        );
        addGoal(obtainAllItemsFromTagGoal(id("obtain_all_golden_tools"), EnigmaticsBingoItemTags.GOLDEN_TOOLS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_material_tools"))
        );
        addGoal(obtainAllItemsFromTagGoal(id("obtain_all_diamond_tools"), EnigmaticsBingoItemTags.DIAMOND_TOOLS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_material_tools"))
        );
        addGoal(obtainItemGoal(id("obtain_cobweb"), Items.COBWEB)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.IGLOO,
                        EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        // TODO: Rename a sheep to "jeb_"
        addGoal(breakBlockGoal(id("break_mob_spawner"), Blocks.SPAWNER)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MINESHAFT, EnigmaticsBingoTags.FORTRESS,
                        EnigmaticsBingoTags.STRONGHOLD, EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(obtainItemGoal(id("obtain_orange_glazed_terracotta"), Items.ORANGE_GLAZED_TERRACOTTA)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TRAIL_RUINS, EnigmaticsBingoTags.TERRACOTTA)
        );
        addGoal(obtainItemGoal(id("obtain_green_glazed_terracotta"), Items.GREEN_GLAZED_TERRACOTTA)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.IGLOO, EnigmaticsBingoTags.TERRACOTTA)
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
        addGoal(obtainItemGoal(id("obtain_lime_glazed_terracotta"), Items.LIME_GLAZED_TERRACOTTA)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.TERRACOTTA)
        );
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
        addGoal(obtainItemGoal(id("obtain_sticky_piston"), Items.STICKY_PISTON)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.SLIME, EnigmaticsBingoTags.JUNGLE, EnigmaticsBingoTags.ANCIENT_CITY)
        );
        addGoal(obtainItemGoal(id("obtain_honey_block"), Items.HONEY_BLOCK)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BEEHIVE)
        );
        addGoal(obtainItemGoal(id("obtain_honey_bottle"), Items.HONEY_BOTTLE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BEEHIVE)
        );
        addGoal(obtainSomeItemsFromTagGoal(id("obtain_colored_candle"), ItemTags.CANDLES, 1, 1)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.BEEHIVE, EnigmaticsBingoTags.ANCIENT_CITY)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_colored_candle"))
        );
        addGoal(obtainItemGoal(id("obtain_scaffolding"), Items.SCAFFOLDING)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.JUNGLE)
        );
        addGoal(eatItemGoal(id("eat_cookie"), Items.COOKIE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.JUNGLE)
        );
        // TODO: Use an Anvil
        addGoal(advancementGoal(id("get_advancement_enchanter"),
                new ResourceLocation("minecraft", "story/enchant_item"),
                        Component.translatable("advancements.story.enchant_item.title"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOODLAND_MANSION)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.ENCHANTED_BOOK), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
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
        // TODO: Visit 15-25 unique Biomes
        // TODO: Visit 10-20 unique Overworld Biomes
        // TODO: Show an Egg to the World | Tooltip: Visit 10-20 biomes with an Egg in your off-hand
        // TODO: Anger a Zombified Piglin
        addGoal(advancementGoal(id("get_advancement_we_need_to_go_deeper"),
                new ResourceLocation("minecraft", "nether/enter_the_nether"),
                        Component.translatable("advancements.story.enter_the_nether.title"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOODLAND_MANSION, EnigmaticsBingoTags.NETHER_ENTRY)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.FLINT_AND_STEEL), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        // TODO: Anger a Zombified Piglin
        addGoal(effectGoal(id("get_glowing"), MobEffects.GLOWING)
                .tags(EnigmaticsBingoTags.NETHER_ENTRY)
        );
        addGoal(advancementGoal(id("get_advancement_oh_shiny"),
                new ResourceLocation("minecraft", "nether/distract_piglin"),
                        Component.translatable("advancements.nether.distract_piglin.title"))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.BARTERING)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.GOLD_INGOT), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(dieToDamageTypeGoal(id("die_to_intentional_game_design"), EnigmaticsBingoDamageTypeTags.INTENTIONAL_GAME_DESIGN)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.DIE_TO)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_intentional_game_design"))
                .icon(IndicatorIcon.infer(Items.RED_BED, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        // TODO: Apply Glow Ink to a Crimson Sign
        // TODO: Apply Glow Ink to a Warped Sign
        addGoal(killEntityGoal(id("kill_ghast"), EntityType.GHAST)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_ghast", EntityType.GHAST.getDescription()))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.GHAST)
                .icon(IndicatorIcon.infer(BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.GHAST), Items.NETHERITE_SWORD))
        );
        addGoal(advancementGoal(id("get_advancement_a_terrible_fortress"),
                new ResourceLocation("minecraft", "nether/find_fortress"),
                        Component.translatable("advancements.nether.find_fortress.title"))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.NETHER_EXPLORE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.NETHER_BRICKS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_those_were_the_days"),
                new ResourceLocation("minecraft", "nether/find_bastion"),
                        Component.translatable("advancements.nether.find_bastion.title"))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.NETHER_EXPLORE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.POLISHED_BLACKSTONE_BRICKS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_not_quite_nine_lives"),
                new ResourceLocation("minecraft", "nether/charge_respawn_anchor"),
                        Component.translatable("advancements.nether.charge_respawn_anchor.title"))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_ENTRY)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.RESPAWN_ANCHOR), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_hot_tourist_destinations"),
                new ResourceLocation("minecraft", "nether/explore_nether"),
                        Component.translatable("advancements.nether.explore_nether.title"))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BIOMES, EnigmaticsBingoTags.NETHER_EXPLORE,
                        EnigmaticsBingoTags.NETHER_LATE)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.NETHERITE_BOOTS), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_return_to_sender"),
                new ResourceLocation("minecraft", "nether/return_to_sender"),
                        Component.translatable("advancements.nether.return_to_sender.title"))
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.GHAST)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.FIRE_CHARGE), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_this_boat_has_legs"),
                new ResourceLocation("minecraft", "nether/ride_strider"),
                        Component.translatable("advancements.nether.ride_strider.title"))
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
        addGoal(potionGoal(id("obtain_potion_of_leaping"),
                Potions.LEAPING, Potions.LONG_LEAPING, Potions.STRONG_LEAPING)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.BLAZE_POWDER, EnigmaticsBingoTags.FORTRESS)
        );
        addGoal(potionGoal(id("obtain_potion_of_swiftness"),
                Potions.SWIFTNESS, Potions.LONG_SWIFTNESS, Potions.STRONG_SWIFTNESS)
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
        addGoal(obtainItemGoal(id("obtain_wither_skeleton_skull"), Items.WITHER_SKELETON_SKULL)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.WITHER_SKULL, EnigmaticsBingoTags.FORTRESS)
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
                new ResourceLocation("minecraft", "story/follow_ender_eye"),
                Component.translatable("advancements.story.follow_ender_eye.title"))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.END_ENTRY, EnigmaticsBingoTags.STRONGHOLD)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.ENDER_EYE), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_the_end"),
                new ResourceLocation("minecraft", "end/root"),
                        Component.translatable("advancements.end.root.title"))
                .tags(BingoTags.END, EnigmaticsBingoTags.END_ENTRY)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.END_STONE), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(killEntityGoal(id("kill_ender_dragon"), EntityType.ENDER_DRAGON)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_ender_dragon", EntityType.ENDER_DRAGON.getDescription()))
                .tags(BingoTags.END, EnigmaticsBingoTags.END_ENTRY)
                .icon(IndicatorIcon.infer(Items.DRAGON_HEAD, Items.NETHERITE_SWORD))
        );
        addGoal(obtainItemGoal(id("obtain_dragon_egg"), Items.DRAGON_EGG)
                .tags(BingoTags.END, EnigmaticsBingoTags.END_ENTRY)
        );
        addGoal(dieToDamageTypeGoal(id("fall_out_of_world"), DamageTypeTags.ALWAYS_MOST_SIGNIFICANT_FALL)
                .tags(BingoTags.END, EnigmaticsBingoTags.END_ENTRY, EnigmaticsBingoTags.DIE_TO)
                .name(Component.translatable("enigmaticsbingogoals.goal.fall_out_of_world"))
                .icon(IndicatorIcon.infer(Blocks.END_PORTAL, BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.DEAD)))
        );
        addGoal(obtainItemGoal(id("obtain_lingering_potion"), Items.LINGERING_POTION)
                .tags(BingoTags.END, EnigmaticsBingoTags.POTIONS, EnigmaticsBingoTags.END_ENTRY)
        );
        addGoal(advancementGoal(id("get_advancement_the_city_at_the_end_of_the_game"),
                new ResourceLocation("minecraft", "end/find_end_city"),
                        Component.translatable("advancements.end.find_end_city.title"))
                .tags(BingoTags.END, EnigmaticsBingoTags.END_PROGRESS)
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.PURPUR_PILLAR), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)))
        );
        addGoal(advancementGoal(id("get_advancement_great_view_from_up_here"),
                new ResourceLocation("minecraft", "end/levitate"),
                        Component.translatable("advancements.end.levitate.title"))
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
