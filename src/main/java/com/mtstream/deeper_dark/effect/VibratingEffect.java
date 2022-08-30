package com.mtstream.deeper_dark.effect;

import com.mtstream.deeper_dark.init.SoundEventInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class VibratingEffect extends MobEffect {
    public VibratingEffect(MobEffectCategory category, int colour) {
        super(category, colour);
    }

    @Override
    public void applyEffectTick(LivingEntity ent, int amp) {
        Level lev = ent.level;
        RandomSource ran = RandomSource.create();
        if(lev.getGameTime() % (100/Math.max(1, amp)) == 0){
            ent.playSound(SoundEventInit.VIBRATING.get(), 1.0f, 1.0f);
            for(int i = 0;i <= 10;i++){
                lev.addParticle(ParticleTypes.SCULK_CHARGE_POP, ent.position().x, ent.position().y +
                                Mth.lerp(ran.nextDouble(), 0, ent.getEyeHeight()), ent.position().z,
                        ran.nextDouble()-0.5, ran.nextDouble()-0.5, ran.nextDouble()-0.5);
            }
        }
        super.applyEffectTick(ent,amp);
    }

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }
}
