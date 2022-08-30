package com.mtstream.deeper_dark.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SculkwoundPickaxeItem extends PickaxeItem{

	public SculkwoundPickaxeItem(Tier tier, int p_42962_, float p_42963_, Properties p_42964_) {
		super(tier, p_42962_, p_42963_, p_42964_);
	}
	
	@Override
	public boolean mineBlock(ItemStack stack, Level lev, BlockState state, BlockPos pos,
			LivingEntity ent) {
		if(state.is(Blocks.REINFORCED_DEEPSLATE) && !lev.isClientSide) {
			ItemStack blockStack = new ItemStack(state.getBlock().asItem());
			Block.popResource(lev, pos, blockStack);
		}
		return super.mineBlock(stack, lev, state, pos, ent);
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		if(state.is(Blocks.REINFORCED_DEEPSLATE)) {
			return state.getDestroySpeed(null, null)/10;
		}
		return state.getDestroySpeed(null, null);
	}
}
