package de.rasmusantons.enigmaticsbingogoals.tags;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoGoals;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public final class EnigmaticsBingoFeatureTags {
    private EnigmaticsBingoFeatureTags() {
    }

    public static final TagKey<ConfiguredFeature<?, ?>> HUGE_WARPED_FUNGI = create("huge_warped_fungi");
    public static final TagKey<ConfiguredFeature<?, ?>> HUGE_CRIMSON_FUNGI = create("huge_crimson_fungi");

    private static TagKey<ConfiguredFeature<?, ?>> create(String name) {
        return TagKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(EnigmaticsBingoGoals.MOD_ID, name));
    }
}
