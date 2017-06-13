
// (c) 2017 uchicom
package com.uchicom.jl;

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Permission;
import java.util.Properties;

import javax.imageio.ImageIO;

import com.uchicom.jl.action.ExitListener;
import com.uchicom.jl.action.MenuDisplayListener;
import com.uchicom.jl.action.ProcessActionListener;
import com.uchicom.jl.window.NeoIfFrame;

/**
 * 起動コマンドとパスなどを登録しておく ActionListenerを自作するか、MainArgsで指定するか、main
 *
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class Launcher {

    /** plateの状態 */
    private int launcherStatus;
	private File configFile = new File("conf/jl.properties");
	private Properties config = new Properties();
	private TrayIcon trayIcon;
	private NeoIfFrame menuFrame;
	private CheckboxMenuItem checkboxMenuItem = new CheckboxMenuItem("表示");

	public Launcher() {
		// セキュリティーマネージャーの設定
		setSecurity();
	}
	public void execute() {
		initProperties();
		menuFrame = new NeoIfFrame(config);
		SystemTray tray = SystemTray.getSystemTray();
		if (SystemTray.isSupported()) {

			// タスクトレイ
			BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_BGR);
			// 背景はプログラムで変更すると良い
			try {
				image = ImageIO.read(
						Thread.currentThread().getContextClassLoader().getResource("com/uchicom/jl/image/jl.png"));
			} catch (Exception e) {
				e.printStackTrace();
			}

			trayIcon = new TrayIcon(image, "jl");
			trayIcon.setPopupMenu(createPopupMenu());
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				e.printStackTrace();
			}

		}
	}

	public PopupMenu createPopupMenu() {
		PopupMenu popupMenu = new PopupMenu();
		checkboxMenuItem.addItemListener(new MenuDisplayListener(this));
		//初回表示
		checkboxMenuItem.setState(true);
		display(true);
		popupMenu.add(checkboxMenuItem);
		popupMenu.addSeparator();
		MenuItem item = new MenuItem("終了");
		item.addActionListener(new ExitListener(this));
		popupMenu.add(item);
		//createMenu(popupMenu, "popup");
		return popupMenu;
	}
	public Menu createMenu(Menu menu, String key) {
		String menuKeys = config.getProperty(key);
		if (menuKeys == null) return menu;
		for (String menuKey : menuKeys.split(",")) {
			String cKey = key + "." + menuKey;
			String nextKey = config.getProperty(cKey);
			if ("[-]".equals(menuKey)) {
				menu.addSeparator();
			} else if ("[e]".equals(menuKey)) {
				MenuItem menuItem = new MenuItem("終了");
				menuItem.addActionListener(new ExitListener(this));
				menu.add(menuItem);
			} else if (nextKey != null) {
				//メニュー
				menu.add(createMenu(new Menu("abcd"), cKey));
			} else {
				String name = config.getProperty(cKey + ".NAME");
				String commands = config.getProperty(cKey + ".COMMAND");
				MenuItem menuItem = new MenuItem(name);
				if (commands != null && !"".equals(commands)) {
					menuItem.addActionListener(new ProcessActionListener(commands.split("\\|")));
				}
				menu.add(menuItem);
			}
		}
		return menu;
	}

	/**
	 *
	 */

	private void initProperties() {
		if (configFile.exists() && configFile.isFile()) {
			try (FileInputStream fis = new FileInputStream(configFile);) {
				config.load(fis);
			} catch (FileNotFoundException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
	}

	private void storeProperties() {
		try {
			if (!configFile.exists()) {
				configFile.getParentFile().mkdirs();
				configFile.createNewFile();
			}
			try (FileOutputStream fos = new FileOutputStream(configFile);) {
				config.store(fos, "Launcher Ver1.0.0");
			} catch (FileNotFoundException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	/**
	 * 終了する
	 */
	public void exit() {
		storeProperties();
		if (menuFrame != null) {
			menuFrame.dispose();
		}
		SystemTray tray = SystemTray.getSystemTray();
		if (SystemTray.isSupported()) {
			tray.remove(trayIcon);
		}
		exit(0);
	}

	/**
	 * 編集.
	 */
	public void edit() {

	}

	public void display(boolean selected) {
		menuFrame.setVisible(selected);
	}

	/**
	 * セキュリティマネージャーでexitをSecurityExceptionにする。
	 */
	protected void setSecurity() {
		System.setSecurityManager(new SecurityManager() {
			/**
			 *
			 */
			public void checkPermission(Permission perm) {
				if ("setSecurityManager".equals(perm.getName())) {
					throw new SecurityException("setSecurityManager is no permission.");
				}
			}

			/**
			 *
			 */
			public void checkExit(int status) {
				if (launcherStatus == 0) {
					throw new SecurityException("exit is no permission.");
				}
			}
		});
	}

    /**
     * サーバーを終了する。
     */
    public void exit(int status) {
    	launcherStatus = 1;
        System.exit(status);
    }
}
