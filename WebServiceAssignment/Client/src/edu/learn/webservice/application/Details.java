package edu.learn.webservice.application;

import java.io.BufferedReader;
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

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONArray;

/**
 * Servlet implementation class Details
 */
@WebServlet("/details")
public class Details extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static Logger log = Logger.getLogger(Details.class);

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PropertyConfigurator.configure(this.getClass().getClassLoader()
				.getResource("log4j.properties"));
		log.info("Entering client for get details of employee");

		String url = "http://localhost:8080/WebServiceApp/rest/service/getdetails";
		URL object = new URL(url);

		try {
			/*
			 * HTTP Connection is opened.
			 */
			HttpURLConnection connection = (HttpURLConnection) object
					.openConnection();
			log.info("HTTP Connection is opened");
			/**
			 * setting headers for connection
			 */
			connection.setRequestMethod("GET");
			log.info("Request method is GET");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);

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
			log.info("Getting Respopnse Code:" + responseCode);

			log.info("Getting Respopnse Message:" + responseMessage);

			/**
			 * Receiving data from web service
			 */
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			String inputData;
			StringBuffer output = new StringBuffer();
			while ((inputData = reader.readLine()) != null) {
				output.append(inputData);
			}

			reader.close();
			log.info("Receiving data from web service");

			request.setAttribute("json", (output.toString()));

			JSONArray jsonarray = new JSONArray(output.toString());

			if (jsonarray.length() == 0) {
				PrintWriter out = response.getWriter();
				out.println("Try again");
				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("/welcome.jsp");
				requestDispatcher.include(request, response);
				log.info("No data in the database");

				log.info(".......End");
			} else {
				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("/getdetails.jsp");
				requestDispatcher.forward(request, response);
				log.info("Details of employee displayed");
				log.info(".......End");
			}
		} catch (MalformedURLException exception) {

			exception.printStackTrace();

		} catch (IOException exception) {

			exception.printStackTrace();
		}

	}

}
