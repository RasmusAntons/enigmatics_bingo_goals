package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoGoals;
import net.minecraft.resources.Identifier;

public final class EnigmaticsBingoGoalIds {
    private EnigmaticsBingoGoalIds() {
    }

    public static final class VeryEasy {
        private VeryEasy() {
        }

        private static final String PREFIX = "very_easy/";
        private static Identifier id(String path) {
            return Identifier.fromNamespaceAndPath(EnigmaticsBingoGoals.MOD_ID, PREFIX + path);
        }

        public static final Identifier BREED_CHICKEN = id("breed_chicken");
        public static final Identifier CROUCH_250_METERS = id("crouch_500_meters");
        public static final Identifier EMPTY_HUNGER = id("empty_hunger");
        public static final Identifier FILL_A_COMPOSTER = id("fill_a_composter");
        public static final Identifier FULL_UNIQUE_INVENTORY = id("full_unique_inventory");
        public static final Identifier HATCH_BABY_CHICKEN = id("hatch_baby_chicken");
        public static final Identifier OBTAIN_ALL_WOODEN_TOOLS = id("obtain_all_wooden_tools");
        public static final Identifier OBTAIN_ALL_STONE_TOOLS = id("obtain_all_stone_tools");
        public static final Identifier OBTAIN_BLACK_GLAZED_TERRACOTTA = id("obtain_black_glazed_terracotta");
        public static final Identifier OBTAIN_BLUE_GLAZED_TERRACOTTA = id("obtain_blue_glazed_terracotta");
        public static final Identifier OBTAIN_GRAY_GLAZED_TERRACOTTA = id("obtain_gray_glazed_terracotta");
        public static final Identifier OBTAIN_ORANGE_GLAZED_TERRACOTTA = id("obtain_orange_glazed_terracotta");
        public static final Identifier OBTAIN_BOOKSHELF = id("obtain_bookshelf");
        public static final Identifier OBTAIN_WHITE_GLAZED_TERRACOTTA = id("obtain_white_glazed_terracotta");
        public static final Identifier REACH_BUILD_LIMIT = id("reach_build_limit");
        public static final Identifier REACH_WORLD_CENTER = id("reach_world_center");
        public static final Identifier SPRINT_1K_METERS = id("sprint_1k_meters");
        public static final Identifier STAND_ON_BEDROCK = id("stand_on_bedrock");
        public static final Identifier DIE_TO_SUFFOCATION = id("die_to_suffocation");
        public static final Identifier KILL_BABY_MOB = id("kill_baby_mob");
    }

    public static final class Easy {
        private Easy() {
        }

        private static final String PREFIX = "easy/";
        private static Identifier id(String path) {
            return Identifier.fromNamespaceAndPath(EnigmaticsBingoGoals.MOD_ID, PREFIX + path);
        }

        public static final Identifier NEVER_SEEDS = id("never_seeds");
        public static final Identifier NEVER_TOUCH_WATER = id("never_touch_water");
        public static final Identifier NEVER_FALL_DAMAGE = id("never_fall_damage");
        public static final Identifier NEVER_FIRE_DAMAGE = id("never_fire_damage");
        public static final Identifier NEVER_LEVELS = id("never_levels");
        public static final Identifier GET_ADVANCEMENTS = id("get_advancements");
        public static final Identifier OBTAIN_HEART_OF_THE_SEA = id("obtain_heart_of_the_sea");
        public static final Identifier OBTAIN_POTION_OF_WATER_BREATHING = id("obtain_potion_of_water_breathing");
        public static final Identifier GET_SOME_EFFECTS = id("get_some_effects");
        public static final Identifier GET_POISON = id("get_poison");
        public static final Identifier GET_JUMP_BOOST = id("get_jump_boost");
        public static final Identifier GET_BLINDNESS = id("get_blindness");
        public static final Identifier GET_SATURATION = id("get_saturation");
        public static final Identifier GET_ABSORPTION = id("get_absorption");
        public static final Identifier EAT_SUSPICIOUS_STEW = id("eat_suspicious_stew");
        public static final Identifier CLEAR_EFFECT_WITH_MILK = id("clear_effect_with_milk");
        public static final Identifier REACH_LEVELS = id("reach_levels");
        public static final Identifier OBTAIN_SOME_SMALL_FLOWERS = id("obtain_some_small_flowers");
        public static final Identifier DEAL_500_HEARTS_OF_DAMAGE = id("deal_500_hearts_of_damage");
        public static final Identifier DIE_TO_BEE = id("die_to_bee");
        public static final Identifier DIE_TO_FIREWORKS = id("die_to_fireworks");
        public static final Identifier BREAK_DIAMOND_ORE = id("break_diamond_ore");
        public static final Identifier OBTAIN_REPEATER = id("obtain_repeater");
        public static final Identifier OBTAIN_POWERED_RAIL = id("obtain_powered_rail");
        public static final Identifier OBTAIN_DETECTOR_RAIL = id("obtain_detector_rail");
        public static final Identifier OBTAIN_ACTIVATOR_RAIL = id("obtain_activator_rail");
        public static final Identifier OBTAIN_PISTON = id("obtain_piston");
        public static final Identifier TAME_CAT = id("tame_cat");
        public static final Identifier TAME_WOLF = id("tame_wolf");
        public static final Identifier BREED_RABBIT = id("breed_rabbit");
        public static final Identifier KILL_GHAST = id("kill_ghast");
        public static final Identifier KILL_SNOW_GOLEM = id("kill_snow_golem");
        public static final Identifier KILL_50_MOBS = id("kill_50_mobs");
        public static final Identifier KILL_100_MOBS = id("kill_100_mobs");
        public static final Identifier KILL_15_ARTHROPODS = id("kill_15_arthropods");
        public static final Identifier KILL_30_ARTHROPODS = id("kill_30_arthropods");
        public static final Identifier KILL_20_AQUATIC_MOBS = id("kill_20_aquatic_mobs");
        public static final Identifier KILL_40_AQUATIC_MOBS = id("kill_40_aquatic_mobs");
        public static final Identifier KILL_30_UNDEAD_MOBS = id("kill_30_undead_mobs");
        public static final Identifier KILL_50_UNDEAD_MOBS = id("kill_50_undead_mobs");
        public static final Identifier KILL_SOME_UNIQUE_MOBS = id("kill_some_unique_mobs");
        public static final Identifier CLEAN_ARMOR_IN_CAULDRON = id("clean_armor_in_cauldron");
        public static final Identifier FILL_A_CHISELED_BOOKSHELF = id("fill_a_chiseled_bookshelf");
        public static final Identifier GET_ADVANCEMENT_WHAT_A_DEAL = id("get_advancement_what_a_deal");
        public static final Identifier EAT_RABBIT_STEW = id("eat_rabbit_stew");
        public static final Identifier ARMOR_STAND_FULL_ARMOR = id("armor_stand_full_armor");
        public static final Identifier WEAR_FULL_LEATHER = id("wear_full_leather");
        public static final Identifier WEAR_FULL_IRON = id("wear_full_iron");
        public static final Identifier EAT_GLOW_BERRIES = id("eat_glow_berries");
        public static final Identifier USE_CARTOGRAPHY_TABLE = id("use_cartography_table");
        public static final Identifier USE_FLOWER_PATTERN = id("use_flower_pattern");
        public static final Identifier EAT_SOME_UNIQUE_FOODS = id("eat_some_unique_foods");
        public static final Identifier SIGN_BOOK_AND_QUILL = id("sign_book_and_quill");
        public static final Identifier MAKE_COPY_OF_COPY = id("make_copy_of_copy");
        public static final Identifier WEAR_PUMPKIN = id("wear_pumpkin");
        public static final Identifier OBTAIN_MOSSY_STONE_BRICK_WALL = id("obtain_mossy_stone_brick_wall");
        public static final Identifier OBTAIN_MOSSY_COBBLESTONE_WALL = id("obtain_mossy_cobblestone_wall");
        public static final Identifier OBTAIN_POLISHED_TUFF_WALL = id("obtain_polished_tuff_wall");
        public static final Identifier OBTAIN_ALL_IRON_TOOLS = id("obtain_all_iron_tools");
        public static final Identifier OBTAIN_ALL_COPPER_TOOLS = id("obtain_all_copper_tools");
        public static final Identifier OBTAIN_STACK_OF_RED_CONCRETE = id("obtain_stack_of_red_concrete");
        public static final Identifier OBTAIN_STACK_OF_YELLOW_CONCRETE = id("obtain_stack_of_yellow_concrete");
        public static final Identifier OBTAIN_STACK_OF_ORANGE_CONCRETE = id("obtain_stack_of_orange_concrete");
        public static final Identifier OBTAIN_STACK_OF_BLACK_CONCRETE = id("obtain_stack_of_black_concrete");
        public static final Identifier OBTAIN_STACK_OF_WHITE_CONCRETE = id("obtain_stack_of_white_concrete");
        public static final Identifier OBTAIN_STACK_OF_GRAY_CONCRETE = id("obtain_stack_of_gray_concrete");
        public static final Identifier OBTAIN_STACK_OF_LIGHT_GRAY_CONCRETE = id("obtain_stack_of_light_gray_concrete");
        public static final Identifier OBTAIN_STACK_OF_PINK_CONCRETE = id("obtain_stack_of_pink_concrete");
        public static final Identifier OBTAIN_STACK_OF_MAGENTA_CONCRETE = id("obtain_stack_of_magenta_concrete");
        public static final Identifier OBTAIN_STACK_OF_BLUE_CONCRETE = id("obtain_stack_of_blue_concrete");
        public static final Identifier OBTAIN_STACK_OF_PURPLE_CONCRETE = id("obtain_stack_of_purple_concrete");
        public static final Identifier OBTAIN_STACK_OF_RED_WOOL = id("obtain_stack_of_red_wool");
        public static final Identifier OBTAIN_STACK_OF_YELLOW_WOOL = id("obtain_stack_of_yellow_wool");
        public static final Identifier OBTAIN_STACK_OF_ORANGE_WOOL = id("obtain_stack_of_orange_wool");
        public static final Identifier OBTAIN_STACK_OF_BLACK_WOOL = id("obtain_stack_of_black_wool");
        public static final Identifier OBTAIN_STACK_OF_WHITE_WOOL = id("obtain_stack_of_white_wool");
        public static final Identifier OBTAIN_STACK_OF_GRAY_WOOL = id("obtain_stack_of_gray_wool");
        public static final Identifier OBTAIN_STACK_OF_LIGHT_GRAY_WOOL = id("obtain_stack_of_light_gray_wool");
        public static final Identifier OBTAIN_STACK_OF_PINK_WOOL = id("obtain_stack_of_pink_wool");
        public static final Identifier OBTAIN_STACK_OF_MAGENTA_WOOL = id("obtain_stack_of_magenta_wool");
        public static final Identifier OBTAIN_STACK_OF_BLUE_WOOL = id("obtain_stack_of_blue_wool");
        public static final Identifier OBTAIN_STACK_OF_PURPLE_WOOL = id("obtain_stack_of_purple_wool");
        public static final Identifier ANGER_ZOMBIFIED_PIGLIN = id("anger_zombified_piglin");
        public static final Identifier GET_ADVANCEMENT_WE_NEED_TO_GO_DEEPER = id("get_advancement_we_need_to_go_deeper");
        public static final Identifier GROW_TREE_IN_NETHER = id("grow_tree_in_nether");
        public static final Identifier GET_GLOWING = id("get_glowing");
        public static final Identifier GET_ADVANCEMENT_OH_SHINY = id("get_advancement_oh_shiny");
        public static final Identifier DIE_TO_DOLPHIN = id("die_to_dolphin");
        public static final Identifier DIE_TO_IRON_GOLEM = id("die_to_iron_golem");
        public static final Identifier DIE_TO_STALACTITE = id("die_to_stalactite");
        public static final Identifier EAT_POISONOUS_POTATO = id("eat_poisonous_potato");
        public static final Identifier GET_ADVANCEMENT_RETURN_TO_SENDER = id("get_advancement_return_to_sender");
        public static final Identifier KILL_ZOMBIE_VILLAGER = id("kill_zombie_villager");
        public static final Identifier NEVER_50_DAMAGE = id("never_50_damage");
        public static final Identifier OBTAIN_ALL_GOLDEN_TOOLS = id("obtain_all_golden_tools");
        public static final Identifier OBTAIN_ALL_RAW_ORE_BLOCKS = id("obtain_all_raw_ore_blocks");
        public static final Identifier OBTAIN_CAKE = id("obtain_cake");
        public static final Identifier OBTAIN_DAYLIGHT_DETECTOR = id("obtain_daylight_detector");
        public static final Identifier OBTAIN_DISPENSER = id("obtain_dispenser");
        public static final Identifier OBTAIN_FLOWERING_AZALEA = id("obtain_flowering_azalea");
        public static final Identifier OBTAIN_GRASS_BLOCK = id("obtain_grass_block");
        public static final Identifier OBTAIN_MUD_BRICK_WALL = id("obtain_mud_brick_wall");
        public static final Identifier OBTAIN_SOME_MUSIC_DISCS = id("obtain_some_music_discs");
        public static final Identifier DIE_TO_TNT_MINECART = id("die_to_tnt_minecart");
        public static final Identifier KILL_SOME_UNIQUE_HOSTILE_MOBS = id("kill_some_unique_hostile_mobs");
        public static final Identifier KILL_BAT_WITH_ARROW = id("kill_bat_with_arrow");
        public static final Identifier KILL_RABBIT_WITH_ARROW = id("kill_rabbit_with_arrow");
        public static final Identifier OBTAIN_HANGING_SIGN = id("obtain_hanging_sign");
        public static final Identifier OBTAIN_GLOW_ITEM_FRAME = id("obtain_glow_item_frame");
        public static final Identifier OBTAIN_TARGET = id("obtain_target");
        public static final Identifier OBTAIN_CHISELED_COPPER = id("obtain_chiseled_copper");
        public static final Identifier OBTAIN_CHISELED_DEEPSLATE = id("obtain_chiseled_deepslate");
        public static final Identifier OBTAIN_CHISELED_NETHER_BRICKS = id("obtain_chiseled_nether_bricks");
        public static final Identifier OBTAIN_CHISELED_POLISHED_BLACKSTONE = id("obtain_chiseled_polished_blackstone");
        public static final Identifier OBTAIN_BLACK_STAINED_GLASS_PANE = id("obtain_black_stained_glass_pane");
        public static final Identifier OBTAIN_BLUE_STAINED_GLASS_PANE = id("obtain_blue_stained_glass_pane");
        public static final Identifier OBTAIN_GRAY_STAINED_GLASS_PANE = id("obtain_gray_stained_glass_pane");
        public static final Identifier OBTAIN_LIGHT_GRAY_STAINED_GLASS_PANE = id("obtain_light_gray_stained_glass_pane");
        public static final Identifier OBTAIN_MAGENTA_STAINED_GLASS_PANE = id("obtain_magenta_stained_glass_pane");
        public static final Identifier OBTAIN_ORANGE_STAINED_GLASS_PANE = id("obtain_orange_stained_glass_pane");
        public static final Identifier OBTAIN_PINK_STAINED_GLASS_PANE = id("obtain_pink_stained_glass_pane");
        public static final Identifier OBTAIN_PURPLE_STAINED_GLASS_PANE = id("obtain_purple_stained_glass_pane");
        public static final Identifier OBTAIN_RED_STAINED_GLASS_PANE = id("obtain_red_stained_glass_pane");
        public static final Identifier OBTAIN_WHITE_STAINED_GLASS_PANE = id("obtain_white_stained_glass_pane");
        public static final Identifier OBTAIN_YELLOW_STAINED_GLASS_PANE = id("obtain_yellow_stained_glass_pane");
        public static final Identifier UNIQUE_FOODS_ON_CAMPFIRE = id("unique_foods_on_campfire");
        public static final Identifier OBTAIN_BELL = id("obtain_bell");
        public static final Identifier USE_LOOM = id("use_loom");
        public static final Identifier SURVIVE_EXPLOSION = id("survive_explosion");
        public static final Identifier NAME_A_SHEEP_JEB = id("name_a_sheep_jeb");
        public static final Identifier FEED_GOLDEN_DANDELION_TO_DIFFERENT_MOBS = id("feed_golden_dandelion_to_different_mobs");
        public static final Identifier NEVER_CROUCH = id("never_crouch");
    }

    public static final class Medium {
        private Medium() {
        }

        private static final String PREFIX = "medium/";
        private static Identifier id(String path) {
            return Identifier.fromNamespaceAndPath(EnigmaticsBingoGoals.MOD_ID, PREFIX + path);
        }

        public static final Identifier NEVER_OBTAIN_CRAFTING_TABLE = id("never_obtain_crafting_table");
        public static final Identifier NEVER_25_DAMAGE = id("never_25_damage");
        public static final Identifier NEVER_DIE = id("never_die");
        public static final Identifier KILL_ENEMY_PLAYER = id("kill_enemy_player");
        public static final Identifier GET_ADVANCEMENTS = id("get_advancements");
        public static final Identifier OBTAIN_DARK_PRISMARINE = id("obtain_dark_prismarine");
        public static final Identifier GIVE_EFFECT_TO_OTHER_TEAM = id("give_effect_to_other_team");
        public static final Identifier PLAY_MUSIC_TO_OTHER_TEAM = id("play_music_to_other_team");
        public static final Identifier HIT_OTHER_TEAM_WITH_SNOWBALL = id("hit_other_team_with_snowball");
        public static final Identifier HIT_OTHER_TEAM_WITH_WIND_CHARGE = id("hit_other_team_with_wind_charge");
        public static final Identifier VISIT_SOME_UNIQUE_OVERWORLD_BIOMES = id("visit_some_unique_overworld_biomes");
        public static final Identifier GET_SOME_EFFECTS = id("get_some_effects");
        public static final Identifier GET_SLOWNESS = id("get_slowness");
        public static final Identifier GET_MINING_FATIGUE = id("get_mining_fatigue");
        public static final Identifier GET_NAUSEA = id("get_nausea");
        public static final Identifier DIE_TO_INTENTIONAL_GAME_DESIGN = id("die_to_intentional_game_design");
        public static final Identifier DIE_TO_GOAT = id("die_to_goat");
        public static final Identifier DIE_TO_MAGIC = id("die_to_magic");
        public static final Identifier DIE_TO_ANVIL = id("die_to_anvil");
        public static final Identifier OBTAIN_OBSERVER = id("obtain_observer");
        public static final Identifier OBTAIN_STICKY_PISTON = id("obtain_sticky_piston");
        public static final Identifier OBTAIN_REDSTONE_LAMP = id("obtain_redstone_lamp");
        public static final Identifier OBTAIN_COMPARATOR = id("obtain_comparator");
        public static final Identifier BREAK_EMERALD_ORE = id("break_emerald_ore");
        public static final Identifier KILL_SILVERFISH = id("kill_silverfish");
        public static final Identifier KILL_BREEZE = id("kill_breeze");
        public static final Identifier KILL_BOGGED = id("kill_bogged");
        public static final Identifier TAME_OCELOT = id("tame_ocelot");
        public static final Identifier BREED_PIG = id("breed_pig");
        public static final Identifier BREED_FOX = id("breed_fox");
        public static final Identifier BREED_ARMADILLO = id("breed_armadillo");
        public static final Identifier BREED_HORSE = id("breed_horse");
        public static final Identifier BREED_AXOLOTL = id("breed_axolotl");
        public static final Identifier BREED_STRIDER = id("breed_strider");
        public static final Identifier KILL_SOME_UNIQUE_MOBS = id("kill_some_unique_mobs");
        public static final Identifier KILL_SOME_UNIQUE_HOSTILE_MOBS = id("kill_some_unique_hostile_mobs");
        public static final Identifier KILL_MOB_WHILE_DEAD = id("kill_mob_while_dead");
        public static final Identifier KILL_WITCH = id("kill_witch");
        public static final Identifier KILL_VINDICATOR = id("kill_vindicator");
        public static final Identifier KILL_ELDER_GUARDIAN = id("kill_elder_guardian");
        public static final Identifier OBTAIN_MUSHROOM_STEM = id("obtain_mushroom_stem");
        public static final Identifier OBTAIN_WARPED_NYLIUM = id("obtain_warped_nylium");
        public static final Identifier OBTAIN_CHAINMAIL_ARMOR = id("obtain_chainmail_armor");
        public static final Identifier OBTAIN_4_DIFFERENT_SEEDS = id("obtain_4_different_seeds");
        public static final Identifier EAT_BEETROOT_SOUP = id("eat_beetroot_soup");
        public static final Identifier OBTAIN_TROPICAL_FISH_BUCKET = id("obtain_tropical_fish_bucket");
        public static final Identifier OBTAIN_TADPOLE_BUCKET = id("obtain_tadpole_bucket");
        public static final Identifier GET_ADVANCEMENT_SOUND_OF_MUSIC = id("get_advancement_sound_of_music");
        public static final Identifier EQUIP_WOLF_ARMOR = id("equip_wolf_armor");
        public static final Identifier WEAR_FULL_GOLD = id("wear_full_gold");
        public static final Identifier OBTAIN_SOME_SAPLINGS = id("obtain_some_saplings");
        public static final Identifier OBTAIN_SOME_BONEMEALABLE_BLOCKS = id("obtain_some_bonemealable_blocks");
        public static final Identifier EAT_SOME_UNIQUE_FOODS = id("eat_some_unique_foods");
        public static final Identifier GET_ADVANCEMENT_SNIPER_DUEL = id("get_advancement_sniper_duel");
        public static final Identifier GET_ADVANCEMENT_BULLSEYE = id("get_advancement_bullseye");
        public static final Identifier RIDE_PIG_FOR_300_METERS = id("ride_pig_for_300_meters");
        public static final Identifier RIDE_PIG_LAVA = id("ride_pig_lava");
        public static final Identifier USE_CARROT_ON_A_STICK = id("use_carrot_on_a_stick");
        public static final Identifier RIDE_HORSE = id("ride_horse");
        public static final Identifier GET_ANY_SPYGLASS_ADVANCEMENT = id("get_any_spyglass_advancement");
        public static final Identifier OBTAIN_SPONGE = id("obtain_sponge");
        public static final Identifier OBTAIN_SOME_TRIM_TEMPLATES = id("obtain_some_trim_templates");
        public static final Identifier OBTAIN_COBWEB = id("obtain_cobweb");
        public static final Identifier BREAK_MOB_SPAWNER = id("break_mob_spawner");
        public static final Identifier OBTAIN_CYAN_GLAZED_TERRACOTTA = id("obtain_cyan_glazed_terracotta");
        public static final Identifier OBTAIN_SOME_DIFFERENT_COLORS_OF_TERRACOTTA = id("obtain_some_different_colors_of_terracotta");
        public static final Identifier OBTAIN_SLIME_BLOCK = id("obtain_slime_block");
        public static final Identifier OBTAIN_HONEY_BLOCK = id("obtain_honey_block");
        public static final Identifier OBTAIN_SCAFFOLDING = id("obtain_scaffolding");
        public static final Identifier GET_ADVANCEMENT_ENCHANTER = id("get_advancement_enchanter");
        public static final Identifier OBTAIN_STACK_OF_CYAN_WOOL = id("obtain_stack_of_cyan_wool");
        public static final Identifier OBTAIN_STACK_OF_GREEN_WOOL = id("obtain_stack_of_green_wool");
        public static final Identifier USE_GLOW_INK_ON_CRIMSON_SIGN = id("use_glow_ink_on_crimson_sign");
        public static final Identifier USE_GLOW_INK_ON_WARPED_SIGN = id("use_glow_ink_on_warped_sign");
        public static final Identifier GET_ADVANCEMENT_A_TERRIBLE_FORTRESS = id("get_advancement_a_terrible_fortress");
        public static final Identifier GET_ADVANCEMENT_THOSE_WERE_THE_DAYS = id("get_advancement_those_were_the_days");
        public static final Identifier GET_ADVANCEMENT_NOT_QUITE_NINE_LIVES = id("get_advancement_not_quite_nine_lives");
        public static final Identifier GET_ADVANCEMENT_HOT_TOURIST_DESTINATIONS = id("get_advancement_hot_tourist_destinations");
        public static final Identifier OBTAIN_END_CRYSTAL = id("obtain_end_crystal");
        public static final Identifier OBTAIN_ENDER_EYE = id("obtain_ender_eye");
        public static final Identifier OBTAIN_ENDER_CHEST = id("obtain_ender_chest");
        public static final Identifier OBTAIN_POTION_OF_OOZING = id("obtain_potion_of_oozing");
        public static final Identifier OBTAIN_POTION_OF_INFESTATION = id("obtain_potion_of_infestation");
        public static final Identifier OBTAIN_POTION_OF_WEAVING = id("obtain_potion_of_weaving");
        public static final Identifier OBTAIN_POTION_OF_WIND_CHARGING = id("obtain_potion_of_wind_charging");
        public static final Identifier OBTAIN_POTION_OF_STRENGTH = id("obtain_potion_of_strength");
        public static final Identifier OBTAIN_POTION_OF_REGENERATION = id("obtain_potion_of_regeneration");
        public static final Identifier OBTAIN_POTION_OF_HEALING = id("obtain_potion_of_healing");
        public static final Identifier OBTAIN_POTION_OF_SLOWNESS = id("obtain_potion_of_slowness");
        public static final Identifier OBTAIN_POTION_OF_HARMING = id("obtain_potion_of_harming");
        public static final Identifier OBTAIN_POTION_OF_POISON = id("obtain_potion_of_poison");
        public static final Identifier OBTAIN_POTION_OF_NIGHT_VISION = id("obtain_potion_of_night_vision");
        public static final Identifier OBTAIN_POTION_OF_SWIFTNESS = id("obtain_potion_of_swiftness");
        public static final Identifier OBTAIN_SOME_FIRE_CHARGES = id("obtain_some_fire_charges");
        public static final Identifier OBTAIN_NETHERITE_SCRAP = id("obtain_netherite_scrap");
        public static final Identifier BREAK_TURTLE_EGG = id("break_turtle_egg");
        public static final Identifier BREED_HOGLIN = id("breed_hoglin");
        public static final Identifier DIE_TO_LLAMA = id("die_to_llama");
        public static final Identifier DIE_TO_STRAY = id("die_to_stray");
        public static final Identifier EAT_COOKIE = id("eat_cookie");
        public static final Identifier GET_WEAKNESS = id("get_weakness");
        public static final Identifier OBTAIN_COLORED_CANDLE = id("obtain_colored_candle");
        public static final Identifier OBTAIN_GREEN_GLAZED_TERRACOTTA = id("obtain_green_glazed_terracotta");
        public static final Identifier OBTAIN_LIME_GLAZED_TERRACOTTA = id("obtain_lime_glazed_terracotta");
        public static final Identifier OBTAIN_HONEY_BOTTLE = id("obtain_honey_bottle");
        public static final Identifier OBTAIN_POWDER_SNOW_BUCKET = id("obtain_powder_snow_bucket");
        public static final Identifier REACH_LEVELS = id("reach_levels");
        public static final Identifier TAME_PARROT = id("tame_parrot");
        public static final Identifier TAME_SOME_CATS = id("tame_some_cats");
        public static final Identifier TAME_SOME_WOLVES = id("tame_some_wolves");
        public static final Identifier WEAR_4_DIFFERENT_MATERIALS = id("wear_4_different_materials");
        public static final Identifier USE_GRINDSTONE_TO_DISENCHANT = id("use_grindstone_to_disenchant");
        public static final Identifier USE_ANVIL = id("use_anvil");
        public static final Identifier BREED_SOME_UNIQUE_MOBS = id("breed_some_unique_mobs");
        public static final Identifier GET_ADVANCEMENT_SUBSPACE_BUBBLE = id("get_advancement_subspace_bubble");
        public static final Identifier GET_ADVANCEMENT_IS_IT_A_BIRD = id("get_advancement_is_it_a_bird");
        public static final Identifier GET_ADVANCEMENT_IS_IT_A_BALLOON = id("get_advancement_is_it_a_balloon");
        public static final Identifier HUGE_WARPED_FUNGUS_IN_OVERWORLD = id("huge_warped_fungus_in_overworld");
        public static final Identifier KILL_ENDERMITE = id("kill_endermite");
        public static final Identifier KILL_ZOGLIN = id("kill_zoglin");
        public static final Identifier OBTAIN_ALL_DIAMOND_TOOLS = id("obtain_all_diamond_tools");
        public static final Identifier OBTAIN_EXPERIENCE_BOTTLE = id("obtain_experience_bottle");
        public static final Identifier OBTAIN_COPPER_BULB = id("obtain_copper_bulb");
        public static final Identifier GET_ADVANCEMENT_MINECRAFT_TRIALS_EDITION = id("get_advancement_minecraft_trials_edition");
        public static final Identifier GET_ADVANCEMENT_CRAFTERS_CRAFTING_CRAFTERS = id("get_advancement_crafters_crafting_crafters");
        public static final Identifier GET_ADVANCEMENT_WHO_NEEDS_ROCKETS = id("get_advancement_who_needs_rockets");
        public static final Identifier GET_ADVANCEMENT_BLOWBACK = id("get_advancement_blowback");
        public static final Identifier OBTAIN_LINGERING_POTION = id("obtain_lingering_potion");
        public static final Identifier OBTAIN_GILDED_BLACKSTONE = id("obtain_gilded_blackstone");
        public static final Identifier OBTAIN_SEA_LANTERN = id("obtain_sea_lantern");
        public static final Identifier OBTAIN_SOUL_LANTERN = id("obtain_soul_lantern");
        public static final Identifier OBTAIN_SOUL_CAMPFIRE = id("obtain_soul_campfire");
        public static final Identifier OBTAIN_BAMBOO_MOSAIC = id("obtain_bamboo_mosaic");
        public static final Identifier OBTAIN_TINTED_GLASS = id("obtain_tinted_glass");
        public static final Identifier OBTAIN_SOME_MUSIC_DISCS = id("obtain_some_music_discs");
        public static final Identifier OBTAIN_GOAT_HORN = id("obtain_goat_horn");
        public static final Identifier DIE_TO_VINES = id("die_to_vines");
        public static final Identifier GET_ADVANCEMENT_STAY_HYDRATED = id("get_advancement_stay_hydrated");
    }

    public static final class Hard {
        private Hard() {
        }

        private static final String PREFIX = "hard/";
        private static Identifier id(String path) {
            return Identifier.fromNamespaceAndPath(EnigmaticsBingoGoals.MOD_ID, PREFIX + path);
        }

        public static final Identifier GET_ADVANCEMENTS = id("get_advancements");
        public static final Identifier CURE_ZOMBIE_VILLAGER = id("cure_zombie_villager");
        public static final Identifier GET_SOME_EFFECTS = id("get_some_effects");
        public static final Identifier BREED_MULE = id("breed_mule");
        public static final Identifier VISIT_SOME_UNIQUE_OVERWORLD_BIOMES = id("visit_some_unique_overworld_biomes");
        public static final Identifier TAME_SOME_CATS = id("tame_some_cats");
        public static final Identifier TAME_SOME_WOLVES = id("tame_some_wolves");
        public static final Identifier BREED_SOME_UNIQUE_MOBS = id("breed_some_unique_mobs");
        public static final Identifier KILL_SOME_UNIQUE_HOSTILE_MOBS = id("kill_some_unique_hostile_mobs");
        public static final Identifier KILL_ENDER_DRAGON = id("kill_ender_dragon");
        public static final Identifier WEAR_FULL_DIAMOND = id("wear_full_diamond");
        public static final Identifier HUGE_CRIMSON_FUNGUS_IN_OVERWORLD = id("huge_crimson_fungus_in_overworld");
        public static final Identifier USE_SKULL_PATTERN = id("use_skull_pattern");
        public static final Identifier EAT_SOME_UNIQUE_FOODS = id("eat_some_unique_foods");
        public static final Identifier KILL_SOME_UNIQUE_MOBS = id("kill_some_unique_mobs");
        public static final Identifier GET_ADVANCEMENT_IS_IT_A_PLANE = id("get_advancement_is_it_a_plane");
        public static final Identifier GET_HERO_OF_THE_VILLAGE = id("get_hero_of_the_village");
        public static final Identifier GET_POSTMORTAL = id("get_postmortal");
        public static final Identifier OBTAIN_ALL_HORSE_ARMORS = id("obtain_all_horse_armors");
        public static final Identifier GET_ADVANCEMENT_CAREFUL_RESTORATION = id("get_advancement_careful_restoration");
        public static final Identifier OBTAIN_POTION_OF_LEAPING = id("obtain_potion_of_leaping");
        public static final Identifier OBTAIN_POTION_OF_SLOW_FALLING = id("obtain_potion_of_slow_falling");
        public static final Identifier OBTAIN_POTION_OF_THE_TURTLE_MASTER = id("obtain_potion_of_the_turtle_master");
        public static final Identifier GET_ADVANCEMENT_THE_END = id("get_advancement_the_end");
        public static final Identifier OBTAIN_DRAGON_EGG = id("obtain_dragon_egg");
        public static final Identifier GET_ADVANCEMENT_THE_CITY_AT_THE_END_OF_THE_GAME = id("get_advancement_the_city_at_the_end_of_the_game");
        public static final Identifier GET_ADVANCEMENT_GREAT_VIEW_FROM_UP_HERE = id("get_advancement_great_view_from_up_here");
        public static final Identifier EAT_CHORUS_FRUIT = id("eat_chorus_fruit");
        public static final Identifier OBTAIN_ELYTRA = id("obtain_elytra");
        public static final Identifier OBTAIN_DRAGON_HEAD = id("obtain_dragon_head");
        public static final Identifier OBTAIN_PURPUR_BLOCK = id("obtain_purpur_block");
        public static final Identifier NEVER_DAMAGE = id("never_damage");
        public static final Identifier NEVER_LEVELS = id("never_levels");
        public static final Identifier REACH_LEVELS = id("reach_levels");
        public static final Identifier FALL_OUT_OF_WORLD = id("fall_out_of_world");
        public static final Identifier GET_ADVANCEMENT_EYE_SPY = id("get_advancement_eye_spy");
        public static final Identifier GET_ADVANCEMENT_THIS_BOAT_HAS_LEGS = id("get_advancement_this_boat_has_legs");
        public static final Identifier OBTAIN_CRIMSON_NYLIUM = id("obtain_crimson_nylium");
        public static final Identifier OBTAIN_LODESTONE = id("obtain_lodestone");
        public static final Identifier OBTAIN_NETHERITE_INGOT = id("obtain_netherite_ingot");
        public static final Identifier OBTAIN_WITHER_SKELETON_SKULL = id("obtain_wither_skeleton_skull");
        public static final Identifier BREED_WHITE_FROG = id("breed_white_frog");
        public static final Identifier BREED_ORANGE_FROG = id("breed_orange_frog");
        public static final Identifier BREED_GREEN_FROG = id("breed_green_frog");
        public static final Identifier OBTAIN_SOME_SAPLINGS = id("obtain_some_saplings");
        public static final Identifier USE_GLOBE_PATTERN = id("use_globe_pattern");
        public static final Identifier RIDE_HAPPY_GHAST = id("ride_happy_ghast");
    }

    public static final class VeryHard {
        private VeryHard() {
        }

        private static final String PREFIX = "very_hard/";
        private static Identifier id(String path) {
            return Identifier.fromNamespaceAndPath(EnigmaticsBingoGoals.MOD_ID, PREFIX + path);
        }

        public static final Identifier GET_ADVANCEMENTS = id("get_advancements");
        public static final Identifier EAT_SOME_UNIQUE_FOODS = id("eat_some_unique_foods");
        public static final Identifier GET_SOME_EFFECTS = id("get_some_effects");
        public static final Identifier KILL_SOME_UNIQUE_HOSTILE_MOBS = id("kill_some_unique_hostile_mobs");
        public static final Identifier TAME_SOME_CATS = id("tame_some_cats");
        public static final Identifier TAME_SOME_WOLVES = id("tame_some_wolves");
        public static final Identifier SUMMON_THE_WITHER = id("summon_the_wither");
        public static final Identifier KILL_SOME_UNIQUE_MOBS = id("kill_some_unique_mobs");
        public static final Identifier OBTAIN_NETHER_STAR = id("obtain_nether_star");
        public static final Identifier USE_SNOUT_PATTERN = id("use_snout_pattern");
    }
}
