package com.services;

import com.dtos.CompteDto;
import com.dtos.CompteUserResponse;
import com.dtos.ModifCompteDto;
import com.entities.Compte;

import java.util.List;

public interface CompteService {

    Compte saveCompte(CompteDto CompteDto);

    CompteUserResponse getCompteById(Long CompteId);

    boolean deleteCompte(Long CompteId);

    List<CompteUserResponse> getAllComptes();

    CompteUserResponse updateCompte(Long compteId, ModifCompteDto modifcompteDto);
}
