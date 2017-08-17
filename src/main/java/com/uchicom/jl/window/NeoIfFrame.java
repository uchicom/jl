// (c) 2017 uchicom
package com.uchicom.jl.window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JFrame;

import com.uchicom.jl.Constants;
import com.uchicom.jl.PolygonMenuItem;
import com.uchicom.jl.action.JarActionListener;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class NeoIfFrame extends JFrame {

	private Properties config;
	private boolean open;
	private List<PolygonMenuItem> polygonMenuItemList = new ArrayList<>();
	public NeoIfFrame(Properties config) {
		this.config = config;
		initComponents();
	}

	/**
	 *
	 */
	private void initComponents() {
		//		setContentPane(new ButtonPanel());
//		this.setLocation(800, 200);

		polygonMenuItemList.add(new PolygonMenuItem(
				new int[] {0, 0, 30},
				new int[] {0, 30, 0},
				3,
				"JL",
				0,
				15,
				new Color(255, 0, 0, 200),
				new Color(255, 0, 0, 100),
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (open) {
							open = false;
						} else {
							open = true;
							Thread thread = new Thread() {
								public void run() {
									try {
										Thread.sleep(Constants.AUTO_HIDE_TIME);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									open = false;
									repaint();
								}
							};
							thread.setDaemon(true);
							thread.start();
						}
						repaint();
					}
				},
				true));
		initMenu();
		setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		setUndecorated(true);//タイトルが消える　ボタンで消すようにする？

		setBackground(new Color(0, 0, 0, 0));
		setAlwaysOnTop(true); //常に全面に表示
		//		frame.setAutoRequestFocus(false);
		//		frame.setFocusable(false);
//		        this.getRootPane().setBorder(new LineBorder(Color.black, 2));
		setFocusableWindowState(false); //画面を選択してもフォーカスが移動しない、作業中も移動しない
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				for (PolygonMenuItem polygonMenuItem : polygonMenuItemList) {
					polygonMenuItem.setOnMouse(polygonMenuItem.contains(e.getX(), e.getY()));
				}
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				for (PolygonMenuItem polygonMenuItem : polygonMenuItemList) {
					polygonMenuItem.setOnMouse(false);
				}
				repaint();
			}


			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					for (PolygonMenuItem polygonMenuItem : polygonMenuItemList) {
						if (polygonMenuItem.contains(e.getX(), e.getY())) {
							polygonMenuItem.getActionListener().actionPerformed(null);
							break;
						}
					}
				}
			}

		});
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
//				System.out.println(e);

			}

			@Override
			public void mouseMoved(MouseEvent e) {
				boolean changed = false;
				for (PolygonMenuItem polygonMenuItem : polygonMenuItemList) {
					if (polygonMenuItem.contains(e.getX(), e.getY())) {
						if (!polygonMenuItem.isOnMouse()) {
							polygonMenuItem.setOnMouse(true);
							changed = true;
						}
					} else {
						if (polygonMenuItem.isOnMouse()) {
							polygonMenuItem.setOnMouse(false);
							changed = true;
						}
					}
				}
				if (changed) {
					repaint();
				}
			}

		});
		flags[0] = 1;
//		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//		GraphicsDevice device = ge.getDefaultScreenDevice();
//		GraphicsConfiguration gc = device.getDefaultConfiguration();
//		device.setFullScreenWindow(this);
		pack();
	}

	private void initMenu() {
		int offsetX = 50;
		int offsetY = 20;
		int r = 30;


		String[] menu = config.getProperty("hex").split(",");
		for (int i = 0; i < menu.length; i++) {
			try {
				PolygonMenuItem polygon = new PolygonMenuItem(
					new int[] { offsetX + r, offsetX + (r/2), offsetX + -(r/2), offsetX + -r, offsetX + -(r/2), offsetX + (r/2) },
					new int[] { offsetY + 0, offsetY + (r/2), offsetY + (r/2), offsetY + 0, offsetY-(r/2), offsetY -(r/2)},
					6,
					config.getProperty(menu[i] + ".NAME"),
					offsetX - (r/2),
					offsetY + (r/4),
					new Color(255, 0, 0, 200),
					new Color(255, 0, 0, 100),
					new JarActionListener(config.getProperty(menu[i] + ".JAR"),
							config.getProperty(menu[i] + ".MAIN")),
					false
				);
				offsetX += r + r/2 + 2;
				if (i % 2 == 0) {
					offsetY += r/2 + 2;
				} else {
					offsetY -= r/2 + 2;
				}
				polygonMenuItemList.add(polygon);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	boolean light = true;
	int index = 0;
	int[] flags = new int[100];

	@Override
	public void paint(Graphics g) {
		super.paint(g);
//		g.setFont(getFont().deriveFont(36));

		for (PolygonMenuItem polygon : polygonMenuItemList) {
			if (!open && !polygon.isRoot()) continue;
			g.setColor(polygon.getColor());
			g.fillPolygon(polygon);
			g.setColor(Color.WHITE);
			if (polygon.isRoot()) {
				((Graphics2D)g).rotate(Math.toRadians(-45), 5, 5);
				g.drawString(polygon.getName(), polygon.getX(), polygon.getY());
				((Graphics2D)g).rotate(Math.toRadians(45), 0, 0);
			} else {
				g.drawString(polygon.getName(), polygon.getX(), polygon.getY());
			}
		}

	}
	private MenuItem[][][] matrix = new MenuItem[10][10][2];
	/**
	 * 配列の座標を計算し、マトリックスに格納済みのメニューを返却する。
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public MenuItem getMenuItem(int pointX, int pointY) {
		int y = pointY / 10;
		int x = pointX / 10;
		int z = 0;
		boolean judge = (y + x) % 2 == 0;
		if (judge) {
			if (x % 10 + y % 10 < 10) {
				z = 0;
			} else {
				z = 1;
			}
		} else {
			if (x % 10 < y % 10) {
				z = 0;
			} else {
				z = 1;
			}
		}
		return matrix[y][x][z];
	}
}
