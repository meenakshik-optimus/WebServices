package edu.learn.webservice.client;


import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class EmployeeDAO {

	private Configuration configuration;
	private SessionFactory sessionFactory;
	private Session session;
	private Employee employee;

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
		int status;
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

	public List<Employee> getDetails() {

		/**
		 * Get Session object
		 */
		session = sessionFactory.openSession();
		
		/**
		 * Starting Transaction
		 */
		Transaction transaction = session.beginTransaction();

		Query query = session.createQuery("FROM Employee");

		List<Employee> lists = (List<Employee>) query.list();

		transaction.commit();

		session.close();

		return (lists);

	}

	public int updateEmployeeDetails(int id, String employeeName, int salary,
			String department) {

		int result = 0;

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