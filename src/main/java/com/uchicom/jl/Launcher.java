// (c) 2017 uchicom
package com.uchicom.jl;

import java.awt.AWTException;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.uchicom.rssr.RssrFrame;

/**
 * ActionListenerを自作するか、MainArgsで指定するか、main
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
			//背景はプログラムで変更すると良い
			try {
				image = ImageIO.read(Thread.currentThread()
						.getContextClassLoader()
						.getResource("com/uchicom/jl/image/jl.png"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			PopupMenu popup = new PopupMenu();

			Menu menu = new Menu("editor");
			popup.add(menu);
			MenuItem menuItem = new MenuItem("rssr");
			menuItem.addActionListener((e)->{
				RssrFrame frame = new RssrFrame(new File("C:\\Users\\shigeki\\Dropbox\\uchicom\\software\\rssr\\conf\\rssr.properties"));
				frame.setVisible(true);
			});
			menu.add(menuItem);
			menu = new Menu("設定");menuItem = new MenuItem("インストール");
			menuItem.addActionListener((e)->{
				InstallDialog dialog = new InstallDialog();
				dialog.setModal(true);
				dialog.setVisible(true);
			});
			menu.add(menuItem);
			popup.add(menu);
			TrayIcon trayIcon = new TrayIcon(image, "jl");
			trayIcon.setPopupMenu(popup);
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				e.printStackTrace();
			}

		}
	}
}
