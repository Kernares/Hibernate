package org.example;

import org.example.model.Item;
import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.From;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration()
            .addAnnotatedClass(Person.class)
            .addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

           Person person = new Person("Warrior", 22);

           person.addItem(new Item("Sword"));
           person.addItem(new Item("Plate mail"));
           person.addItem(new Item("Shield"));

           session.save(person);

           session.getTransaction().commit();

        } finally {
            sessionFactory.close();
        }
    }
}
