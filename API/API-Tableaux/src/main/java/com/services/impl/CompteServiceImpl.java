package com.services.impl;


import com.dtos.CompteDto;
import com.entities.Compte;
import com.entities.Tableau;
import com.mappers.CompteMapper;
import com.mappers.TableauMapper;
import com.repositories.CompteRepository;
import com.services.CompteService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service("CompteService")
@Transactional
public class CompteServiceImpl implements CompteService {

    private final CompteRepository compteRepository;
    private final CompteMapper compteMapper;

    public CompteServiceImpl(CompteRepository compteRepository, CompteMapper compteMapper) {
        this.compteRepository = compteRepository;
        this.compteMapper = compteMapper;
    }

    @Override
    public CompteDto saveCompte(CompteDto compteDto) {
        Compte compte = compteMapper.toEntity(compteDto);
        Compte savedCompte = compteRepository.save(compte);
        return compteMapper.toDto(savedCompte);
    }

    @Override
    @Transactional(readOnly = true)
    public CompteDto getCompteById(Long compteId) {
        Compte compte = compteRepository.findById(compteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Le compte avec l'ID %d n'existe pas", compteId)));
        return compteMapper.toDto(compte);
    }

    @Override
    public boolean deleteCompte(Long compteId) {
        Compte compte = compteRepository.findById(compteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Le compte avec l'ID %d n'existe pas", compteId)));
        compteRepository.deleteById(compte.getCptId());
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompteDto> getAllComptes() {
        return compteRepository.findAll()
                .stream()
                .map(compteMapper::toDto)
                .toList();
    }

    @Override
    public CompteDto updateCompte(Long compteId, CompteDto compteDto) {
        Compte compte = compteRepository.findById(compteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Le compte avec l'ID %d n'existe pas", compteId)));

        // Mise à jour des champs simples
        if (compteDto.getCptMail() != null) {
            compte.setCptMail(compteDto.getCptMail());
        }
        if (compteDto.getCptPseudo() != null) {
            compte.setCptPseudo(compteDto.getCptPseudo());
        }
        if (compteDto.getCptMdp() != null) {
            compte.setCptMdp(compteDto.getCptMdp());
        }
        compte.setCptIsAdmin(compteDto.getCptIsAdmin());

        // Gestion des tableaux créés par ce compte
        if (compteDto.getTableauxCrees() != null) {
            var updatedTableaux = compteDto.getTableauxCrees().stream()
                    .map(tableauDto -> {
                        Tableau t = new TableauMapper().toEntity(tableauDto); // mapper sans compte
                        t.setCreateur(compte); // lier le créateur après
                        return t;
                    })
                    .toList(); // Set au lieu de List si tableauxCrees est un Set

            compte.setTableauxCrees(updatedTableaux);
        }

        Compte savedCompte = compteRepository.save(compte);
        return compteMapper.toDto(savedCompte);
    }
}