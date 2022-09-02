package com.mtstream.deeper_dark.block;

import com.mtstream.deeper_dark.blockEntity.DimLampBlockEntity;
import com.mtstream.deeper_dark.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public class DimLampBlock extends BaseEntityBlock {

	public static final BooleanProperty LIT = BooleanProperty.create("lit");

	public DimLampBlock(Properties prop) {
		super(prop);
		this.registerDefaultState(this.getStateDefinition().any().setValue(LIT, false));
	}

	@Override
	public void tick(BlockState state, ServerLevel lev, BlockPos pos, RandomSource ran) {
		lev.setBlock(pos, state.cycle(LIT), 3);
	}


	@Override
	public void neighborChanged(BlockState state, Level lev, BlockPos pos, Block blk, BlockPos pos1, boolean b) {
		if(!lev.isClientSide){
			boolean hasSignal = lev.hasNeighborSignal(pos);
			if(hasSignal != state.getValue(LIT)){
				if(hasSignal){
					lev.setBlock(pos, state.cycle(LIT), 3);
				}else{
					lev.scheduleTick(pos, this, 2);
				}
			}
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(LIT);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return BlockEntityInit.DIM_LAMP.get().create(pos, state);
	}

	@Override
	public RenderShape getRenderShape(BlockState p_49232_) {
		return RenderShape.MODEL;
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level lev, BlockState state, BlockEntityType<T> type) {
		return createTickerHelper(type, BlockEntityInit.DIM_LAMP.get(), DimLampBlockEntity::tick);
	}
}
