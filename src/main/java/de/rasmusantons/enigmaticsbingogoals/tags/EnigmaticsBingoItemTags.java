package de.rasmusantons.enigmaticsbingogoals.tags;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoGoals;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class EnigmaticsBingoItemTags {
    public static final TagKey<Item> WOODEN_TOOLS = create("wooden_tools");
    public static final TagKey<Item> STONE_TOOLS = create("stone_tools");
    public static final TagKey<Item> IRON_TOOLS = create("iron_tools");
    public static final TagKey<Item> GOLDEN_TOOLS = create("golden_tools");
    public static final TagKey<Item> DIAMOND_TOOLS = create("diamond_tools");
    public static final TagKey<Item> HORSE_ARMORS = create("horse_armors");
    public static final TagKey<Item> RAW_ORE_BLOCKS = create("raw_ore_blocks");
    public static final TagKey<Item> CHAINMAIL_ARMOR = create("chainmail_armor");
    public static final TagKey<Item> SEEDS = create("seeds");
    public static final TagKey<Item> SAPLINGS = create("saplings");
    public static final TagKey<Item> BOOKS = create("books");

    private EnigmaticsBingoItemTags() {
    }

    private static TagKey<Item> create(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(EnigmaticsBingoGoals.MOD_ID, name));
    }
}
