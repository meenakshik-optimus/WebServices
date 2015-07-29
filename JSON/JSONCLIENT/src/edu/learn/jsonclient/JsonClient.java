package edu.learn.jsonclient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonClient {
	public static void main(String args[]) throws IOException {

		String url = "http://localhost:8080/JSONRESOURCE/jsn/abc/simple";
		URL object = new URL(url);

		/*
		 * HTTP Connection is opened.
		 */
		HttpURLConnection connection = (HttpURLConnection) object
				.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");

		String urlParameters;
		BufferedReader bufferedReader = new BufferedReader(new FileReader(
				"Json.txt"));
		connection.setDoOutput(true);

		DataOutputStream writer = new DataOutputStream(
				connection.getOutputStream());

		while ((urlParameters = bufferedReader.readLine()) != null) {
			writer.writeBytes(urlParameters);
		}
		writer.flush();
		bufferedReader.close();
		int responseCode = connection.getResponseCode();

		/*
		 * Response is received.
		 */
		System.out.println("Response Code: " + responseCode);

		/**
		 * Receiving data from webservice
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		String inputData;
		StringBuffer response = new StringBuffer();
		while ((inputData = reader.readLine()) != null) {
			response.append(inputData);
		}
		reader.close();
		System.out.println(response.toString());

	}
}