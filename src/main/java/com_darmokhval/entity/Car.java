package com_darmokhval.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "car")
@ToString
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "model")
    private String model;
    @Column(name = "type")
    private Type type;
    @Column(name = "power")
    private int power;
    @Column(name = "price")
    private int price;
    @Column(name = "year")
    private int year;

    public Car(String model, Type type, int power, int price, int year) {
        this.model = model;
        this.type = type;
        this.power = power;
        this.price = price;
        this.year = year;
    }
}
