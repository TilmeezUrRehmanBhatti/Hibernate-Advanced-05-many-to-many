package com.tilmeez.hibernate.demo;

import com.tilmeez.hibernate.demo.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class DeleteMaryStudnetDemo {

    public static void main(String[] args) {

        // create session factory
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Review.class)
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        // create session
        Session session = factory.getCurrentSession();

        try {

            // start a transaction
            session.beginTransaction();

            // get the student mary from database
            int theId = 6;
            Student tempStudent = session.get(Student.class, theId);

            System.out.println("\nLoaded student: " + tempStudent);
            System.out.println("Courses: " + tempStudent.getCourses());

            // delete student
            System.out.println("\nDeleting student: " + tempStudent);
            session.delete(tempStudent);
            // commit transaction
            session.getTransaction().commit();

            System.out.println("Done!");

        }finally {
            // add clean uo code
            session.close();

            factory.close();
        }
    }
}













