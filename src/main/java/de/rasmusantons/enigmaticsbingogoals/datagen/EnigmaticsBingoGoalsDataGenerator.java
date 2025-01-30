package de.rasmusantons.enigmaticsbingogoals.datagen;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoDifficulties;
import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoTags;
import de.rasmusantons.enigmaticsbingogoals.datagen.goal.EnigmaticsBingoGoalProvider;
import de.rasmusantons.enigmaticsbingogoals.datagen.tag.EnigmaticsBingoDamageTypeTagProvider;
import de.rasmusantons.enigmaticsbingogoals.datagen.tag.EnigmaticsBingoEntityTypeTagProvider;
import de.rasmusantons.enigmaticsbingogoals.datagen.tag.EnigmaticsBingoFeatureTagProvider;
import de.rasmusantons.enigmaticsbingogoals.datagen.tag.EnigmaticsBingoItemTagProvider;
import io.github.gaming32.bingo.data.BingoRegistries;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;

public class EnigmaticsBingoGoalsDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(EnigmaticsBingoItemTagProvider::new);
        pack.addProvider(EnigmaticsBingoFeatureTagProvider::new);
        pack.addProvider(EnigmaticsBingoDamageTypeTagProvider::new);
        pack.addProvider(EnigmaticsBingoEntityTypeTagProvider::new);
        pack.addProvider(EnigmaticsBingoGoalProvider::new);
        pack.addProvider(EnigmaticsBingoDynamicRegistryProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(BingoRegistries.TAG, EnigmaticsBingoTags::bootstrap);
        registryBuilder.add(BingoRegistries.DIFFICULTY, EnigmaticsBingoDifficulties::bootstrap);
    }
}
