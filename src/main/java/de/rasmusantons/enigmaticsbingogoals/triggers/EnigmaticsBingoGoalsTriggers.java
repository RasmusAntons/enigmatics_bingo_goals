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

    public static final RegistryValue<WearPumpkinTrigger> WEAR_PUMPKIN = register("wear_pumpkin", WearPumpkinTrigger::new);
    public static final RegistryValue<PlayMusicToOtherTeamTrigger> PLAY_MUSIC_TO_OTHER_TEAM = register("play_music_to_other_team", PlayMusicToOtherTeamTrigger::new);
    public static final RegistryValue<AdvancementsTrigger> ADVANCEMENTS = register("advancements", AdvancementsTrigger::new);
    public static final RegistryValue<EmptyHungerTrigger> EMPTY_HUNGER = register("empty_hunger", EmptyHungerTrigger::new);
    public static final RegistryValue<HitOtherTeamWithProjectileTrigger> HIT_OTHER_TEAM_WITH_PROJECTILE = register("hit_other_team_with_projectile", HitOtherTeamWithProjectileTrigger::new);
    public static final RegistryValue<GiveEffectToOtherTeamTrigger> GIVE_EFFECT_TO_OTHER_TEAM = register("give_effect_to_other_team", GiveEffectToOtherTeamTrigger::new);
    public static final RegistryValue<VehicleInventoryChangeTrigger> VEHICLE_INVENTORY_CHANGE = register("vehicle_inventory_change", VehicleInventoryChangeTrigger::new);
    public static final RegistryValue<AdvancementProgressTrigger> CHECK_ADVANCEMENT_PROGRESS = register("advancement_progress", AdvancementProgressTrigger::new);
    public static final RegistryValue<DamageExceptTeamTrigger> DAMAGE_EXCEPT_TEAM = register("damage_except_team", DamageExceptTeamTrigger::new);
    public static final RegistryValue<KillMobsTrigger> KILL_MOBS = register("kill_mobs", KillMobsTrigger::new);
    public static final RegistryValue<TadpoleMaturesTrigger> TADPOLE_MATURES = register("tadpole_matures", TadpoleMaturesTrigger::new);
    public static final RegistryValue<WriteBookTrigger> WRITE_BOOK = register("write_book", WriteBookTrigger::new);
    public static final RegistryValue<ApplyPatternTrigger> APPLY_PATTERN = register("apply_pattern", ApplyPatternTrigger::new);

    public static void load() {
    }

    private static <T extends CriterionTrigger<?>> RegistryValue<T> register(String name, Supplier<T> init) {
        return REGISTER.register(ResourceLocation.fromNamespaceAndPath(EnigmaticsBingoGoals.MOD_ID, name), init);
    }
}
