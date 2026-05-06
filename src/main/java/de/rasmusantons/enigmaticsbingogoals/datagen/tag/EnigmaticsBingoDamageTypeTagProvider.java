package de.rasmusantons.enigmaticsbingogoals.datagen.tag;

import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoDamageTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class EnigmaticsBingoDamageTypeTagProvider extends FabricTagsProvider<DamageType> {
    public EnigmaticsBingoDamageTypeTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.DAMAGE_TYPE, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider arg) {
        builder(EnigmaticsBingoDamageTypeTags.MAGIC).add(
                DamageTypes.INDIRECT_MAGIC
        );

        builder(EnigmaticsBingoDamageTypeTags.STALACTITE).add(
                DamageTypes.FALLING_STALACTITE
        );

        builder(EnigmaticsBingoDamageTypeTags.ANVIL).add(
                DamageTypes.FALLING_ANVIL
        );

        builder(EnigmaticsBingoDamageTypeTags.FIREWORKS).add(
                DamageTypes.FIREWORKS
        );

        builder(EnigmaticsBingoDamageTypeTags.INTENTIONAL_GAME_DESIGN).add(
                DamageTypes.BAD_RESPAWN_POINT
        );
        builder(EnigmaticsBingoDamageTypeTags.SUFFOCATION).add(
                DamageTypes.IN_WALL
        );
    }
}
