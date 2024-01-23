package com_darmokhval.hibernate;

import com_darmokhval.entity.Car;
import com_darmokhval.entity.Type;
import com_darmokhval.entity.Word;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        try (StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build()) {
            Metadata metadata = new MetadataSources(serviceRegistry)
                    .addAnnotatedClass(Word.class)
                    .getMetadataBuilder()
                    .build();

            try (SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
                Session session = sessionFactory.openSession()) {

                Transaction transaction = session.beginTransaction();
                String[] wordsArray = {
                        "car", "pen", "book", "computer", "apple", "table", "ocean", "mountain", "guitar", "sun",
                        "moon", "star", "flower", "bird", "house", "river", "city", "dog", "cat", "bicycle",
                        "phone", "camera", "pizza", "garden", "cloud", "rain", "coffee", "shoes", "hat", "music",
                        "film", "painting", "beach", "friend", "love", "smile", "key", "door", "window", "lamp",
                        "watch", "tree", "grass", "ball", "mirror", "candle", "cookie", "bridge", "road", "skyscraper"
                };
                for (String s : wordsArray) {
                    Word word = new Word(s);
                    session.persist(word);
                }
                List<Word> wordsFromDatabase = session.createNativeQuery("select * from word", Word.class).list();
                wordsFromDatabase.forEach(System.out::println);
                wordsFromDatabase.forEach(word -> {
                    System.out.println(word.getValue());
                });

                String[] carModels = {"Toyota", "Honda", "Ford", "Chevrolet", "Volkswagen", "Nissan", "BMW", "Audi", "Volvo"};
                Integer[] carPower = {100, 125, 150, 175, 200, 250, 300, 350, 400};
                Integer[] carPrice = {7000, 10000, 8500, 12500, 15000, 20000, 25000, 30000, 40000};
                Integer[] carYear = {1999, 1996, 2005, 2011, 2012, 2022, 2023, 2024, 2018};
                for (int i = 0; i < 49; i++) {
                    Random random = new Random();
                    Type[] types = Type.values();
                    int index = random.nextInt(types.length);
                    Car car = new Car(carModels[index],types[index], carPower[index], carPrice[index], carYear[index]);
                    session.persist(car);
                }
                List<Car> carsFromDatabase = session.createNativeQuery("select * from car", Car.class).list();
                carsFromDatabase.forEach(System.out::println);



                transaction.commit();
            }
        }
    }
}
