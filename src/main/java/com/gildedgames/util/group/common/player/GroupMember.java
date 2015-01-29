package com.gildedgames.util.group.common.player;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.io_manager.IOManager;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerHook;
import com.gildedgames.util.player.common.player.IPlayerProfile;

public class GroupMember implements IPlayerHook
{

	private IPlayerProfile profile;

	private IPlayerHookPool<GroupMember> pool;

	private boolean isDirty;

	public GroupMember(IPlayerProfile profile, IPlayerHookPool<GroupMember> pool)
	{
		this.profile = profile;
		this.pool = pool;
	}

	@Override
	public void read(NBTTagCompound arg0)
	{

	}

	@Override
	public void write(NBTTagCompound arg0)
	{

	}

	@Override
	public boolean isDirty()
	{
		return this.isDirty;
	}

	@Override
	public void markClean()
	{
		this.isDirty = false;
	}

	@Override
	public void markDirty()
	{
		this.isDirty = true;
	}

	@Override
	public void readFromClient(ByteBuf arg0)
	{

	}

	@Override
	public void readFromServer(ByteBuf arg0)
	{

	}

	@Override
	public void writeToClient(ByteBuf arg0)
	{

	}

	@Override
	public void writeToServer(ByteBuf arg0)
	{

	}

	@Override
	public void entityInit(EntityPlayer player)
	{

	}

	@Override
	public IPlayerHookPool<GroupMember> getParentPool()
	{
		return this.pool;
	}

	@Override
	public IPlayerProfile getProfile()
	{
		return this.profile;
	}

	@Override
	public void onChangedDimension()
	{

	}

	@Override
	public void onDeath()
	{

	}

	@Override
	public boolean onLivingAttack(DamageSource arg0)
	{
		return true;
	}

	@Override
	public void onRespawn()
	{

	}

	@Override
	public void onUpdate()
	{

	}

}
