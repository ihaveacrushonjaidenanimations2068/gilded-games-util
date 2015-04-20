package com.gildedgames.util.world.common.world;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class WorldMinecraftFactoryClient implements IWorldFactory<WorldMinecraft>
{

	@Override
	public WorldMinecraft create(int dimId, boolean isRemote)
	{
		if (!isRemote)
		{
			return null;
		}

		World world = Minecraft.getMinecraft().theWorld;

		if (world.provider.getDimensionId() == dimId)
		{
			return new WorldMinecraft(world);
		}
		throw new RuntimeException("Tried to access world with Dimension id " + dimId + " on the client, but that's not where the player is.");
	}

}
