package com.services;

import com.dtos.CompteDto;

import java.util.List;

public interface CompteService {

    CompteDto saveCompte(CompteDto CompteDto);

    CompteDto getCompteById(Long CompteId);

    boolean deleteCompte(Long CompteId);

    List<CompteDto> getAllComptes();

    CompteDto updateCompte(Long CompteId, CompteDto CompteDto);
}
