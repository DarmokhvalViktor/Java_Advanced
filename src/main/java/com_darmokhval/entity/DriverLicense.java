package com_darmokhval.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "driver_license")
@ToString
public class DriverLicense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "series")
    private String series;

    public DriverLicense(String series) {
        this.series = series;
    }
}
