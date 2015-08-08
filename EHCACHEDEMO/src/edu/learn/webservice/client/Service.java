package edu.learn.webservice.client;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Service class containing all the services provided by webservice
 * @author optimus157
 *
 */
@Path("/service")
public class Service {
	EmployeeDAO employeeDAO = new EmployeeDAO();

	/**
	 * service method for adding employee
	 * 
	 * @param message
	 * @return
	 * @throws JSONException
	 */
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addEmployee(String message) throws JSONException {

		System.out.println(message);
		JSONObject object = new JSONObject(message);
		int status = employeeDAO.addEmployeeDetails(
				object.getString("employeeName"), object.getInt("salary"),
				object.getString("department"));
		/**
		 * casting int value to Integer class object
		 */
		Integer addStatus = status;
		return addStatus.toString();

	}

	/**
	 * service for getting all employee
	 * 
	 * @return
	 */
	@GET
	@Path("/getdetails")
	public String getEmployeeDetails() {
		String employeeDetails = employeeDAO.getDetails();
		return employeeDetails;
	}

	/**
	 * service for getting employee by id using ehcache
	 * 
	 * @param jsonString
	 * @return
	 * @throws JSONException
	 */
	@POST
	@Path("/getemployeedetail")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getEmployeeDetail(String jsonString) throws JSONException {
		JSONObject object = new JSONObject(jsonString);
		int id = object.getInt("employeeId");

		Integer employeeId = id;
		List<Employee> employee = employeeDAO.getDetailById(employeeId);
		JsonString json = new JsonString();
		String employeeJson = json.getJson(employee);
		return employeeJson;

	}

	/**
	 * service method for updating employee details
	 * 
	 * @param message
	 * @return
	 * @throws JSONException
	 */
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

	/**
	 * service for authentication of admin
	 * 
	 * @param message
	 * @return
	 * @throws JSONException
	 */
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