package de.rasmusantons.enigmaticsbingogoals;

import io.github.gaming32.bingo.Bingo;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class EnigmaticsBingoItemTags {
    private EnigmaticsBingoItemTags() {
    }

    public static final TagKey<Item> WOODEN_TOOLS = create("wooden_tools");
    public static final TagKey<Item> STONE_TOOLS = create("stone_tools");
    public static final TagKey<Item> IRON_TOOLS = create("iron_tools");
    public static final TagKey<Item> GOLDEN_TOOLS = create("golden_tools");
    public static final TagKey<Item> DIAMOND_TOOLS = create("diamond_tools");
    public static final TagKey<Item> HORSE_ARMORS = create("horse_armors");
    public static final TagKey<Item> RAW_ORE_BLOCKS = create("raw_ore_blocks");
    public static final TagKey<Item> CHAINMAIL = create("chainmail");
    public static final TagKey<Item> SEEDS = create("seeds");
    public static final TagKey<Item> SAPLINGS = create("saplings");

    private static TagKey<Item> create(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(Bingo.MOD_ID, name));
    }
}
