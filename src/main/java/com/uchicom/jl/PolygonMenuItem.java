// (c) 2017 uchicom
package com.uchicom.jl;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.event.ActionListener;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class PolygonMenuItem extends Polygon {

	private String name;
	private boolean onMouse;
	private Color onColor;
	private Color offColor;
	private int x;
	private int y;
	private ActionListener actionListener;
	public PolygonMenuItem(int[] arg0, int[] arg1, int arg2,
			String name, int x, int y, Color onColor, Color offColor, ActionListener actionListener) {
		super(arg0, arg1, arg2);
		this.name = name;
		this.x = x;
		this.y = y;
		this.onColor = onColor;
		this.offColor = offColor;
		this.actionListener = actionListener;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isOnMouse() {
		return onMouse;
	}
	public void setOnMouse(boolean onMouse) {
		this.onMouse = onMouse;
	}
	public Color getColor() {
		return onMouse ? onColor : offColor;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public ActionListener getActionListener() {
		return actionListener;
	}
	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}
}
