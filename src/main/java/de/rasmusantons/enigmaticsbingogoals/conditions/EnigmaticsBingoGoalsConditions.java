package de.rasmusantons.enigmaticsbingogoals.conditions;

import com.mojang.serialization.MapCodec;
import io.github.gaming32.bingo.Bingo;
import io.github.gaming32.bingo.platform.registry.DeferredRegister;
import io.github.gaming32.bingo.platform.registry.RegistryValue;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class EnigmaticsBingoGoalsConditions {
    public static final DeferredRegister<MapCodec<? extends LootItemCondition>> REGISTER =
            DeferredRegister.create(BuiltInRegistries.LOOT_CONDITION_TYPE);

    public static final RegistryValue<MapCodec<FullUniqueInventoryCondition>> FULL_UNIQUE_INVENTORY = register("full_unique_inventory", FullUniqueInventoryCondition.CODEC);
    public static final RegistryValue<MapCodec<KillEnemyPlayerCondition>> KILL_ENEMY_PLAYER = register("kill_enemy_player", KillEnemyPlayerCondition.CODEC);
    public static final RegistryValue<MapCodec<NumberOfEffectsCondition>> NUMBER_OF_EFFECTS = register("number_of_effects", NumberOfEffectsCondition.CODEC);
    public static final RegistryValue<MapCodec<PlayerAliveCondition>> PLAYER_ALIVE = register("player_alive", PlayerAliveCondition.CODEC);
    public static final RegistryValue<MapCodec<UniqueFoodsOnCampfireCondition>> UNIQUE_FOODS_ON_CAMPFIRE = register("unique_foods_on_campfire", UniqueFoodsOnCampfireCondition.CODEC);

    public static void load() {
    }

    private static <T extends LootItemCondition> RegistryValue<MapCodec<T>> register(String registryName, MapCodec<T> codec) {
        return REGISTER.register(Identifier.fromNamespaceAndPath(Bingo.MOD_ID, registryName), () -> codec);
    }
}
