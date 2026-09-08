package de.rasmusantons.enigmaticsbingogoals.datagen.tag;

import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.BlockItemId;
import net.minecraft.references.BlockItemIds;
import net.minecraft.references.ItemIds;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class EnigmaticsBingoItemTagProvider extends FabricTagsProvider.ItemTagsProvider {

    public EnigmaticsBingoItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider registries) {
        builder(EnigmaticsBingoItemTags.WOODEN_TOOLS).add(
                ItemIds.WOODEN_AXE,
                ItemIds.WOODEN_SHOVEL,
                ItemIds.WOODEN_PICKAXE,
                ItemIds.WOODEN_HOE,
                ItemIds.WOODEN_SWORD,
                ItemIds.WOODEN_SPEAR
        );

        builder(EnigmaticsBingoItemTags.STONE_TOOLS).add(
                ItemIds.STONE_AXE,
                ItemIds.STONE_SHOVEL,
                ItemIds.STONE_PICKAXE,
                ItemIds.STONE_HOE,
                ItemIds.STONE_SWORD,
                ItemIds.STONE_SPEAR
        );

        builder(EnigmaticsBingoItemTags.IRON_TOOLS).add(
                ItemIds.IRON_AXE,
                ItemIds.IRON_SHOVEL,
                ItemIds.IRON_PICKAXE,
                ItemIds.IRON_HOE,
                ItemIds.IRON_SWORD,
                ItemIds.IRON_SPEAR
        );

        builder(EnigmaticsBingoItemTags.COPPER_TOOLS).add(
                ItemIds.COPPER_AXE,
                ItemIds.COPPER_SHOVEL,
                ItemIds.COPPER_PICKAXE,
                ItemIds.COPPER_HOE,
                ItemIds.COPPER_SWORD,
                ItemIds.COPPER_SPEAR
        );

        builder(EnigmaticsBingoItemTags.GOLDEN_TOOLS).add(
                ItemIds.GOLDEN_AXE,
                ItemIds.GOLDEN_SHOVEL,
                ItemIds.GOLDEN_PICKAXE,
                ItemIds.GOLDEN_HOE,
                ItemIds.GOLDEN_SWORD,
                ItemIds.GOLDEN_SPEAR
        );

        builder(EnigmaticsBingoItemTags.DIAMOND_TOOLS).add(
                ItemIds.DIAMOND_AXE,
                ItemIds.DIAMOND_SHOVEL,
                ItemIds.DIAMOND_PICKAXE,
                ItemIds.DIAMOND_HOE,
                ItemIds.DIAMOND_SWORD,
                ItemIds.DIAMOND_SPEAR
        );

        builder(EnigmaticsBingoItemTags.HORSE_ARMORS).add(
                ItemIds.LEATHER_HORSE_ARMOR,
                ItemIds.IRON_HORSE_ARMOR,
                ItemIds.GOLDEN_HORSE_ARMOR,
                ItemIds.DIAMOND_HORSE_ARMOR,
                ItemIds.COPPER_HORSE_ARMOR
        );

        builder(EnigmaticsBingoItemTags.RAW_ORE_BLOCKS).add(
                BlockItemIds.RAW_COPPER_BLOCK,
                BlockItemIds.RAW_GOLD_BLOCK,
                BlockItemIds.RAW_IRON_BLOCK
        );

        builder(EnigmaticsBingoItemTags.CHAINMAIL_ARMOR).add(
                ItemIds.CHAINMAIL_HELMET,
                ItemIds.CHAINMAIL_CHESTPLATE,
                ItemIds.CHAINMAIL_LEGGINGS,
                ItemIds.CHAINMAIL_BOOTS
        );

        builder(EnigmaticsBingoItemTags.SEEDS).add(
                BlockItemIds.WHEAT_CROP,
                BlockItemIds.MELON_CROP,
                BlockItemIds.PUMPKIN_CROP,
                BlockItemIds.BEETROOT_CROP,
                BlockItemIds.TORCHFLOWER_CROP,
                BlockItemIds.PITCHER_CROP
        );

        builder(EnigmaticsBingoItemTags.SAPLINGS).add(
                BlockItemIds.OAK_SAPLING,
                BlockItemIds.SPRUCE_SAPLING,
                BlockItemIds.BIRCH_SAPLING,
                BlockItemIds.JUNGLE_SAPLING,
                BlockItemIds.ACACIA_SAPLING,
                BlockItemIds.DARK_OAK_SAPLING,
                BlockItemIds.CHERRY_SAPLING,
                BlockItemIds.PALE_OAK_SAPLING
        );

        builder(EnigmaticsBingoItemTags.BOOKS).add(
                ItemIds.BOOK,
                ItemIds.WRITABLE_BOOK,
                ItemIds.WRITTEN_BOOK,
                ItemIds.ENCHANTED_BOOK,
                ItemIds.KNOWLEDGE_BOOK
        );

        builder(EnigmaticsBingoItemTags.MUSIC_DISCS).add(
                ItemIds.MUSIC_DISC_13,
                ItemIds.MUSIC_DISC_CAT,
                ItemIds.MUSIC_DISC_BLOCKS,
                ItemIds.MUSIC_DISC_CHIRP,
                ItemIds.MUSIC_DISC_FAR,
                ItemIds.MUSIC_DISC_MALL,
                ItemIds.MUSIC_DISC_MELLOHI,
                ItemIds.MUSIC_DISC_STAL,
                ItemIds.MUSIC_DISC_STRAD,
                ItemIds.MUSIC_DISC_WARD,
                ItemIds.MUSIC_DISC_11,
                ItemIds.MUSIC_DISC_WAIT,
                ItemIds.MUSIC_DISC_PIGSTEP,
                ItemIds.MUSIC_DISC_OTHERSIDE,
                ItemIds.MUSIC_DISC_5,
                ItemIds.MUSIC_DISC_RELIC,
                ItemIds.MUSIC_DISC_PRECIPICE,
                ItemIds.MUSIC_DISC_CREATOR,
                ItemIds.MUSIC_DISC_CREATOR_MUSIC_BOX,
                ItemIds.MUSIC_DISC_LAVA_CHICKEN,
                ItemIds.MUSIC_DISC_TEARS
        );

        builder(EnigmaticsBingoItemTags.COPPER_BULBS).add(
                BlockItemIds.COPPER_BULB.asList().toArray(new BlockItemId[0])
        );

        builder(EnigmaticsBingoItemTags.HANGING_SIGNS).add(
                BlockItemIds.OAK_HANGING_SIGN,
                BlockItemIds.SPRUCE_HANGING_SIGN,
                BlockItemIds.BIRCH_HANGING_SIGN,
                BlockItemIds.JUNGLE_HANGING_SIGN,
                BlockItemIds.ACACIA_HANGING_SIGN,
                BlockItemIds.DARK_OAK_HANGING_SIGN,
                BlockItemIds.MANGROVE_HANGING_SIGN,
                BlockItemIds.CHERRY_HANGING_SIGN,
                BlockItemIds.BAMBOO_HANGING_SIGN,
                BlockItemIds.CRIMSON_HANGING_SIGN,
                BlockItemIds.WARPED_HANGING_SIGN,
                BlockItemIds.PALE_OAK_HANGING_SIGN
        );

        builder(EnigmaticsBingoItemTags.COLORED_CANDLES).add(
                BlockItemIds.DYED_CANDLE.asList().toArray(new BlockItemId[0])
        );
    }
}
