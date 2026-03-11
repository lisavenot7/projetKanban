package com.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "colonnes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Colonne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clnId;

    private String clnNom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tabId")
    private Tableau tableau;

    @OneToMany(mappedBy = "colonne", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tache> taches;
}
