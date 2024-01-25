package com_darmokhval.hibernate;

import com_darmokhval.entity.Car;
import com_darmokhval.entity.DriverLicense;
import com_darmokhval.entity.Owner;
import com_darmokhval.entity.Type;
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
                    .addAnnotatedClass(Car.class)
                    .addAnnotatedClass(DriverLicense.class)
                    .addAnnotatedClass(Owner.class)
                    .getMetadataBuilder()
                    .build();

            try (SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
                Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();

                String[] carModels = {"Toyota", "Honda", "Ford", "Chevrolet", "Volkswagen", "Nissan", "BMW", "Audi", "Volvo"};
                Integer[] carPower = {100, 125, 150, 175, 200, 250, 300, 350, 400};
                Integer[] carPrice = {7000, 10000, 8500, 12500, 15000, 20000, 25000, 30000, 40000};
                Integer[] carYear = {1999, 1996, 2005, 2011, 2012, 2022, 2023, 2024, 2018};
                List<Car> cars = new ArrayList<>();
                Random random = new Random();

                for (int i = 0; i < 49; i++) {
                    Type[] types = Type.values();
                    int index = random.nextInt(types.length);
                    Car car = new Car(carModels[index],types[index], carPower[index], carPrice[index], carYear[index]);
                    cars.add(car);
                    session.persist(car);
                }

                for (int i = 0; i < 7; i++) {
                    Owner owner = new Owner();
                    owner.setName("|" + "owner|".repeat(i));
                    owner.setDriverLicense(new DriverLicense("-" +"01-".repeat(i)));
                    owner.setCars(cars.subList(7 * i, 7 * i + 7));
                    session.persist(owner);
                }

                List<Owner> ownersFromDatabase = session.createQuery("from Owner", Owner.class).list();
                ownersFromDatabase.forEach(System.out::println);

                List<Car> carsFromDatabase = session.createNativeQuery("select * from car", Car.class).list();
                carsFromDatabase.forEach(System.out::println);


                transaction.commit();
            }
        }
    }
}
