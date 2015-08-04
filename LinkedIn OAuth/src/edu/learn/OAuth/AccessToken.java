package edu.learn.OAuth;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import org.json.JSONObject;

public class AccessToken {

	public String getAccessToken(String code) throws IOException {

		String url = "https://api.linkedin.com/uas/oauth2/accessToken";

		URL object = new URL(url);

		HttpURLConnection connection = (HttpURLConnection) object
				.openConnection();

		Properties property = new Properties();

		InputStream input = null;

		input = new FileInputStream(
				"C:\\eclipse\\Workspace\\LinkedInTest\\data.properties");

		/**
		 * Properties file Loaded
		 */
		property.load(input);

		/**
		 * setting headers for connection
		 */
		connection.setRequestMethod("POST");

		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		connection.setDoOutput(true);

		DataOutputStream writer = new DataOutputStream(
				connection.getOutputStream());

		String string = "grant_type=" + property.getProperty("grant_type")
				+ "&code=" + code + "&redirect_uri="
				+ property.getProperty("redirect_uri") + "&client_id="
				+ property.getProperty("client_id") + "&client_secret="
				+ property.getProperty("client_secret");
		writer.writeBytes(string);
		writer.flush();

		/**
		 * getting the response code and response message
		 */
		int responseCode = connection.getResponseCode();
		String responseMessage = connection.getResponseMessage();

		System.out.println("Response Code: " + responseCode);
		System.out.println("Response Message: " + responseMessage);

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));

		String inputData;
		StringBuffer output = new StringBuffer();
		while ((inputData = reader.readLine()) != null) {
			output.append(inputData);
		}

		reader.close();

		return output.toString();
	}

	public String getProfile(String json) throws IOException {
		JSONObject jsonObject = new JSONObject(json);
		String accesstoken = jsonObject.getString("access_token");
		System.out.println(accesstoken);
		String url = "https://api.linkedin.com/v1/people/~?format=json&oauth2_access_token="
				+ accesstoken;

		URL object = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) object
				.openConnection();

		connection.setRequestMethod("GET");

		connection.setRequestProperty("Content-Type", "x-li-format: json");
		connection.setDoOutput(true);
		int responseCode = connection.getResponseCode();
		String responseMessage = connection.getResponseMessage();

		System.out.println("Response Code: " + responseCode);
		System.out.println("Response Message: " + responseMessage);

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));

		String inputData;
		StringBuffer output = new StringBuffer();
		while ((inputData = reader.readLine()) != null) {
			output.append(inputData);
		}

		reader.close();
		return output.toString();

	}

	public String postData(String json) throws IOException {
		JSONObject jsonObject = new JSONObject(json);
		String accesstoken = jsonObject.getString("access_token");
		System.out.println(accesstoken);

		String dataa = "{\"comment\":\"Hello testing post\",\"visibility\": {\"code\":\"anyone\"}}";

		String url = "https://api.linkedin.com/v1/people/~/shares?&format=json&oauth2_access_token="
				+ accesstoken;

		System.out.println("url : " + url);
		URL object = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) object
				.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Authorization", "Bearer " + accesstoken);
		// connection.setRequestProperty("Authorization","oauth2");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Content-Type", "x-li-format: json");
		connection.setDoOutput(true);
		DataOutputStream writer = new DataOutputStream(
				connection.getOutputStream());
		// OutputStream writer = connection.getOutputStream();
		writer.writeBytes(dataa);
		// writer.write(dataa.getBytes());

		writer.flush();
		int responseCode = connection.getResponseCode();
		String responseMessage = connection.getResponseMessage();

		System.out.println("Response Code: " + responseCode);
		System.out.println("Response Message: " + responseMessage);

		return "hi";

	}

}
