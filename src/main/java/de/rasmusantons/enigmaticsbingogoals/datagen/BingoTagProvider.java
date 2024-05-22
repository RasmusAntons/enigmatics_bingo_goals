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
        // TODO: buried_treasure 1
        // TODO: pvp 1
        // TODO: overtakable 1
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.COVER_DISTANCE).difficultyMax(1, 1, 1, 1, 1, 1).build());
        // TODO: golden_apple 1
        // TODO: puffer_fish 1
        // TODO: get_effect_batch 1
        // TODO: get_effect 2
        // TODO: milk 1
        // TODO: level 2
        // TODO: reach_level 1
        // TODO: overworld_entry 2
        // TODO: chicken 1
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.REACH_WORLD_LIMIT).difficultyMax(2, 2, 2, 2, 2, 2).build());
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.DIE_TO).difficultyMax(2, 2, 2, 2, 2, 2).build());
        // TODO: caving 2
        // TODO: mountain 2
        // TODO: tame_animal 1
        // TODO: breed_mob 2
        // TODO: unique_mobs 1
        // TODO: unique_neutral_mobs 1
        // TODO: unique_hostile_mobs 1
        // TODO: kill_mob 3
        // TODO: kill_mob_batch 1
        tagAdder.accept(BingoTag.builder(BingoTags.VILLAGE).difficultyMax(3, 3, 3, 3, 3, 3).build());
        // TODO: bucket_with_mob 1
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.MUSIC_DISC).difficultyMax(1, 1, 1, 1, 1, 1).build());
        // TODO: armor 1
        // TODO: plant_batch 1
        // TODO: seeds 1
        // TODO: lush_cave 1
        // TODO: use_workstation 1
        // TODO: loom 1
        // TODO: unique_food 1
        // TODO: silk_touch 1
        // TODO: bow 1
        // TODO: saddle 1
        // TODO: amethyst 1
        // TODO: book 1
        // TODO: outpost 2
        // TODO: redstone 1
        // TODO: ocean_monument 1
        // TODO: raid 1
        // TODO: rare_collectible_batch 1
        // TODO: wall 1
        // TODO: stew 1
        // TODO: full_tool_set 1
        // TODO: shipwreck 1
        // TODO: mineshaft 1
        // TODO: terracotta 1
        // TODO: slime 1
        tagAdder.accept(BingoTag.builder(EnigmaticsBingoTags.BEEHIVE).difficultyMax(1, 1, 1, 1, 1, 1).build());
        // TODO: jungle 1
        // TODO: anvil 1
        // TODO: concrete 1
        // TODO: wool 1
        // TODO: nether_entry 3
        // TODO: nether_explore 2
        // TODO: ghast 1
        // TODO: strider 1
        // TODO: eye_of_ender 1
        // TODO: blaze_powder 2
        // TODO: potions 1
        // TODO: instant_damage 1
        // TODO: saturation 1
        // TODO: weakness 1
        // TODO: night_vision 1
        // TODO: slowness 1
        // TODO: leaping 1
        // TODO: poison 1
        // TODO: wither_skull 1
        // TODO: fortress 2
        // TODO: crimson_forest 2
        // TODO: warped_forest 2
        // TODO: soul_sand 1
        // TODO: bartering 1
        // TODO: nether_late 2
        // TODO: netherite 1
        // TODO: end_entry 1
        // TODO: end_progress 2
        // TODO: end_ship 1
        // TODO: igloo 8
        // TODO: ancient_city 4
        // TODO: witch_hut 2
        // TODO: woodland_mansion 9
        // TODO: trail_ruins 3
    }
}
