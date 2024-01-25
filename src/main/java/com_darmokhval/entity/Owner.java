package com_darmokhval.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "owner")
@ToString
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    @OneToMany
    @JoinTable(
            name = "owner_car",
            joinColumns = @JoinColumn(name = "owner_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id")
    )
    private List<Car> cars;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "driver_license_id", referencedColumnName = "id")
    private DriverLicense driverLicense;

    public Owner(String name, List<Car> cars, DriverLicense driverLicense) {
        this.name = name;
        this.cars = cars;
        this.driverLicense = driverLicense;
    }

    public Owner(String name) {
        this.name = name;
    }

}
