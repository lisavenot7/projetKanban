package com.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "comptes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cptId;

    private String cptMail;
    private String cptPseudo;
    private String cptMdp;
    private boolean cptIsAdmin;

    // Tableaux auxquels le compte participe
    @ManyToMany
    @JoinTable(
            name = "compte_tableau",         // table de jointure
            joinColumns = @JoinColumn(name = "cptId"),  // FK vers Compte
            inverseJoinColumns = @JoinColumn(name = "tabId") // FK vers Tableau
    )
    private Set<Tableau> participations = new HashSet<>();

    // Tableaux créés par ce compte
    @OneToMany(mappedBy = "createur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tableau> tableauxCrees;

    @OneToMany(mappedBy = "compteAttribue", cascade = CascadeType.ALL)
    private List<Tache> taches; // les tâches attribuées à ce compte


}