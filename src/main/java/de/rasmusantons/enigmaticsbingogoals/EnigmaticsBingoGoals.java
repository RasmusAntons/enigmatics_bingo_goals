package de.rasmusantons.enigmaticsbingogoals;

import de.rasmusantons.enigmaticsbingogoals.conditions.EnigmaticsBingoGoalsConditions;
import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import net.fabricmc.api.ModInitializer;

public class EnigmaticsBingoGoals implements ModInitializer {
    public static final String MOD_ID = "enigmaticsbingogoals";

    @Override
    public void onInitialize() {
        EnigmaticsBingoGoalsConditions.load();
        EnigmaticsBingoGoalsTriggers.load();
    }
}
