package de.rasmusantons.enigmaticsbingogoals.tags;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoGoals;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class EnigmaticsBingoEntityTypeTags {
    public static final TagKey<EntityType<?>> MOBS = create("mobs");
    public static final TagKey<EntityType<?>> HOSTILE = create("hostile");

    private EnigmaticsBingoEntityTypeTags() {
    }

    private static TagKey<EntityType<?>> create(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(EnigmaticsBingoGoals.MOD_ID, name));
    }
}
