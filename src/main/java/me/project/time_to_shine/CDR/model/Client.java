package me.project.time_to_shine.CDR.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private int id;


    private String number;


    @Transient
    private List<ClientInterval> intervals = new ArrayList<>();

}
