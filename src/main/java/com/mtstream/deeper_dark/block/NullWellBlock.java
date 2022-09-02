package com.mtstream.deeper_dark.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class NullWellBlock extends Block {

    public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");

    public NullWellBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.getStateDefinition().any().setValue(ACTIVATED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED);
    }

    @Override
    public void animateTick(BlockState state, Level lev, BlockPos pos, RandomSource ran) {
        if(state.getValue(ACTIVATED)) {
            for (int i = 0; i <= 5; i++) {
                lev.addParticle(ParticleTypes.ASH,
                        pos.getX() + 0.0625 + (ran.nextDouble() * 0.875),
                        pos.getY() + 0.0625 + (ran.nextDouble() * 0.875),
                        pos.getZ() + 0.0625 + (ran.nextDouble() * 0.875),
                        0, 0.5, 0);
                lev.addParticle(ParticleTypes.SMOKE, pos.getX()+ran.nextDouble(), pos.getY()+1, pos.getZ()+ran.nextDouble(),
                        ran.nextDouble()-0.5, ran.nextDouble()/2, ran.nextDouble()-0.5);
            }
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level lev, BlockPos pos, Block blk, BlockPos pos1, boolean b) {
        boolean hasSignal = lev.hasNeighborSignal(pos);
        boolean activated = state.getValue(ACTIVATED);
        if(hasSignal != activated){
            if(!lev.isClientSide){
                if(hasSignal){
                    lev.playSound(null, pos, SoundEvents.BARREL_OPEN, SoundSource.BLOCKS, 1.0f, 1.0f);
                    lev.playSound(null, pos, SoundEvents.END_PORTAL_SPAWN, SoundSource.BLOCKS, 1.0f, 1.0f);
                }else{
                    lev.playSound(null, pos, SoundEvents.BARREL_CLOSE, SoundSource.BLOCKS, 1.0f, 1.0f);
                }
                lev.setBlock(pos, state.setValue(ACTIVATED, hasSignal), 3);
            }
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext con) {
        VoxelShape open = Shapes.or(
                box(0,0,0,16,1,16),
                box(0,0,0,2,16,16),
                box(0,0,0,16,16,2),
                box(14,0,0,16,16,16),
                box(0,0,14,16,16,16));
        VoxelShape shape = state.getValue(ACTIVATED) ? open : Shapes.or(open, box(0, 15,0,16,16,16));
        return shape;
    }

    @Override
    public void entityInside(BlockState state, Level lev, BlockPos pos, Entity ent) {
        List<Entity> entities = lev.getEntities(null, box(2,1,2,14,12,14).bounds().move(pos));
        for(Entity entity : entities){
            if(!lev.isClientSide) {
                ent.hurt(DamageSource.OUT_OF_WORLD, 4);
                if(entity instanceof ItemEntity itemEnt){
                    if(itemEnt.getItem().is(Items.SCULK)){
                        ServerLevel serverLev = lev.getServer().getLevel(lev.dimension());
                        if(serverLev != null) popExperience(serverLev, pos.above(), 1);
                    }
                }
            }
        }
        ent.makeStuckInBlock(state, new Vec3(0.5,0.5,0.5));
    }

    @Override
    public void stepOn(Level lev, BlockPos pos, BlockState state, Entity ent) {
        if(!lev.isClientSide && state.getValue(ACTIVATED)) pullEntity(ent, pos);
    }

    public static void pullEntity(Entity ent, BlockPos pos){
        Vec3 pullForce = ent.position().subtract(Vec3.atLowerCornerOf(pos).add(0.5,0.5,0.5)).scale(ent instanceof ItemEntity ? -0.2 : -1.5);
        ent.setDeltaMovement(ent.getDeltaMovement().add(pullForce));
    }
}
