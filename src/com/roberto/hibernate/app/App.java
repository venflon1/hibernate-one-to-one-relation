package com.roberto.hibernate.app;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.roberto.hibernate.model.Instructor;
import com.roberto.hibernate.model.InstructorDetail;

public class App {

	public static void main(String[] args) {
		SessionFactory sessionFactory = new Configuration()
												.configure("hibernate.cfg.xml")
												.addAnnotatedClass(Instructor.class)
												.addAnnotatedClass(InstructorDetail.class)
												.buildSessionFactory();

		Session dbSession = sessionFactory.openSession();

		InstructorDetail instructorDetail1 = new InstructorDetail("venflon1", "programming");
		Instructor instructor1 = new Instructor("Roberto", "Amato", "venflon1", instructorDetail1);
		
		// =========== INSERT ===========
		Transaction trx = dbSession.beginTransaction();
		// NOTE: this will also save the details object 
		// because of CascadeType.ALL 
		dbSession.save(instructor1);
		System.out.println("save instructor and relative detail");
		trx.commit();
		
		Instructor instructor = dbSession.get(Instructor.class, 2L);
		System.out.println("the instructor:" + instructor);
		System.out.println("the instructor-detail:" + instructor.getInstructorDetail());
		dbSession.close();
		
		dbSession = sessionFactory.openSession();
		
		// =========== DELETE ===========
		trx = dbSession.beginTransaction();
		// NOTE: this will also delete the details object 
		// because of CascadeType.ALL 
		dbSession.delete(instructor1);
		System.out.println("delete instructor and relative detail");
		trx.commit();
		
		dbSession.close();
		
		sessionFactory.close();
	}
}
