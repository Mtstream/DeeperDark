package com.mtstream.deeper_dark.init;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.mtstream.deeper_dark.DeeperDark;
import com.mtstream.deeper_dark.blockEntity.DimLampBlockEntity;
import com.mtstream.deeper_dark.blockEntity.DimLightBlockEntity;
import com.mtstream.deeper_dark.blockEntity.PedestalBlockEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITYREG = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, DeeperDark.MOD_ID);

    public static final RegistryObject<BlockEntityType<DimLampBlockEntity>> DIM_LAMP = register("dim_lamp",
            () -> BlockEntityType.Builder.of(DimLampBlockEntity::new, BlockInit.DIM_LAMP.get()).build(null));

    public static final RegistryObject<BlockEntityType<DimLightBlockEntity>> DIM_LIGHT = register("dim_light",
            () -> BlockEntityType.Builder.of(DimLightBlockEntity::new, BlockInit.DIM_LIGHT.get()).build(null));

    public static final RegistryObject<BlockEntityType<PedestalBlockEntity>> PEDESTAL = register("pedestal",
            () -> BlockEntityType.Builder.of(PedestalBlockEntity::new, BlockInit.PEDESTAL.get()).build(null));

    public static <T extends BlockEntityType<?>> RegistryObject<T> register(String name, Supplier<T> sup){
        return BLOCK_ENTITYREG.register(name, sup);
    }
}
