package edu.learn.jsonresource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/abc")
public class Resource {
	@POST
	@Path("/simple")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createJsonString(String message) throws JSONException {

		JSONObject object = new JSONObject(message);

		/**
		 * using increment method of JSONObject to increment the id
		 */
		object.increment("id");
		return object.toString();

	}
}
