package de.rasmusantons.enigmaticsbingogoals.datagen;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoAdvancements;
import io.github.gaming32.bingo.datagen.BingoDataGenUtil;
import io.github.gaming32.bingo.datagen.tag.BingoEntityTypeTagProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.criterion.EntityFlagsPredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.EntityTypePredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.PlayerInteractTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.NonNull;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class EnigmaticsBingoAdvancementProvider extends FabricAdvancementProvider {
    protected EnigmaticsBingoAdvancementProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider wrapperLookup, @NonNull Consumer<AdvancementHolder> consumer) {
        var entityTypes = wrapperLookup.lookupOrThrow(Registries.ENTITY_TYPE);
        var items = wrapperLookup.lookupOrThrow(Registries.ITEM);

        Advancement.Builder ageLockDifferentMobs = Advancement.Builder.advancement();
        entityTypes.listElements()
                .filter(type -> {
                    Class<? extends Entity> entityClass = BingoDataGenUtil.getEntityTypeClass(type.value());
                    return entityClass != null
                            && BingoEntityTypeTagProvider.canBeAgeLocked(entityClass) &&
                            !(BingoDataGenUtil.loadVanillaTag(EntityTypeTags.CANNOT_BE_AGE_LOCKED, wrapperLookup).contains(type));
                })
                .forEach(type -> {
                    ageLockDifferentMobs.addCriterion("feed_" + type.value().toShortString(), PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
                            ItemPredicate.Builder.item().of(items, Items.GOLDEN_DANDELION),
                            Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity()
                                    .entityType(EntityTypePredicate.of(entityTypes, type.value()))
                                    .flags(EntityFlagsPredicate.Builder.flags().setIsBaby(true))))
                    ));
                });
        ageLockDifferentMobs.save(consumer, EnigmaticsBingoAdvancements.FEED_GOLDEN_DANDELION_TO_DIFFERENT_MOBS.toString());
    }
}
