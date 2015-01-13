package com.gildedgames.util.worldhook.common.world;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

/**
 * Wrapper around an implementation of Minecraft worlds.
 * Recommended access is through WorldCore.get().
 * @author Emile
 *
 */
public interface IWorld
{
	
	IBlockAccess getBlockAccess();

	IBlockState getBlockState(BlockPos pos);

	boolean setBlockState(BlockPos pos, IBlockState state);

	boolean setBlockState(BlockPos pos, IBlockState newState, int flags);

	boolean isAirBlock(BlockPos pos);

	boolean setBlockToAir(BlockPos pos);

	boolean destroyBlock(BlockPos pos, boolean dropBlock);

	int getDimensionID();

	Random getRandom();

	TileEntity getTileEntity(BlockPos pos);

	void setTileEntity(BlockPos pos, TileEntity tileEntity);

	boolean isWrapperFor(int dimId, boolean isRemote);

	boolean isRemote();

	boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default);

}
