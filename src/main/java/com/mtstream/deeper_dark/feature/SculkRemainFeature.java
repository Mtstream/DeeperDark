package com.mtstream.deeper_dark.feature;

import java.util.Random;

import com.mojang.serialization.Codec;
import com.mtstream.deeper_dark.init.BlockInit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SculkRemainFeature extends Feature<NoneFeatureConfiguration>{

	public SculkRemainFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> contex) {
		WorldGenLevel lev = contex.level();
		BlockPos pos = contex.origin();
		
		Axis axis = new Random().nextBoolean() ? Axis.X : Axis.Z;
		Direction dir = Direction.fromAxisAndDirection(axis, (new Random().nextBoolean() ? AxisDirection.POSITIVE : AxisDirection.NEGATIVE));
		
		int length = new Random().nextInt(5)+1;
		
		if(!lev.getBlockState(pos).is(Blocks.SCULK))
			return false;
		BlockPos currentPos = pos.below();
		int placedLength = 0;
		for(int i = 0; i < length; i++) {
			if(checkBurried(lev, currentPos)) {
				break;
			}
			BlockState state = BlockInit.SCULK_REMAIN.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, axis);
			lev.setBlock(currentPos, state, 2);
			currentPos = currentPos.relative(dir);
			placedLength = i;
		}
		return placedLength > 0;
		
	}

	private static boolean checkBurried(WorldGenLevel lev, BlockPos pos){
		for(Direction dir : Direction.values()){
			if(lev.isEmptyBlock(pos.relative(dir))){
				return false;
			}
		}
		return true;
	}

}
