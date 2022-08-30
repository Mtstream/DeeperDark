package com.mtstream.deeper_dark;

import com.mtstream.deeper_dark.init.*;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("deeper_dark")
public class DeeperDark {
	public static final String MOD_ID = "deeper_dark";
	
	public DeeperDark() {
		IEventBus ieb = FMLJavaModLoadingContext.get().getModEventBus();
		ItemInit.ITEMREG.register(ieb);
		BlockInit.BLOCKREG.register(ieb);
		FeatureInit.FEATUREREG.register(ieb);
		ConfiguredFeatureInit.CONFIGURED_FEATUREREG.register(ieb);
		PlacedFeatureInit.PLACED_FEATUREREG.register(ieb);
		EffectInit.EFFECTREG.register(ieb);
		SoundEventInit.SOUND_EVENTREG.register(ieb);
	}
}
