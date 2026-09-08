package de.rasmusantons.enigmaticsbingogoals.datagen.tag;

import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoEntityTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.packs.VanillaAdventureAdvancements;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.MobCategory;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EnigmaticsBingoEntityTypeTagProvider extends FabricTagsProvider.EntityTypeTagsProvider {
    public static List<EntityType<?>> UNUSED_ENTITIES = List.of(EntityTypes.GIANT, EntityTypes.ILLUSIONER, EntityTypes.ZOMBIE_HORSE);
    public static List<EntityType<?>> MISC_ENTITIES_TO_KEEP = List.of(EntityTypes.SNOW_GOLEM, EntityTypes.IRON_GOLEM, EntityTypes.VILLAGER);

    public EnigmaticsBingoEntityTypeTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    public static boolean isHostile(EntityType<?> entityType) {
        return VanillaAdventureAdvancements.MOBS_TO_KILL.contains(entityType);
    }

    public static boolean isMob(EntityType<?> entityType) {
        return (entityType.getCategory() != MobCategory.MISC || MISC_ENTITIES_TO_KEEP.contains(entityType)) && !UNUSED_ENTITIES.contains(entityType);
    }

    public static EntityType<?>[] getEntityTagDuringDatagen(TagKey<EntityType<?>> typeTag, HolderLookup.RegistryLookup<EntityType<?>> entityTypes) {
        if (typeTag.equals(EnigmaticsBingoEntityTypeTags.HOSTILE))
            return entityTypes.listElements().map(Holder.Reference::value).filter(EnigmaticsBingoEntityTypeTagProvider::isHostile).toArray(EntityType[]::new);
        if (typeTag.equals(EnigmaticsBingoEntityTypeTags.MOBS))
            return entityTypes.listElements().map(Holder.Reference::value).filter(EnigmaticsBingoEntityTypeTagProvider::isMob).toArray(EntityType[]::new);
        return null;
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        HolderLookup.RegistryLookup<EntityType<?>> entityTypes = provider.lookupOrThrow(Registries.ENTITY_TYPE);
        TagAppender<EntityType<?>> hostileBuilder = builder(EnigmaticsBingoEntityTypeTags.HOSTILE);
        TagAppender<EntityType<?>> mobsBuilder = builder(EnigmaticsBingoEntityTypeTags.MOBS);

        entityTypes.listElements().forEach(type -> {
            if (isHostile(type.value())) {
                hostileBuilder.add(type.key());
            }
            if (isMob(type.value())) {
                mobsBuilder.add(type.key());
            }
        });
    }
}
