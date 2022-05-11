package com.engine.congestiontaxcalculator.entity;

import com.engine.congestiontaxcalculator.model.vehicle.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "car")
    private Set<History> carHistory = new HashSet<>();
}
