package edu.learn.filewrite;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteFile {

	public static void main(String[] args) {

		try {
			String content = "This is the content to write into file";

			/**
			 * conversion of string into byte array
			 */
			byte[] bytes = content.getBytes();

			/**
			 * byteCount variable to take into account the number of bytes into
			 * string
			 */
			int byteCount = 0;

			/**
			 * count variable to keep track of file created
			 */
			int count = 1;

			/**
			 * file creation
			 */
			File file = new File("output.txt");
			FileOutputStream saveFile = new FileOutputStream(file);
			DataOutputStream save = new DataOutputStream(saveFile);

			while (byteCount < bytes.length) {

				/**
				 * if file does not exist, then create it
				 */
				/**
				 * if (!file.exists()) { file.createNewFile(); }
				 */

				save.writeByte(bytes[byteCount]);
				if (file.length() > 2) {

					file = new File("output" + String.valueOf(count++) + ".txt");
					saveFile = new FileOutputStream(file);
					save = new DataOutputStream(saveFile);
				}
				byteCount++;
			}

			System.out.println("Done");
			save.close();

		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

}
