package edu.learn.webservice.application;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateController
 */
@WebServlet("/updateController")
public class UpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));

		String employeeName = request.getParameter("employeeName");

		int salary = Integer.parseInt(request.getParameter("salary"));

		String department = request.getParameter("department");

		/**
		 * creating JSON message
		 */
		String JsonString = "{\"id\":" + id + ",\"employeeName\":"
				+ employeeName + ",\"salary\":" + salary + ",\"department\":"
				+ department + "}";

		try {
			String url = "http://localhost:8080/WebServiceApp/rest/service/update";
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
			connection.setDoOutput(true);

			DataOutputStream writer = new DataOutputStream(
					connection.getOutputStream());

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

			int status = Integer.parseInt(output.toString());

			if (status == 1) {
				PrintWriter out = response.getWriter();

				out.println("Updated");

				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/welcome.jsp");

				requestDispatcher.include(request, response);
			}

			else {

				PrintWriter out = response.getWriter();

				out.println("Try again");

				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("/update.jsp");

				requestDispatcher.include(request, response);

			}

		} catch (MalformedURLException exception) {

			exception.printStackTrace();

		} catch (IOException exception) {

			exception.printStackTrace();
		}
	}

}
