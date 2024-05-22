package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoTags;
import de.rasmusantons.enigmaticsbingogoals.conditions.FullUniqueInventoryCondition;
import de.rasmusantons.enigmaticsbingogoals.conditions.KillEnemyPlayerCondition;
import de.rasmusantons.enigmaticsbingogoals.triggers.PlayMusicToOtherTeamTrigger;
import de.rasmusantons.enigmaticsbingogoals.triggers.WearPumpkinTrigger;
import io.github.gaming32.bingo.data.BingoGoal;
import io.github.gaming32.bingo.data.BingoTags;
import io.github.gaming32.bingo.data.icons.*;
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
                .name(Component.literal("Never obtain crafting table"))
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.CRAFTING_TABLE), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(BingoGoal.builder(id("never_seeds"))
                .criterion("obtain", InventoryChangeTrigger.TriggerInstance.hasItems(Items.WHEAT_SEEDS))
                .tags(
                        BingoTags.NEVER,
                        EnigmaticsBingoTags.SEEDS
                )
                .name(Component.literal("Never obtain wheat seeds"))
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.WHEAT_SEEDS), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(BingoGoal.builder(id("never_touch_water"))
                .criterion("touch", EnterBlockTrigger.TriggerInstance.entersBlock(Blocks.WATER))
                .tags(BingoTags.NEVER)
                .name(Component.literal("Never touch water"))
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.WATER_BUCKET), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(BingoGoal.builder(id("never_damage"))
                .criterion("damage", EntityHurtPlayerTrigger.TriggerInstance.entityHurtPlayer())
                .tags(BingoTags.NEVER, EnigmaticsBingoTags.NEVER_TAKE_DAMAGE)
                .name(Component.literal("Never take damage"))
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
                .name(Component.literal("Never take fall damage"))
                .icon(new IndicatorIcon(EffectIcon.of(MobEffects.HARM), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(BingoGoal.builder(id("never_fire_damage"))
                .criterion("damage", EntityHurtPlayerTrigger.TriggerInstance.entityHurtPlayer(
                        DamagePredicate.Builder.damageInstance().type(
                                DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(DamageTypeTags.IS_FIRE))
                        )
                ))
                .tags(BingoTags.NEVER, EnigmaticsBingoTags.NEVER_TAKE_DAMAGE)
                .name(Component.literal("Never take fire damage"))
                .icon(new IndicatorIcon(BlockIcon.ofBlock(Blocks.FIRE), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(neverLevelsGoal(id("never_levels"), 1, 3));
        addGoal(BingoGoal.builder(id("never_die"))
                .criterion("die", BingoTriggers.DEATH.get().createCriterion(
                        DeathTrigger.TriggerInstance.death(null)
                ))
                .tags(BingoTags.NEVER, EnigmaticsBingoTags.NEVER_TAKE_DAMAGE, EnigmaticsBingoTags.PLAYER_KILL)
                .name(Component.literal("Never die"))
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
                .name(Component.literal("Kill an enemy player"))
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
        addGoal(potionGoal(id("obtain_potion_of_water_breathing"), Potions.WATER_BREATHING, Potions.LONG_WATER_BREATHING)
                .tags(EnigmaticsBingoTags.POTIONS, EnigmaticsBingoTags.BURIED_TREASURE, EnigmaticsBingoTags.SHIPWRECK)
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
                .name(Component.literal("Make the enemy listen to a jukebox"))
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.JUKEBOX), ItemIcon.ofItem(Items.PLAYER_HEAD)))
        );
        // TODO: Hit an enemy player with a snowball
        // TODO: Have a higher level than the enemy
        // TODO: Eat more unique foods than the enemy
        // TODO: Kill more unique mobs than the enemy
        // TODO: Kill more unique hostile mobs than the enemy
        // TODO: Kill more unique neutral mobs than the enemy
        // TODO: Take hunger damage
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
                .name(Component.literal("Reach the world center"))
                .icon(ItemIcon.ofItem(Items.COMPASS))
        );
        // TODO: Cure a zombie villager
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
        // TODO: Remove a status effect with a Milk Bucket
        // TODO: Craft a Cake
        // TODO: Reach level 15-40
        // TODO: Reach level
        // TODO: Use a Composter
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
                .name(Component.literal("Fill your inventory with unique items"))
                .icon(ItemIcon.ofItem(Items.CHEST))
        );
        // TODO: Deal 500 hearts of damage
        // TODO: Get a full set of Wooden tools
        // TODO: Get a full set of Stone tools
        addGoal(BingoGoal.builder(id("stand_on_bedrock"))
                .criterion("stand_on", PlayerTrigger.TriggerInstance.located(
                        EntityPredicate.Builder.entity().steppingOn(
                                LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(
                                        Block.byItem(Items.BEDROCK)
                                ))
                        ))
                )
                .tags(
                        BingoTags.OVERWORLD,
                        EnigmaticsBingoTags.OVERWORLD_ENTRY,
                        EnigmaticsBingoTags.REACH_WORLD_LIMIT
                )
                .name(Component.literal("Stand on bedrock"))
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
                .name(Component.literal("Reach the build limit"))
                .icon(ItemIcon.ofItem(Items.LADDER))
        );
        addGoal(dieToEntityGoal(id("die_to_llama"), EntityType.LLAMA)
                .tags(
                        BingoTags.OVERWORLD,
                        EnigmaticsBingoTags.DIE_TO
                )
                .name(Component.literal("Die to a llama"))
                .icon(
                        IndicatorIcon.infer(
                                EffectIcon.of(MobEffects.WITHER),
                                EntityType.LLAMA
                                // BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.LLAMA),
                        )
                )
        );
        // TODO: Die to an Iron Golem
        addGoal(dieToEntityGoal(id("die_to_bee"), EntityType.BEE)
                .tags(
                        BingoTags.OVERWORLD,
                        EnigmaticsBingoTags.DIE_TO,
                        EnigmaticsBingoTags.BEEHIVE
                )
                .name(Component.literal("Die to a bee"))
                .icon(
                        IndicatorIcon.infer(
                                EffectIcon.of(MobEffects.WITHER),
                                EntityType.BEE
                                // BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.BEE)
                        )
                )
        );
        // TODO: Die to a Dolphin
        // TODO: Die to a Goat
        // TODO: Die to an Anvil
        // TODO: Die to a Stalactite
        // TODO: Die to a TNT Minecart
        // TODO: Die to a Firework
        // TODO: Die to Magic
        // TODO: Die to falling off vines
        // TODO: Mine Diamond Ore
        // TODO: Obtain all Raw Ore Blocks
        // TODO: Craft a Repeater
        // TODO: Craft a Dispenser
        // TODO: Craft a Powered Rail
        // TODO: Craft a Detector Rail
        // TODO: Craft an Activator Rail
        // TODO: Craft an Observer
        // TODO: Craft a Piston
        // TODO: Craft a Sticky Piston
        // TODO: Craft a Redstone Lamp
        // TODO: Craft a Comparator
        // TODO: Mine Emerald Ore
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
        // TODO: Eat Beetroot Stew
        // TODO: Eat Rabbit Stew
        // TODO: Obtain a Bell
        // TODO: Eat a Poisonous Potato
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
        // TODO: Eat Glow Berries
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
                .name(Component.literal("Wear a carved pumpkin continuously for 5 minutes"))
                .icon(ItemIcon.ofItem(Items.CARVED_PUMPKIN))
                .progress("wear")
        );
        // TODO: Obtain a Bottle O' Enchanting
        // TODO: Obtain Sponge
        // TODO: Obtain 1 Armor Trim
        // TODO: Obtain 2 Armor Trim
        // TODO: Obtain 3 Armor Trim
        // TODO: Obtain all Horse Armors
        // TODO: Make a pot out of 4 Pottery Sherds
        // TODO: Obtain a Mud Brick Wall
        // TODO: Obtain a Mossy Stone Brick Wall
        // TODO: Obtain a Mossy Cobblestone Brick Wall
        // TODO: Eat Suspicious Stew
        // TODO: Get a full set of Iron tools
        // TODO: Get a full set of Gold tools
        // TODO: Get a full set of Diamond tools
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
        // TODO: Obtain 64 Red Concrete
        // TODO: Obtain 64 Yellow Concrete
        // TODO: Obtain 64 Orange Concrete
        // TODO: Obtain 64 Black Concrete
        // TODO: Obtain 64 White Concrete
        // TODO: Obtain 64 Gray Concrete
        // TODO: Obtain 64 Light Gray Concrete
        // TODO: Obtain 64 Pink Concrete
        // TODO: Obtain 64 Magenta Concrete
        // TODO: Obtain 64 Blue Concrete
        // TODO: Obtain 64 Purple Concrete
        // TODO: Obtain 64 Green Wool
        // TODO: Obtain 64 Red Wool
        // TODO: Obtain 64 Yellow Wool
        // TODO: Obtain 64 Orange Wool
        // TODO: Obtain 64 Black Wool
        // TODO: Obtain 64 White Wool
        // TODO: Obtain 64 Gray Wool
        // TODO: Obtain 64 Light Gray Wool
        // TODO: Obtain 64 Pink Wool
        // TODO: Obtain 64 Magenta Wool
        // TODO: Obtain 64 Blue Wool
        // TODO: Obtain 64 Purple Wool
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
        // TODO: Obtain a Potion of Strength
        // TODO: Obtain a Potion of Instant Health
        // TODO: Obtain a Potion of Slowness
        // TODO: Obtain a Potion of Instant Damage
        // TODO: Obtain a Potion of Poison
        // TODO: Obtain a Potion of Night Vision
        // TODO: Obtain a Potion of Leaping
        // TODO: Obtain a Potion of Swiftness
        // TODO: Obtain a Potion of Slow Falling
        // TODO: Obtain a Potion of the Turtle Master
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
        // TODO: Eat a Chrous Fruit
        // TODO: Craft a Purpur Block
    }
}
