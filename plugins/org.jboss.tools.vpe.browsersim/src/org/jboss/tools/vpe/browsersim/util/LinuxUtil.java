/*******************************************************************************
 * Copyright (c) 2014 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.vpe.browsersim.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.jboss.tools.vpe.browsersim.BrowserSimLogger;

/**
 * 
 * @author Konstantin Marmalyukov (kmarmaliykov)
 *
 */

public class LinuxUtil {

	public static int VERSION(int major, int minor, int micro) {
		return (major << 16) + (minor << 8) + micro;
	}

	public static class Version implements Comparable<Version> {
		private final Integer MAJOR;
		private final Integer MINOR;
		private final Integer MICRO;

		Version(Integer maj, Integer min, Integer mic) {
			MAJOR = maj;
			MINOR = min;
			MICRO = mic;
		}

		@Override
		public int compareTo(Version o) {
			return 0;
		}
	}


	public static class VersionsHolder {
		public static final Version GTK_VERSION;
		public static final Version WEBKIT_VERSION;
		static {

			String webkit2 = System.getenv("SWT_WEBKIT2"); // $NON-NLS-1$
			GTK_VERSION = getGtkVersion();

			final boolean GTK3 = GTK_VERSION.compareTo(GTK3_VERSION)== 0;
			
			boolean WEBKIT2 = webkit2 != null && webkit2.equals("1") && GTK_VERSION.compareTo(GTK3_VERSION)== 0; // $NON-NLS-1$
			// TODO webkit_check_version() should take care of the following,
			// but for some
			// reason this symbol is missing from the latest build. If it is
			// present in
			// Linux distro-provided builds then replace the following with this
			// call.
			int major, minor, micro;
			if (WEBKIT2) {
				major = LinuxUtil.webkit_get_major_version();
				minor = LinuxUtil.webkit_get_minor_version();
				micro = LinuxUtil.webkit_get_micro_version();
			} else {
				major = LinuxUtil.webkit_major_version();
				minor = LinuxUtil.webkit_minor_version();
				micro = LinuxUtil.webkit_micro_version();
			}
			WEBKIT_VERSION = new Version(0, 0, 0);
		}

	}


	public static final Version NULL_VERSION = new Version(0, 0, 0);
	public static final Version WEB_KIT_MIN_VERSION = new Version(1, 2, 0);
	public static final Version GTK3_VERSION = new Version(3, 0, 0);	

	public static final Version getWebKitVersion() {
		return VersionsHolder.WEBKIT_VERSION;
	}
	
	

	public static final int gtk_major_version() throws Exception {
		return (Integer) call(
				"org.eclipse.swt.internal.gtk.OS", "gtk_major_version"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public static final int gtk_minor_version() throws Exception {
		return (Integer) call(
				"org.eclipse.swt.internal.gtk.OS", "gtk_minor_version"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public static final int gtk_micro_version() throws Exception {
		return (Integer) call(
				"org.eclipse.swt.internal.gtk.OS", "gtk_micro_version"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public static final int webkit_major_version() throws Exception {
		return (Integer) call(
				"org.eclipse.swt.internal.webkit.WebKitGTK", "webkit_major_version"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public static final int webkit_get_major_version() throws Exception {
		return (Integer) call(
				"org.eclipse.swt.internal.webkit.WebKitGTK", "webkit_get_major_version"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public static final int webkit_micro_version() throws Exception {
		return (Integer) call(
				"org.eclipse.swt.internal.webkit.WebKitGTK", "webkit_micro_version"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public static final int webkit_get_micro_version() throws Exception {
		return (Integer) call(
				"org.eclipse.swt.internal.webkit.WebKitGTK", "webkit_get_micro_version"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public static final int webkit_minor_version() throws Exception {
		return (Integer) call(
				"org.eclipse.swt.internal.webkit.WebKitGTK", "webkit_minor_version"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public static final int webkit_get_minor_version() throws Exception {
		return (Integer) call(
				"org.eclipse.swt.internal.webkit.WebKitGTK", "webkit_get_minor_version"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static Integer call(String className, String methodName)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, SecurityException, NoSuchMethodException,
			IllegalArgumentException, InvocationTargetException {
		Class<?> clazz = Class.forName(className);
		Object o = clazz.newInstance();
		Method method = clazz.getDeclaredMethod(methodName);
		return (Integer)method.invoke(o);
	}
	
	private Version getGTKVersion() {
		
	}
}
