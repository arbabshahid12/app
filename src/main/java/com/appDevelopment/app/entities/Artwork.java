package com.appDevelopment.app.entities;


import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Artwork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "year_of_completion")
    private int yearOfCompletion;

    @Column(name = "price")
    private double price;

    @Column(name = "sold")
    private boolean sold;

    @ManyToOne
    @JoinColumn(name = "Artist_id")
    private Artist artist;

}
