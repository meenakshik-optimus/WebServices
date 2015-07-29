package edu.learn.postweb;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/product")
public class Product {

	@POST
	@Path("/add")
	public Response addProduct(@FormParam("id") int id,
			@FormParam("productName") String name,
			@FormParam("price") float price) {
		System.out.println(id + " " + name + " " + price);
		return Response
				.status(200)
				.entity(" Product is added successfuly!<br> id: " + id
						+ "<br> productName: " + name + "<br> price: " + price)
				.build();
	}
}
