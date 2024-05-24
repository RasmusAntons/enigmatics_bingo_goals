package de.rasmusantons.enigmaticsbingogoals.datagen;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoTags;
import io.github.gaming32.bingo.data.BingoTag;
import io.github.gaming32.bingo.data.BingoTags;
import io.github.gaming32.bingo.util.BingoUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class BingoTagProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;

    public BingoTagProvider(FabricDataOutput output) {
        this.pathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "bingo/tags");
    }

    @Override
    @NotNull
    public CompletableFuture<?> run(CachedOutput output) {
        Set<ResourceLocation> existingTags = new HashSet<>();
        List<CompletableFuture<?>> generators = new ArrayList<>();

        Consumer<BingoTag.Holder> tagAdder = tag -> {
            if (!existingTags.add(tag.id())) {
                throw new IllegalArgumentException("Duplicate tag " + tag.id());
            } else {
                Path path = pathProvider.json(tag.id());
                generators.add(DataProvider.saveStable(
                        output, BingoUtil.toJsonElement(BingoTag.CODEC, tag.tag()), path
                ));
            }
        };

        addTags(tagAdder);

        return CompletableFuture.allOf(generators.toArray(CompletableFuture[]::new));
    }

    @Override
    @NotNull
    public String getName() {
        return "Bingo tags";
    }

    private void addTags(Consumer<BingoTag.Holder> tagAdder) {
        tagAdder.accept(BingoTag.builder(BingoTags.ACTION).difficultyMax(20, 20, 20, 20, 20, 20).build());
        tagAdder.accept(BingoTag.builder(BingoTags.BUILD).difficultyMax(20, 20, 20, 20, 20, 20).build());
        tagAdder.accept(BingoTag.builder(BingoTags.ITEM).difficultyMax(25, 25, 20, 20, 20, 20).build());
        tagAdder.accept(BingoTag.builder(BingoTags.STAT).difficultyMax(5, 5, 5, 5, 5, 5).build());
        tagAdder.accept(BingoTag.builder(BingoTags.COLOR).difficultyMax(2, 2, 2, 2, 2, 2).build());
        tagAdder.accept(BingoTag.builder(BingoTags.COMBAT).difficultyMax(5, 10, 20, 20, 20, 20).build());

        tagAdder.accept(BingoTag.builder(BingoTags.FINISH)
                .difficultyMax(1, 1, 1, 1, 1, 1)
                .disallowOnSameLine()
                .specialType(BingoTag.SpecialType.FINISH)
                .build()
        );

        tagAdder.accept(BingoTag.builder(BingoTags.OVERWORLD).difficultyMax(25, 25, 24, 21, 18, 21).build());
        tagAdder.accept(BingoTag.builder(BingoTags.NETHER).difficultyMax(0, 2, 5, 10, 15, 10).build());
        tagAdder.accept(BingoTag.builder(BingoTags.END).difficultyMax(0, 0, 0, 1, 5, 3).build());

        tagAdder.accept(BingoTag.builder(BingoTags.OCEAN).difficultyMax(5, 5, 5, 5, 5, 5).build());
        tagAdder.accept(BingoTag.builder(BingoTags.RARE_BIOME).difficultyMax(0, 1, 2, 4, 6, 4).build());

        tagAdder.accept(BingoTag.builder(BingoTags.NEVER)
                .difficultyMax(3, 3, 3, 3, 3, 3)
                .disallowOnSameLine()
                .specialType(BingoTag.SpecialType.NEVER)
                .build()
        );
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.NEVER_TAKE_DAMAGE).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.PLAYER_KILL).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.ADVANCEMENTS).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.BURIED_TREASURE).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.PVP).difficultyMax(1, 1, 1, 1, 1, 1).build());
        // TODO: overtakable 1
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.COVER_DISTANCE).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.GOLDEN_APPLE).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.PUFFER_FISH).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.GET_EFFECT_BATCH).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.GET_EFFECT).difficultyMax(2, 2, 2, 2, 2, 2).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.SUSPICIOUS_STEW).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.MILK).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.LEVEL).difficultyMax(2, 2, 2, 2, 2, 2).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.REACH_LEVEL).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.OVERWORLD_ENTRY).difficultyMax(2, 2, 2, 2, 2, 2).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.CHICKEN).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.REACH_WORLD_LIMIT).difficultyMax(2, 2, 2, 2, 2, 2).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.DIE_TO).difficultyMax(2, 2, 2, 2, 2, 2).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.CAVING).difficultyMax(2, 2, 2, 2, 2, 2).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.MOUNTAIN).difficultyMax(2, 2, 2, 2, 2, 2).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.TAME_ANIMAL).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.BREED_MOB).difficultyMax(2, 2, 2, 2, 2, 2).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.UNIQUE_MOBS).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.UNIQUE_NEUTRAL_MOBS).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.UNIQUE_HOSTILE_MOBS).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.KILL_MOB).difficultyMax(3, 3, 3, 3, 3, 3).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.KILL_MOB_BATCH).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(BingoTags.VILLAGE).difficultyMax(3, 3, 3, 3, 3, 3).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.FROG).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.BUCKET_WITH_MOB).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.MUSIC_DISC).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.ARMOR).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.PLANT_BATCH).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.SEEDS).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.LUSH_CAVE).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.USE_WORKSTATION).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.LOOM).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.UNIQUE_FOOD).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.SILK_TOUCH).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.BOW).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.SADDLE).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.AMETHYST).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.BOOK).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.OUTPOST).difficultyMax(2, 2, 2, 2, 2, 2).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.REDSTONE).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.OCEAN_MONUMENT).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.RAID).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.RARE_COLLECTIBLE_BATCH).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.WALL).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.STEW).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.FULL_TOOL_SET).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.SHIPWRECK).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.MINESHAFT).difficultyMax(2, 2, 2, 2, 2, 2).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.TERRACOTTA).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.SLIME).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.BEEHIVE).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.JUNGLE).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.ANVIL).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.CONCRETE).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.WOOL).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.NETHER_ENTRY).difficultyMax(3, 3, 3, 3, 3, 3).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.NETHER_EXPLORE).difficultyMax(2, 2, 2, 2, 2, 2).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.GHAST).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.STRIDER).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.EYE_OF_ENDER).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.BLAZE_POWDER).difficultyMax(2, 2, 2, 2, 2, 2).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.POTIONS).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.INSTANT_DAMAGE).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.SATURATION).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.WEAKNESS).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.NIGHT_VISION).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.SLOWNESS).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.LEAPING).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.POISON).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.WITHER_SKULL).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.FORTRESS).difficultyMax(2, 2, 2, 2, 2, 2).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.CRIMSON_FOREST).difficultyMax(2, 2, 2, 2, 2, 2).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.WARPED_FOREST).difficultyMax(2, 2, 2, 2, 2, 2).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.SOUL_SAND).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.BARTERING).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.NETHER_LATE).difficultyMax(2, 2, 2, 2, 2, 2).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.NETHERITE).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.END_ENTRY).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.END_PROGRESS).difficultyMax(2, 2, 2, 2, 2, 2).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.END_SHIP).difficultyMax(1, 1, 1, 1, 1, 1).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.IGLOO).difficultyMax(8, 8, 8, 8, 8, 8).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.ANCIENT_CITY).difficultyMax(4, 4, 4, 4, 4, 4).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.WITCH_HUT).difficultyMax(2, 2, 2, 2, 2, 2).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.STRONGHOLD).difficultyMax(2, 2, 2, 2, 2, 2).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.WOODLAND_MANSION).difficultyMax(9, 9, 9, 9, 9, 9).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.TRAIL_RUINS).difficultyMax(3, 3, 3, 3, 3, 3).build());
    }
}
