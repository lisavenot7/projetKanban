package com.services.impl;

import com.dtos.ColonneDto;
import com.dtos.TableauDto;
import com.entities.Colonne;
import com.entities.Tableau;
import com.mappers.TableauMapper;
import com.repositories.ColonneRepository;
import com.repositories.CompteRepository;
import com.repositories.TableauRepository;
import com.services.ColonneService;
import com.services.TableauService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("TableauService")
@Transactional
public class TableauServiceImpl implements TableauService {

    private final TableauRepository tableauRepository;
    private final CompteRepository compteRepository;
    private final ColonneRepository colonneRepository;

    private final ColonneService colonneService;

    private final TableauMapper tableauMapper;

    public TableauServiceImpl(TableauRepository tableauRepository, TableauMapper tableauMapper, CompteRepository compteRepository, ColonneRepository colonneRepository, ColonneService colonneService) {
        this.tableauRepository = tableauRepository;
        this.tableauMapper = tableauMapper;
        this.compteRepository = compteRepository;
        this.colonneRepository = colonneRepository;
        this.colonneService = colonneService;
    }

    @Override
    public TableauDto saveTableau(TableauDto tableauDto) {
        var tableau = tableauMapper.toEntity(tableauDto);
        var savedTableau = tableauRepository.save(tableau);
        return tableauMapper.toDto(savedTableau);
    }

    @Override
    @Transactional(readOnly = true)
    public TableauDto getTableauById(Long tableauId) {
        var tableau = tableauRepository.findById(tableauId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("La tableau avec l'ID %d n'existe pas", tableauId)));
        return tableauMapper.toDto(tableau);
    }

    @Override
    public boolean deleteTableau(Long tableauId) {
        var tableau = tableauRepository.findById(tableauId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("La tableau avec l'ID %d n'existe pas", tableauId)));
        tableauRepository.deleteById(tableau.getTabId());
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TableauDto> getAllTableaux() {
        return tableauRepository.findAll().stream()
                .map(tableauMapper::toDto)
                .toList();
    }

    @Override
    public TableauDto updateTableau(Long tableauId, TableauDto tableauDto) {
        var tableau = tableauRepository.findById(tableauId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Le tableau avec l'ID %d n'existe pas", tableauId)));

        if (tableauDto.getTabNom() != null) {
            tableau.setTabNom(tableauDto.getTabNom());
        }
        if (tableauDto.getTabDateCreation() != null) {
            tableau.setTabDateCreation(tableauDto.getTabDateCreation());
        }

        // Si createurId est fourni, on met à jour le créateur
        if (tableauDto.getCreateurId() != null) {
            var createur = compteRepository.findById(tableauDto.getCreateurId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            String.format("Le compte avec l'ID %d n'existe pas", tableauDto.getCreateurId())));
            tableau.setCreateur(createur);
        }

        // Si comptesIds est fourni, on met à jour les participants
        if (tableauDto.getComptesIds() != null) {
            var participants = tableauDto.getComptesIds().stream()
                    .map(id -> compteRepository.findById(id)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                    String.format("Le compte avec l'ID %d n'existe pas", id))))
                    .collect(Collectors.toSet());
            tableau.setParticipants(participants);
        }

        var savedTableau = tableauRepository.save(tableau);
        return tableauMapper.toDto(savedTableau);
    }

    @Override
    public TableauDto createTableau(Long cptId, TableauDto tableauDto) {

        var compte = compteRepository.findById(cptId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Le compte avec l'ID %d n'existe pas", cptId)));

        Tableau tableau = tableauMapper.toEntity(tableauDto);

        tableau.setCreateur(compte);

        // 🔥 1. Sauvegarder UNE SEULE FOIS
        Tableau tableauCreated = tableauRepository.save(tableau);

        // 🔥 2. Lier au compte
        compte.getTableauxCrees().add(tableauCreated);
        compteRepository.save(compte);

        return tableauMapper.toDto(tableauCreated);
    }

    @Override
    public List<TableauDto> getTableauxByCompte(Long id) {

        var compte = compteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Le compte avec l'ID %d n'existe pas", id)));

        List<TableauDto> listTab = new ArrayList<>();

        compte.getTableauxCrees().forEach(tableau -> {listTab.add(tableauMapper.toDto(tableau));});
        compte.getParticipations().forEach(tableau ->  {listTab.add(tableauMapper.toDto(tableau));});
        return listTab;
    }

    @Override
    public List<TableauDto> getTableauxCreesByCompte(Long id) {

        var compte = compteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Le compte avec l'ID %d n'existe pas", id)));

        List<TableauDto> listTab = new ArrayList<>();

        compte.getTableauxCrees().forEach(tableau -> {listTab.add(tableauMapper.toDto(tableau));});
        return listTab;
    }

    @Override
    public List<TableauDto> getTableauxParticipesByCompte(Long id) {

        var compte = compteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Le compte avec l'ID %d n'existe pas", id)));

        List<TableauDto> listTab = new ArrayList<>();

        compte.getParticipations().forEach(tableau ->  {listTab.add(tableauMapper.toDto(tableau));});
        return listTab;
    }
}
