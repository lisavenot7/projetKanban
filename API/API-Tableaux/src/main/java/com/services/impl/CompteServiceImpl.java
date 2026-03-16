package com.services.impl;


import com.dtos.CompteDto;
import com.dtos.ModifCompteDto;
import com.entities.Compte;
import com.entities.Tableau;
import com.entities.User;
import com.mappers.CompteMapper;
import com.repositories.CompteRepository;
import com.repositories.TableauRepository;
import com.repositories.UserRepository;
import com.services.CompteService;
import com.services.TableauService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    public CompteServiceImpl(CompteRepository compteRepository, UserRepository userRepository, TableauRepository tableauRepository, CompteMapper compteMapper, TableauService tableauService) {
        this.compteRepository = compteRepository;
        this.userRepository = userRepository;
        this.tableauRepository = tableauRepository;
        this.compteMapper = compteMapper;
        this.tableauService = tableauService;
    }

    @Override
    public Compte saveCompte(CompteDto compteDto) {
        Compte compte = compteMapper.toEntity(compteDto);
        return compteRepository.save(compte);
    }

    @Override
    @Transactional(readOnly = true)
    public Compte getCompteById(Long compteId) {
        return compteRepository.findById(compteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Le compte avec l'ID %d n'existe pas", compteId)));
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
    public List<Compte> getAllComptes() {
        return compteRepository.findAll();
    }

    @Override
    public Compte updateCompte(Long compteId, ModifCompteDto modifcompteDto) {
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

        compte.setCptIsAdmin(modifcompteDto.getCptIsAdmin());

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

        return compteRepository.save(compte);
    }
}