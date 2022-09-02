package com.mtstream.deeper_dark.blockEntity;

import com.mtstream.deeper_dark.block.DimLampBlock;
import com.mtstream.deeper_dark.init.BlockEntityInit;
import com.mtstream.deeper_dark.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class DimLampBlockEntity extends BlockEntity {

    public DimLampBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.DIM_LAMP.get(), pos, state);
    }

    public static <E extends BlockEntity> void tick(Level lev, BlockPos pos, BlockState state, DimLampBlockEntity ent) {
        if(!lev.isClientSide){
            if(state.getValue(DimLampBlock.LIT)){
                for(BlockPos lightPos : getLightArea(pos, 2, 2)){
                    if(lev.getBlockState(lightPos).is(BlockInit.DIM_LIGHT.get()))
                        continue;
                    for(Direction dir : Direction.values()){
                        if(lev.getBlockState(lightPos.relative(dir)).is(BlockInit.DIM_LIGHT.get())){
                            continue;
                        }
                    }
                    if(lev.isEmptyBlock(lightPos)) {
                        DimLightBlockEntity.placeDimLight(lev, pos, lightPos);
                    }else{
                        for(Direction dir : Direction.values()){
                            if(lev.isEmptyBlock(lightPos.relative(dir))){
                                DimLightBlockEntity.placeDimLight(lev, pos, lightPos.relative(dir));
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public static List<BlockPos> getLightArea(BlockPos pos, int range, int space){
        List<BlockPos> area = new ArrayList<>();
        for(int j = 1;j <= range;j++){
            area.addAll(makeLoop(pos, j, space));
        }
        for(int i = 0;i <= range;i++){
            for(int j = 1;j <= range;j++){
                area.addAll(makeLoop(pos.above((space+1)*i), j, space));
            }
        }
        for(int i = 0;i <= range;i++){
            for(int j = 1;j <= range;j++){
                area.addAll(makeLoop(pos.below((space+1)*i), j, space));
            }
        }
        return area;
    }

    public static List<BlockPos> makeLoop(BlockPos centre, int radius, int space){
        List<BlockPos> area = new ArrayList<>();
        BlockPos origin = centre.west((space + 1) * radius).north((space + 1) * radius);
        BlockPos current = origin;
        for(Direction dir : new Direction[]{Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH}){
            for(int i = 0;i<(radius*2);i++){
                area.add(current);
                current = current.relative(dir, space+1);
            }
        }
        return area;
    }
}
