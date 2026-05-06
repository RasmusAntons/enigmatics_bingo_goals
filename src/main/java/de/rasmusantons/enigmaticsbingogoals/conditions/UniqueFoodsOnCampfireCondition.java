package de.rasmusantons.enigmaticsbingogoals.conditions;

import com.mojang.serialization.MapCodec;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

import java.util.Set;

public record UniqueFoodsOnCampfireCondition(MinMaxBounds.Ints foods) implements LootItemCondition {
    public static final MapCodec<UniqueFoodsOnCampfireCondition> CODEC = MinMaxBounds.Ints.CODEC
            .fieldOf("foods")
            .xmap(UniqueFoodsOnCampfireCondition::new, UniqueFoodsOnCampfireCondition::foods);

    @Override
    public @NonNull MapCodec<UniqueFoodsOnCampfireCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(LootContext lootContext) {
        Vec3 position = lootContext.getOptionalParameter(LootContextParams.ORIGIN);
        if (position == null)
            return false;
        BlockEntity blockEntity = lootContext.getLevel().getBlockEntity(
                new BlockPos((int) Math.floor(position.x), (int) Math.floor(position.y), (int) Math.floor(position.z))
        );
        if (!(blockEntity instanceof CampfireBlockEntity campfireBlockEntity))
            return false;
        int uniqueItems = (int) campfireBlockEntity.getItems().stream()
                .map(ItemStack::getItem).filter(item -> item != Items.AIR).distinct().count();
        return foods.matches(uniqueItems);
    }

    @Override
    public @NonNull Set<ContextKey<?>> getReferencedContextParams() {
        return Set.of(LootContextParams.ORIGIN);
    }
}
