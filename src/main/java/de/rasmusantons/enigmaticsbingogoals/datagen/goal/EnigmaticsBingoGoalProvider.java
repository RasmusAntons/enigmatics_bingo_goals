package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import com.google.gson.JsonElement;
import com.mojang.serialization.DynamicOps;
import io.github.gaming32.bingo.data.goal.BingoGoal;
import io.github.gaming32.bingo.data.goal.GoalBuilder;
import io.github.gaming32.bingo.fabric.datagen.goal.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class EnigmaticsBingoGoalProvider extends FabricCodecDataProvider<BingoGoal> {
    private static final List<GoalProviderProvider> PROVIDERS = List.of(
            EnigmaticsVeryEasyGoalProvider::new,
            EnigmaticsEasyGoalProvider::new,
            EnigmaticsMediumGoalProvider::new,
            EnigmaticsHardGoalProvider::new,
            EnigmaticsVeryHardGoalProvider::new
    );

    public EnigmaticsBingoGoalProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, PackOutput.Target.DATA_PACK, "bingo/goal", BingoGoal.CODEC);
    }

    @Override
    @NotNull
    public String getName() {
        return "Bingo goals";
    }

    @Override
    protected void configure(BiConsumer<ResourceLocation, BingoGoal> adder, HolderLookup.Provider registries) {
        final DynamicOps<JsonElement> oldOps = GoalBuilder.JSON_OPS.get();
        try {
            GoalBuilder.JSON_OPS.set(registries.createSerializationContext(oldOps));
            for (final GoalProviderProvider provider : PROVIDERS) {
                provider.create(adder, registries).addGoals();
            }
        } finally {
            GoalBuilder.JSON_OPS.set(oldOps);
        }
    }

    @FunctionalInterface
    private interface GoalProviderProvider {
        DifficultyGoalProvider create(BiConsumer<ResourceLocation, BingoGoal> adder, HolderLookup.Provider registries);
    }
}
