package com.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "comptes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compte implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cptId;

    @Column(unique = true, length = 100, nullable = false)
    private String cptMail;

    private String cptMdp;
    private int cptIsAdmin;
    private int cptIsActive;

    public int getIsAdmin() {
        return cptIsAdmin;
    }

    @OneToOne
    @JoinColumn(name = "usrId") // clé étrangère
    private User user;

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


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.cptMdp;
    }

    @Override
    public String getUsername() {
        return this.cptMail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return cptIsActive==1;
    }
}