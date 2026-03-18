package com.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "actions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String dateModification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cpt_id") // colonne FK vers le compte créateur
    private Compte auteur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tch_id") // colonne FK vers le compte créateur
    private Tache tacheModifiee;

}
