package edu.learn.webservice.client;

import java.util.Iterator;
import java.util.List;

public class JsonString {

	public String getJson(List<Employee> list) {
		Iterator<Employee> iterator = list.iterator();
		StringBuffer response = new StringBuffer("");
		response.append("[");
		while (iterator.hasNext()) {
			Employee employee = (Employee) iterator.next();
			int id = employee.getId();
			System.out.println(id);
			String name = employee.getEmployeeName();
			int salary = employee.getSalary();
			String department = employee.getDepartment();
			response.append("{\"id\":" + id + ",\"employeeName\":\"" + name
					+ "\",\"salary\":\"" + salary + "\",\"department\":\""
					+ department + "\"},");

		}
		int length = response.length();
		response.deleteCharAt(length - 1);
		response.append("]");
		return (response.toString());
	}
}
