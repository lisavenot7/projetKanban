package com.services.impl;

import com.dtos.CompteUserResponse;
import com.dtos.ParticipantsDto;
import com.dtos.TableauDto;
import com.entities.Compte;
import com.entities.Tableau;
import com.mappers.CompteUserMapper;
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
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service("TableauService")
@Transactional
public class TableauServiceImpl implements TableauService {

    private final TableauRepository tableauRepository;
    private final CompteRepository compteRepository;
    private final ColonneRepository colonneRepository;

    private final ColonneService colonneService;

    private final TableauMapper tableauMapper;
    private final CompteUserMapper compteUserMapper;

    public TableauServiceImpl(TableauRepository tableauRepository, TableauMapper tableauMapper, CompteRepository compteRepository, ColonneRepository colonneRepository, ColonneService colonneService, CompteUserMapper compteUserMapper) {
        this.tableauRepository = tableauRepository;
        this.tableauMapper = tableauMapper;
        this.compteRepository = compteRepository;
        this.colonneRepository = colonneRepository;
        this.colonneService = colonneService;
        this.compteUserMapper = compteUserMapper;
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

    @Override
    @Transactional
    public List<CompteUserResponse> getParticipants(Long id) {
        var tableau = tableauRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Le tableau avec l'ID %d n'existe pas", id)));

        return tableau.getParticipants()
                .stream()
                .map(compteUserMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public List<CompteUserResponse> setParticipants(Long id, ParticipantsDto dto) {

        var tableau = tableauRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tableau introuvable"));

        // 🔥 Charger les comptes actuels liés à CE tableau
        var anciensParticipants = new HashSet<>(tableau.getParticipants());

        // 🔥 Charger les nouveaux
        var nouveauxParticipants = new HashSet<>(
                compteRepository.findAllById(dto.getIds())
        );

        if (nouveauxParticipants.size() != dto.getIds().size()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Un ou plusieurs participants n'existent pas");
        }

        // 🔥 SUPPRESSION propre
        for (Compte compte : anciensParticipants) {
            compte.getParticipations().remove(tableau);
        }

        // 🔥 AJOUT propre
        for (Compte compte : nouveauxParticipants) {
            compte.getParticipations().add(tableau);
        }

        // 🔥 Synchroniser côté inverse (important en mémoire)
        tableau.getParticipants().clear();
        tableau.getParticipants().addAll(nouveauxParticipants);

        return tableau.getParticipants()
                .stream()
                .map(compteUserMapper::toDto)
                .toList();
    }

    @Override
    public CompteUserResponse getCreateur(Long id) {
        var tableau = tableauRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Le tableau avec l'ID %d n'existe pas", id)));
        return compteUserMapper.toDto(tableau.getCreateur());
    }
}
