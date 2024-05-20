package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import de.rasmusantons.enigmaticsbingogoals.triggers.PlayMusicToOtherTeamTrigger;
import de.rasmusantons.enigmaticsbingogoals.triggers.WearPumpkinTrigger;
import io.github.gaming32.bingo.data.BingoDifficulties;
import io.github.gaming32.bingo.data.BingoGoal;
import io.github.gaming32.bingo.data.BingoTags;
import io.github.gaming32.bingo.data.icons.EffectIcon;
import io.github.gaming32.bingo.data.icons.IndicatorIcon;
import io.github.gaming32.bingo.data.icons.ItemIcon;
import net.minecraft.advancements.critereon.EnterBlockTrigger;
import net.minecraft.advancements.critereon.EntityHurtPlayerTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

public class HardGoalProvider extends EnigmaticsDifficultyGoalProvider {
    public HardGoalProvider(Consumer<BingoGoal.Holder> goalAdder) {
        super(BingoDifficulties.HARD, goalAdder);
    }

    @Override
    public void addGoals(HolderLookup.Provider provider) {
        addGoal(BingoGoal.builder(id("never_crafting_table"))
                .criterion("obtain", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .tags(BingoTags.NEVER)
                .name(Component.literal("Never obtain crafting table"))
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.CRAFTING_TABLE), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(BingoGoal.builder(id("never_damage"))
                .criterion("damage", EntityHurtPlayerTrigger.TriggerInstance.entityHurtPlayer())
                .tags(BingoTags.NEVER)
                .name(Component.literal("Never take damage"))
                .icon(new IndicatorIcon(EffectIcon.of(MobEffects.HARM), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(neverLevelsGoal(id("never_levels"), 1, 3));
        addGoal(BingoGoal.builder(id("never_touch_water"))
                .criterion("touch", EnterBlockTrigger.TriggerInstance.entersBlock(Blocks.WATER))
                .tags(BingoTags.NEVER)
                .name(Component.literal("Never touch water"))
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.WATER_BUCKET), ItemIcon.ofItem(Items.BARRIER)))
        );
        addGoal(BingoGoal.builder(id("play_music_to_other_team"))
                .criterion("music", PlayMusicToOtherTeamTrigger.TriggerInstance.playMusic())
                .name(Component.literal("Make the enemy listen to a jukebox"))
                .icon(new IndicatorIcon(ItemIcon.ofItem(Items.JUKEBOX), ItemIcon.ofItem(Items.PLAYER_HEAD)))
        );
    }
}
