package de.rasmusantons.enigmaticsbingogoals.tags;

import io.github.gaming32.bingo.util.ResourceLocations;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public class EnigmaticsBingoDamageTypeTags {
    private EnigmaticsBingoDamageTypeTags() {
    }

    public static final TagKey<DamageType> MAGIC = create("magic");
    public static final TagKey<DamageType> STALACTITE = create("stalactite");
    public static final TagKey<DamageType> ANVIL = create("anvil");
    public static final TagKey<DamageType> FIREWORKS = create("fireworks");
    public static final TagKey<DamageType> INTENTIONAL_GAME_DESIGN = create("intentional_game_design");

    private static TagKey<DamageType> create(String name) {
        return TagKey.create(Registries.DAMAGE_TYPE, ResourceLocations.bingo(name));
    }
}
