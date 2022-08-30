package com.mtstream.deeper_dark.init;

import com.google.common.base.Supplier;
import com.mtstream.deeper_dark.DeeperDark;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ConfiguredFeatureInit {
	
	public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATUREREG = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, DeeperDark.MOD_ID);

	public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> SCULK_REMAIN = register("sculk_remain",
			() -> new ConfiguredFeature<>(FeatureInit.SCULK_RAMAIN.get(), FeatureConfiguration.NONE));

	public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> SCULK_VINE = register("sculk_vine",
			() -> new ConfiguredFeature<>(FeatureInit.SCULK_VINE.get(), FeatureConfiguration.NONE));

	public static final <FC extends FeatureConfiguration, F extends Feature<FC>> RegistryObject<ConfiguredFeature<FC, ?>> register(String name ,Supplier<ConfiguredFeature<FC, F>> sup) {
		return CONFIGURED_FEATUREREG.register(name, sup);
	}

	
	
}
