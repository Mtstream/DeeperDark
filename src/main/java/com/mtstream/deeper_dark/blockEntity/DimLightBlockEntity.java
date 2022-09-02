package com.mtstream.deeper_dark.blockEntity;

import com.mtstream.deeper_dark.block.DimLampBlock;
import com.mtstream.deeper_dark.init.BlockEntityInit;
import com.mtstream.deeper_dark.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DimLightBlockEntity extends BlockEntity {

    private BlockPos master;

    public DimLightBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.DIM_LIGHT.get(), pos, state);
        this.master = pos;
    }

    public static void placeDimLight(Level lev, BlockPos lampPos, BlockPos lightPos){
        lev.setBlock(lightPos, BlockInit.DIM_LIGHT.get().defaultBlockState(), 2);
        BlockEntity blkEnt = lev.getBlockEntity(lightPos);
        if(blkEnt instanceof DimLightBlockEntity lightEnt){
            lightEnt.setMaster(lampPos);
        }
    }

    public BlockPos getMaster(){
        return this.master;
    }

    public void setMaster(BlockPos pos){
        this.master = pos;
    }

    public static <E extends BlockEntity> void tick(Level lev, BlockPos pos, BlockState state, DimLightBlockEntity ent) {
        if(!lev.isClientSide){
            BlockState masterState = lev.getBlockState(ent.master);
            if(masterState.is(BlockInit.DIM_LAMP.get())){
                if (masterState.getValue(DimLampBlock.LIT)){
                    return;
                }
            }
            lev.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag cpt) {
        cpt.putLong("Master", this.master.asLong());
    }

    @Override
    public void load(CompoundTag cpt) {
        this.master = BlockPos.of(cpt.getLong("Master"));
    }
}
