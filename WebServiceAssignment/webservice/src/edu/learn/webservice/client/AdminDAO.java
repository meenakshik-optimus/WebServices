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

public class AdminDAO {
	private Configuration configuration;
	private SessionFactory sessionFactory;
	private Session session;
	
	static Logger log = Logger.getLogger(EmployeeDAO.class);
	public AdminDAO() {
		
		configuration = new Configuration();

		configuration.configure("hibernate.cfg.xml");

		/**
		 * create sessionfactory
		 */
		sessionFactory = configuration.buildSessionFactory();

	}
	public int authenticate(String adminName,
			String password) {
		PropertyConfigurator.configure(this.getClass().getClassLoader().getResource("log4j.properties"));
		int result = 0;
		log.info("Entering in method for authenticating admin...");
		try {

			/**
			 * Get Session object
			 */
			session = sessionFactory.openSession();

			/**
			 * Starting Transaction
			 */
			Transaction transaction = session.beginTransaction();
			String hql = "FROM Admin WHERE adminName= :adminName AND password = :password";
			Query query = session.createQuery(hql);
			query.setParameter("adminName", adminName);
			
			query.setParameter("password", password);
			List<Admin> results = (List<Admin>)query.list();
			
			if(results.size()!=0){
				result=1;
				System.out.println("hii");
			}
			transaction.commit();

		} catch (HibernateException exception) {

			exception.printStackTrace();

			result = 0;
		}
		return result;
	}
}
