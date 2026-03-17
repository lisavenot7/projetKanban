package com.services.impl;


import com.dtos.CompteDto;
import com.dtos.CompteUserResponse;
import com.dtos.ModifCompteDto;
import com.entities.Compte;
import com.entities.Tableau;
import com.entities.User;
import com.mappers.CompteMapper;
import com.mappers.CompteUserMapper;
import com.repositories.CompteRepository;
import com.repositories.TableauRepository;
import com.repositories.UserRepository;
import com.services.CompteService;
import com.services.TableauService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("CompteService")
@Transactional
public class CompteServiceImpl implements CompteService {

    private final CompteRepository compteRepository;
    private final UserRepository userRepository;
    private final TableauRepository tableauRepository;
    private final CompteMapper compteMapper;

    private final TableauService tableauService;
    private final CompteUserMapper compteUserMapper;

    public CompteServiceImpl(CompteRepository compteRepository, UserRepository userRepository, TableauRepository tableauRepository, CompteMapper compteMapper, TableauService tableauService, CompteUserMapper compteUserMapper) {
        this.compteRepository = compteRepository;
        this.userRepository = userRepository;
        this.tableauRepository = tableauRepository;
        this.compteMapper = compteMapper;
        this.tableauService = tableauService;
        this.compteUserMapper = compteUserMapper;
    }

    @Override
    public Compte saveCompte(CompteDto compteDto) {
        Compte compte = compteMapper.toEntity(compteDto);
        return compteRepository.save(compte);
    }

    @Override
    @Transactional(readOnly = true)
    public CompteUserResponse getCompteById(Long compteId) {

        Compte compte = compteRepository.findById(compteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Le compte avec l'ID %d n'existe pas", compteId)));

        return compteUserMapper.toDto(compte);
    }

    @Override
    public boolean deleteCompte(Long compteId) {
        Compte compte = compteRepository.findById(compteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Le compte avec l'ID %d n'existe pas", compteId)));

        User user = compte.getUser();

        userRepository.deleteById(user.getUsrId());
        compteRepository.deleteById(compte.getCptId());
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompteUserResponse> getAllComptes() {
        List<Compte> comptes = compteRepository.findAll();

        return comptes.stream()
                .map(compteUserMapper::toDto)
                .toList();
    }

    @Override
    public CompteUserResponse updateCompte(Long compteId, ModifCompteDto modifcompteDto) {
        Compte compte = compteRepository.findById(compteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Le compte avec l'ID %d n'existe pas", compteId)));

        // Mise à jour des champs simples
        if (modifcompteDto.getCptMail() != null) {
            compte.setCptMail(modifcompteDto.getCptMail());
        }

        if (modifcompteDto.getCptMdp() != null) {
            compte.setCptMdp(modifcompteDto.getCptMdp());
        }

        if (modifcompteDto.getCptIsAdmin() != null) {
            compte.setCptIsAdmin(modifcompteDto.getCptIsAdmin());
        }

        if (modifcompteDto.getCptIsActive() != null) {
            compte.setCptIsActive(modifcompteDto.getCptIsActive());
        }

        if (modifcompteDto.getNom() != null) {
            compte.getUser().setNom(modifcompteDto.getNom());
        }

        if (modifcompteDto.getPrenom() != null) {
            compte.getUser().setPrenom(modifcompteDto.getPrenom());
        }
        userRepository.save(compte.getUser());

        // Gestion des tableaux créés par ce compte
        if (modifcompteDto.getTableauxCrees() != null) {
            modifcompteDto.getTableauxCrees().forEach(tableauDto -> {
                Optional<Tableau> tableauBDD = tableauRepository.findById(tableauDto.getTabId());
                if (tableauBDD.isPresent()) {
                    tableauService.updateTableau(tableauDto.getTabId(), tableauDto);
                } else {
                    tableauService.createTableau(compteId,tableauDto);
                }
            });
        }

        return compteUserMapper.toDto(compteRepository.save(compte));
    }
}