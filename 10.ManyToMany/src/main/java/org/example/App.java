package org.example;

import org.example.model.Actor;
import org.example.model.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration()
            .addAnnotatedClass(Actor.class)
            .addAnnotatedClass(Movie.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        try (sessionFactory){
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            Movie movie1 = new Movie("Pulp fiction", 1994);
            Actor actor1 = new Actor("Samuel L. Jackson", 72);
            Actor actor2 = new Actor("Harvey Keitel", 81);

            // Arrays.asList()
            movie1.setActors(new ArrayList<>(List.of(actor1, actor2)));

            actor1.setMovies(new ArrayList<>(Collections.singletonList(movie1)));
            actor2.setMovies(new ArrayList<>(Collections.singletonList(movie1)));

            session.save(movie1);

            session.save(actor1);
            session.save(actor2);

            session.getTransaction().commit();

        }
    }
}
