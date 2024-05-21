package de.rasmusantons.enigmaticsbingogoals;

import io.github.gaming32.bingo.Bingo;
import net.minecraft.resources.ResourceLocation;

public final class EnigmaticsBingoTags {
    private EnigmaticsBingoTags() {
    }

    // BingoTags.NEVER 3
    public static final ResourceLocation NEVER_TAKE_DAMAGE = create("never_take_damage");
    public static final ResourceLocation PLAYER_KILL = create("player_kill");
    // TODO: advancements 1
    // TODO: buried_treasure 1
    // TODO: pvp 1
    // TODO: overtakable 1
    public static final ResourceLocation COVER_DISTANCE = create("cover_distance");
    // TODO: golden_apple 1
    // TODO: puffer_fish 1
    // TODO: get_effect_batch 1
    // TODO: get_effect 2
    // TODO: milk 1
    // TODO: level 2
    // TODO: reach_level 1
    // TODO: overworld_entry 2
    // TODO: chicken 1
    public static final ResourceLocation REACH_WORLD_LIMIT = create("reach_world_limit");
    public static final ResourceLocation DIE_TO = create("die_to");
    // TODO: caving 2
    // TODO: mountain 2
    // TODO: tame_animal 1
    // TODO: breed_mob 2
    // TODO: unique_mobs 1
    // TODO: unique_neutral_mobs 1
    // TODO: unique_hostile_mobs 1
    // TODO: kill_mob 3
    // TODO: kill_mob_batch 1
    // BingoTags.VILLAGE 3
    // TODO: bucket_with_mob 1
    public static final ResourceLocation MUSIC_DISC = create("music_disc");
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
    public static final ResourceLocation BEEHIVE = create("beehive");
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

    

    private static ResourceLocation create(String name) {
        return new ResourceLocation(Bingo.MOD_ID, name);
    }
}
