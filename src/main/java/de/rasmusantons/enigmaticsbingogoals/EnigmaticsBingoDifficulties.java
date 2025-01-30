package de.rasmusantons.enigmaticsbingogoals;

import io.github.gaming32.bingo.data.BingoDifficulty;
import io.github.gaming32.bingo.data.BingoRegistries;
import net.minecraft.Util;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public final class EnigmaticsBingoDifficulties {
    public static final ResourceKey<BingoDifficulty> VERY_EASY = createKey("very_easy");
    public static final ResourceKey<BingoDifficulty> EASY = createKey("easy");
    public static final ResourceKey<BingoDifficulty> MEDIUM = createKey("medium");
    public static final ResourceKey<BingoDifficulty> HARD = createKey("hard");
    public static final ResourceKey<BingoDifficulty> VERY_HARD = createKey("very_hard");

    private EnigmaticsBingoDifficulties() {
    }

    public static void bootstrap(BootstrapContext<BingoDifficulty> context) {
        register(context, VERY_EASY, 5, new int[] {0, 0, 0, 0, 0, 20, 5, 0, 0, 0});
        register(context, EASY, 6, new int[] {0, 0, 0, 0, 0, 3, 17, 5, 0, 0});
        register(context, MEDIUM, 7, new int[] {0, 0, 0, 0, 0, 3, 5, 14, 4, 0});
        register(context, HARD, 8, new int[] {0, 0, 0, 0, 0, 0, 5, 10, 10, 0});
        register(context, VERY_HARD, 9, new int[] {0, 0, 0, 0, 0, 0, 0, 5, 17, 3});
    }

    private static void register(BootstrapContext<BingoDifficulty> context, ResourceKey<BingoDifficulty> key, int number, int[] distribution) {
        context.register(key, new BingoDifficulty(
                Component.translatable(Util.makeDescriptionId("bingo_difficulty", key.location())), number, distribution
        ));
    }

    private static ResourceKey<BingoDifficulty> createKey(String name) {
        return ResourceKey.create(BingoRegistries.DIFFICULTY, ResourceLocation.fromNamespaceAndPath(EnigmaticsBingoGoals.MOD_ID, name));
    }
}
