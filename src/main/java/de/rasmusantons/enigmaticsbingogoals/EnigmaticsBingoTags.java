package de.rasmusantons.enigmaticsbingogoals;

import io.github.gaming32.bingo.Bingo;
import net.minecraft.resources.ResourceLocation;

public final class EnigmaticsBingoTags {
    // BingoTags.NEVER 3
    public static final ResourceLocation NEVER_TAKE_DAMAGE = create("never_take_damage");
    public static final ResourceLocation PLAYER_KILL = create("player_kill");
    public static final ResourceLocation ADVANCEMENTS = create("advancements");
    public static final ResourceLocation BURIED_TREASURE = create("buried_treasure");
    public static final ResourceLocation PVP = create("pvp");
    // TODO: overtakable 1
    public static final ResourceLocation COVER_DISTANCE = create("cover_distance");
    public static final ResourceLocation PUFFER_FISH = create("puffer_fish");
    public static final ResourceLocation GET_EFFECT = create("get_effect");
    public static final ResourceLocation MILK = create("milk");
    public static final ResourceLocation LEVEL = create("level");
    public static final ResourceLocation REACH_LEVEL = create("reach_level");
    public static final ResourceLocation OVERWORLD_ENTRY = create("overworld_entry");
    public static final ResourceLocation REACH_WORLD_LIMIT = create("reach_world_limit");
    public static final ResourceLocation DIE_TO = create("die_to");
    public static final ResourceLocation CAVING = create("caving");
    public static final ResourceLocation MOUNTAIN = create("mountain");
    public static final ResourceLocation TAME_ANIMAL = create("tame_animal");
    public static final ResourceLocation BREED_MOB = create("breed_mob");
    public static final ResourceLocation KILL_MOB = create("kill_mob");
    // BingoTags.VILLAGE 3
    public static final ResourceLocation BUCKET_WITH_MOB = create("bucket_with_mob");
    public static final ResourceLocation ARMOR = create("armor");
    public static final ResourceLocation PLANT_BATCH = create("plant_batch");
    public static final ResourceLocation USE_WORKSTATION = create("use_workstation");
    public static final ResourceLocation LOOM = create("loom");
    public static final ResourceLocation UNIQUE_FOOD = create("unique_food");
    public static final ResourceLocation SILK_TOUCH = create("silk_touch");
    public static final ResourceLocation BOW = create("bow");
    public static final ResourceLocation PIG = create("pig");
    public static final ResourceLocation SADDLE = create("saddle");
    public static final ResourceLocation AMETHYST = create("amethyst");
    public static final ResourceLocation WRITE_BOOK = create("write_book");
    public static final ResourceLocation OUTPOST = create("outpost");
    public static final ResourceLocation REDSTONE = create("redstone");
    public static final ResourceLocation OCEAN_MONUMENT = create("ocean_monument");
    public static final ResourceLocation RAID = create("raid");
    public static final ResourceLocation RARE_COLLECTIBLE_BATCH = create("rare_collectible_batch");
    public static final ResourceLocation WALL = create("wall");
    public static final ResourceLocation STEW = create("stew");
    public static final ResourceLocation FULL_TOOL_SET = create("full_tool_set");
    public static final ResourceLocation SHIPWRECK = create("shipwreck");
    public static final ResourceLocation MINESHAFT = create("mineshaft");
    public static final ResourceLocation SLIME = create("slime");
    public static final ResourceLocation BEEHIVE = create("beehive");
    public static final ResourceLocation JUNGLE = create("jungle");
    public static final ResourceLocation ANVIL = create("anvil");
    public static final ResourceLocation NETHER_ENTRY = create("nether_entry");
    public static final ResourceLocation GROW_TREE = create("grow_tree");
    public static final ResourceLocation BIOMES = create("biome");
    public static final ResourceLocation OVERWORLD_EXPLORE = create("overworld_explore");
    public static final ResourceLocation NETHER_EXPLORE = create("nether_explore");
    public static final ResourceLocation GHAST = create("ghast");
    public static final ResourceLocation STRIDER = create("strider");
    public static final ResourceLocation EYE_OF_ENDER = create("eye_of_ender");
    public static final ResourceLocation BLAZE_POWDER = create("blaze_powder");
    public static final ResourceLocation POTIONS = create("potions");
    public static final ResourceLocation INSTANT_DAMAGE = create("instant_damage");
    public static final ResourceLocation STRAY = create("stray");
    public static final ResourceLocation WITHER_SKULL = create("wither_skull");
    public static final ResourceLocation FORTRESS = create("fortress");
    public static final ResourceLocation CRIMSON_FOREST = create("crimson_forest");
    public static final ResourceLocation WARPED_FOREST = create("warped_forest");
    public static final ResourceLocation SOUL_SAND = create("soul_sand");
    public static final ResourceLocation BARTERING = create("bartering");
    public static final ResourceLocation NETHER_LATE = create("nether_late");
    public static final ResourceLocation NETHERITE = create("netherite");
    public static final ResourceLocation END_ENTRY = create("end_entry");
    public static final ResourceLocation END_PROGRESS = create("end_progress");
    public static final ResourceLocation END_SHIP = create("end_ship");
    public static final ResourceLocation IGLOO = create("igloo");
    public static final ResourceLocation ANCIENT_CITY = create("ancient_city");
    public static final ResourceLocation WITCH_HUT = create("witch_hut");
    public static final ResourceLocation STRONGHOLD = create("stronghold");
    public static final ResourceLocation WOODLAND_MANSION = create("woodland_mansion");
    public static final ResourceLocation TRAIL_RUINS = create("trail_ruins");
    public static final ResourceLocation TRIAL_CHAMBER = create("trial_chamber");
    public static final ResourceLocation BREEZE = create("breeze");
    public static final ResourceLocation SILVERFISH = create("silverfish");
    public static final ResourceLocation SWAMP = create("swamp");
    public static final ResourceLocation SIGN = create("sign");
    public static final ResourceLocation BASTION = create("bastion");
    public static final ResourceLocation GLOW_INK = create("glow_ink");
    public static final ResourceLocation GOAT = create("goat");
    public static final ResourceLocation MOSS = create("moss");

    private EnigmaticsBingoTags() {
    }

    private static ResourceLocation create(String name) {
        return ResourceLocation.fromNamespaceAndPath(Bingo.MOD_ID, name);
    }
}
