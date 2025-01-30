package de.rasmusantons.enigmaticsbingogoals.mixin;

import de.rasmusantons.enigmaticsbingogoals.triggers.EnigmaticsBingoGoalsTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractCauldronBlock.class)
public abstract class AbstractCauldronBlockMixin {
    @Inject(method = "useItemOn", at = @At("HEAD"))
    protected void useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {

        if ((stack.is(ItemTags.DYEABLE)) && stack.getItem() instanceof ArmorItem && stack.has(DataComponents.DYED_COLOR)) {
            if ((AbstractCauldronBlock) (Object) this instanceof LayeredCauldronBlock layeredCauldronBlock) {
                if (layeredCauldronBlock.getStateDefinition().getOwner() == Blocks.WATER_CAULDRON) {
                    EnigmaticsBingoGoalsTriggers.CLEAN_ARMOR_IN_CAULDRON.get().trigger((ServerPlayer) player, stack);
                }
            }
        }
    }
}