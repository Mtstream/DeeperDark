package com.mtstream.deeper_dark.block;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Function;

public class SculkLightBlock extends Block {

    public SculkLightBlock(Properties prop) {
        super(prop);
        this.registerDefaultState(getStateDefinition().any().setValue(EXPLODE, 0));
    }

    public static final IntegerProperty EXPLODE = IntegerProperty.create("explode",0,2);

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        VoxelShape shape = state.getValue(EXPLODE) == 0 ? Shapes.block() : Shapes.empty();
        return shape;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(EXPLODE);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource source) {
        if(state.getValue(EXPLODE) == 1){
            level.setBlock(pos, state.setValue(EXPLODE, 2), 3);
            level.scheduleTick(pos,this,2);
            return;
        }
        if(state.getValue(EXPLODE) == 2){
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            return;
        }
    }

    @Override
    public void onProjectileHit(Level lev, BlockState state, BlockHitResult res, Projectile pro) {
        if(pro instanceof Arrow){
            BlockPos pos = res.getBlockPos();
            explode(lev, pos, state, this);
        }
    }

    @Override
    public void stepOn(Level lev, BlockPos pos, BlockState state, Entity ent) {
        if(ent instanceof LivingEntity){
            explode(lev, pos, state, this);
        }
    }

    public static void explode(Level lev, BlockPos pos, BlockState state, Block blk){
        RandomSource ran = RandomSource.create();
        if(!lev.isClientSide){
            lev.setBlock(pos, state.setValue(EXPLODE, 1), 3);
            lev.playSound(null, pos, SoundEvents.SHULKER_SHOOT, SoundSource.BLOCKS, 1.0f, 1.0f);
            lev.scheduleTick(pos, blk, 2);
        }
        for(int i = 0;i <= 15;i++){
            lev.addParticle(ParticleTypes.GLOW,pos.getX()+ran.nextDouble(),pos.getY()+ran.nextDouble(),pos.getZ()+ran.nextDouble(),
                    ran.nextDouble()*10,ran.nextDouble()*10,ran.nextDouble()*10);
            lev.addParticle(ParticleTypes.SCULK_CHARGE_POP,pos.getX()+ran.nextDouble(),pos.getY()+ran.nextDouble(),pos.getZ()+ran.nextDouble(),
                    ran.nextDouble()-0.5,ran.nextDouble()-0.5,ran.nextDouble()-0.5);
        }
        lev.addParticle(ParticleTypes.SONIC_BOOM, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, 0, 0, 0);
    }
}
