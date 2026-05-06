package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoDifficulties;
import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoTags;
import de.rasmusantons.enigmaticsbingogoals.datagen.EnigmaticsBingoSynergies;
import de.rasmusantons.enigmaticsbingogoals.tags.EnigmaticsBingoEntityTypeTags;
import io.github.gaming32.bingo.data.goal.BingoGoal;
import io.github.gaming32.bingo.data.icons.CycleIcon;
import io.github.gaming32.bingo.data.icons.IndicatorIcon;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.EntityTypePredicate;
import net.minecraft.advancements.criterion.SummonedEntityTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.packs.VanillaHusbandryAdvancements;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BannerPatterns;

import java.util.Arrays;
import java.util.function.BiConsumer;

import static de.rasmusantons.enigmaticsbingogoals.datagen.goal.EnigmaticsBingoGoalIds.VeryHard.*;

public class EnigmaticsVeryHardGoalProvider extends EnigmaticsDifficultyGoalProvider {
    public EnigmaticsVeryHardGoalProvider(BiConsumer<Identifier, BingoGoal> goalAdder, HolderLookup.Provider registries) {
        super(EnigmaticsBingoDifficulties.VERY_HARD, goalAdder, registries);
    }

    @Override
    public void addGoals() {
        final var entityTypes = registries.lookupOrThrow(Registries.ENTITY_TYPE);
        final var items = registries.lookupOrThrow(Registries.ITEM);

        addGoal(advancementsGoal(GET_ADVANCEMENTS, 36, 40));
        addGoal(advancementProgressGoal(EAT_SOME_UNIQUE_FOODS,
                Identifier.withDefaultNamespace("husbandry/balanced_diet"), 33, 38)
                .name(Component.translatable("enigmaticsbingogoals.goal.eat_some_unique_foods", 0),
                        subber -> subber.sub("with.0", "count")
                )
                .tooltip(Component.translatable("enigmaticsbingogoals.goal.eat_some_unique_foods.tooltip",
                        Component.translatable(Items.CAKE.getDescriptionId()))
                )
                .tags(EnigmaticsBingoTags.OVERWORLD, EnigmaticsBingoTags.UNIQUE_FOOD)
                .icon(
                        CycleIcon.infer(Arrays.stream(VanillaHusbandryAdvancements.EDIBLE_ITEMS)),
                        subber -> subber.sub("icons.*.item.count", "count")
                )
        );
        addGoal(numberOfEffectsGoal(GET_SOME_EFFECTS, 20, 30));
        addGoal(killEntitiesFromTagGoal(KILL_SOME_UNIQUE_HOSTILE_MOBS, EnigmaticsBingoEntityTypeTags.HOSTILE, 20, 24, true)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_unique_hostile_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.UNIQUE_HOSTILE_MOBS)
        );
        addGoal(tameSomeCatsGoal(TAME_SOME_CATS, registries.lookupOrThrow(Registries.CAT_VARIANT), 9, 11));
        addGoal(tameSomeWolvesGoal(TAME_SOME_WOLVES, 6, 9));
        addGoal(BingoGoal.builder(SUMMON_THE_WITHER)
                .criterion("summon", SummonedEntityTrigger.TriggerInstance.summonedEntity(
                        EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityTypes, EntityType.WITHER)))
                )
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.WITHER_SKULL, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.NETHER_LATE)
                .name(Component.translatable("enigmaticsbingogoals.goal.summon_the_wither", EntityType.WITHER.getDescription()))
                .antisynergy(EnigmaticsBingoSynergies.WITHER)
                .icon(IndicatorIcon.infer(EntityType.WITHER, Items.WITHER_SKELETON_SKULL))
        );
        addGoal(killEntitiesFromTagGoal(KILL_SOME_UNIQUE_MOBS, EnigmaticsBingoEntityTypeTags.MOBS, 40, 45, true)
                .name(Component.translatable("enigmaticsbingogoals.goal.kill_some_unique_mobs", 0),
                        subber -> subber.sub("with.0", "amount"))
                .tags(EnigmaticsBingoTags.KILL_MOB)
                .antisynergy(EnigmaticsBingoSynergies.UNIQUE_NEUTRAL_MOBS, EnigmaticsBingoSynergies.UNIQUE_HOSTILE_MOBS)
        );
        addGoal(obtainItemGoal(OBTAIN_NETHER_STAR, items, Items.NETHER_STAR)
                .antisynergy(EnigmaticsBingoSynergies.WITHER)
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.WITHER_SKULL, EnigmaticsBingoTags.FORTRESS, EnigmaticsBingoTags.NETHER_LATE)
        );
        addGoal(makeBannerWithPatternItemGoal(USE_SNOUT_PATTERN, items, Items.PIGLIN_BANNER_PATTERN,
                BannerPatterns.PIGLIN, "Snout Pattern")
                .tags(EnigmaticsBingoTags.NETHER, EnigmaticsBingoTags.NETHER_LATE, EnigmaticsBingoTags.NETHER_EXPLORE, EnigmaticsBingoTags.BASTION)
        );
    }
}
