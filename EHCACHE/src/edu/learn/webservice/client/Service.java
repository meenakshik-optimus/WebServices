package edu.learn.webservice.client;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.PropertyConfigurator;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/service")
public class Service {
	EmployeeDAO employeeDAO = new EmployeeDAO();

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addEmployee(String message) throws JSONException {

		System.out.println(message);
		JSONObject object = new JSONObject(message);
		try {
			if (object.getString("employeeName") == "") {
				int b = 10 / 0;
			}
		} catch (Exception e) {
			System.out.println("error");
		}
		int status = employeeDAO.addEmployeeDetails(
				object.getString("employeeName"), object.getInt("salary"),
				object.getString("department"));
		/**
		 * casting int value to Integer class object
		 */
		Integer addStatus = status;
		return addStatus.toString();

	}

	@GET
	@Path("/getdetails")
	public String getEmployeeDetails() {
		String employeeDetails = employeeDAO.getDetails();
		return employeeDetails;
	}

	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateEmployee(String message) throws JSONException {
		JSONObject object = new JSONObject(message);
		int status = employeeDAO.updateEmployeeDetails(object.getInt("id"),
				object.getString("employeeName"), object.getInt("salary"),
				object.getString("department"));

		/**
		 * casting int value to Integer class object
		 */
		Integer updateStatus = status;
		return updateStatus.toString();

	}

	@POST
	@Path("/authenticate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String authenticateAdmin(String message) throws JSONException {
		JSONObject object = new JSONObject(message);
		AdminDAO adminDAO = new AdminDAO();
		int status = adminDAO.authenticate(object.getString("adminName"),
				object.getString("Password"));

		/**
		 * casting int value to Integer class object
		 */
		Integer authenticationStatus = status;
		return authenticationStatus.toString();

	}

}