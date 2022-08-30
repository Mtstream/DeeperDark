package com.mtstream.deeper_dark.init;

import com.mtstream.deeper_dark.DeeperDark;
import com.mtstream.deeper_dark.feature.SculkRemainFeature;

import com.mtstream.deeper_dark.feature.SculkVineFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FeatureInit {
	public static final DeferredRegister<Feature<?>> FEATUREREG = DeferredRegister.create(ForgeRegistries.FEATURES, DeeperDark.MOD_ID);
	
	public static final RegistryObject<SculkRemainFeature> SCULK_RAMAIN = FEATUREREG.register("sculk_remain", 
			()-> new SculkRemainFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<SculkVineFeature> SCULK_VINE = FEATUREREG.register("sculk_vine",
			()-> new SculkVineFeature(NoneFeatureConfiguration.CODEC));
}
