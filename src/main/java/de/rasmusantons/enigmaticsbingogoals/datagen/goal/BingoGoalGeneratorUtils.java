package de.rasmusantons.enigmaticsbingogoals.datagen.goal;

import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData.BlockEntityTagOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Unit;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Optional;

public class BingoGoalGeneratorUtils {
    public enum PlayerHeadTextures {
        BEE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDQyMGM5YzQzZTA5NTg4MGRjZDJlMjgxYzgxZjQ3YjE2M2I0NzhmNThhNTg0YmI2MWY5M2U2ZTEwYTE1NWYzMSJ9fX0="),
        LLAMA("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWY3ZDkwYjMwNWFhNjQzMTNjOGQ0NDA0ZDhkNjUyYTk2ZWJhOGE3NTRiNjdmNDM0N2RjY2NkZDVhNmE2MzM5OCJ9fX0="),
        GHAST("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGI2YTcyMTM4ZDY5ZmJiZDJmZWEzZmEyNTFjYWJkODcxNTJlNGYxYzk3ZTVmOTg2YmY2ODU1NzFkYjNjYzAifX19"),
        ELDER_GUARDIAN("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzBmODY4Y2FmMTljZjIxMjRmMGZlZjk4ZTZiODc3M2QyN2ZiZjQyZDkzYWFiMDZiMjJlZTAzM2IyYWVlNjQ0NyJ9fX0="),
        DEAD("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGYwMGQ5ZmU1YTYwODlmNGFiNDcwNWIzNzFlZTAyYjJhNmQ3YjVlOWZhYTUwMDJlMWQyOTcyY2RhOTY0Y2ViNyJ9fX0=");


        private final String textures;

        PlayerHeadTextures(String textures) {
            this.textures = textures;
        }

        public String getTextures() {
            return textures;
        }
    }

    public static ItemStack getCustomPLayerHead(PlayerHeadTextures textures) {
        ItemStack stack = new ItemStack(Items.PLAYER_HEAD);
        PropertyMap properties = new PropertyMap();
        properties.put("textures", new Property("textures", textures.getTextures()));
        ResolvableProfile profile = new ResolvableProfile(Optional.empty(), Optional.empty(), properties);
        stack.set(DataComponents.PROFILE, profile);
        return stack;
    }

    public static ItemStack getOminousBanner(HolderLookup.Provider registries) {
        var patternRegistry = registries.lookupOrThrow(Registries.BANNER_PATTERN);
        
        ItemStack itemStack = new ItemStack(Items.WHITE_BANNER);
        BannerPatternLayers bannerPatternLayers = new BannerPatternLayers.Builder().addIfRegistered(patternRegistry, BannerPatterns.RHOMBUS_MIDDLE, DyeColor.CYAN).addIfRegistered(patternRegistry, BannerPatterns.STRIPE_BOTTOM, DyeColor.LIGHT_GRAY).addIfRegistered(patternRegistry, BannerPatterns.STRIPE_CENTER, DyeColor.GRAY).addIfRegistered(patternRegistry, BannerPatterns.BORDER, DyeColor.LIGHT_GRAY).addIfRegistered(patternRegistry, BannerPatterns.STRIPE_MIDDLE, DyeColor.BLACK).addIfRegistered(patternRegistry, BannerPatterns.HALF_HORIZONTAL, DyeColor.LIGHT_GRAY).addIfRegistered(patternRegistry, BannerPatterns.CIRCLE_MIDDLE, DyeColor.LIGHT_GRAY).addIfRegistered(patternRegistry, BannerPatterns.BORDER, DyeColor.BLACK).build();
        itemStack.set(DataComponents.BANNER_PATTERNS, bannerPatternLayers);
        itemStack.set(DataComponents.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
        itemStack.set(DataComponents.ITEM_NAME, Component.translatable("block.minecraft.ominous_banner").withStyle(ChatFormatting.GOLD));
        return itemStack;
    }
}
