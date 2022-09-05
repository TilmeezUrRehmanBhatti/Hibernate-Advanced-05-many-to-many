package com.tilmeez.hibernate.demo;

import com.tilmeez.hibernate.demo.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class DeletePacmanCoursesDemo {

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

            // get the pacman course from db
            int theId = 6;
            Course tempCourse = session.get(Course.class, theId);

            // delete the course
            System.out.println("Deleting course: " + tempCourse);
            session.delete(tempCourse);


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













