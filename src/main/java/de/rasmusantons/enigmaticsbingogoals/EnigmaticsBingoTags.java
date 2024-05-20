package de.rasmusantons.enigmaticsbingogoals;

import io.github.gaming32.bingo.Bingo;
import net.minecraft.resources.ResourceLocation;

public final class EnigmaticsBingoTags {
    private EnigmaticsBingoTags() {
    }

    public static final ResourceLocation DIE_TO = create("die_to");
    public static final ResourceLocation BEEHIVE = create("beehive");
    public static final ResourceLocation NEVER_TAKE_DAMAGE = create("never_take_damage");
    public static final ResourceLocation COVER_DISTANCE = create("cover_distance");
    public static final ResourceLocation REACH_WORLD_LIMIT = create("reach_world_limit");
    public static final ResourceLocation PLAYER_KILL = create("player_kill");
    public static final ResourceLocation MUSIC_DISC = create("music_disc");

    private static ResourceLocation create(String name) {
        return new ResourceLocation(Bingo.MOD_ID, name);
    }
}
