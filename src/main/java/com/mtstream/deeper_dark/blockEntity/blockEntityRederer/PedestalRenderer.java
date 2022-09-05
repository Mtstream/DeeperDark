package com.mtstream.deeper_dark.blockEntity.blockEntityRederer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mtstream.deeper_dark.block.PedestalBlock;
import com.mtstream.deeper_dark.blockEntity.PedestalBlockEntity;
import com.mtstream.deeper_dark.init.BlockInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

public class PedestalRenderer implements BlockEntityRenderer<PedestalBlockEntity> {

    public final BlockEntityRendererProvider.Context con;

    public PedestalRenderer(BlockEntityRendererProvider.Context con){
        this.con = con;
    }

    @Override
    public void render(@NotNull PedestalBlockEntity ent, float f, PoseStack poseStack, MultiBufferSource src, int light, int overlay) {
//        BlockRenderDispatcher brd = this.con.getBlockRenderDispatcher();
//        brd.renderSingleBlock(BlockInit.PEDESTAL.get().defaultBlockState().setValue(PedestalBlock.RENDER, true), poseStack, src, light, overlay, ModelData.EMPTY, null);
        long gameTime = ent.getLevel().getGameTime();

        poseStack.pushPose();
        poseStack.translate(0.5f, 1.1, 0.5f);

        poseStack.mulPose(Vector3f.YP.rotationDegrees(gameTime%360));
        Minecraft.getInstance().getItemRenderer().renderStatic(ent.getStack(), ItemTransforms.TransformType.GROUND, light, overlay, poseStack, src, 0);

        poseStack.popPose();
    }
}
