package de.rasmusantons.enigmaticsbingogoals.conditions;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public enum FullUniqueInventoryCondition implements LootItemCondition {
    INSTANCE;

    public static final MapCodec<FullUniqueInventoryCondition> CODEC = MapCodec.unit(INSTANCE);

    @NotNull
    @Override
    public LootItemConditionType getType() {
        return INSTANCE.getType();
    }

    @Override
    public boolean test(LootContext lootContext) {
        final Entity thisEntity = lootContext.getParam(LootContextParams.THIS_ENTITY);
        if (!(thisEntity instanceof ServerPlayer serverPlayer))
            return false;
        Inventory inventory = serverPlayer.getInventory();
        Set<Item> seenItems = new HashSet<>();
        for (ItemStack itemStack : inventory.items) {
            if (itemStack.isEmpty())
                return false;
            Item item = itemStack.getItem();
            if (seenItems.contains(item))
                return false;
            seenItems.add(item);
        }
        return true;
    }

    @NotNull
    @Override
    public Set<LootContextParam<?>> getReferencedContextParams() {
        return Set.of(LootContextParams.THIS_ENTITY);
    }
}
