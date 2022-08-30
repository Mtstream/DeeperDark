package com.mtstream.deeper_dark.init;

import com.google.common.base.Supplier;
import com.mtstream.deeper_dark.DeeperDark;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundEventInit {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTREG = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DeeperDark.MOD_ID);

    public static final RegistryObject<SoundEvent> VIBRATING = register("effect.vibrating",
            () -> new SoundEvent(SoundEvents.SOUL_ESCAPE.getLocation()));

    public static <T extends SoundEvent> RegistryObject<T> register(String name, Supplier<T> sup){
        return SOUND_EVENTREG.register(name, sup);
    }
}
