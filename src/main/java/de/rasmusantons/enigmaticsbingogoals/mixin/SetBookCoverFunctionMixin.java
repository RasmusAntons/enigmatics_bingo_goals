package de.rasmusantons.enigmaticsbingogoals.mixin;

import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.Filterable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.SetBookCoverFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(SetBookCoverFunction.class)
public abstract class SetBookCoverFunctionMixin {


    @Final
    @Shadow
    private Optional<Filterable<String>> title;

    @Final
    @Shadow
    private Optional<Integer> generation;

    @Inject(method = "run", at = @At("TAIL"))
    protected void setBookCover(ItemStack stack, LootContext context, CallbackInfoReturnable<ItemStack> cir) {

        System.out.println("TEST");
        System.out.println(context);

        if (context.hasParam(LootContextParams.THIS_ENTITY)) {
            Entity entity = context.getParam(LootContextParams.THIS_ENTITY);

            System.out.println(entity);
            System.out.println(title);
            System.out.println(generation.isPresent());

            if (entity instanceof ServerPlayer && generation.isPresent()) {

                EnigmaticsBingoGoalsTriggers.WRITE_BOOK.get().trigger((ServerPlayer) entity, title.toString(), generation.get());
            }
        }
    }
}