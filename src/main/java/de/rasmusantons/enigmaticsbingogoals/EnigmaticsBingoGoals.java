package de.rasmusantons.enigmaticsbingogoals;

import de.rasmusantons.enigmaticsbingogoals.conditions.EnigmaticsBingoGoalsConditions;
import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import io.github.gaming32.bingo.Bingo;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class EnigmaticsBingoGoals implements ModInitializer {
    public static final String MOD_ID = "enigmaticsbingogoals";

    @Override
    public void onInitialize() {
        if (!FabricLoader.getInstance().isModLoaded(Bingo.MOD_ID))
            throw new RuntimeException("Bingo mod isn't loaded???");
        EnigmaticsBingoGoalsConditions.load();
        EnigmaticsBingoGoalsTriggers.load();
    }
}
