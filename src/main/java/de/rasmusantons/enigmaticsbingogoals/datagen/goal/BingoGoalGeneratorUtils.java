package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Ordering;
import com.google.common.collect.Table;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.serialization.Lifecycle;
import de.rasmusantons.enigmaticsbingogoals.datagen.tag.EnigmaticsBingoEntityTypeTagProvider;
import io.github.gaming32.bingo.data.icons.*;
import io.github.gaming32.bingo.fabric.datagen.BingoDataGenFabric;
import net.minecraft.ChatFormatting;
import net.minecraft.core.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.WolfVariant;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BannerPatterns;

import java.util.*;
import java.util.stream.Stream;

public class BingoGoalGeneratorUtils {
    public static ItemStack getCustomPLayerHead(PlayerHeadTextures textures) {
        ItemStack stack = new ItemStack(Items.PLAYER_HEAD);
        PropertyMap properties = new PropertyMap();
        properties.put("textures", new Property("textures", textures.getTextures()));
        ResolvableProfile profile = new ResolvableProfile(Optional.empty(), Optional.empty(), properties);
        stack.set(DataComponents.PROFILE, profile);
        return stack;
    }

    public static GoalIcon getEntityIcon(EntityType<?> entityType, int count) {
        if (entityType == EntityType.ENDER_DRAGON)
            return ItemIcon.ofItem(Items.DRAGON_HEAD);
        if (entityType == EntityType.ELDER_GUARDIAN)
            return new ItemIcon(getCustomPLayerHead(PlayerHeadTextures.ELDER_GUARDIAN));
        if (entityType == EntityType.GHAST)
            return new ItemIcon(getCustomPLayerHead(PlayerHeadTextures.GHAST));
        return EntityIcon.ofSpawnEgg(entityType, new CompoundTag(), count);
    }

    public static GoalIcon getEntityIcon(TagKey<EntityType<?>> entityTypeTag, int count) {
        var resolvedTag = EnigmaticsBingoEntityTypeTagProvider.getEntityTagDuringDatagen(entityTypeTag);
        if (resolvedTag == null)
            return new EntityTypeTagCycleIcon(entityTypeTag, count);
        return CycleIcon.infer(Arrays.stream(resolvedTag).map(e -> getEntityIcon(e, count)));
    }

    public static EntityIcon getCatVariantIcon(ResourceKey<CatVariant> variant) {
        CompoundTag data = new CompoundTag();
        data.putString("variant", variant.location().toString());
        return new EntityIcon(EntityType.CAT, data, new ItemStack(Items.CAT_SPAWN_EGG));
    }

    public static EntityIcon getWolfVariantIcon(ResourceKey<WolfVariant> variant) {
        CompoundTag data = new CompoundTag();
        data.putString("variant", variant.location().toString());
        return new EntityIcon(EntityType.WOLF, data, new ItemStack(Items.WOLF_SPAWN_EGG));
    }

    public static EntityIcon getFrogVariantIcon(ResourceKey<FrogVariant> variant) {
        CompoundTag data = new CompoundTag();
        data.putString("variant", variant.location().toString());
        return new EntityIcon(EntityType.FROG, data, new ItemStack(Items.FROG_SPAWN_EGG));
    }

    public static CycleIcon getAllEffectsIcon() {
        return CycleIcon.infer(BuiltInRegistries.MOB_EFFECT.stream().map(effect -> EffectIcon.of(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect))));
    }

    public static GoalIcon createAllDifferentMaterialsIcon(HolderLookup.Provider registries) {
        final int iterations = 4;
        final var armors = getPlayerArmors(registries);
        final var materials = ImmutableList.copyOf(armors.columnKeySet());
        final ImmutableList.Builder<GoalIcon> icons = ImmutableList.builderWithExpectedSize(iterations * armors.rowMap().size());
        int materialIndex = 0;
        for (int iteration = 0; iteration < iterations; iteration++) {
            for (final var type : armors.rowKeySet()) {
                Item item;
                do {
                    item = armors.get(type, materials.get(materialIndex++ % materials.size()));
                } while (item == null);
                icons.add(ItemIcon.ofItem(item));
            }
        }
        return new CycleIcon(icons.build());
    }

    public static ItemStack getOminousBanner(HolderLookup.Provider registries) {
        var patternRegistry = registries.lookupOrThrow(Registries.BANNER_PATTERN);

        ItemStack itemStack = new ItemStack(Items.WHITE_BANNER);
        BannerPatternLayers bannerPatternLayers = new BannerPatternLayers.Builder()
                .addIfRegistered(patternRegistry, BannerPatterns.RHOMBUS_MIDDLE, DyeColor.CYAN)
                .addIfRegistered(patternRegistry, BannerPatterns.STRIPE_BOTTOM, DyeColor.LIGHT_GRAY)
                .addIfRegistered(patternRegistry, BannerPatterns.STRIPE_CENTER, DyeColor.GRAY)
                .addIfRegistered(patternRegistry, BannerPatterns.BORDER, DyeColor.LIGHT_GRAY)
                .addIfRegistered(patternRegistry, BannerPatterns.STRIPE_MIDDLE, DyeColor.BLACK)
                .addIfRegistered(patternRegistry, BannerPatterns.HALF_HORIZONTAL, DyeColor.LIGHT_GRAY)
                .addIfRegistered(patternRegistry, BannerPatterns.CIRCLE_MIDDLE, DyeColor.LIGHT_GRAY)
                .addIfRegistered(patternRegistry, BannerPatterns.BORDER, DyeColor.BLACK)
                .build();
        itemStack.set(DataComponents.BANNER_PATTERNS, bannerPatternLayers);
        itemStack.set(DataComponents.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
        itemStack.set(DataComponents.ITEM_NAME, Component.translatable("block.minecraft.ominous_banner").withStyle(ChatFormatting.GOLD));
        return itemStack;
    }

    static class WolfVariantCollector implements BootstrapContext<WolfVariant> {
        List<ResourceKey<WolfVariant>> variants = new ArrayList<>();
        @Override
        public Holder.Reference<WolfVariant> register(ResourceKey<WolfVariant> key, WolfVariant value, Lifecycle registryLifecycle) {
            variants.add(key);
            return null;
        }

        @Override
        public <S> HolderGetter<S> lookup(ResourceKey<? extends Registry<? extends S>> registryKey) {
            return new HolderGetter<S>() {
                @Override
                public Optional<Holder.Reference<S>> get(ResourceKey<S> resourceKey) {
                    return Optional.of((Holder.Reference<S>) Holder.Reference.createStandAlone(null, Biomes.CHERRY_GROVE));
                }

                @Override
                public Optional<HolderSet.Named<S>> get(TagKey<S> tagKey) {
                    return Optional.of((HolderSet.Named<S>) HolderSet.emptyNamed(null, BiomeTags.IS_SAVANNA));
                }
            };
        }
    }

    public static Table<EquipmentSlot, ResourceLocation, Item> getPlayerArmors(HolderLookup.Provider registries) {
        final var armors = ImmutableTable.<EquipmentSlot, ResourceLocation, Item>builder();
        armors.orderRowsBy(Ordering.natural());
        armors.orderColumnsBy(Ordering.natural());
        Stream.of(ItemTags.HEAD_ARMOR, ItemTags.CHEST_ARMOR, ItemTags.LEG_ARMOR, ItemTags.FOOT_ARMOR)
                .map(tag -> BingoDataGenFabric.loadVanillaTag(tag, registries))
                .flatMap(HolderSet::stream)
                .distinct()
                .map(Holder::value)
                .forEach(item -> {
                    final var equippable = item.components().get(DataComponents.EQUIPPABLE);
                    if (equippable == null || equippable.assetId().isEmpty()) return;
                    armors.put(equippable.slot(), equippable.assetId().get().location(), item);
                });
        return armors.build();
    }

    public enum PlayerHeadTextures {
        BEE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDQyMGM5YzQzZTA5NTg4MGRjZDJlMjgxYzgxZjQ3YjE2M2I0NzhmNThhNTg0YmI2MWY5M2U2ZTEwYTE1NWYzMSJ9fX0="),
        LLAMA("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWY3ZDkwYjMwNWFhNjQzMTNjOGQ0NDA0ZDhkNjUyYTk2ZWJhOGE3NTRiNjdmNDM0N2RjY2NkZDVhNmE2MzM5OCJ9fX0="),
        GHAST("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGI2YTcyMTM4ZDY5ZmJiZDJmZWEzZmEyNTFjYWJkODcxNTJlNGYxYzk3ZTVmOTg2YmY2ODU1NzFkYjNjYzAifX19"),
        ELDER_GUARDIAN("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzBmODY4Y2FmMTljZjIxMjRmMGZlZjk4ZTZiODc3M2QyN2ZiZjQyZDkzYWFiMDZiMjJlZTAzM2IyYWVlNjQ0NyJ9fX0="),
        DEAD("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGYwMGQ5ZmU1YTYwODlmNGFiNDcwNWIzNzFlZTAyYjJhNmQ3YjVlOWZhYTUwMDJlMWQyOTcyY2RhOTY0Y2ViNyJ9fX0="),
        ANGRY_BIRD("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDlhMDQ5YjAyOTIxNDY2NzQxODZiOTg0NmRmZjI3OTJhNjIzZTc5NTcxODQ5NzIwOWUxY2U2ZDI4NzE3NzNkNyJ9fX0="),
        BABY("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTQ2N2IyYThmY2E4YmQxNTljMmUxOTQ3OTQ3YmQxZDU2MmZiYzI3MTZlZmVlNDhkNTU3OTRmZTUzNWVmYmEifX19");

        private final String textures;

        PlayerHeadTextures(String textures) {
            this.textures = textures;
        }

        public String getTextures() {
            return textures;
        }
    }
}
