package com.mtstream.deeper_dark.blockEntity;

import com.mtstream.deeper_dark.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PedestalBlockEntity extends BlockEntity {

    private ItemStack stack;

    public PedestalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.PEDESTAL.get(), pos, state);
        this.stack = ItemStack.EMPTY;
    }

    public ItemStack getStack(){
        return this.stack;
    }

    public void setStack(ItemStack stack){
        this.stack = stack;
        this.setChanged();
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if(this.level != null) {
            this.level.setBlockAndUpdate(this.worldPosition, getBlockState());
        }
    }

    public void playerUse(Level lev, Player pla, InteractionHand han){
        pla.displayClientMessage(Component.literal(this.stack.toString()), true);
        if(!lev.isClientSide){
            ItemStack playerStack = pla.getItemInHand(han);
            pla.setItemInHand(han, this.getStack());
            this.setStack(playerStack);
        }
        pla.displayClientMessage(Component.literal(this.stack.toString()), true);
    }

    @Override
    protected void saveAdditional(CompoundTag cpt) {
        cpt.put("Item", this.stack.serializeNBT());
    }

    @Override
    public void load(CompoundTag cpt) {
        CompoundTag itemTag = (CompoundTag) cpt.get("Item");
        this.stack.deserializeNBT(itemTag);
    }
}
