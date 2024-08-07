package de.rasmusantons.enigmaticsbingogoals.datagen.tag;

import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoEntityTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.advancements.packs.VanillaAdventureAdvancements;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EnigmaticsBingoEntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider {
    public static List<EntityType<?>> UNUSED_ENTITIES = List.of(EntityType.GIANT, EntityType.ILLUSIONER, EntityType.ZOMBIE_HORSE);
    public static List<EntityType<?>> MISC_ENTITIES_TO_KEEP = List.of(EntityType.SNOW_GOLEM, EntityType.IRON_GOLEM, EntityType.VILLAGER);

    public EnigmaticsBingoEntityTypeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    public static EntityType<?>[] getHostileMobs() {
        return VanillaAdventureAdvancements.MOBS_TO_KILL.toArray(new EntityType<?>[0]);
    }

    public static EntityType<?>[] getAllMobs() {
        return BuiltInRegistries.ENTITY_TYPE.stream()
                .filter(entityType -> entityType.getCategory() != MobCategory.MISC || MISC_ENTITIES_TO_KEEP.contains(entityType))
                .filter(entityType -> !UNUSED_ENTITIES.contains(entityType))
                .toArray(EntityType[]::new);
    }

    public static EntityType<?>[] getEntityTagDuringDatagen(TagKey<EntityType<?>> typeTag) {
        if (typeTag.equals(EnigmaticsBingoEntityTypeTags.HOSTILE))
            return getHostileMobs();
        if (typeTag.equals(EnigmaticsBingoEntityTypeTags.MOBS))
            return getAllMobs();
        return null;
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(EnigmaticsBingoEntityTypeTags.HOSTILE).add(getHostileMobs());
        getOrCreateTagBuilder(EnigmaticsBingoEntityTypeTags.MOBS).add(getAllMobs());
    }
}
