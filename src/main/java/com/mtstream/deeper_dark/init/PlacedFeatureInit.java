package com.mtstream.deeper_dark.init;

import com.mtstream.deeper_dark.DeeperDark;
import com.mtstream.deeper_dark.gen.Generator;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class PlacedFeatureInit {
	
	public static final DeferredRegister<PlacedFeature> PLACED_FEATUREREG = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, DeeperDark.MOD_ID);
	
	public static final RegistryObject<PlacedFeature> SCULK_REMAIN_PLACED = register("sculk_remain_placed", ConfiguredFeatureInit.SCULK_REMAIN,
			Generator.uniGen(150,HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(-60), VerticalAnchor.aboveBottom(100))));

	public static final RegistryObject<PlacedFeature> SCULK_VINE_PLACED = register("sculk_vine_placed", ConfiguredFeatureInit.SCULK_VINE,
			Generator.uniGen(240,HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(24), VerticalAnchor.aboveBottom(44))));

	public static RegistryObject<PlacedFeature> register(String name, RegistryObject<? extends ConfiguredFeature<?, ?>> conf, List<PlacementModifier> mod) {
		return PLACED_FEATUREREG.register(name, () -> new PlacedFeature((Holder<ConfiguredFeature<?, ?>>) conf.getHolder().get(), mod));
	}

}
