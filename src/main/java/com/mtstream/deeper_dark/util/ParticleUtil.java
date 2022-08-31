package com.mtstream.deeper_dark.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;

public class ParticleUtil {
    public static void addRandomPosParticle(ParticleOptions par, Level lev, BlockPos pos){
        lev.addParticle(par, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0);
    }
}
