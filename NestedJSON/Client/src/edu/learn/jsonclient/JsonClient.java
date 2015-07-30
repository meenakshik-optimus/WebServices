package edu.learn.jsonclient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class JsonClient {
	public static void main(String args[]) throws IOException {

		/**
		 * creating URL object for the url where web service is deployed
		 */
		String url = "http://localhost:8080/JSONRESOURCE/jsn/abc/simple";
		URL object = new URL(url);

		/*
		 * HTTP Connection is opened.
		 */
		HttpURLConnection connection = (HttpURLConnection) object
				.openConnection();

		/**
		 * setting headers for connection
		 */
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");

		/**
		 * reading data from file Nested.txt which is having JSOONObject message
		 */
		String urlParameters;
		BufferedReader bufferedReader = new BufferedReader(new FileReader("Nested.txt"));
		connection.setDoOutput(true);

		DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
		StringBuffer s = new StringBuffer();

		/**
		 * writing data to bytes
		 */
		while ((urlParameters = bufferedReader.readLine()) != null) {
			writer.writeBytes(urlParameters);
			s.append(urlParameters);
		}

		System.out.println(s.toString());
		writer.flush();
		bufferedReader.close();

		/**
		 * getting the response code and response message
		 */
		int responseCode = connection.getResponseCode();
		String responseMessage = connection.getResponseMessage();

		/*
		 * Response is received.
		 */
		System.out.println("Response Code: " + responseCode);
		System.out.println("Response Message: " + responseMessage);
		/**
		 * Receiving data from webservice
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputData;
		StringBuffer response = new StringBuffer();
		while ((inputData = reader.readLine()) != null) {
			response.append(inputData);
		}
		reader.close();

		/**
		 * creating JSONObject from respone
		 */
		JSONObject jsonResponse = new JSONObject(response.toString());
		System.out.println("Street address: "
				+ jsonResponse.getString("streetAddress"));
		System.out.println("City: " + jsonResponse.getString("city"));
	}
}