package de.rasmusantons.enigmaticsbingogoals.datagen.tag;

import com.mojang.logging.LogUtils;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoFeatureTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EnigmaticsBingoFeatureTagProvider extends FabricTagProvider<ConfiguredFeature<?, ?>> {
    private static final Logger LOGGER = LogUtils.getLogger();

    public EnigmaticsBingoFeatureTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.CONFIGURED_FEATURE, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        getOrCreateTagBuilder(EnigmaticsBingoFeatureTags.HUGE_WARPED_FUNGI).add(
                TreeFeatures.WARPED_FUNGUS_PLANTED,
                TreeFeatures.WARPED_FUNGUS
        );

        getOrCreateTagBuilder(EnigmaticsBingoFeatureTags.HUGE_CRIMSON_FUNGI).add(
                TreeFeatures.CRIMSON_FUNGUS_PLANTED,
                TreeFeatures.CRIMSON_FUNGUS
        );
    }

    @SuppressWarnings("unchecked")
    private static ResourceKey<ConfiguredFeature<?, ?>>[] getTreeFeatures() {
        List<ResourceKey<ConfiguredFeature<?, ?>>> result = new ArrayList<>();

        for (Field field : TreeFeatures.class.getFields()) {
            if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isStatic(field.getModifiers()) || !Modifier.isFinal(field.getModifiers())) {
                continue;
            }
            if (field.getType() != ResourceKey.class) {
                continue;
            }
            ResourceKey<?> resourceKey;
            try {
                resourceKey = (ResourceKey<?>) field.get(null);
            } catch (ReflectiveOperationException e) {
                LOGGER.error("Error getting field '{}'", field.getName(), e);
                continue;
            }
            if (!resourceKey.isFor(Registries.CONFIGURED_FEATURE)) {
                continue;
            }
            ResourceKey<ConfiguredFeature<?, ?>> featureKey = (ResourceKey<ConfiguredFeature<?,?>>) resourceKey;
            String path = featureKey.location().getPath();
            if (!path.contains("fungus") && !path.contains("mushroom")) {
                result.add(featureKey);
            }
        }

        return result.toArray(ResourceKey[]::new);
    }
}
