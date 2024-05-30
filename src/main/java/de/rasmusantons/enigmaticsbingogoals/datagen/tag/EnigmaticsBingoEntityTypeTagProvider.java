package de.rasmusantons.enigmaticsbingogoals.datagen.tag;

import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoEntityTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.advancements.packs.VanillaAdventureAdvancements;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;

public class EnigmaticsBingoEntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider {
    public EnigmaticsBingoEntityTypeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    public static EntityType<?>[] getHostileMobs() {
        return VanillaAdventureAdvancements.MOBS_TO_KILL.toArray(new EntityType<?>[0]);
    }

    public static EntityType<?>[] getEntityTagDuringDatagen(TagKey<EntityType<?>> typeTag) {
        if (typeTag.equals(EnigmaticsBingoEntityTypeTags.HOSTILE)) {
            return getHostileMobs();
        }
        throw new RuntimeException("Invalid entity type tag: " + typeTag);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(EnigmaticsBingoEntityTypeTags.HOSTILE).add(getHostileMobs());
    }
}
