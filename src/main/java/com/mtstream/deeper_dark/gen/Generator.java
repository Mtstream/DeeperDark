package com.mtstream.deeper_dark.gen;

import java.util.List;

import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public class Generator {
	public static List<PlacementModifier> gen(PlacementModifier mod, PlacementModifier mod1){
		return List.of(mod, InSquarePlacement.spread(), mod1, BiomeFilter.biome());
	}
	public static List<PlacementModifier> uniGen(int i, PlacementModifier mod){
		return gen(CountPlacement.of(i), mod);
	}
}