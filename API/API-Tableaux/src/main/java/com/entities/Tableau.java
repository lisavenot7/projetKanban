package com.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tableaux")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tableau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tabId;

    private String tabNom;
    private String tabDateCreation;

    // Colonnes du tableau
    @OneToMany(mappedBy = "tableau", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Colonne> colonnes;

    // Participants (ManyToMany)
    @ManyToMany(mappedBy = "participations")
    private Set<Compte> participants = new HashSet<>();

    // Créateur du tableau (ManyToOne)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cpt_id") // colonne FK vers le compte créateur
    private Compte createur;
}