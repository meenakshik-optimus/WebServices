package edu.learn.property;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class PropertyRead {

	public static void main(String args[]) {


		/**
		 * Properties object is created
		 */
		Properties property = new Properties();
		
		InputStream input = null;

		try {
			input = new FileInputStream("data.properties");

			/**
			 * Properties file Loaded
			 */
			property.load(input);

			/**
			 * the values of particular properties are displayed
			 */
			System.out.println(property.getProperty("Id"));
			System.out.println(property.getProperty("Name"));
			System.out.println(property.getProperty("Salary"));

			/**
			 *  Displaying the whole list
			 */
			Enumeration<?> enumeration = property.propertyNames();
			while (enumeration.hasMoreElements()) {
				String key = (String) enumeration.nextElement();
				String value = property.getProperty(key);
				System.out.println(key + " : " + value);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
