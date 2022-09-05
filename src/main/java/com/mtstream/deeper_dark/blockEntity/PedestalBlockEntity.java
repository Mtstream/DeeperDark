package com.mtstream.deeper_dark.blockEntity;

import com.mtstream.deeper_dark.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class PedestalBlockEntity extends BlockEntity {

    protected final ItemStackHandler inv;

    public PedestalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.PEDESTAL.get(), pos, state);
        this.inv = new ItemStackHandler(){
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                PedestalBlockEntity.setChanged(level, pos, state);
            }
        };
    }

    public ItemStack getStack(){

        return this.inv.getStackInSlot(0);
    }

    public void setStack(ItemStack stack){
        this.inv.setStackInSlot(0, stack);
        this.setChanged();
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if(this.level != null) {
            this.level.setBlockAndUpdate(this.worldPosition, getBlockState());
            this.level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_IMMEDIATE);
        }
    }

    public void playerUse(Level lev, Player pla, InteractionHand han, Direction dir){
        pla.displayClientMessage(Component.literal(this.getStack().toString()), true);
        if(!lev.isClientSide && dir == Direction.UP){
            ItemStack playerStack = pla.getItemInHand(han);
            pla.setItemInHand(han, this.getStack());
            this.setStack(playerStack);
        }
        pla.displayClientMessage(Component.literal(this.getStack().toString()), true);
    }

    @Override
    protected void saveAdditional(CompoundTag cpt) {
        super.saveAdditional(cpt);
        cpt.put("Item", this.inv.serializeNBT());
    }

    @Override
    public CompoundTag getUpdateTag() {
        return serializeNBT();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        this.load(tag);
    }


    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }



    @Override
    public void load(CompoundTag cpt) {
        super.load(cpt);
        CompoundTag itemTag = (CompoundTag) cpt.get("Item");
        this.inv.deserializeNBT(itemTag);
    }


}
