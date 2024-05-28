package de.rasmusantons.enigmaticsbingogoals.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;

import java.util.concurrent.CompletableFuture;

public class EnigmaticsBingoDamageTypeTagProvider extends FabricTagProvider<DamageType> {
    public EnigmaticsBingoDamageTypeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.DAMAGE_TYPE, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        getOrCreateTagBuilder(EnigmaticsBingoDamageTypeTags.MAGIC).add(
                DamageTypes.INDIRECT_MAGIC
        );

        getOrCreateTagBuilder(EnigmaticsBingoDamageTypeTags.STALACTITE).add(
                DamageTypes.FALLING_STALACTITE
        );

        getOrCreateTagBuilder(EnigmaticsBingoDamageTypeTags.ANVIL).add(
                DamageTypes.FALLING_ANVIL
        );

        getOrCreateTagBuilder(EnigmaticsBingoDamageTypeTags.FIREWORKS).add(
                DamageTypes.FIREWORKS
        );

        getOrCreateTagBuilder(EnigmaticsBingoDamageTypeTags.INTENTIONAL_GAME_DESIGN).add(
                DamageTypes.BAD_RESPAWN_POINT
        );
    }
}
