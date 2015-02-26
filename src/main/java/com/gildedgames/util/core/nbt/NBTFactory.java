package com.gildedgames.util.core.nbt;

import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.factory.IOObserver;
import net.minecraft.nbt.NBTTagCompound;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NBTFactory implements IOFactory<NBTTagCompound, NBTTagCompound>
{

	private int writeIndex, readIndex;

	private final ArrayList<IOObserver<NBTTagCompound, NBTTagCompound>> observers = new ArrayList<IOObserver<NBTTagCompound, NBTTagCompound>>();

	@Override
	public NBTTagCompound createInput(byte[] reading)
	{

		try
		{
			DataInputStream stream = new DataInputStream(new ByteArrayInputStream(reading));
			NBTTagCompound tag = NBTHelper.readInputNBT(stream);
			stream.close();
			return tag;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return new NBTTagCompound();
	}

	@Override
	public NBTTagCompound createOutput()
	{
		return new NBTTagCompound();
	}

	@Override
	public IOBridge createInputBridge(NBTTagCompound input)
	{
		return new NBTBridge(input);
	}

	@Override
	public IOBridge createOutputBridge(NBTTagCompound output)
	{
		return new NBTBridge(output);
	}

	@Override
	public List<IOObserver<NBTTagCompound, NBTTagCompound>> getObservers()
	{
		return this.observers;
	}

}
