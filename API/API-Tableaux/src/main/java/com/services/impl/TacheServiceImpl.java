package com.services.impl;

import com.dtos.TableauDto;
import com.dtos.TacheDto;
import com.entities.Colonne;
import com.entities.Compte;
import com.entities.Tableau;
import com.entities.Tache;
import com.mappers.ColonneMapper;
import com.mappers.TableauMapper;
import com.mappers.TacheMapper;
import com.repositories.ColonneRepository;
import com.repositories.CompteRepository;
import com.repositories.TableauRepository;
import com.repositories.TacheRepository;
import com.services.TacheService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service("TacheService")
@Transactional
public class TacheServiceImpl implements TacheService {

    private final TacheRepository tacheRepository;
    private final CompteRepository compteRepository;
    private final ColonneRepository colonneRepository;
    private final TacheMapper tacheMapper;
    private final TableauMapper tableauMapper;
    private final TableauRepository tableauRepository;

    public TacheServiceImpl(TacheRepository tacheRepository,
                            CompteRepository compteRepository,
                            ColonneRepository colonneRepository,
                            TacheMapper tacheMapper, TableauMapper tableauMapper, TableauRepository tableauRepository) {
        this.tacheRepository = tacheRepository;
        this.compteRepository = compteRepository;
        this.colonneRepository = colonneRepository;
        this.tacheMapper = tacheMapper;
        this.tableauMapper = tableauMapper;
        this.tableauRepository = tableauRepository;
    }

    @Override
    public TacheDto saveTache(TacheDto tacheDto) {
        Tache tache = tacheMapper.toEntity(tacheDto);

        // Récupérer le compte attribué si présent
        if (tacheDto.getCompteId() != null) {
            Compte compte = compteRepository.findById(tacheDto.getCompteId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Le compte avec l'ID " + tacheDto.getCompteId() + " n'existe pas"));
            tache.setCompteAttribue(compte);
        }

        // Récupérer la colonne si présent
        if (tacheDto.getColonneId() != null) {
            Colonne colonne = colonneRepository.findById(tacheDto.getColonneId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "La colonne avec l'ID " + tacheDto.getColonneId() + " n'existe pas"));
            tache.setColonne(colonne);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Une tâche doit appartenir à une colonne");
        }

        Tache savedTache = tacheRepository.save(tache);
        return tacheMapper.toDto(savedTache);
    }

    @Override
    @Transactional(readOnly = true)
    public TacheDto getTacheById(Long tacheId) {
        Tache tache = tacheRepository.findById(tacheId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "La tâche avec l'ID " + tacheId + " n'existe pas"));
        return tacheMapper.toDto(tache);
    }

    @Override
    public boolean deleteTache(Long tacheId) {
        Tache tache = tacheRepository.findById(tacheId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "La tâche avec l'ID " + tacheId + " n'existe pas"));
        tacheRepository.deleteById(tache.getTchId());
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TacheDto> getAllTaches() {
        return tacheRepository.findAll()
                .stream()
                .map(tacheMapper::toDto)
                .toList();
    }

    @Override
    public TacheDto updateTache(Long tacheId, TacheDto tacheDto) {
        Tache tache = tacheRepository.findById(tacheId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "La tâche avec l'ID " + tacheId + " n'existe pas"));

        if (tacheDto.getTchTitre() != null) tache.setTchTitre(tacheDto.getTchTitre());
        if (tacheDto.getTchDescription() != null) tache.setTchDescription(tacheDto.getTchDescription());
        if (tacheDto.getTchDateLimite() != null) tache.setTchDateLimite(tacheDto.getTchDateLimite());
        tache.setTchPriorite(tacheDto.getTchPriorite());
        tache.setTchStatus(tacheDto.getTchStatus());

        // Mettre à jour le compte attribué si fourni
        if (tacheDto.getCompteId() != null) {
            Compte compte = compteRepository.findById(tacheDto.getCompteId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Le compte avec l'ID " + tacheDto.getCompteId() + " n'existe pas"));
            tache.setCompteAttribue(compte);
        }

        // Mettre à jour la colonne si fourni
        if (tacheDto.getColonneId() != null) {
            Colonne colonne = colonneRepository.findById(tacheDto.getColonneId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "La colonne avec l'ID " + tacheDto.getColonneId() + " n'existe pas"));
            tache.setColonne(colonne);
        }

        Tache savedTache = tacheRepository.save(tache);
        return tacheMapper.toDto(savedTache);
    }

    @Override
    public TacheDto createTache(Long colonneId, TacheDto tacheDto) {

        // 1. Récupérer la colonne
        Colonne colonne = colonneRepository.findById(colonneId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "La colonne avec l'ID " + colonneId + " n'existe pas"));

        // 2. Convertir DTO → Entity
        Tache tache = tacheMapper.toEntity(tacheDto);

        // 3. Associer la colonne
        tache.setColonne(colonne);

        // 4. Associer le compte si présent
        if (tacheDto.getCompteId() != null) {
            Compte compte = compteRepository.findById(tacheDto.getCompteId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Le compte avec l'ID " + tacheDto.getCompteId() + " n'existe pas"));
            tache.setCompteAttribue(compte);
        }

        // 5. Sauvegarder la tâche
        Tache tacheCree = tacheRepository.save(tache);

        // 6. Retourner le DTO
        return tacheMapper.toDto(tacheCree);
    }
}