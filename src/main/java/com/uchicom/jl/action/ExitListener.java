// (c) 2017 uchicom
package com.uchicom.jl.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.uchicom.jl.Launcher;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class ExitListener implements ActionListener {
	private Launcher launcher;
	public ExitListener(Launcher launcher) {
		this.launcher = launcher;
	}
	/* (非 Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		launcher.exit();
	}

}
