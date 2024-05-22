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
    public static final TagKey<Item> IRON_TOOLS = create("iron_tools");

    private static TagKey<Item> create(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(Bingo.MOD_ID, name));
    }
}
