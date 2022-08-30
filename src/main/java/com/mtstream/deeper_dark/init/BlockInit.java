package com.mtstream.deeper_dark.init;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.mtstream.deeper_dark.DeeperDark;

import com.mtstream.deeper_dark.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit{
	public static final DeferredRegister<Block> BLOCKREG = DeferredRegister.create(ForgeRegistries.BLOCKS, DeeperDark.MOD_ID);
	public static final DeferredRegister<Item> ITEMREG = ItemInit.ITEMREG;
	
	public static final RegistryObject<Block> SCULK_REMAIN = register("sculk_remain",
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.BASALT).dynamicShape().sound(SoundType.BASALT)
					.requiresCorrectToolForDrops().strength(45.0F, 1200.0F)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

	public static final RegistryObject<Block> SCULKLIGHT = register("sculklight",
			() -> new SculkLightBlock(BlockBehaviour.Properties.copy(Blocks.SHROOMLIGHT).dynamicShape().instabreak()),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

	public static final RegistryObject<Block> CHAINING_VINE = register("chaining_vine",
			() -> new ChainingVineBlock(BlockBehaviour.Properties.copy(Blocks.SHROOMLIGHT).dynamicShape().instabreak().sound(SoundType.GRASS)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

	public static final RegistryObject<Block> SONIC_EMITTER = register("sonic_emitter",
			() -> new SonicEmitterBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).dynamicShape().lightLevel((state)->{
				return state.getValue(SonicEmitterBlock.CHARGE) * 3;
			})),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));

	public static final RegistryObject<Block> NULL_WELL = register("null_well",
			() -> new NullWellBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).dynamicShape().lightLevel((state)->{
				return state.getValue(NullWellBlock.ACTIVATED) ? 15 : 0;
			})),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));

	public static <T extends Block> RegistryObject<T> registerBlock(final String name,final Supplier<? extends T> sup){
		return BLOCKREG.register(name, sup);
	}
	
	public static <T extends Block> RegistryObject<T> register(String name, Supplier<? extends T> sup,
		Function<RegistryObject<T>, Supplier<? extends Item>> item){
		RegistryObject<T> obj = registerBlock(name, sup);
		ITEMREG.register(name, item.apply(obj));
		return obj;
	}
}
