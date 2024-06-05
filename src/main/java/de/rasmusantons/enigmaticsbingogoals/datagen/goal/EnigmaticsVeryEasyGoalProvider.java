package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoTags;
import de.rasmusantons.enigmaticsbingogoals.conditions.FullUniqueInventoryCondition;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoDamageTypeTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoEntityTypeTags;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoItemTags;
import de.rasmusantons.enigmaticsbingogoals.triggers.DamageExceptTeamTrigger;
import de.rasmusantons.enigmaticsbingogoals.triggers.EmptyHungerTrigger;
import de.rasmusantons.enigmaticsbingogoals.triggers.WearPumpkinTrigger;
import io.github.gaming32.bingo.conditions.HasAnyEffectCondition;
import io.github.gaming32.bingo.data.BingoDifficulties;
import io.github.gaming32.bingo.data.BingoGoal;
import io.github.gaming32.bingo.data.BingoTags;
import io.github.gaming32.bingo.data.icons.*;
import io.github.gaming32.bingo.data.progresstrackers.CriterionProgressTracker;
import io.github.gaming32.bingo.data.tags.BingoFeatureTags;
import io.github.gaming32.bingo.triggers.ChickenHatchTrigger;
import io.github.gaming32.bingo.triggers.GrowFeatureTrigger;
import io.github.gaming32.bingo.triggers.RelativeStatsTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.advancements.packs.VanillaHusbandryAdvancements;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.function.BiConsumer;

public class EnigmaticsVeryEasyGoalProvider extends EnigmaticsDifficultyGoalProvider {
    public EnigmaticsVeryEasyGoalProvider(BiConsumer<ResourceLocation, BingoGoal> goalAdder, HolderLookup.Provider registries) {
        super(BingoDifficulties.VERY_EASY, goalAdder, registries);
    }

    @Override
    public void addGoals() {
    }
}
