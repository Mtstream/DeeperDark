package com.mtstream.deeper_dark.init;

import com.google.common.base.Supplier;
import com.mtstream.deeper_dark.DeeperDark;
import com.mtstream.deeper_dark.effect.VibratingEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EffectInit {
    public static final DeferredRegister<MobEffect> EFFECTREG = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, DeeperDark.MOD_ID);

    public static final RegistryObject<MobEffect> VIBRATING = register("vibrating",
            () -> new VibratingEffect(MobEffectCategory.HARMFUL, 0255255));

    public static <T extends MobEffect> RegistryObject<T> register(String name, Supplier<? extends T> sup){
        return EFFECTREG.register(name, sup);
    }
}
