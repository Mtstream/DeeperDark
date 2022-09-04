package com.mtstream.deeper_dark.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.warden.SonicBoom;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;

import java.util.List;

public class LevelUtils {
    public static boolean hasSignal(LevelReader lev, BlockPos pos){
        for(Direction dir : Direction.values()){
            if(lev.getDirectSignal(pos, dir) > 0){
                return true;
            }
        }
        return false;
    }

    public static ServerLevel getServerLevel(Level lev){
        return lev.getServer().getLevel(lev.dimension());
    }

    public static void shootSonicBoom(Level lev, Vec3 source, Vec3 facing){
        if(!lev.isClientSide)
            lev.playSound(null, new BlockPos(source), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.BLOCKS, 1.0f, 1.0f);
        for(int i = 0;i < 64;i++){
            Vec3 current = source.add(facing.scale(i));
            BlockPos pos = new BlockPos(current);
            if(!lev.isEmptyBlock(pos) && !lev.getBlockState(pos).getCollisionShape(lev, pos).isEmpty()){
                if(!lev.isClientSide)
                    lev.playSound(null, new BlockPos(source), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.BLOCKS, 1.0f, 1.0f);
                break;
            }
            if(!lev.isClientSide){
                List<Entity> ents = lev.getEntities(null, Shapes.block().bounds().move(current));
                for(Entity ent : ents){
                    if(ent instanceof  LivingEntity){
                        ent.hurt(new DamageSource("sonic_boom").bypassArmor().bypassEnchantments().setMagic(),10.0f);
                    }
                }
                ServerLevel serverLev = getServerLevel(lev);
                serverLev.sendParticles(ParticleTypes.SONIC_BOOM, current.x(), current.y(), current.z(), 1, 0, 0, 0, 0);
            }
        }
    }
}
