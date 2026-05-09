package de.rasmusantons.enigmaticsbingogoals;

import net.minecraft.resources.Identifier;

public class EnigmaticsBingoAdvancements {
    private static Identifier advancementId(String name) {
        return Identifier.fromNamespaceAndPath(EnigmaticsBingoGoals.MOD_ID, name);
    }

    public static final Identifier FEED_GOLDEN_DANDELION_TO_DIFFERENT_MOBS = advancementId("feed_golden_dandelion_to_different_mobs");
}
