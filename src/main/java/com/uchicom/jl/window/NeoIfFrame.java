// (c) 2017 uchicom
package com.uchicom.jl.window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class NeoIfFrame extends JFrame {

	public NeoIfFrame(GraphicsConfiguration gc) {
		super(gc);
		initComponents();
	}

	/**
	 *
	 */
	private void initComponents() {
		//		setContentPane(new ButtonPanel());
//		this.setLocation(800, 200);

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
				System.out.println(e);
//				if (light) {
//					flags[++index] = 1;
//				} else {
//					flags[--index] = 0;
//				}
//				if (index % 10 == 0) {
//					if (light) {
//						light = false;
//					} else {
//						light = true;
//					}
//				}
//				repaint();
			}


			@Override
			public void mouseExited(MouseEvent e) {
				System.out.println(e);
			}


			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(e);
//				System.exit(0);
			}

		});
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				System.out.println(e);

			}

			@Override
			public void mouseMoved(MouseEvent e) {
				System.out.println(e);
				System.out.println(e);
				if (light) {
					flags[++index] = 1;
				} else {
					flags[--index] = 0;
				}
				if (index % 10 == 0) {
					if (light) {
						light = false;
					} else {
						light = true;
					}
				}
				repaint();

			}

		});
		flags[0] = 1;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = device.getDefaultConfiguration();
//		device.setFullScreenWindow(this);
		pack();
	}
	boolean light = true;
	int index = 0;
	int[] flags = new int[100];

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		int offsetX = 100;
		int offsetY = 100;
		int r = 20;
//		g.setFont(getFont().deriveFont(36));
		for (int i = 0; i < flags.length; i++) {
			int flag = flags[i];
			if (flag == 1) {
				g.setColor(new Color(255, 0, 0, 100));
				g.fillPolygon(new Polygon(
						new int[] { offsetX + r, offsetX + (r/2), offsetX + -(r/2), offsetX + -r, offsetX + -(r/2), offsetX + (r/2) },
						new int[] { offsetY + 0, offsetY + (int)(r * Math.cos(60)), offsetY + (int)(r * Math.cos(60)), offsetY + 0, offsetY + -(int)(r * Math.cos(60)), offsetY + -(int)(r * Math.cos(60)) },
						6));
				g.setColor(Color.WHITE);
				g.drawString(String.valueOf(i), offsetX - (r/2), offsetY + (r/2));
			}
			offsetX += r + r/2 + 2;
			offsetY += (int)(r * -Math.sin(30)) + 2;
		}

	}
}
