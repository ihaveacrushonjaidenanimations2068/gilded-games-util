package com.gildedgames.util.world.common;

import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.world.common.world.IWorld;

public interface IWorldHook extends NBT
{
	
	void onLoad();
	
	void onUnload();
	
	void onSave();
	
	void onUpdate();

	IWorld getWorld();
	
}
