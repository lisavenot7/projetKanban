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
            joinColumns = @JoinColumn(name = "cpt_id"),  // FK vers Compte
            inverseJoinColumns = @JoinColumn(name = "tab_id") // FK vers Tableau
    )
    private Set<Tableau> participations = new HashSet<>();

    // Tableaux créés par ce compte
    @OneToMany(mappedBy = "createur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tableau> tableauxCrees;
}