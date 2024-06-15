package de.rasmusantons.enigmaticsbingogoals;

import de.rasmusantons.enigmaticsbingogoals.conditions.EnigmaticsBingoGoalsConditions;
import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import io.github.gaming32.bingo.platform.BingoPlatform;
import net.fabricmc.api.ModInitializer;

public class EnigmaticsBingoGoals implements ModInitializer {
    public static final String MOD_ID = "enigmaticsbingogoals";

    @Override
    public void onInitialize() {
        if (BingoPlatform.platform == null)
            throw new RuntimeException("Bingo mod isn't initialized???");
        EnigmaticsBingoGoalsConditions.load();
        EnigmaticsBingoGoalsTriggers.load();
    }
}
