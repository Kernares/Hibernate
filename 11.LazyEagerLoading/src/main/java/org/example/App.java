package org.example;

import org.example.model.Item;
import org.example.model.Person;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.From;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            Person person = session.get(Person.class, 1);
            System.out.println("Get person from the table");

            session.getTransaction().commit();
            //session.close();

            System.out.println("Session is over");

            //Open session and transaction again(in any place in code that we want)
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            System.out.println("Inside second transaction");

            person = (Person) session.merge(person);

            Hibernate.initialize(person.getItems());

//            List<Item> items = session.createQuery("select i from Item i where i.owner.id = :personId",
//                    Item.class).setParameter("personId", person.getId()).getResultList();
//
//            System.out.println(items);

            session.getTransaction().commit();

            System.out.println("Out of second session");

            System.out.println(person.getItems());

        } finally {
            sessionFactory.close();
        }
    }
}
