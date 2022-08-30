package com.mtstream.deeper_dark.item;

import java.util.function.Supplier;

import com.mtstream.deeper_dark.init.ItemInit;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

@SuppressWarnings("deprecation")
public enum ModTiers implements Tier {
	SCULKWOUND(5, 4096, 8.0F, 4.0F, 17, () -> {
		return Ingredient.of(ItemInit.SCULKWOUND_VERTEBRA.get());
	});
	
	private final int level;
	private final int uses;
	private final float speed;
	private final float damage;
	private final int enchantmentValue;
	private final LazyLoadedValue<Ingredient> repairIngredient;
	
	private ModTiers(int level, int uses, float speed, float damage, int ecv, Supplier<Ingredient> sup) {
		this.level = level;
	    this.uses = uses;
	    this.speed = speed;
	    this.damage = damage;
	    this.enchantmentValue = ecv;
	    this.repairIngredient = new LazyLoadedValue<>(sup);
	}
	
	

	public int getUses() {
	     return this.uses;
	}

	public float getSpeed() {
		return this.speed;
	}

	public float getAttackDamageBonus() {
		return this.damage;
	}

	public int getLevel() {
		return this.level;
	}

	public int getEnchantmentValue() {
		return this.enchantmentValue;
	}

	public Ingredient getRepairIngredient() {
		return this.repairIngredient.get();
	}

}
