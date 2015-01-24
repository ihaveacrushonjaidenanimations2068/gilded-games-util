package com.gildedgames.util.io_manager;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

import org.junit.Assert;
import org.junit.Test;

import com.gildedgames.util.io_manager.util.nbt.NBTFactory;
import com.gildedgames.util.io_manager.util.nbt.NBTManager;
import com.gildedgames.util.testutil.DataSet;
import com.gildedgames.util.testutil.TestConstructor;
import com.gildedgames.util.testutil.TestMetadata;
import com.gildedgames.util.testutil.TestNBTFile;
import com.gildedgames.util.testutil.TestPlayerHook;
import com.gildedgames.util.testutil.TestPlayerHookFactory;
import com.gildedgames.util.testutil.TestWorldHook;

public class IOManagerTest
{

	private NBTManager create()
	{
		return new NBTManager();
	}

	private NBTManager dataSet()
	{
		NBTManager manager = this.create();
		manager.register(TestWorldHook.class, 0);
		manager.register(TestPlayerHook.class, 1);
		manager.register(TestPlayerHookFactory.class, 2);
		manager.register(TestMetadata.class, 3);
		manager.register(TestNBTFile.class, 4);
		return manager;
	}

	@Test
	public void testRegisterAndGetClass()
	{
		NBTManager manager = this.create();
		manager.register(TestPlayerHookFactory.class, 1);
		Assert.assertEquals(manager.getID(TestPlayerHookFactory.class), 1);
		Assert.assertEquals(manager.getID(new TestPlayerHookFactory()), 1);
		Assert.assertEquals(manager.getClassFromID(1), TestPlayerHookFactory.class);
	}

	@Test
	public void testReadWriteOne()
	{
		List<TestNBTFile> files = DataSet.nbtFiles();
		NBTManager manager = this.dataSet();
		for (TestNBTFile object : files)
		{
			File file = DataSet.fileFor(object.toString() + ".test");
			try
			{
				manager.writeFile(file, object, new NBTFactory());
				TestNBTFile readBack = (TestNBTFile) manager.readFile(file, new NBTFactory());
				Assert.assertEquals(object, readBack);
			}
			catch (IOException e)
			{
				e.printStackTrace();
				Assert.fail(e.getMessage());
			}
		}
	}

	@Test
	public void testReadWriteTwo()
	{
		List<TestNBTFile> files = DataSet.nbtFiles();
		NBTManager manager = this.dataSet();
		for (TestNBTFile object : files)
		{
			File file = DataSet.fileFor(object.toString() + ".test");
			try
			{
				manager.writeFile(file, object, new NBTFactory());
				TestNBTFile toReadIn = new TestNBTFile(-1, -1);
				manager.readFile(file, toReadIn, new NBTFactory());
				Assert.assertEquals(object, toReadIn);
			}
			catch (IOException e)
			{
				e.printStackTrace();
				Assert.fail(e.getMessage());
			}
		}
	}

	@Test
	public void testReadWriteMetadata()
	{
		List<TestNBTFile> files = DataSet.nbtFiles();
		NBTManager manager = this.dataSet();
		for (TestNBTFile object : files)
		{
			File file = DataSet.fileFor(object.toString() + ".test");
			try
			{
				manager.writeFile(file, object, new NBTFactory());
				TestMetadata readBack = (TestMetadata) manager.readFileMetadata(file, new NBTFactory());
				Assert.assertEquals(object.getMetadata().get(), readBack);
			}
			catch (IOException e)
			{
				e.printStackTrace();
				Assert.fail(e.getMessage());
			}
		}
	}

	@Test
	public void testClone() throws IOException
	{
		TestMetadata files = new TestMetadata(1);
		NBTManager manager = this.dataSet();
		TestMetadata clone = manager.clone(new NBTTagCompound(), files);
		Assert.assertEquals(files, clone);
	}

	@Test
	public void testCreate()
	{
		NBTManager dataset = this.dataSet();
		Object o = dataset.createFromID(1);
		Assert.assertTrue(o instanceof TestPlayerHook);
		o = dataset.createFromID(3, new TestConstructor());
		Assert.assertTrue(o instanceof TestMetadata && ((TestMetadata) o).id == 1);
	}

}
