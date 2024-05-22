package de.rasmusantons.enigmaticsbingogoals.datagen;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class EnigmaticsBingoItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public EnigmaticsBingoItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        getOrCreateTagBuilder(EnigmaticsBingoItemTags.WOODEN_TOOLS).add(
                Items.WOODEN_AXE,
                Items.WOODEN_SHOVEL,
                Items.WOODEN_PICKAXE,
                Items.WOODEN_HOE,
                Items.WOODEN_SWORD
        );

        getOrCreateTagBuilder(EnigmaticsBingoItemTags.STONE_TOOLS).add(
                Items.STONE_AXE,
                Items.STONE_SHOVEL,
                Items.STONE_PICKAXE,
                Items.STONE_HOE,
                Items.STONE_SWORD
        );

        getOrCreateTagBuilder(EnigmaticsBingoItemTags.IRON_TOOLS).add(
                Items.IRON_AXE,
                Items.IRON_SHOVEL,
                Items.IRON_PICKAXE,
                Items.IRON_HOE,
                Items.IRON_SWORD
        );

        getOrCreateTagBuilder(EnigmaticsBingoItemTags.GOLDEN_TOOLS).add(
                Items.GOLDEN_AXE,
                Items.GOLDEN_SHOVEL,
                Items.GOLDEN_PICKAXE,
                Items.GOLDEN_HOE,
                Items.GOLDEN_SWORD
        );

        getOrCreateTagBuilder(EnigmaticsBingoItemTags.DIAMOND_TOOLS).add(
                Items.DIAMOND_AXE,
                Items.DIAMOND_SHOVEL,
                Items.DIAMOND_PICKAXE,
                Items.DIAMOND_HOE,
                Items.DIAMOND_SWORD
        );

        getOrCreateTagBuilder(EnigmaticsBingoItemTags.HORSE_ARMORS).add(
                Items.LEATHER_HORSE_ARMOR,
                Items.IRON_HORSE_ARMOR,
                Items.GOLDEN_HORSE_ARMOR,
                Items.DIAMOND_HORSE_ARMOR
        );
    }
}
