package edu.learn.webservice.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import edu.learn.webservice.client.Employee;

/**
 * EmployeeDAO class for communicating with database
 * 
 * @author optimus157
 *
 */
public class EmployeeDAO {

	/**
	 * variables declarations
	 */
	private Configuration configuration;
	private SessionFactory sessionFactory;
	private Session session;
	private Employee employee;
	static Logger log = Logger.getLogger(EmployeeDAO.class);
	CacheManager cacheManager = CacheManager.getInstance();
	List<Employee> employees = new ArrayList<Employee>();

	/**
	 * Constructor for EmployeeDAO
	 */
	public EmployeeDAO() {

		configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");

		/**
		 * create sessionfactory
		 */
		sessionFactory = configuration.buildSessionFactory();
	}

	/**
	 * method to add Employee in database
	 * 
	 * @param employeeName
	 * @param salary
	 * @param department
	 * @return
	 */
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
	 * the method for gettiong details from database using EHCACHE
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
		} catch (HibernateException exception) {

			exception.printStackTrace();

		}

		System.out.println("------------------------------");
		System.out.println("Waiting......");

		try {
			Thread.sleep(180000);
		} catch (InterruptedException exception) {
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
		} catch (HibernateException exception) {
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
		} catch (HibernateException exception) {
			exception.printStackTrace();
		}
		return (employee.toString());
	}

	/**
	 * updateEmployee class for updating employee details
	 * 
	 * @param id
	 * @param employeeName
	 * @param salary
	 * @param department
	 * @return
	 */
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

	/**
	 * Getting employee detail by id using EHCACHE
	 * 
	 * @param id
	 * @return
	 */
	public List<Employee> getDetailById(Integer id) {

		Cache cache = cacheManager.getCache("EmployeeCache");

		if (cache == null) {
			employees = getEmployee(id);
			return employees;
		} else {
			int check = check(cache, id);
			if (check == 1) {
				Employee employee = getCache(cache, id);
				employees.add(employee);
			} else {
				employees = getEmployee(id);
			}
			return employees;
		}
	}

	/**
	 * method for getting Employee details by hibernate queries if details are
	 * not available in cache
	 * 
	 * @param id
	 * @return
	 */
	public List<Employee> getEmployee(Integer id) {

		// SessionFactory object is configured and session is opened.

		// SessionFactory sessionFactory = new Configuration().configure(
		// "hibernate.cfg.xml").buildSessionFactory();
		session = sessionFactory.openSession();
		Transaction transaction = null;
		List<Employee> employees = null;
		try {

			/**
			 * Transaction begin.
			 */

			transaction = session.beginTransaction();
			Query query = session.createQuery("FROM Employee Where id = :id");
			query.setParameter("id", id);
			employees = query.list();
			for (Iterator iterator = employees.iterator(); iterator.hasNext();) {
				Employee employee = (Employee) iterator.next();
				System.out.println("EmployeeName: "
						+ employee.getEmployeeName());
				System.out.println("Id: " + employee.getId());

				/**
				 * creating a cache manager and cretaes cache in cache manager
				 */
				Cache cache = cacheMethod();

				/**
				 * Put data in cache
				 */
				cache.put(new Element(employee.getId(), employee));
			}

		} catch (HibernateException hibernateException) {
			if (transaction != null) {
				transaction.rollback();
			}
			hibernateException.printStackTrace();
		} finally {
			session.close();
		}
		return employees;
	}

	/**
	 * @param cache
	 * @param id
	 * @return
	 */
	public int check(Cache cache, Integer id) {
		if (cache.isKeyInCache(id)) {
			return 1;
		} else {
			return 0;
		}

	}

	/**
	 * @param cache
	 * @param id
	 * @return
	 */
	public Employee getCache(Cache cache, Integer id) {
		return (Employee) cache.get(id).getObjectValue();
	}

	/**
	 * Method cacheMethod creates a cache Manager and creates a cache in cache
	 * manager
	 * 
	 * @return cache
	 */
	public Cache cacheMethod() {

		/**
		 * Creates a cache called EmployeeCache
		 */
		cacheManager.addCache("EmployeeCache");

		/**
		 * Get cache called EmployeeCache
		 */
		Cache cache = cacheManager.getCache("EmployeeCache");
		return cache;
	}

}