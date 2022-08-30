package com.mtstream.deeper_dark.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;

public class LevelUtils {
    public static boolean hasSignal(LevelReader lev, BlockPos pos){
        for(Direction dir : Direction.values()){
            if(lev.getDirectSignal(pos, dir) > 0){
                return true;
            }
        }
        return false;
    }
}
