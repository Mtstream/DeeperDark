package com.mtstream.deeper_dark.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class SonicBoomBlock extends Block {

    public SonicBoomBlock(Properties prop) {
        super(prop);
    }


    @Override
    public void entityInside(BlockState state, Level lev, BlockPos pos, Entity ent) {
        if(ent instanceof LivingEntity && !lev.isClientSide){
            ent.hurt(new DamageSource("sonicBoom").bypassArmor().bypassEnchantments().setMagic(),10.0f);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel lev, BlockPos pos, RandomSource ran) {
        lev.addParticle(ParticleTypes.SONIC_BOOM, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, 0, 0,0);
        lev.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
    }
}
