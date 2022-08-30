package com.mtstream.deeper_dark.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NullWellBlock extends Block {

    public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");

    public NullWellBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.getStateDefinition().any().setValue(ACTIVATED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED);
    }

    @Override
    public void neighborChanged(BlockState state, Level lev, BlockPos pos, Block blk, BlockPos pos1, boolean b) {
        boolean hasSignal = lev.hasNeighborSignal(pos);
        boolean activated = state.getValue(ACTIVATED);
        if(hasSignal != activated){
            if(!lev.isClientSide){
                lev.setBlock(pos, state.setValue(ACTIVATED, hasSignal), 3);
            }
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext con) {
        VoxelShape open = Shapes.or(
                box(0,0,0,16,1,16),
                box(0,0,0,2,16,16),
                box(0,0,0,16,16,2),
                box(14,0,0,16,16,16),
                box(0,0,14,16,16,16));
        VoxelShape shape = state.getValue(ACTIVATED) ? open : Shapes.block();
        return shape;
    }

    @Override
    public void entityInside(BlockState state, Level lev, BlockPos pos, Entity ent) {
        ent.hurt(DamageSource.OUT_OF_WORLD, 4);
    }
}
