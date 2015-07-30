package edu.learn.property;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Properties;

public class PropertyWrite {

	public static void main(String args[]) throws IOException {

		/**
		 * Properties object is created
		 */
		Properties property = new Properties();

		OutputStream out = null;

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter your id:");
		int id = Integer.parseInt(br.readLine());

		System.out.println("Enter your Name:");
		String name = br.readLine();

		System.out.println("Enter your Salary:");
		int salary = Integer.parseInt(br.readLine());

		try {

			StringWriter output = new StringWriter();
			out = new FileOutputStream("data.properties");
			/**
			 * Properties of property file is set
			 */
			property.setProperty("Id", String.valueOf(id));
			property.setProperty("Name", name);
			property.setProperty("Salary", String.valueOf(salary));

			/**
			 * store the properties list in an output writer
			 */
			property.store(output, "Hii " + name + " Here are the properties");

			/**
			 * store the properties list in a file named data.properties
			 */
			property.store(out, "Hello " + name + " Here are the properties");

			System.out.println(output.toString());

		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
