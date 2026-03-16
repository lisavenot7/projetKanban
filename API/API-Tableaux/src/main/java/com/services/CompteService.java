package com.services;

import com.dtos.CompteDto;
import com.dtos.ModifCompteDto;
import com.entities.Compte;

import java.util.List;

public interface CompteService {

    Compte saveCompte(CompteDto CompteDto);

    Compte getCompteById(Long CompteId);

    boolean deleteCompte(Long CompteId);

    List<Compte> getAllComptes();

    Compte updateCompte(Long compteId, ModifCompteDto modifcompteDto);
}
