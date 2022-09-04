package com.mtstream.deeper_dark.blockEntity.blockEntityRederer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mtstream.deeper_dark.blockEntity.PedestalBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;

public class PedestalRenderer implements BlockEntityRenderer<PedestalBlockEntity> {

    public final BlockEntityRendererProvider.Context con;

    public PedestalRenderer(BlockEntityRendererProvider.Context con){
        this.con = con;
    }

    @Override
    public void render(PedestalBlockEntity ent, float f, PoseStack poseStack, MultiBufferSource src, int i, int i1) {
        BlockRenderDispatcher brd = this.con.getBlockRenderDispatcher();
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        ItemStack itemStack = ent.getStack();
        if(itemStack.isEmpty()){
            return;
        }
        poseStack.popPose();
        poseStack.translate(0.5, 1.5, 0.5);
        poseStack.scale(0.5f, 0.5f, 0.5f);
        renderer.renderStatic(null, itemStack, ItemTransforms.TransformType.FIXED, false, poseStack, src, Minecraft.getInstance().level, i, i1, 0);
        poseStack.popPose();
    }
}
