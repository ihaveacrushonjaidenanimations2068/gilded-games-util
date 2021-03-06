package com.gildedgames.util.modules.ui.common;

import com.gildedgames.util.io_manager.io.NBT;
import com.gildedgames.util.modules.ui.data.TickInfo;
import com.gildedgames.util.modules.ui.data.UIContainer;
import com.gildedgames.util.modules.ui.data.UIContainerEvents;
import com.gildedgames.util.modules.ui.input.InputProvider;

import java.util.List;

public interface Ui extends NBT
{

	UIContainer seekContent();

	UIContainerEvents events();

	List<UIContainer> seekAllContent();

	void init(InputProvider input);

	void initContent(InputProvider input);

	void onClose(InputProvider input);

	void onResolutionChange(InputProvider input);

	boolean isEnabled();

	void setEnabled(boolean enabled);

	/**
	 * Will not be called if this element is disabled with isEnabled()
	 * @param input
	 */
	void tick(TickInfo tickInfo, InputProvider input);

}
