package com.mtstream.deeper_dark.block;

import com.mtstream.deeper_dark.blockEntity.PedestalBlockEntity;
import com.mtstream.deeper_dark.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class PedestalBlock extends BaseEntityBlock {

    public PedestalBlock(Properties prop) {
        super(prop);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityInit.PEDESTAL.get().create(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level lev, BlockPos pos, Player pla, InteractionHand han, BlockHitResult res) {
        BlockEntity blkEnt = lev.getBlockEntity(pos);
        if(blkEnt instanceof PedestalBlockEntity pedestalEnt){
            pedestalEnt.playerUse(lev, pla, han);
        }
        return InteractionResult.sidedSuccess(lev.isClientSide);
    }
}
