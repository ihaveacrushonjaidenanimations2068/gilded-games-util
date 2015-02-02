package com.gildedgames.util.io_manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.exceptions.IORegistryTakenException;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.factory.ISerializeBehaviour;
import com.gildedgames.util.io_manager.io.IO;
import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.overhead.IORegistry;
import com.gildedgames.util.io_manager.overhead.IOSerializer;
import com.gildedgames.util.io_manager.overhead.IOSerializerVolatile;

public class IOCore implements IORegistry, IOSerializer, IOSerializerVolatile
{

	private static final IOCore INSTANCE = new IOCore();
	
	private List<IORegistry> externalRegistries = new ArrayList<IORegistry>();
	
	protected IOSerializer fileComponent;
	
	protected IOSerializerVolatile volatileComponent;
	
	protected IORegistry registryComponent;
	
	private IOCore()
	{
		this.registryComponent = new IORegistryCore(this.externalRegistries);
		this.fileComponent = new IOSerializerCore();
		this.volatileComponent = new IOSerializerVolatileCore();
	}
	
	public static IOCore io()
	{
		return IOCore.INSTANCE;
	}
	
	/** Recommended to pass along an instance of the default implementation, IORegistryDefault.
	 * You MUST have a unique registryID, otherwise it will throw an IORegistryTakenException **/
	public void addRegistry(IORegistry registry) throws IORegistryTakenException
	{
		for (IORegistry external : this.externalRegistries)
		{
			if (external.getRegistryID().equals(registry.getRegistryID()))
			{
				throw new IORegistryTakenException(registry);
			}
		}
		
		this.externalRegistries.add(registry);
	}
	
	public IORegistry getRegistry(String registryID)
	{
		for (IORegistry external : this.externalRegistries)
		{
			if (external.getRegistryID().equals(registryID))
			{
				return external;
			}
		}
		
		return null;
	}
	
	public IORegistry getRegistry(Class<?> clazz)
	{
		for (IORegistry external : this.externalRegistries)
		{
			if (external.isClassRegistered(clazz))
			{
				return external;
			}
		}
		
		return null;
	}
	
	@Override
	public IORegistry getParentRegistry()
	{
		return this;
	}

	@Override
	public <T extends IO<I, ?>, I> T read(I input, Class<? extends T> classToReadFrom)
	{
		return this.volatileComponent.read(input, classToReadFrom);
	}

	@Override
	public <T extends IO<I, ?>, I> T read(I input, Class<? extends T> classToReadFrom, IConstructor objectConstructor)
	{
		return this.volatileComponent.read(input, classToReadFrom, objectConstructor);
	}

	@Override
	public <T extends IO<?, O>, O> void write(O output, T objectToWrite)
	{
		this.volatileComponent.write(output, objectToWrite);
	}

	@Override
	public <T extends IO<I, O>, I, O> T clone(I input, O output, T objectToClone) throws IOException
	{
		return this.volatileComponent.clone(input, output, objectToClone);
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> FILE readFile(File file, IOFactory<FILE, I, O> ioFactory) throws IOException
	{
		return this.fileComponent.readFile(file, ioFactory);
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> FILE readFile(File file, IOFactory<FILE, I, O> ioFactory, IConstructor constructor) throws IOException
	{
		return this.fileComponent.readFile(file, ioFactory, constructor);
	}
	
	@Override
	public <I, O, FILE extends IOFile<I, O>> void readFile(File file, FILE ioFile, IOFactory<FILE, I, O> ioFactory) throws IOException
	{
		this.fileComponent.readFile(file, ioFile, ioFactory);
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> void writeFile(File file, FILE ioFile, IOFactory<FILE, I, O> ioFactory) throws IOException
	{
		this.fileComponent.writeFile(file, ioFile, ioFactory);
	}

	@Override
	public String getRegistryID()
	{
		return this.registryComponent.getRegistryID();
	}

	@Override
	public void registerClass(Class<?> classToSerialize, int classID)
	{
		this.registryComponent.registerClass(classToSerialize, classID);
	}

	@Override
	public void registerBehavior(Class<?> classToSerialize, ISerializeBehaviour<?> serializeBehaviour)
	{
		this.registryComponent.registerBehavior(classToSerialize, serializeBehaviour);
	}

	@Override
	public <T> T create(Class<T> registeredClass, IConstructor classConstructor)
	{
		return this.registryComponent.create(registeredClass, classConstructor);
	}

	@Override
	public Object create(String registryID, int registeredClassID)
	{
		return this.registryComponent.create(registryID, registeredClassID);
	}

	@Override
	public Object create(String registryID, int registeredClassID, IConstructor classConstructor)
	{
		return this.registryComponent.create(registryID, registeredClassID, classConstructor);
	}

	@Override
	public Class<?> getClass(String registryID, int registeredClassID)
	{
		return this.registryComponent.getClass(registryID, registeredClassID);
	}

	@Override
	public int getID(Class<?> registeredClass)
	{
		return this.registryComponent.getID(registeredClass);
	}

	@Override
	public int getID(Object objectOfRegisteredClass)
	{
		return this.registryComponent.getID(objectOfRegisteredClass);
	}

	@Override
	public boolean isClassRegistered(Class<?> clazz)
	{
		return this.registryComponent.isClassRegistered(clazz);
	}
	
}
