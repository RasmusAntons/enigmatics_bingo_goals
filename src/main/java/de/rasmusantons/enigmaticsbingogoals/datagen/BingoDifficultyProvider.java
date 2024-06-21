package de.rasmusantons.enigmaticsbingogoals.datagen;

import de.rasmusantons.enigmaticsbingogoals.datagen.goal.EnigmaticsDifficultyGoalProvider;
import io.github.gaming32.bingo.data.BingoDifficulties;
import io.github.gaming32.bingo.data.BingoDifficulty;
import io.github.gaming32.bingo.util.BingoUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class BingoDifficultyProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;

    public BingoDifficultyProvider(FabricDataOutput output) {
        this.pathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "bingo/difficulties");
    }

    @Override
    @NotNull
    public CompletableFuture<?> run(CachedOutput output) {
        Set<ResourceLocation> existingDifficultyIds = new HashSet<>();
        Set<Integer> existingDifficultyNumbers = new HashSet<>();
        List<CompletableFuture<?>> generators = new ArrayList<>();

        Consumer<BingoDifficulty.Holder> difficultyAdder = difficulty -> {
            if (!existingDifficultyIds.add(difficulty.id())) {
                throw new IllegalArgumentException("Duplicate difficulty id " + difficulty.id());
            }
            if (!existingDifficultyNumbers.add(difficulty.difficulty().number())) {
                throw new IllegalArgumentException("Duplicate difficulty number " + difficulty.difficulty().number());
            }
            Path path = pathProvider.json(difficulty.id());
            generators.add(DataProvider.saveStable(
                    output, BingoUtil.toJsonElement(BingoDifficulty.CODEC, difficulty.difficulty()), path
            ));
        };

        addDifficulties(difficultyAdder);

        return CompletableFuture.allOf(generators.toArray(CompletableFuture[]::new));
    }

    @Override
    @NotNull
    public String getName() {
        return "Bingo difficulties";
    }

    private void addDifficulties(Consumer<BingoDifficulty.Holder> difficultyAdder) {
        difficultyAdder.accept(BingoDifficulty.builder(BingoDifficulties.VERY_EASY).number(0).distribution(20, 5, 0, 0, 0).build());
        difficultyAdder.accept(BingoDifficulty.builder(BingoDifficulties.EASY).number(1).distribution(10, 10, 5, 0, 0).build());
        difficultyAdder.accept(BingoDifficulty.builder(BingoDifficulties.MEDIUM).number(2).distribution(3, 5, 14, 4, 0).build());
        difficultyAdder.accept(BingoDifficulty.builder(BingoDifficulties.HARD).number(3).distribution(0, 5, 10, 10, 0).build());
        difficultyAdder.accept(BingoDifficulty.builder(BingoDifficulties.VERY_HARD).number(4).distribution(0, 0, 5, 17, 3).build());
    }
}