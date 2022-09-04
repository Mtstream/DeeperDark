package com.mtstream.deeper_dark.block;

import com.mtstream.deeper_dark.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.WeepingVinesPlantBlock;
import net.minecraft.world.level.block.entity.SculkCatalystBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class ChainingVineBlock extends Block {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_15;

    public ChainingVineBlock(Properties prop) {
        super(prop);
        this.registerDefaultState(this.getStateDefinition().any().setValue(AGE, 0));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader levRd, BlockPos pos) {
        boolean b = levRd.getBlockState(pos.above()).isFaceSturdy(levRd, pos.above(), Direction.DOWN);
        return b || levRd.getBlockState(pos.above()).is(BlockInit.CHAINING_VINE.get());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction dir, BlockState state1, LevelAccessor levAc, BlockPos pos, BlockPos pos1) {
        return state.canSurvive(levAc, pos) ? state : Blocks.AIR.defaultBlockState();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter lev, BlockPos pos, CollisionContext con) {
        return Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    }

    @Override
    public boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        return true;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter get, BlockPos pos, CollisionContext con) {
        return Shapes.empty();
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < 15;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel lev, BlockPos pos, RandomSource ran) {
        if(ran.nextInt(3) < 1){
            return;
        }
        if(lev.isEmptyBlock(pos.below())&&!lev.isClientSide){
            if(new Random().nextInt(5) < 1){
                lev.setBlock(pos.below(), BlockInit.SCULKLIGHT.get().defaultBlockState(), 3);
                return;
            }
            lev.setBlock(pos.below(), BlockInit.CHAINING_VINE.get().defaultBlockState().setValue(AGE, state.getValue(AGE)+1), 3);
        }
    }

}
