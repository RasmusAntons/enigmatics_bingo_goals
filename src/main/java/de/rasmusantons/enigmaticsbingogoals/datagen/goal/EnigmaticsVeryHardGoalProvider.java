package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoTags;
import de.rasmusantons.enigmaticsbingogoals.datagen.EnigmaticsBingoSynergies;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoEntityTypeTags;
import io.github.gaming32.bingo.data.BingoDifficulties;
import io.github.gaming32.bingo.data.BingoGoal;
import io.github.gaming32.bingo.data.BingoTags;
import io.github.gaming32.bingo.data.icons.CycleIcon;
import io.github.gaming32.bingo.data.icons.IndicatorIcon;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.advancements.critereon.SummonedEntityTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.advancements.packs.VanillaHusbandryAdvancements;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BannerPatterns;

import java.util.Arrays;
import java.util.function.BiConsumer;

public class EnigmaticsVeryHardGoalProvider extends EnigmaticsDifficultyGoalProvider {
    public EnigmaticsVeryHardGoalProvider(BiConsumer<ResourceLocation, BingoGoal> goalAdder, HolderLookup.Provider registries) {
        super(BingoDifficulties.VERY_HARD, goalAdder, registries);
    }

    @Override
    public void addGoals() {
        addGoal(advancementsGoal(id("get_advancements"), 36, 40));
        addGoal(advancementProgressGoal(id("eat_some_unique_foods"),
                ResourceLocation.withDefaultNamespace("husbandry/balanced_diet"), 33, 38)
                .name(Component.translatable("enigmaticsbingogoals.goal.eat_some_unique_foods", 0),
                        subber -> subber.sub("with.0", "count")
                )
                .tooltip(Component.translatable("enigmaticsbingogoals.goal.eat_some_unique_foods.tooltip", Items.CAKE.getDescription()))
                .tags(BingoTags.OVERWORLD, EnigmaticsBingoTags.UNIQUE_FOOD)
                .icon(
                        CycleIcon.infer(Arrays.stream(VanillaHusbandryAdvancements.EDIBLE_ITEMS)),
                        subber -> subber.sub("icons.*.item.count", "count")
                )
        );
        addGoal(numberOfEffectsGoal(id("get_some_effects"), 20, 30));
        addGoal(killEntitiesFromTagGoal(id("kill_some_unique_hostile_mobs"), EnigmaticsBingoEntityTypeTags.HOSTILE, 20, 24, true)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_unique_hostile_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.UNIQUE_HOSTILE_MOBS)
        );
        addGoal(tameSomeCatsGoal(id("tame_some_cats"), 9, 11));
        addGoal(tameSomeWolvesGoal(id("tame_some_wolves"), 6, 9));
        addGoal(BingoGoal.builder(id("summon_the_wither"))
                .criterion("summon", SummonedEntityTrigger.TriggerInstance.summonedEntity(
                        EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(EntityType.WITHER)))
                )
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.WITHER_SKULL, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.NETHER_LATE)
                .name(Component.translatable("enigmaticsbingogoals.goal.summon_the_wither", EntityType.WITHER.getDescription()))
                .antisynergy(EnigmaticsBingoSynergies.WITHER)
                .icon(IndicatorIcon.infer(EntityType.WITHER, Items.WITHER_SKELETON_SKULL))
        );
        addGoal(killEntitiesFromTagGoal(id("kill_some_unique_mobs"), EnigmaticsBingoEntityTypeTags.MOBS, 40, 45, true)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_unique_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.UNIQUE_NEUTRAL_MOBS, EnigmaticsBingoSynergies.UNIQUE_HOSTILE_MOBS)
        );
        addGoal(obtainItemGoal(id("obtain_nether_star"), Items.NETHER_STAR)
                .antisynergy(EnigmaticsBingoSynergies.WITHER)
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.WITHER_SKULL, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.NETHER_LATE)
        );
        addGoal(makeBannerWithPatternItemGoal(id("use_snout_pattern"),  Items.PIGLIN_BANNER_PATTERN,
                BannerPatterns.PIGLIN, "Snout Pattern")
                .tags(BingoTags.NETHER, EnigmaticsBingoTags.NETHER_LATE, EnigmaticsBingoTags.NETHER_EXPLORE, EnigmaticsBingoTags.BASTION)
        );
    }
}
