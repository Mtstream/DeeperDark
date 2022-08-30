package com.mtstream.deeper_dark.block;

import com.mtstream.deeper_dark.init.BlockInit;
import com.mtstream.deeper_dark.util.LevelUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.warden.SonicBoom;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.ObserverBlock;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.Shapes;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SonicEmitterBlock extends DirectionalBlock {

    public static final IntegerProperty CHARGE = IntegerProperty.create("charge", 0, 4);
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");
    public static final BooleanProperty ISCHARGING = BooleanProperty.create("is_charging");

    public SonicEmitterBlock(Properties prop) {
        super(prop);
        this.registerDefaultState(this.getStateDefinition().any().setValue(CHARGE, 0).setValue(FACING, Direction.NORTH).setValue(POWERED, false).setValue(ISCHARGING, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(FACING, POWERED, CHARGE, ISCHARGING));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext con) {
        BlockState state = this.defaultBlockState().setValue(FACING, con.getNearestLookingDirection().getOpposite());
        return state;
    }

    @Override
    public void neighborChanged(BlockState state, Level lev, BlockPos pos, Block block, BlockPos pos1, boolean b) {
        boolean hasSignal = lev.hasNeighborSignal(pos);
        if((hasSignal != state.getValue(POWERED)) && !state.getValue(ISCHARGING)){
            if(hasSignal)
                sonicBoom(lev, pos, state.getValue(FACING));
            if(!lev.isClientSide){
                lev.scheduleTick(pos ,this, 20);
                lev.setBlock(pos, state.cycle(POWERED).setValue(ISCHARGING, !hasSignal).setValue(CHARGE, hasSignal?0:state.getValue(CHARGE)), 3);
            }
        }
    }

    @Override
    public void onPlace(BlockState state, Level lev, BlockPos pos, BlockState state1, boolean b) {
        lev.scheduleTick(pos, this, 20);
    }

    @Override
    public void tick(BlockState state, ServerLevel lev, BlockPos pos, RandomSource ran) {
        int charge = state.getValue(CHARGE);
        if(!lev.isClientSide){
            if(charge < 4 && state.getValue(ISCHARGING)){
                lev.setBlock(pos, state.setValue(CHARGE, charge + 1), 3);
            }
            if(charge == 3){
                lev.setBlock(pos, state.setValue(CHARGE, charge + 1).setValue(ISCHARGING, false), 3);
            }
        }
    }

    public static void sonicBoom(Level lev, BlockPos pos, Direction dir){
        if(!lev.isClientSide){
            lev.playSound((Player) null, pos, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.BLOCKS, 1.0f, 1.0f);
        }
        BlockPos currentPos = pos;
        for(int i = 0;i<=64;i++){
            currentPos = currentPos.relative(dir);
            if(!lev.getBlockState(currentPos).getCollisionShape(lev, currentPos).isEmpty() && !lev.isEmptyBlock(currentPos)){
                break;
            }
            if(!lev.isClientSide){
                List<Entity> ents = lev.getEntities(null, Shapes.block().bounds().move(currentPos));
                for(Entity ent : ents){
                    if(ent instanceof  LivingEntity){
                        ent.hurt(new DamageSource("sonicBoom").bypassArmor().bypassEnchantments().setMagic(),10.0f);
                    }
                }
            }
            ServerLevel serverLev = lev.getServer().getLevel(lev.dimension());
            serverLev.sendParticles(ParticleTypes.SONIC_BOOM, currentPos.getX()+0.5, currentPos.getY()+0.5, currentPos.getZ()+0.5, 1, 0, 0, 0, 0);
        }
    }

}
