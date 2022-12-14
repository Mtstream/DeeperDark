package com.mtstream.deeper_dark.block;

import com.mtstream.deeper_dark.blockEntity.PedestalBlockEntity;
import com.mtstream.deeper_dark.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PedestalBlock extends BaseEntityBlock {


    public PedestalBlock(Properties prop) {
        super(prop);
    }



    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return Shapes.or(
                box(2, 0, 2, 14, 4, 14),
                box(5, 4, 5, 11, 10, 11),
                box(0, 10, 0, 16, 16, 16)
        );
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
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
            pedestalEnt.playerUse(lev, pla, han, res.getDirection());
        }
        return InteractionResult.sidedSuccess(lev.isClientSide);
    }

    @Override
    public void stepOn(Level lev, BlockPos pos, BlockState state, Entity ent) {
        BlockEntity blkEnt = lev.getBlockEntity(pos);
        if(blkEnt instanceof PedestalBlockEntity pedestalEnt && ent instanceof Player pla){
            pla.displayClientMessage(Component.literal(pedestalEnt.getStack().toString()), true);
        }
    }
}
