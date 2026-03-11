package com.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "tableaux")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tableau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tabId;
    private int cptId;
    private String tabNom;
    private String tabDateCreation;

    @OneToMany(mappedBy = "tableau", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Colonne> colonnes;
}
