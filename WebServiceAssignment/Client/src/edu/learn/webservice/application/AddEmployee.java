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

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Servlet implementation class AddEmployee
 */
@WebServlet("/AddEmployee")
public class AddEmployee extends HttpServlet {

	private static final long serialVersionUID = 1L;

	static Logger log = Logger.getLogger(AddEmployee.class);

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PropertyConfigurator.configure(this.getClass().getClassLoader().getResource("log4j.properties"));
		log.info("Entering in client for adding employee...");
		String employeeName = request.getParameter("employeeName");

		int salary = Integer.parseInt(request.getParameter("salary"));

		String department = request.getParameter("department");

		/**
		 * creating JSON message
		 */
		String JsonString = "{\"employeeName\":" + employeeName
				+ ",\"salary\":" + salary + ",\"department\":" + department
				+ "}";

		String url = "http://localhost:8080/WebServiceApp/rest/service/add";
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
			connection.setRequestMethod("POST");
			log.info("Request method is POST");

			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);

			DataOutputStream writer = new DataOutputStream(
					connection.getOutputStream());
			log.info("Creation of new DataOutputStream");

			writer.writeBytes(JsonString);
			log.info("Writing data to the stream");
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
			int status = Integer.parseInt(output.toString());

			if (status == 1) {
				PrintWriter out = response.getWriter();
				out.println("Employee added");

				log.info("Successful addition of employee");

				log.info(".......End");

				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("/welcome.jsp");
				requestDispatcher.include(request, response);
			} else {
				PrintWriter out = response.getWriter();
				out.println("Try again");
				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("/index.jsp");
				requestDispatcher.include(request, response);
				log.info("Some issue in adding data");
			}
		} catch (MalformedURLException exception) {

			exception.printStackTrace();

		} catch (IOException exception) {

			exception.printStackTrace();
		}
	}

}
