package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoItemTags;
import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoTags;
import de.rasmusantons.enigmaticsbingogoals.conditions.FullUniqueInventoryCondition;
import de.rasmusantons.enigmaticsbingogoals.conditions.KillEnemyPlayerCondition;
import de.rasmusantons.enigmaticsbingogoals.triggers.EmptyHungerTrigger;
import de.rasmusantons.enigmaticsbingogoals.triggers.PlayMusicToOtherTeamTrigger;
import de.rasmusantons.enigmaticsbingogoals.triggers.WearPumpkinTrigger;
import io.github.gaming32.bingo.conditions.HasAnyEffectCondition;
import io.github.gaming32.bingo.data.BingoGoal;
import io.github.gaming32.bingo.data.BingoTags;
import io.github.gaming32.bingo.data.icons.BlockIcon;
import io.github.gaming32.bingo.data.icons.EffectIcon;
import io.github.gaming32.bingo.data.icons.IndicatorIcon;
import io.github.gaming32.bingo.data.icons.ItemIcon;
import io.github.gaming32.bingo.triggers.BingoTriggers;
import io.github.gaming32.bingo.triggers.DeathTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;

import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;

public class EnigmaticsGoalProvider extends EnigmaticsDifficultyGoalProvider {
    public EnigmaticsGoalProvider(Consumer<BingoGoal.Holder> goalAdder) {
        super(EnigmaticsDifficultyGoalProvider.ENIGMATICS, goalAdder);
    }

    @Override
    public void addGoals(HolderLookup.Provider provider) {
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
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.PLAYER_HEAD), ItemIcon.ofItem(Items.BARRIER)))
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
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.JUKEBOX), ItemIcon.ofItem(Items.PLAYER_HEAD)))
        );
        // TODO: Hit an enemy player with a snowball
        // TODO: Have a higher level than the enemy
        // TODO: Eat more unique foods than the enemy
        // TODO: Kill more unique mobs than the enemy
        // TODO: Kill more unique hostile mobs than the enemy
        // TODO: Kill more unique neutral mobs than the enemy
        addGoal(BingoGoal.builder(id("empty_hunger"))
                .criterion("empty_hunger", EmptyHungerTrigger.TriggerInstance.emptyHunger())
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.OVERWORLD_ENTRY, EnigmaticsBingoTags.COVER_DISTANCE)
                .name(Component.translatable("empty_hunger"))
                .icon(new IndicatorIcon(EffectIcon.of(MobEffects.HUNGER), ItemIcon.ofItem(Items.CLOCK)))
        );
        // TODO: Sprint for 1k meters
        // TODO: Crouch for 500 meters
        addGoal(BingoGoal.builder(id("reach_world_center"))
                .criterion("reach", PlayerTrigger.TriggerInstance.located(
                        LocationPredicate.Builder.location()
                                .setX(MinMaxBounds.Doubles.between(0.0, 1.0))
                                .setZ(MinMaxBounds.Doubles.between(0.0, 1.0))
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
        // TODO: Get 3 status effects concurrently
        // TODO: Get 6 status effects concurrently
        // TODO: Get 10 status effects concurrently
        addGoal(effectGoal(id("get_absorption"), MobEffects.ABSORPTION)
                .tags(EnigmaticsBingoTags.GOLDEN_APPLE, EnigmaticsBingoTags.GET_EFFECT,
                        EnigmaticsBingoTags.IGLOO, EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(effectGoal(id("get_nausea"), MobEffects.CONFUSION)
                .tags(EnigmaticsBingoTags.PUFFER_FISH, EnigmaticsBingoTags.GET_EFFECT)
        );
        addGoal(effectGoal(id("get_poison"), MobEffects.POISON)
                .tags(EnigmaticsBingoTags.PUFFER_FISH, EnigmaticsBingoTags.GET_EFFECT,
                        EnigmaticsBingoTags.POISON, EnigmaticsBingoTags.SUSPICIOUS_STEW,
                        EnigmaticsBingoTags.BEEHIVE, EnigmaticsBingoTags.MINESHAFT)
        );
        addGoal(effectGoal(id("get_weakness"), MobEffects.WEAKNESS)
                .tags(EnigmaticsBingoTags.GET_EFFECT, EnigmaticsBingoTags.WEAKNESS,
                        EnigmaticsBingoTags.SUSPICIOUS_STEW, EnigmaticsBingoTags.IGLOO)
        );
        addGoal(effectGoal(id("get_slowness"), MobEffects.MOVEMENT_SLOWDOWN)
                .tags(EnigmaticsBingoTags.GET_EFFECT, EnigmaticsBingoTags.SLOWNESS)
        );
        addGoal(effectGoal(id("get_leaping"), MobEffects.JUMP)
                .tags(EnigmaticsBingoTags.GET_EFFECT, EnigmaticsBingoTags.LEAPING,
                        EnigmaticsBingoTags.SUSPICIOUS_STEW)
        );
        addGoal(effectGoal(id("get_blindness"), MobEffects.BLINDNESS)
                .tags(EnigmaticsBingoTags.GET_EFFECT, EnigmaticsBingoTags.SUSPICIOUS_STEW)
        );
        addGoal(effectGoal(id("get_saturation"), MobEffects.SATURATION)
                .tags(EnigmaticsBingoTags.GET_EFFECT, EnigmaticsBingoTags.SATURATION,
                        EnigmaticsBingoTags.SUSPICIOUS_STEW)
        );
        addGoal(effectGoal(id("get_mining_fatigue"), MobEffects.DIG_SLOWDOWN)
                .tags(EnigmaticsBingoTags.GET_EFFECT, EnigmaticsBingoTags.SATURATION,
                        EnigmaticsBingoTags.OCEAN_MONUMENT)
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
                .icon(ItemIcon.ofItem(Items.MILK_BUCKET))
        );
        addGoal(obtainItemGoal(id("obtain_cake"), Items.CAKE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MILK, EnigmaticsBingoTags.CHICKEN)
        );
        // TODO: Reach level 15-40
        // TODO: Reach level
        // TODO: Fill up a Composter
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
        addGoal(obtainAllItemsFromTag(id("obtain_all_wooden_tools"), EnigmaticsBingoItemTags.WOODEN_TOOLS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET,  EnigmaticsBingoTags.OVERWORLD_ENTRY)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_wooden_tools"))
        );
        addGoal(obtainAllItemsFromTag(id("obtain_all_stone_tools"), EnigmaticsBingoItemTags.STONE_TOOLS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET, EnigmaticsBingoTags.OVERWORLD_ENTRY)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_stone_tools"))
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
        // TODO: Hatch a Chicken from an Egg
        // TODO: Breed a Chicken
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
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_llama",
                        EntityType.LLAMA.getDescription()))
        );
        addGoal(dieToEntityGoal(id("die_to_iron_golem"), EntityType.IRON_GOLEM)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE, EnigmaticsBingoTags.DIE_TO)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_llama",
                        EntityType.IRON_GOLEM.getDescription()))
        );
        addGoal(dieToEntityGoal(id("die_to_bee"), EntityType.BEE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO, EnigmaticsBingoTags.BEEHIVE)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_llama",
                        EntityType.BEE.getDescription()))
        );
        addGoal(dieToEntityGoal(id("die_to_dolphin"), EntityType.DOLPHIN)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_llama",
                        EntityType.DOLPHIN.getDescription()))
        );
        addGoal(dieToEntityGoal(id("die_to_goat"), EntityType.GOAT)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO, EnigmaticsBingoTags.MOUNTAIN)
                .name(Component.translatable("enigmaticsbingogoals.goal.die_to_llama",
                        EntityType.GOAT.getDescription()))
        );
        // TODO: Die to an Anvil
        // TODO: Die to a Stalactite
        // TODO: Die to a TNT Minecart
        // TODO: Die to a Firework
        // TODO: Die to Magic
        // TODO: Die to falling off vines
        addGoal(breakBlockGoal(id("break_diamond_ore"), Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING)
        );
        addGoal(obtainAllItemsFromTag(id("obtain_all_raw_ore_blocks"), EnigmaticsBingoItemTags.RAW_ORE_BLOCKS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_all_raw_ore_blocks"))
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
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.NETHER_ENTRY)
        );
        addGoal(obtainItemGoal(id("obtain_piston"), Items.PISTON)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.WOODLAND_MANSION)
        );
        addGoal(obtainItemGoal(id("obtain_sticky_piston"), Items.STICKY_PISTON)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.SLIME, EnigmaticsBingoTags.JUNGLE, EnigmaticsBingoTags.ANCIENT_CITY)
        );
        addGoal(obtainItemGoal(id("obtain_redstone_lamp"), Items.REDSTONE_LAMP)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.ANCIENT_CITY)
        );
        addGoal(obtainItemGoal(id("obtain_comparator"), Items.COMPARATOR)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CAVING, EnigmaticsBingoTags.REDSTONE,
                        EnigmaticsBingoTags.NETHER_ENTRY, EnigmaticsBingoTags.ANCIENT_CITY)
        );
        addGoal(breakBlockGoal(id("break_emerald_ore"), Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.MOUNTAIN)
        );
        // TODO: Kill a Silverfish
        // TODO: Obtain a Bucket of Powdered Snow
        // TODO: Tame a Cat
        // TODO: Tame a Wolf
        // TODO: Tame a Parrot
        // TODO: Gain an Ocelot's trust
        // TODO: Breed a Frog
        // TODO: Breed a Pig
        // TODO: Breed a Mule
        // TODO: Breed a Fox
        // TODO: Breed a Armadillo
        // TODO: Breed a Horse
        // TODO: Breed an Axolotl
        // TODO: Breed a Rabbit
        // TODO: Breed a Strider
        // TODO: Breed a Hoglin
        // TODO: Break a Turtle Egg
        // TODO: Breed (6-15) unique mobs
        // TODO: Kill (10-25) unique Mobs
        // TODO: Kill (5-7) unique Neutral Mobs
        // TODO: Kill (7-10) unique Neutral Mobs
        // TODO: Kill (7-10) unique Hostile Mobs
        // TODO: Kill (11-15) unique Hostile Mobs
        // TODO: Kill a Witch
        // TODO: Kill a Vindicator
        // TODO: Kill a Zombie Villager
        // TODO: Kill an Endermite
        // TODO: Kill a Zoglin
        // TODO: Kill a Snow Golem
        // TODO: Kill an Elder Guardian
        // TODO: Kill 50 mobs
        // TODO: Kill 100 mobs
        // TODO: Kill 30 Arthropods
        // TODO: Kill 30 Undead Mobs
        // TODO: Kill 50 Undead Mobs
        // TODO: Kill 5 baby neutral/hostile mobs
        // TODO: Wash something in a Cauldron
        // TODO: Wear 4 different armor materials
        // TODO: Wear a full set of Diamond Armor
        // TODO: Obtain a Grass Block
        // TODO: Obtain a Mushroom Stem
        // TODO: Obtain a Crimson Nylium
        // TODO: Obtain a Warped Nylium
        // TODO: Grow huge crimson fungi in overworld
        // TODO: Grow huge warped fungi in overworld
        // TODO: Fill a Chiseled Bookshelf with books
        // TODO: Trade with a Villager
        // TODO: Obtain Chain armor
        // TODO: Obtain 4 different types of seeds
        addGoal(eatItemGoal(id("eat_beetroot_soup"), Items.BEETROOT_SOUP)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE, EnigmaticsBingoTags.STEW)
        );
        addGoal(eatItemGoal(id("eat_rabbit_stew"), Items.RABBIT_STEW)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE, EnigmaticsBingoTags.STEW)
        );
        // TODO: Obtain a Bell
        addGoal(eatItemGoal(id("eat_poisonous_potato"), Items.POISONOUS_POTATO)
                .tags(BingoTags.OVERWORLD, BingoTags.VILLAGE, EnigmaticsBingoTags.POISON, EnigmaticsBingoTags.SHIPWRECK)
        );
        // TODO: Obtain a Tropical Fish in a Bucket
        // TODO: Obtain a Tadpole in a Bucket
        // TODO: Obtain 4 unique Music Discs
        // TODO: Listen to a Jukebox
        // TODO: Equip Wolf Armor
        // TODO: Give an armor stand 4 pieces of armor
        // TODO: Wear a full set of Leather Armor
        // TODO: Wear a full set of Iron Armor
        // TODO: Wear a full set of Gold Armor
        // TODO: Obtain 5 unique saplings
        // TODO: Obtain (5-8) unique flowers
        // TODO: Obtain (5-10) bonemeal-able blocks
        // TODO: Obtain a Flowering Azalea
        addGoal(eatItemGoal(id("eat_glow_berries"), Items.GLOW_BERRIES)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.LUSH_CAVE, EnigmaticsBingoTags.ANCIENT_CITY)
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
        // TODO: Get the "Sniper Duel" advancement
        // TODO: Get the "Bullseye" advancement
        // TODO: Ride a Pig
        // TODO: Ride a Horse
        // TODO: Ride a Strider
        // TODO: Look at a Parrot through a spy glass
        // TODO: Look at a Ghast through a spy glass
        // TODO: Look at the Ender Dragon through a spy glass
        // TODO: Get any Spyglass advancement
        // TODO: Sign a Book and Quill
        // TODO: Obtain a Bookshelf
        // TODO: Win a raid
        // TODO: Use a Totem of Undying
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
        // TODO: Obtain a Bottle O' Enchanting
        // TODO: Obtain Sponge
        // TODO: Obtain 1 Armor Trim
        // TODO: Obtain 2 Armor Trim
        // TODO: Obtain 3 Armor Trim
        addGoal(obtainAllItemsFromTag(id("obtain_all_horse_armors"), EnigmaticsBingoItemTags.HORSE_ARMORS)
                .tags(BingoTags.OVERWORLD, BingoTags.NETHER, BingoTags.END, EnigmaticsBingoTags.RARE_COLLECTIBLE_BATCH,
                        EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.MINESHAFT, BingoTags.VILLAGE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_all_horse_armors"))
        );
        // TODO: Make a pot out of 4 Pottery Sherds
        // TODO: Obtain a Mud Brick Wall
        // TODO: Obtain a Mossy Stone Brick Wall
        // TODO: Obtain a Mossy Cobblestone Brick Wall
        addGoal(eatItemGoal(id("eat_suspicious_stew"), Items.SUSPICIOUS_STEW)
                .tags(BingoTags.END, EnigmaticsBingoTags.SUSPICIOUS_STEW, EnigmaticsBingoTags.SATURATION,
                        EnigmaticsBingoTags.WEAKNESS, EnigmaticsBingoTags.NIGHT_VISION, EnigmaticsBingoTags.LEAPING,
                        EnigmaticsBingoTags.POISON)
        );
        addGoal(obtainAllItemsFromTag(id("obtain_all_iron_tools"), EnigmaticsBingoItemTags.IRON_TOOLS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_iron_tools"))
        );
        addGoal(obtainAllItemsFromTag(id("obtain_all_golden_tools"), EnigmaticsBingoItemTags.GOLDEN_TOOLS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_golden_tools"))
        );
        addGoal(obtainAllItemsFromTag(id("obtain_all_diamond_tools"), EnigmaticsBingoItemTags.DIAMOND_TOOLS)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.FULL_TOOL_SET)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_full_set_of_diamond_tools"))
        );
        // TODO: Obtain a Potion of Water Breathing
        // TODO: Obtain Cobweb
        // TODO: Rename a sheep to "jeb_"
        // TODO: Break a Mob Spawner
        // TODO: Obtain Orange Glazed Terracotta
        // TODO: Obtain Green Glazed Terracotta
        // TODO: Obtain Blue Glazed Terracotta
        // TODO: Obtain Black Glazed Terracotta
        // TODO: Obtain Gray Glazed Terracotta
        // TODO: Obtain White Glazed Terracotta
        // TODO: Obtain Lime Glazed Terracotta
        // TODO: Obtain Cyan Glazed Terracotta
        // TODO: Obtain 9 different colors of Terracotta
        // TODO: Obtain a Slime Block
        // TODO: Obtain a Sticky Piston
        // TODO: Obtain a Honey Block
        // TODO: Obtain Honey Bottle
        // TODO: Obtain a colored candle
        // TODO: Obtain a Scaffolding
        // TODO: Eat a Cookie
        // TODO: Use an Anvil
        // TODO: Enchant an item
        // TODO: Die to Anvil
        addGoal(obtainItemGoal(id("obtain_stack_of_red_concrete"), Items.RED_CONCRETE, 64, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CONCRETE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.RED_CONCRETE.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_yellow_concrete"), Items.YELLOW_CONCRETE, 64, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CONCRETE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.YELLOW_CONCRETE.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_orange_concrete"), Items.ORANGE_CONCRETE, 64, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CONCRETE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.ORANGE_CONCRETE.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_black_concrete"), Items.BLACK_CONCRETE, 64, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CONCRETE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.BLACK_CONCRETE.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_white_concrete"), Items.WHITE_CONCRETE, 64, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CONCRETE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.WHITE_CONCRETE.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_gray_concrete"), Items.GRAY_CONCRETE, 64, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CONCRETE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.GRAY_CONCRETE.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_light_gray_concrete"), Items.LIGHT_GRAY_CONCRETE, 64, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CONCRETE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.LIGHT_GRAY_CONCRETE.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_pink_concrete"), Items.PINK_CONCRETE, 64, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CONCRETE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.PINK_CONCRETE.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_magenta_concrete"), Items.MAGENTA_CONCRETE, 64, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CONCRETE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.MAGENTA_CONCRETE.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_blue_concrete"), Items.BLUE_CONCRETE, 64, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CONCRETE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.BLUE_CONCRETE.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_purple_concrete"), Items.PURPLE_CONCRETE, 64, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.CONCRETE)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.PURPLE_CONCRETE.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_green_wool"), Items.GREEN_WOOL, 64, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOOL, EnigmaticsBingoTags.IGLOO)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.GREEN_WOOL.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_red_wool"), Items.RED_WOOL, 64, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOOL)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.RED_WOOL.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_yellow_wool"), Items.YELLOW_WOOL, 64, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOOL)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.YELLOW_WOOL.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_orange_wool"), Items.ORANGE_WOOL, 64, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOOL)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.ORANGE_WOOL.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_black_wool"), Items.BLACK_WOOL, 64, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOOL)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.BLACK_WOOL.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_white_wool"), Items.WHITE_WOOL, 64, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOOL)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.WHITE_WOOL.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_gray_wool"), Items.GRAY_WOOL, 64, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOOL, EnigmaticsBingoTags.ANCIENT_CITY)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.GRAY_WOOL.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_light_gray_wool"), Items.LIGHT_GRAY_WOOL, 64, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOOL)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.LIGHT_GRAY_WOOL.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_pink_wool"), Items.PINK_WOOL, 64, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOOL)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.PINK_WOOL.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_magenta_wool"), Items.MAGENTA_WOOL, 64, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOOL)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.MAGENTA_WOOL.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_blue_wool"), Items.BLUE_WOOL, 64, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOOL)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.BLUE_WOOL.getDescription()))
        );
        addGoal(obtainItemGoal(id("obtain_stack_of_purple_wool"), Items.PURPLE_WOOL, 64, 64)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.WOOL)
                .name(Component.translatable("enigmaticsbingogoals.goal.obtain_stack_of",
                        Items.PURPLE_WOOL.getDescription()))
        );
        // TODO: Reach the Nether
        // TODO: Anger a Zombified Piglin
        // TODO: Get Glowing
        // TODO: Get the "Oooh, shiny!" advancement
        // TODO: Die to Intentional Game Design
        // TODO: Apply Glow Ink to a Crimson Sign
        // TODO: Apply Glow Ink to a Warped Sign
        // TODO: Kill a Ghast
        // TODO: Find a Fortress
        // TODO: Find a Bastion
        // TODO: Fully charge a Respawn Anchor
        // TODO: Get the "Hot Tourist Destinations" advancement
        // TODO: Get the "Return to Sender" advancement
        // TODO: Get the "This Boat Has Legs" advancement
        // TODO: Obtain an End Crystal
        // TODO: Obtain an Eye of Ender
        // TODO: Obtain an Ender Chest
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
        // TODO: Obtain a Wither Skeleton Skull
        // TODO: Spawn a Wither
        // TODO: Obtain a Wither Star
        // TODO: Obtain (6-10) Fire Charges
        // TODO: Obtain a Soul Lantern
        // TODO: Obtain a Lodestone
        // TODO: Obtain Netherite Scrap
        // TODO: Obtain a Netherite Ingot
        // TODO: Reach the end
        // TODO: Kill the Dragon
        // TODO: Obtain a Dragon Egg
        // TODO: Fall in the void
        // TODO: Obtain a Lingering Potion
        // TODO: Reach an End City
        // TODO: Get the "Great View From Up Here" advancement
        // TODO: Obtain an Elytra
        // TODO: Obtain a Dragon Head
        addGoal(eatItemGoal(id("eat_chorus_fruit"), Items.CHORUS_FRUIT)
                .tags(BingoTags.END, EnigmaticsBingoTags.END_PROGRESS)
        );
        // TODO: Craft a Purpur Block
    }
}
