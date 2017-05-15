// (c) 2017 uchicom
package com.uchicom.jl.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class JarActionListener implements ActionListener {

	private String main;
	private URLClassLoader classLoader;
	private Class<?> clazz;
	private Method method;
	public JarActionListener(String path, String main) throws Exception {
		classLoader = new URLClassLoader(new URL[] { new URL(path) });
		this.main = main;
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Thread thread = new Thread(()-> {
		try {
			clazz = classLoader.loadClass(main);
			method = clazz.getMethod("main", new Class[] { String[].class });
			method.invoke(clazz, new Object[] { new String[0] });
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			} catch (InvocationTargetException e1) {
				e1.printStackTrace();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});
		thread.setContextClassLoader(classLoader);
		thread.setDaemon(true);
		thread.start();

	}

}
