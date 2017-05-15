// (c) 2017 uchicom
package com.uchicom.jl.action;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import com.uchicom.jl.Launcher;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class MenuDisplayListener implements ItemListener {
	private Launcher launcher;
	public MenuDisplayListener(Launcher launcher) {
		this.launcher = launcher;
	}

	/* (非 Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		launcher.display(e.getStateChange() == ItemEvent.SELECTED);
	}

}
