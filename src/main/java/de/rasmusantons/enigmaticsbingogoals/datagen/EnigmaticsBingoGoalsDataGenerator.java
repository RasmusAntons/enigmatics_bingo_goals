package de.rasmusantons.enigmaticsbingogoals.datagen;

import de.rasmusantons.enigmaticsbingogoals.datagen.goal.EnigmaticsBingoGoalProvider;
import de.rasmusantons.enigmaticsbingogoals.datagen.tag.EnigmaticsBingoDamageTypeTagProvider;
import de.rasmusantons.enigmaticsbingogoals.datagen.tag.EnigmaticsBingoEntityTypeTagProvider;
import de.rasmusantons.enigmaticsbingogoals.datagen.tag.EnigmaticsBingoItemTagProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class EnigmaticsBingoGoalsDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(EnigmaticsBingoItemTagProvider::new);
        pack.addProvider(EnigmaticsBingoDamageTypeTagProvider::new);
        pack.addProvider(EnigmaticsBingoEntityTypeTagProvider::new);
        pack.addProvider(BingoDifficultyProvider::new);
        pack.addProvider(BingoTagProvider::new);
        pack.addProvider(EnigmaticsBingoGoalProvider::new);
    }
}
