package com.gildedgames.util.tab.client;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Mouse;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.tab.client.util.RenderTabGroup;
import com.gildedgames.util.tab.common.TabAPI;
import com.gildedgames.util.tab.common.networking.packet.PacketOpenTab;
import com.gildedgames.util.tab.common.util.ITab;
import com.gildedgames.util.tab.common.util.ITabGroup;
import com.gildedgames.util.tab.common.util.ITabGroupHandler;

public class TabClientEvents
{

	@SideOnly(Side.CLIENT)
	private final static RenderTabGroup tabGroupRenderer = new RenderTabGroup();

	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event)
	{
		GuiScreen gui = event.gui;
		
		ITabGroupHandler groupHandler = TabAPI.INSTANCE.getActiveGroup();
		
		if (groupHandler != null)
		{
			ITabGroup activeGroup = groupHandler.getSide(Side.CLIENT);
			
			if (activeGroup != null)
			{
				ITab selectedTab = activeGroup.getSelectedTab();
				
				if (event.gui != null && selectedTab.isTabValid(gui))
				{
					return;
				}
				else
				{
					TabAPI.INSTANCE.setActiveGroup(null);
				}
			}
		}

		for (ITabGroupHandler tabGroupHandler : TabAPI.INSTANCE.getRegisteredTabGroups().values())
		{
			ITabGroup tabGroup = tabGroupHandler.getSide(Side.CLIENT);
			
			for (ITab tab : tabGroup.getTabs())
			{
				if (tab != null && event.gui != null && tab.isTabValid(gui))
				{
					ITab selectedTab = tabGroup.getSelectedTab();
					
					if (selectedTab != null && !selectedTab.isTabValid(gui))
					{
						if (tabGroup.getRememberSelectedTab())
						{
							event.setCanceled(true);

							if (tabGroup.getRememberedTab() != null)
							{
								tabGroup.setSelectedTab(tabGroup.getRememberedTab());
							}
							else
							{
								tabGroup.setSelectedTab(tabGroup.getEnabledTabs().get(0));
							}

							tabGroup.getSelectedTab().onOpen(Minecraft.getMinecraft().thePlayer);
							UtilCore.NETWORK.sendToServer(new PacketOpenTab(tabGroup.getSelectedTab()));
						}
						else
						{
							tabGroup.setSelectedTab(tabGroup.getEnabledTabs().get(0));
						}
					}
					else
					{
						tabGroup.setSelectedTab(tab);
					}

					TabAPI.INSTANCE.setActiveGroup(tabGroupHandler);

					return;
				}
			}
		}
	}

	@SubscribeEvent
	public void tickStart(TickEvent.ClientTickEvent event)
	{
		if (event.phase == TickEvent.Phase.START)
		{
			ITabGroupHandler groupHandler = TabAPI.INSTANCE.getActiveGroup();
			
			if (groupHandler != null)
			{
				ITabGroup activeGroup = groupHandler.getSide(Side.CLIENT);
				
				if (activeGroup != null)
				{
					while (Mouse.next())
					{
						ITab hoveredTab = null;
	
						if (activeGroup != null)
						{
							hoveredTab = this.tabGroupRenderer.getHoveredTab(activeGroup);
						}
	
						if (Mouse.getEventButtonState() && hoveredTab != null)
						{
							if (hoveredTab != activeGroup.getSelectedTab())
							{
								activeGroup.getSelectedTab().onClose(Minecraft.getMinecraft().thePlayer);
								activeGroup.setSelectedTab(hoveredTab);
	
								if (hoveredTab != activeGroup.getRememberedTab() && hoveredTab.isRemembered())
								{
									if (activeGroup.getRememberedTab() != null)
									{
										activeGroup.getRememberedTab().onClose(Minecraft.getMinecraft().thePlayer);
									}
	
									activeGroup.setRememberedTab(hoveredTab);
								}
	
								hoveredTab.onOpen(Minecraft.getMinecraft().thePlayer);
								UtilCore.NETWORK.sendToServer(new PacketOpenTab(hoveredTab));
							}
						}
						else if (Minecraft.getMinecraft().currentScreen != null)
						{
							try
							{
								Minecraft.getMinecraft().currentScreen.handleMouseInput();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void tickEnd(TickEvent.RenderTickEvent event)
	{
		if (event.phase == TickEvent.Phase.END)
		{
			ITabGroupHandler groupHandler = TabAPI.INSTANCE.getActiveGroup();
			
			if (groupHandler != null)
			{
				ITabGroup activeGroup = groupHandler.getSide(Side.CLIENT);
				
				if (activeGroup != null)
				{
					this.tabGroupRenderer.render(activeGroup);
				}
			}
		}
	}
	
}
