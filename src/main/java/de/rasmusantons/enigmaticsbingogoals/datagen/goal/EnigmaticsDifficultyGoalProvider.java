package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoTags;
import de.rasmusantons.enigmaticsbingogoals.triggers.AdvancementsTrigger;
import de.rasmusantons.enigmaticsbingogoals.triggers.WearPumpkinTrigger;
import io.github.gaming32.bingo.Bingo;
import io.github.gaming32.bingo.data.BingoGoal;
import io.github.gaming32.bingo.data.BingoTags;
import io.github.gaming32.bingo.data.icons.BlockIcon;
import io.github.gaming32.bingo.data.icons.EffectIcon;
import io.github.gaming32.bingo.data.icons.IndicatorIcon;
import io.github.gaming32.bingo.data.icons.ItemIcon;
import io.github.gaming32.bingo.data.progresstrackers.CriterionProgressTracker;
import io.github.gaming32.bingo.data.subs.BingoSub;
import io.github.gaming32.bingo.fabric.datagen.goal.DifficultyGoalProvider;
import io.github.gaming32.bingo.triggers.EntityKilledPlayerTrigger;
import io.github.gaming32.bingo.triggers.ExperienceChangeTrigger;
import io.github.gaming32.bingo.triggers.RelativeStatsTrigger;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

public abstract class EnigmaticsDifficultyGoalProvider extends DifficultyGoalProvider {
    public static final ResourceLocation ENIGMATICS = new ResourceLocation(Bingo.MOD_ID, "enigmatics");

    public EnigmaticsDifficultyGoalProvider(ResourceLocation difficulty, Consumer<BingoGoal.Holder> goalAdder) {
        super(difficulty, goalAdder);
    }

    protected static BingoGoal.Builder dieToEntityGoal(ResourceLocation id, EntityType<?> entityType) {
        return BingoGoal.builder(id)
                .criterion("die", EntityKilledPlayerTrigger.builder()
                        .creditedEntity(EntityPredicate.Builder.entity().of(entityType).build())
                        .build()
                );
    }

    protected static BingoGoal.Builder neverLevelsGoal(ResourceLocation id, int minLevels, int maxLevels) {
        return BingoGoal.builder(id)
                .sub("count", BingoSub.random(minLevels, maxLevels))
                .criterion("obtain", ExperienceChangeTrigger.builder().levels(MinMaxBounds.Ints.atLeast(0)).build(),
                        subber -> subber.sub("conditions.levels.min", "count"))
                .tags(BingoTags.NEVER, BingoTags.STAT)
                .name(Component.translatable("Do not reach level %s", 0), subber -> subber.sub("with.0", "count"))
                .icon(
                        new IndicatorIcon(ItemIcon.ofItem(Items.EXPERIENCE_BOTTLE), ItemIcon.ofItem(Items.BARRIER)),
                        subber -> subber.sub("base.item.count", "count"))
                .antisynergy("levels");
    }

    protected static BingoGoal.Builder neverDamageGoal(ResourceLocation id, int damage) {
        return BingoGoal.builder(id)
                .criterion("damage", RelativeStatsTrigger.builder()
                        .stat(Stats.DAMAGE_TAKEN, MinMaxBounds.Ints.atLeast(damage * 20)).build()
                )
                .tags(BingoTags.NEVER, EnigmaticsBingoTags.NEVER_TAKE_DAMAGE)
                .name(Component.literal(String.format("Never take %d hearts of damage", damage)))
                .icon(new IndicatorIcon(EffectIcon.of(MobEffects.HARM), ItemIcon.ofItem(Items.BARRIER)))
                .progress(new CriterionProgressTracker("damage", 0.05f));
    }

    protected static BingoGoal.Builder advancementsGoal(ResourceLocation id, int minNumber, int maxNumber) {
        return BingoGoal.builder(id)
                .sub("number", BingoSub.random(minNumber, maxNumber))
                .criterion("achieve", AdvancementsTrigger.TriggerInstance.advancements(MinMaxBounds.Ints.atLeast(0)),
                        subber -> subber.sub("conditions.number.min", "number"))
                .tags(BingoTags.STAT, EnigmaticsBingoTags.ADVANCEMENTS)
                .name(Component.translatable("Get %s advancements", 0), subber -> subber.sub("with.0", "number"))
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.ELYTRA), BlockIcon.ofBlock(Blocks.GOLD_BLOCK)),
                        subber -> subber.sub("base.item.count", "number"))
                .progress("achieve");
    }
}
