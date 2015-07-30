package edu.learn.json;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 * Servlet implementation class AddProductClient
 */
@WebServlet("/AddProductClient")
public class AddProductClient extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		/**
		 * getting parameters from the request form
		 */

		int id = Integer.parseInt(request.getParameter("id"));

		String productName = request.getParameter("productName");

		int price = Integer.parseInt(request.getParameter("price"));

		/**
		 * creating JSON message
		 */
		String JsonString = "{\"id\":" + id + ",\"productName\":" + productName
				+ ",\"price\":" + price + "}";

		/**
		 * creating URL object for the url where web service is deployed
		 */
		String url = "http://localhost:8080/JSONWEB/rest/abc/simple";
		URL object = new URL(url);

		/*
		 * HTTP Connection is opened.
		 */
		HttpURLConnection connection = (HttpURLConnection) object.openConnection();

		/**
		 * setting headers for connection
		 */
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setDoOutput(true);

		DataOutputStream writer = new DataOutputStream(connection.getOutputStream());

		writer.writeBytes(JsonString);

		writer.flush();

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
		 * Receiving data from web service
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		String inputData;
		StringBuffer output = new StringBuffer();
		while ((inputData = reader.readLine()) != null) {
			output.append(inputData);
		}

		reader.close();

		/**
		 * creating JSONObject from respone
		 */
		JSONObject jsonResponse = new JSONObject(output.toString());
		System.out.println("ID: " + jsonResponse.getInt("id"));
		System.out.println("Product Name: "
				+ jsonResponse.getString("productName"));
		System.out.println("Price: " + jsonResponse.getInt("price"));
	}

}
