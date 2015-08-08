package edu.learn.webservice.client;

/**
 * Employee POJO class
 * @author optimus157
 *
 */
public class Employee {
	private int id;
	private String employeeName;
	private int salary;
	private String department;

	/**
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public String getEmployeeName() {
		return employeeName;
	}

	/**
	 * @param employeeName
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	/**
	 * @return
	 */
	public int getSalary() {
		return salary;
	}

	/**
	 * @param salary
	 */
	public void setSalary(int salary) {
		this.salary = salary;
	}

	/**
	 * @return
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "id:" + getId() + " employeeName:" + getEmployeeName()
				+ " department:" + getDepartment();
	}

}
