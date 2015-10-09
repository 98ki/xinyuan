package com.xlj.erp.movefield.entity;

/**
 * 用于分组的ListView和Gridview
 * @author Administrator
 *
 */
public class LineItem {
	public int sectionFirstPosition;
	public boolean isHeader;
	public Object item;

	public LineItem(Object item, boolean isHeader, int sectionFirstPosition) {
		this.isHeader = isHeader;
		this.item = item;
		this.sectionFirstPosition = sectionFirstPosition;
	}
}
