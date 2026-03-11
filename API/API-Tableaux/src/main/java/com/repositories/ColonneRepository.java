package com.repositories;

import com.entities.Colonne;
import com.entities.Tableau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColonneRepository extends JpaRepository<Colonne, Long> {
}
