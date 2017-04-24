// (c) 2017 uchicom
package com.uchicom.jl.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class ProcessActionListener implements ActionListener {

	private String[] command;
	public ProcessActionListener(String... command) {
		this.command = command;
	}
	/* (非 Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
			System.out.println(process.exitValue());
		} catch (IOException | InterruptedException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
	}

}
