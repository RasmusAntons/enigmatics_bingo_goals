package de.rasmusantons.enigmaticsbingogoals.extension;

import net.minecraft.server.level.ServerPlayer;

public interface JukeboxSongPlayerExtension {
    ServerPlayer enigmaticsbingogoals$getLastPlayed();

    void enigmaticsbingogoals$setLastPlayed(ServerPlayer serverPlayer);
}
