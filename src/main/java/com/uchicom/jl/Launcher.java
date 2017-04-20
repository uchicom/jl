// (c) 2017 uchicom
package com.uchicom.jl;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class Launcher {

	public void execute() {
		SystemTray tray = SystemTray.getSystemTray();
		if (SystemTray.isSupported()) {

			// タスクトレイ
			BufferedImage image = new BufferedImage(16, 16,
					BufferedImage.TYPE_INT_BGR);

			try {
				image = ImageIO.read(Thread.currentThread()
						.getContextClassLoader()
						.getResource("img/tray_icon.png"));
			} catch (Exception e) {
			}

			TrayIcon trayIcon = new TrayIcon(image, "test");
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

		}
	}
}
