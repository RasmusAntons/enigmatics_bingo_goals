package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import io.github.gaming32.bingo.data.BingoDifficulties;
import io.github.gaming32.bingo.data.BingoGoal;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;

public class EnigmaticsVeryHardGoalProvider extends EnigmaticsDifficultyGoalProvider {
    public EnigmaticsVeryHardGoalProvider(BiConsumer<ResourceLocation, BingoGoal> goalAdder, HolderLookup.Provider registries) {
        super(BingoDifficulties.VERY_HARD, goalAdder, registries);
    }

    @Override
    public void addGoals() {
    }
}
