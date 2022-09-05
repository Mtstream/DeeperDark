package com.mtstream.deeper_dark.event;

import com.mtstream.deeper_dark.DeeperDark;
import com.mtstream.deeper_dark.blockEntity.blockEntityRederer.PedestalRenderer;
import com.mtstream.deeper_dark.init.BlockEntityInit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DeeperDark.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetupEvt {

    private ClientSetupEvt(){}

    @SubscribeEvent
    public static void clientSetup(EntityRenderersEvent.RegisterRenderers evt){
        evt.registerBlockEntityRenderer(BlockEntityInit.PEDESTAL.get(), PedestalRenderer::new);
    }

}
