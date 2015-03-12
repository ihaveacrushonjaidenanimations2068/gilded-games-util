package com.gildedgames.util.ui;

import java.util.Collection;
import java.util.List;

public interface UIElementContainer
{

	void add(UIElement element);
	
	void addAll(Collection<? extends UIElement> elements);

	void remove(UIElement element);
	
	void removeAll(Collection<? extends UIElement> elements);
	
	void clear();
	
	boolean contains(UIElement element);
	
	int size();
	
	/**
	 * Any element that is an instance of the provided class will be removed.
	 * @param classToRemove
	 */
	void clear(Class<? extends UIElement> classToRemove);
	
	List<UIElement> getElements();
	
	List<UIView> queryAll(List input);

}