package de.rasmusantons.enigmaticsbingogoals.conditions;

import com.mojang.serialization.MapCodec;
import io.github.gaming32.bingo.Bingo;
import io.github.gaming32.bingo.platform.BingoPlatform;
import io.github.gaming32.bingo.platform.registry.DeferredRegister;
import io.github.gaming32.bingo.platform.registry.RegistryValue;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class EnigmaticsBingoGoalsConditions {
    public static final DeferredRegister<LootItemConditionType> REGISTER =
            BingoPlatform.platform.createDeferredRegister(BuiltInRegistries.LOOT_CONDITION_TYPE);

    public static final RegistryValue<LootItemConditionType> FULL_UNIQUE_INVENTORY = register("full_unique_inventory", FullUniqueInventoryCondition.CODEC);
    public static final RegistryValue<LootItemConditionType> KILL_ENEMY_PLAYER = register("kill_enemy_player", KillEnemyPlayerCondition.CODEC);
    public static final RegistryValue<LootItemConditionType> NUMBER_OF_EFFECTS = register("number_of_effects", NumberOfEffectsCondition.CODEC);
    public static final RegistryValue<LootItemConditionType> PLAYER_ALIVE = register("player_alive", PlayerAliveCondition.CODEC);
    public static final RegistryValue<LootItemConditionType> UNIQUE_FOODS_ON_CAMPFIRE = register("unique_foods_on_campfire", UniqueFoodsOnCampfireCondition.CODEC);

    public static void load() {
    }

    private static RegistryValue<LootItemConditionType> register(String registryName, MapCodec<? extends LootItemCondition> codec) {
        return REGISTER.register(ResourceLocation.fromNamespaceAndPath(Bingo.MOD_ID, registryName), () -> new LootItemConditionType(codec));
    }
}
