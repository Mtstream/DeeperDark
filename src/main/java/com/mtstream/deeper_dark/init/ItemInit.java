package com.mtstream.deeper_dark.init;

import com.google.common.base.Supplier;
import com.mtstream.deeper_dark.DeeperDark;
import com.mtstream.deeper_dark.item.ModTiers;
import com.mtstream.deeper_dark.item.SculkwoundAxeItem;
import com.mtstream.deeper_dark.item.SculkwoundPickaxeItem;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMREG = DeferredRegister.create(ForgeRegistries.ITEMS, DeeperDark.MOD_ID);
	
	public static final RegistryObject<Item> SCULKWOUND_VERTEBRA = register("sculkwound_vertebra", 
			() -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	
	public static final RegistryObject<Item> SCULKWOUND_SWORD = register("sculkwound_sword", 
			() -> new SwordItem(ModTiers.SCULKWOUND, 3, -2.4f, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
	
	public static final RegistryObject<Item> SCULKWOUND_PICKAXE = register("sculkwound_pickaxe", 
			() -> new SculkwoundPickaxeItem(ModTiers.SCULKWOUND, 1, -2.8f, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
	
	public static final RegistryObject<Item> SCULKWOUND_AXE = register("sculkwound_axe", 
			() -> new SculkwoundAxeItem(ModTiers.SCULKWOUND, 6.0F, -3.5f, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
	
	public static final RegistryObject<Item> SCULKWOUND_HOE = register("sculkwound_hoe", 
			() -> new PickaxeItem(ModTiers.SCULKWOUND, 1, -2.8f, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

	public static final RegistryObject<Item> SCULK_POWDER = register("sculk_powder",
			() -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	
	public static <T extends Item> RegistryObject<T> register(String name, Supplier<T> sup){
		return ITEMREG.register(name, sup);
	}
}
