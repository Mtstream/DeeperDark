package com.mtstream.deeper_dark.block;

import com.mtstream.deeper_dark.blockEntity.DimLightBlockEntity;
import com.mtstream.deeper_dark.init.BlockEntityInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.internal.TextComponentMessageFormatHandler;
import org.jetbrains.annotations.Nullable;

public class DimLightBlock extends BaseEntityBlock {

    public DimLightBlock(Properties prop) {
        super(prop);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityInit.DIM_LIGHT.get().create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level lev, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, BlockEntityInit.DIM_LIGHT.get(), DimLightBlockEntity::tick);
    }

    @Override
    public InteractionResult use(BlockState state, Level lev, BlockPos pos, Player pla, InteractionHand han, BlockHitResult res) {
        if(!lev.isClientSide){
            return InteractionResult.SUCCESS;
        }else {
            BlockEntity lightent = lev.getBlockEntity(pos);
            if(lightent instanceof DimLightBlockEntity dimLightBlockEntity){
                BlockPos lightPos = dimLightBlockEntity.getMaster();
                pla.displayClientMessage(Component.nullToEmpty(lightPos.toString()), true);

            }
            return InteractionResult.CONSUME;
        }
    }
}
