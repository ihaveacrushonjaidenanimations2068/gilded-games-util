package com.gildedgames.util.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import com.gildedgames.util.core.gui.TestGui;
import com.gildedgames.util.core.gui.util.decorators.MinecraftButtonSounds;
import com.gildedgames.util.core.gui.util.decorators.MinecraftGui;
import com.gildedgames.util.core.gui.util.wrappers.MinecraftButton;
import com.gildedgames.util.core.io.MCSyncableDispatcher;
import com.gildedgames.util.core.nbt.NBTFile;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IORegistry;
import com.gildedgames.util.io_manager.util.IOManagerDefault;
import com.gildedgames.util.menu.client.MenuClientEvents.MenuConfig;
import com.gildedgames.util.ui.common.GuiDecorator;
import com.gildedgames.util.ui.common.Ui;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.data.AssetLocation;
import com.gildedgames.util.ui.data.UiContainerMutable;
import com.gildedgames.util.ui.event.UiEvent;
import com.gildedgames.util.ui.event.GuiFrameEvent;
import com.gildedgames.util.ui.event.KeyboardEvent;
import com.gildedgames.util.ui.event.MouseEvent;
import com.gildedgames.util.ui.event.view.MouseEventGui;
import com.gildedgames.util.ui.event.view.MouseEventGuiFocus;
import com.gildedgames.util.ui.util.Button;
import com.gildedgames.util.ui.util.ButtonList;
import com.gildedgames.util.ui.util.RectangleElement;
import com.gildedgames.util.ui.util.ScrollBar;
import com.gildedgames.util.ui.util.ScrollBar.ButtonScrollEvent;
import com.gildedgames.util.ui.util.TextureElement;
import com.gildedgames.util.ui.util.decorators.RepeatableGui;
import com.gildedgames.util.ui.util.decorators.ScissorableGui;
import com.gildedgames.util.ui.util.decorators.ScrollableGui;
import com.gildedgames.util.world.common.WorldHookPool;

public class UtilServices
{

	private IOManager io;

	private static final String MANAGER_NAME = "GildedGamesUtil";

	public UtilServices()
	{

	}

	private void startIOManager()
	{
		this.io = new IOManagerDefault(MANAGER_NAME);

		IORegistry registry = this.io.getRegistry();

		registry.registerClass(NBTFile.class, 0);
		registry.registerClass(WorldHookPool.class, 1);
		registry.registerClass(MenuConfig.class, 2);
		registry.registerClass(MCSyncableDispatcher.class, 3);
		registry.registerClass(ScrollBar.class, 5);
		registry.registerClass(Button.class, 6);
		registry.registerClass(ButtonList.class, 7);
		registry.registerClass(RectangleElement.class, 8);
		registry.registerClass(TextureElement.class, 9);
		registry.registerClass(RepeatableGui.class, 10);
		registry.registerClass(ScissorableGui.class, 11);
		registry.registerClass(ScrollableGui.class, 12);
		registry.registerClass(MinecraftGui.class, 13);
		registry.registerClass(MinecraftButtonSounds.class, 14);
		registry.registerClass(MinecraftButton.class, 15);
		registry.registerClass(Ui.class, 16);
		registry.registerClass(Gui.class, 17);
		registry.registerClass(GuiDecorator.class, 18);
		registry.registerClass(GuiFrame.class, 19);
		registry.registerClass(GuiFrame.class, 20);
		registry.registerClass(TestGui.class, 21);
		registry.registerClass(UiContainerMutable.class, 22);
		registry.registerClass(UiEvent.class, 23);
		registry.registerClass(GuiFrameEvent.class, 24);
		registry.registerClass(KeyboardEvent.class, 25);
		registry.registerClass(MouseEvent.class, 26);
		registry.registerClass(MouseEventGui.class, 27);
		registry.registerClass(MouseEventGuiFocus.class, 28);
		registry.registerClass(ButtonScrollEvent.class, 29);
	}

	public IOManager getIOManager()
	{
		if (this.io == null)
		{
			this.startIOManager();
		}

		return this.io;
	}

	@SuppressWarnings("resource")
	public InputStream getStreamFromAsset(AssetLocation asset) throws ZipException, IOException
	{
		File source = null;

		String path = "assets//" + asset.getDomain() + "//" + asset.getPath();

		for (ModContainer container : Loader.instance().getActiveModList())
		{
			if (container.getModId().equals(asset.getDomain()))
			{
				source = container.getSource();
			}
		}

		if (source != null)
		{
			if (source.isFile())
			{
				ZipFile zipfile = new ZipFile(source);
				ZipEntry zipentry = zipfile.getEntry(path);

				return zipfile.getInputStream(zipentry);
			}

			return new FileInputStream(new File(source, path));
		}

		return null;
	}

}
