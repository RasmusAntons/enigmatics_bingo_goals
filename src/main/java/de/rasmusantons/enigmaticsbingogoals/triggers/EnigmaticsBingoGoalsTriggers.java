package de.rasmusantons.enigmaticsbingogoals.triggers;

import de.rasmusantons.enigmaticsbingogoals.EnigmaticsBingoGoals;
import io.github.gaming32.bingo.platform.BingoPlatform;
import io.github.gaming32.bingo.platform.registry.DeferredRegister;
import io.github.gaming32.bingo.platform.registry.RegistryValue;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class EnigmaticsBingoGoalsTriggers {
    public static final DeferredRegister<CriterionTrigger<?>> REGISTER =
            BingoPlatform.platform.createDeferredRegister(BuiltInRegistries.TRIGGER_TYPES);

    public static final RegistryValue<EmptyHungerTrigger> EMPTY_HUNGER = register("empty_hunger", EmptyHungerTrigger::new);
    public static final RegistryValue<WearPumpkinTrigger> WEAR_PUMPKIN = register("wear_pumpkin", WearPumpkinTrigger::new);
    public static final RegistryValue<PlayMusicToOtherTeamTrigger> PLAY_MUSIC_TO_OTHER_TEAM = register("play_music_to_other_team", PlayMusicToOtherTeamTrigger::new);
    public static final RegistryValue<AdvancementsTrigger> ADVANCEMENTS = register("advancements", AdvancementsTrigger::new);

    public static void load() {
    }

    private static <T extends CriterionTrigger<?>> RegistryValue<T> register(String name, Supplier<T> init) {
        return REGISTER.register(new ResourceLocation(EnigmaticsBingoGoals.MOD_ID, name), init);
    }
}
