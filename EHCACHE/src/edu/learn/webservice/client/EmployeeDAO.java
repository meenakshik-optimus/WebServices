package edu.learn.webservice.client;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import edu.learn.webservice.client.Employee;

public class EmployeeDAO {

	private Configuration configuration;
	private SessionFactory sessionFactory;
	private Session session;
	private Employee employee;
	static Logger log = Logger.getLogger(EmployeeDAO.class);

	public EmployeeDAO() {

		configuration = new Configuration();

		configuration.configure("hibernate.cfg.xml");

		/**
		 * create sessionfactory
		 */
		sessionFactory = configuration.buildSessionFactory();

	}

	public int addEmployeeDetails(String employeeName, int salary,
			String department) {

		PropertyConfigurator.configure(this.getClass().getClassLoader()
				.getResource("log4j.properties"));
		int status;
		log.info("Entering in method for adding employee...");
		try {

			/**
			 * Get Session object
			 */
			session = sessionFactory.openSession();

			/**
			 * Starting Transaction
			 */
			Transaction transaction = session.beginTransaction();
			employee = new Employee();
			employee.setEmployeeName(employeeName);
			employee.setSalary(salary);
			employee.setDepartment(department);

			session.persist(employee);

			transaction.commit();
			session.close();

			status = 1;

		} catch (HibernateException exception) {
			status = 0;
			exception.printStackTrace();

		}
		return status;
	}

	/**
	 * the method implementing EHCache
	 * 
	 * @return
	 */
	public String getDetails() {

		/**
		 * logging
		 */
		log.info("Entering in method for getting details of employee...");
		PropertyConfigurator.configure(this.getClass().getClassLoader()
				.getResource("log4j.properties"));

		try {
			/**
			 * Getting Session object
			 */
			session = sessionFactory.openSession();

			/**
			 * load method returns the persistent instance of the given entity
			 * class with the given identifier, assuming that the instance
			 * exists.
			 * 
			 */
			Object object = session.load(Employee.class, new Integer(2));

			employee = (Employee) object;

			System.out.println("Object loaded from the database...!!");

			System.out.println("Loaded  employee___" + employee.toString());

			System.out.println("Object Loaded successfully.....!!");
			/**
			 * closing the session
			 */
			session.close();
		}
		catch (HibernateException exception) {

			exception.printStackTrace();

		}

		System.out.println("------------------------------");
		System.out.println("Waiting......");

		try {
			Thread.sleep(180000);
		} 
		catch (InterruptedException exception) {
			exception.printStackTrace();
		}

		System.out.println("180 seconds elapsed.....!!!!!!!!");

		try {
			session = sessionFactory.openSession();
			Object secondObject = session.load(Employee.class, new Integer(2));

			employee = (Employee) secondObject;
			System.out.println("Object loaded from the database...!!");
			System.out
					.println("Loaded  employee___" + employee.getDepartment());

			/**
			 * closing the session
			 */
			session.close();
		} 
		catch (HibernateException exception) {
			exception.printStackTrace();
		}

		try {

			/**
			 * Getting Session object
			 */
			session = sessionFactory.openSession();

			/**
			 * load method returns the persistent instance of the given entity
			 * class with the given identifier, assuming that the instance
			 * exists.
			 * 
			 */
			Object thirdObject = session.load(Employee.class, new Integer(2));

			employee = (Employee) thirdObject;
			System.out
					.println("Object loaded from global cache successfully.....!!");
			System.out.println("Loaded  employee___" + employee.toString());

			/**
			 * closing the session
			 */
			session.close();
		} 
		catch (HibernateException exception) {
			exception.printStackTrace();
		}
		return (employee.toString());
	}

	public int updateEmployeeDetails(int id, String employeeName, int salary,
			String department) {
		PropertyConfigurator.configure(this.getClass().getClassLoader()
				.getResource("log4j.properties"));
		int result = 0;
		log.info("Entering in method for updating employee datails...");
		try {

			/**
			 * Get Session object
			 */
			session = sessionFactory.openSession();

			/**
			 * Starting Transaction
			 */
			Transaction transaction = session.beginTransaction();
			String hql = "UPDATE Employee set employeeName = :employeeName,salary = :salary,department=:department WHERE id = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			query.setParameter("employeeName", employeeName);
			query.setParameter("salary", salary);
			query.setParameter("department", department);
			result = query.executeUpdate();
			transaction.commit();

		} catch (HibernateException exception) {

			exception.printStackTrace();

			result = 0;
		}
		return result;
	}
}