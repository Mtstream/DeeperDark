package com.mtstream.deeper_dark.feature;

import com.mojang.serialization.Codec;
import com.mtstream.deeper_dark.block.ChainingVineBlock;
import com.mtstream.deeper_dark.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkBlock;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SculkVineFeature extends Feature<NoneFeatureConfiguration> {

    public SculkVineFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> con) {
        RandomSource ran = RandomSource.create();
        BlockPos pos = con.origin();
        WorldGenLevel lev = con.level();
        if(!lev.getBlockState(pos.above()).is(Blocks.SCULK) || !lev.isEmptyBlock(pos)){
            return false;
        }
        int length = ran.nextInt(3 ,15);
        BlockPos curPos = pos;
        for(int i = 0;i < length;i++){
            curPos = pos.below(i);
            lev.setBlock(curPos, BlockInit.CHAINING_VINE.get().defaultBlockState().setValue(ChainingVineBlock.AGE, i), 2);
            if(!lev.isEmptyBlock(curPos.below())){
                break;
            }
        }
        if(ran.nextBoolean()&&lev.isEmptyBlock(curPos.below())){
            lev.setBlock(curPos.below(), BlockInit.SCULKLIGHT.get().defaultBlockState(), 2);
        }
        return true;
    }
}
