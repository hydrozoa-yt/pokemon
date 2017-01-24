package com.hydrozoa.pokemon.screen.renderer;

import java.util.Comparator;

import com.hydrozoa.pokemon.model.YSortable;

/**
 * @author hydrozoa
 */
public class WorldObjectYComparator implements Comparator<YSortable> {

	@Override
	public int compare(YSortable o1, YSortable o2) {
		if (o1.getWorldY() < o2.getWorldY()) {
			return -1;
		} else if (o1.getWorldY() > o2.getWorldY()) {
			return 1;
		}
		return 0;
	}
}
