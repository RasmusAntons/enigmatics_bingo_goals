package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoTags;
import de.rasmusantons.enigmaticsbingogoals.conditions.FullUniqueInventoryCondition;
import de.rasmusantons.enigmaticsbingogoals.triggers.WearPumpkinTrigger;
import io.github.gaming32.bingo.data.BingoDifficulties;
import io.github.gaming32.bingo.data.BingoGoal;
import io.github.gaming32.bingo.data.BingoTags;
import io.github.gaming32.bingo.data.icons.IndicatorIcon;
import io.github.gaming32.bingo.data.icons.ItemIcon;
import io.github.gaming32.bingo.triggers.BingoTriggers;
import io.github.gaming32.bingo.triggers.DeathTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;

public class MediumGoalProvider extends EnigmaticsDifficultyGoalProvider {
    public MediumGoalProvider(Consumer<BingoGoal.Holder> goalAdder) {
        super(BingoDifficulties.MEDIUM, goalAdder);
    }

    @Override
    public void addGoals(HolderLookup.Provider provider) {
        addGoal(dieToEntityGoal(id("die_to_bee"), EntityType.BEE)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO, EnigmaticsBingoTags.BEEHIVE)
                .name(Component.literal("Die to a bee"))
                .icon(BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.BEE))
        );
        addGoal(dieToEntityGoal(id("die_to_llama"), EntityType.LLAMA)
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.DIE_TO)
                .name(Component.literal("Die to a llama"))
                .icon(BingoGoalGeneratorUtils.getCustomPLayerHead(BingoGoalGeneratorUtils.PlayerHeadTextures.LLAMA))
        );
        addGoal(BingoGoal.builder(id("full_unique_inventory"))
                .criterion("fill", CriteriaTriggers.INVENTORY_CHANGED.createCriterion(
                        new InventoryChangeTrigger.TriggerInstance(
                                Optional.of(ContextAwarePredicate.create(FullUniqueInventoryCondition.INSTANCE)),
                                InventoryChangeTrigger.TriggerInstance.Slots.ANY,
                                Collections.emptyList()
                        )
                ))
                .tags(BingoTags.OVERWORLD)
                .name(Component.literal("Fill your inventory with unique items"))
                .icon(ItemIcon.ofItem(Items.CHEST))
        );
        addGoal(BingoGoal.builder(id("never_die"))
                .criterion("die", BingoTriggers.DEATH.get().createCriterion(
                        DeathTrigger.TriggerInstance.death(null)
                ))
                .tags(BingoTags.NEVER, EnigmaticsBingoTags.NEVER_TAKE_DAMAGE)
                .name(Component.literal("Never die"))
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.PLAYER_HEAD), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(BingoGoal.builder(id("never_seeds"))
                .criterion("obtain", InventoryChangeTrigger.TriggerInstance.hasItems(Items.WHEAT_SEEDS))
                .tags(BingoTags.NEVER)
                .name(Component.literal("Never obtain wheat seeds"))
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.WHEAT_SEEDS), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(BingoGoal.builder(id("reach_build_limit"))
                .criterion("reach", PlayerTrigger.TriggerInstance.located(
                        LocationPredicate.Builder.atYLocation(MinMaxBounds.Doubles.atLeast(320))
                ))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.REACH_WORLD_LIMIT)
                .name(Component.literal("Reach the build limit"))
                .icon(ItemIcon.ofItem(Items.LADDER))
        );
        addGoal(BingoGoal.builder(id("stand_on_bedrock"))
                .criterion("stand_on", PlayerTrigger.TriggerInstance.located(
                        EntityPredicate.Builder.entity().steppingOn(
                                LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(
                                        Block.byItem(Items.BEDROCK)
                                ))
                        ))
                )
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.REACH_WORLD_LIMIT)
                .name(Component.literal("Stand on bedrock"))
                .icon(ItemIcon.ofItem(Items.BEDROCK))
        );
        addGoal(BingoGoal.builder(id("reach_world_center"))
                .criterion("reach", PlayerTrigger.TriggerInstance.located(
                        LocationPredicate.Builder.location()
                                .setX(MinMaxBounds.Doubles.exactly(0))
                                .setZ(MinMaxBounds.Doubles.exactly(0))
                ))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.COVER_DISTANCE)
                .name(Component.literal("Reach the world center"))
                .icon(ItemIcon.ofItem(Items.LADDER))
        );
        addGoal(BingoGoal.builder(id("wear_pumpkin"))
                .criterion("wear", WearPumpkinTrigger.TriggerInstance.wearPumpkin(MinMaxBounds.Ints.atLeast(300)))
                .name(Component.literal("Wear a carved pumpkin continuously for 5 minutes"))
                .icon(ItemIcon.ofItem(Items.CARVED_PUMPKIN))
        );
    }
}
