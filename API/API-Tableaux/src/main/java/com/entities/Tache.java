package com.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "taches")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tchId;

    private String tchTitre;
    private String tchDescription;
    private String tchDateLimite;
    private int tchPriorite;

    @ManyToOne
    @JoinColumn(name = "cptId") // clé étrangère vers le compte
    private Compte compteAttribue; // compte auquel la tâche est attribuée

    @ManyToOne
    @JoinColumn(name = "clnId") // clé étrangère vers la colonne
    private Colonne colonne;
}
