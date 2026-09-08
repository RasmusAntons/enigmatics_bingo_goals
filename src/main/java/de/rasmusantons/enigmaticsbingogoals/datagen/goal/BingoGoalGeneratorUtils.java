package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Ordering;
import com.google.common.collect.Table;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.serialization.Lifecycle;
import de.rasmusantons.enigmaticsbingogoals.datagen.tag.EnigmaticsBingoEntityTypeTagProvider;
import io.github.gaming32.bingo.data.icons.*;
import io.github.gaming32.bingo.datagen.BingoDataGenUtil;
import io.github.gaming32.bingo.datagen.tag.BingoEntityTypeTagProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.core.*;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.feline.CatVariant;
import net.minecraft.world.entity.animal.frog.FrogVariant;
import net.minecraft.world.entity.animal.wolf.WolfVariant;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BannerPatterns;

import java.util.*;

public class BingoGoalGeneratorUtils {
    public static ItemStackTemplate getCustomPLayerHead(PlayerHeadTextures textures) {
        PropertyMap properties = new PropertyMap(ImmutableMultimap.of("textures", new Property("textures", textures.getTextures())));
        ResolvableProfile profile = ResolvableProfile.createResolved(new GameProfile(
                Mth.createInsecureUUID(RandomSource.create()), textures.name(), properties
        ));
        return new ItemStackTemplate(Items.PLAYER_HEAD, DataComponentPatch.builder().set(DataComponents.PROFILE, profile).build());
    }

    public static GoalIcon getEntityIcon(EntityType<?> entityType, int count) {
        if (entityType == EntityTypes.ENDER_DRAGON)
            return ItemIcon.ofItem(Items.DRAGON_HEAD);
        if (entityType == EntityTypes.ELDER_GUARDIAN)
            return new ItemIcon(getCustomPLayerHead(PlayerHeadTextures.ELDER_GUARDIAN));
        if (entityType == EntityTypes.GHAST)
            return new ItemIcon(getCustomPLayerHead(PlayerHeadTextures.GHAST));
        return EntityIcon.ofSpawnEgg(entityType, new CompoundTag(), count);
    }

    public static GoalIcon getAgeLockableEntitiesIcon(HolderLookup.Provider registries) {
        var entityTypes = registries.lookupOrThrow(Registries.ENTITY_TYPE);
        return CycleIcon.infer(entityTypes.listElements().filter(type -> {
            Class<? extends Entity> entityClass = BingoDataGenUtil.getEntityTypeClass(type.value());
            return entityClass != null
                    && BingoEntityTypeTagProvider.canBeAgeLocked(entityClass) &&
                    !(BingoDataGenUtil.loadVanillaTag(EntityTypeTags.CANNOT_BE_AGE_LOCKED, registries).contains(type));
        }).map(type -> {
            CompoundTag data =  new CompoundTag();
            data.putInt("Age", -24000);
            return EntityIcon.ofSpawnEgg(type.value(), data, 1);
        }));
    }

    public static GoalIcon getEntityIcon(TagKey<EntityType<?>> entityTypeTag, HolderLookup.RegistryLookup<EntityType<?>> entityTypes, int count) {
        var resolvedTag = EnigmaticsBingoEntityTypeTagProvider.getEntityTagDuringDatagen(entityTypeTag, entityTypes);
        if (resolvedTag == null)
            return new EntityTypeTagCycleIcon(entityTypeTag, count);
        return CycleIcon.infer(Arrays.stream(resolvedTag).map(e -> getEntityIcon(e, count)));
    }

    public static EntityIcon getCatVariantIcon(ResourceKey<CatVariant> variant) {
        CompoundTag data = new CompoundTag();
        data.putString("variant", variant.identifier().toString());
        return new EntityIcon(EntityTypes.CAT, data, new ItemStackTemplate(Items.CAT_SPAWN_EGG));
    }

    public static EntityIcon getWolfVariantIcon(ResourceKey<WolfVariant> variant) {
        CompoundTag data = new CompoundTag();
        data.putString("variant", variant.identifier().toString());
        return new EntityIcon(EntityTypes.WOLF, data, new ItemStackTemplate(Items.WOLF_SPAWN_EGG));
    }

    public static EntityIcon getFrogVariantIcon(ResourceKey<FrogVariant> variant) {
        CompoundTag data = new CompoundTag();
        data.putString("variant", variant.identifier().toString());
        return new EntityIcon(EntityTypes.FROG, data, new ItemStackTemplate(Items.FROG_SPAWN_EGG));
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

        ItemStack itemStack = new ItemStack(Items.BANNER.white());
        //noinspection deprecation
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
        itemStack.set(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay.DEFAULT.withHidden(DataComponents.BANNER_PATTERNS, true));
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

    public static Table<EquipmentSlot, String, Item> getPlayerArmors(HolderLookup.Provider registries) {
        final var armors = ImmutableTable.<EquipmentSlot, String, Item>builder();
        armors.orderRowsBy(Ordering.natural());
        armors.orderColumnsBy(Ordering.natural());
        for (Holder<Item> helmet : BingoDataGenUtil.loadVanillaTag(ItemTags.HEAD_ARMOR, registries)) {
            String name = helmet.getRegisteredName();
            if (name.endsWith("_helmet")) {
                armors.put(EquipmentSlot.HEAD, name.substring(0, name.length() - "_helmet".length()), helmet.value());
            } else {
                throw new IllegalStateException("Unexpected helmet name: " + name);
            }
        }
        for (Holder<Item> chestplate : BingoDataGenUtil.loadVanillaTag(ItemTags.CHEST_ARMOR, registries)) {
            String name = chestplate.getRegisteredName();
            if (name.endsWith("_chestplate")) {
                armors.put(EquipmentSlot.CHEST, name.substring(0, name.length() - "_chestplate".length()), chestplate.value());
            } else {
                throw new IllegalStateException("Unexpected chestplate name: " + name);
            }
        }
        for (Holder<Item> leggings : BingoDataGenUtil.loadVanillaTag(ItemTags.LEG_ARMOR, registries)) {
            String name = leggings.getRegisteredName();
            if (name.endsWith("_leggings")) {
                armors.put(EquipmentSlot.LEGS, name.substring(0, name.length() - "_leggings".length()), leggings.value());
            } else {
                throw new IllegalStateException("Unexpected leggings name: " + name);
            }
        }
        for (Holder<Item> boots : BingoDataGenUtil.loadVanillaTag(ItemTags.FOOT_ARMOR, registries)) {
            String name = boots.getRegisteredName();
            if (name.endsWith("_boots")) {
                armors.put(EquipmentSlot.FEET, name.substring(0, name.length() - "_boots".length()), boots.value());
            } else {
                throw new IllegalStateException("Unexpected boots name: " + name);
            }
        }
        return armors.build();
    }

    public enum PlayerHeadTextures {
        BEE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDQyMGM5YzQzZTA5NTg4MGRjZDJlMjgxYzgxZjQ3YjE2M2I0NzhmNThhNTg0YmI2MWY5M2U2ZTEwYTE1NWYzMSJ9fX0="),
        LLAMA("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWY3ZDkwYjMwNWFhNjQzMTNjOGQ0NDA0ZDhkNjUyYTk2ZWJhOGE3NTRiNjdmNDM0N2RjY2NkZDVhNmE2MzM5OCJ9fX0="),
        GHAST("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGI2YTcyMTM4ZDY5ZmJiZDJmZWEzZmEyNTFjYWJkODcxNTJlNGYxYzk3ZTVmOTg2YmY2ODU1NzFkYjNjYzAifX19"),
        ELDER_GUARDIAN("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzBmODY4Y2FmMTljZjIxMjRmMGZlZjk4ZTZiODc3M2QyN2ZiZjQyZDkzYWFiMDZiMjJlZTAzM2IyYWVlNjQ0NyJ9fX0="),
        DEAD("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGYwMGQ5ZmU1YTYwODlmNGFiNDcwNWIzNzFlZTAyYjJhNmQ3YjVlOWZhYTUwMDJlMWQyOTcyY2RhOTY0Y2ViNyJ9fX0="),
        ANGRY_BIRD("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDlhMDQ5YjAyOTIxNDY2NzQxODZiOTg0NmRmZjI3OTJhNjIzZTc5NTcxODQ5NzIwOWUxY2U2ZDI4NzE3NzNkNyJ9fX0="),
        BABY("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTQ2N2IyYThmY2E4YmQxNTljMmUxOTQ3OTQ3YmQxZDU2MmZiYzI3MTZlZmVlNDhkNTU3OTRmZTUzNWVmYmEifX19"),
        WATER("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzRmY2JjMjU2ZDBiZTdlNjgzYWY4NGUzOGM0YmNkYjcxYWZiOTM5ODUzOGEyOWFhOTZjYmZhMzE4YjJlYSJ9fX0="),
        LAVA("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjY5NjVlNmE1ODY4NGMyNzdkMTg3MTdjZWM5NTlmMjgzM2E3MmRmYTk1NjYxMDE5ZGJjZGYzZGJmNjZiMDQ4In19fQ==");

        private final String textures;

        PlayerHeadTextures(String textures) {
            this.textures = textures;
        }

        public String getTextures() {
            return textures;
        }
    }
}
