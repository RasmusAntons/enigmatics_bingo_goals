package de.rasmusantons.enigmaticsbingogoals.datagen;

import de.rasmusantons.enigmaticsbingogoals.datagen.goal.EnigmaticsBingoGoalProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import de.rasmusantons.enigmaticsbingogoals.datagen.tag.EnigmaticsBingoDamageTypeTagProvider;

public class EnigmaticsBingoGoalsDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(EnigmaticsBingoGoalProvider::new);
        pack.addProvider(BingoTagProvider::new);
        pack.addProvider(BingoDifficultyProvider::new);
        pack.addProvider(EnigmaticsBingoItemTagProvider::new);
        pack.addProvider(EnigmaticsBingoDamageTypeTagProvider::new);
    }
}
