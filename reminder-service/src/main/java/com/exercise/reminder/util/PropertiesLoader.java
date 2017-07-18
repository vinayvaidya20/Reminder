package com.exercise.reminder.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// TODO: Auto-generated Javadoc
/**
 * The Class PropertiesLoader: This is to externalise the property file, as of now this file is just having the external path for the crud service.
 * @author Vinay Vaidya
 */
public final class PropertiesLoader {

	/** The system pro. */
	private static Properties systemPro = new Properties();

	/** The in. */
	private static InputStream in = null;

	/**
	 * Instantiates a new properties loader.
	 */
	private PropertiesLoader() {
	}

	/**
	 * Load.
	 */
	public static void load() {
		try {
			in = PropertiesLoader.class.getResourceAsStream("/reminder.properties");
			systemPro.load(in);
		} catch (IOException e) {
			System.out.println("Error Loading property file." + e);
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException ex) {
				System.out.println("Error closing stream." + ex);
			}
		}
	}

	/**
	 * Gets the property.
	 *
	 * @param key the key
	 * @return the property
	 */
	public static String getProperty(String key) {
		load();
		return  systemPro.getProperty(key);
	}

}
