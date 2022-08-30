package com.mtstream.deeper_dark.item;

import net.minecraft.world.damagesource.DamageSource;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class SculkwoundAxeItem extends AxeItem{

	public SculkwoundAxeItem(Tier p_40521_, float p_40522_, float p_40523_, Properties p_40524_) {
		super(p_40521_, p_40522_, p_40523_, p_40524_);
	}
	
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity ent, LivingEntity ent1) {
		ent.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 40));
		if(ent instanceof Warden) {
			ent.hurt(DamageSource.mobAttack(ent1), ent1.getHealth()/4);
		}
		return super.hurtEnemy(stack, ent, ent1);
	}
	
}
