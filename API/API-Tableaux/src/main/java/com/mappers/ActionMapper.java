package com.mappers;

import com.dtos.ActionDto;
import com.entities.Action;
import com.entities.Compte;
import com.entities.Tache;
import com.repositories.CompteRepository;
import com.repositories.TacheRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ActionMapper {

    private final CompteRepository compteRepository;
    private final TacheRepository tacheRepository;

    public ActionMapper(CompteRepository compteRepository,
                        TacheRepository tacheRepository) {
        this.compteRepository = compteRepository;
        this.tacheRepository = tacheRepository;
    }

    public ActionDto toDto(Action action) {
        if (action == null) {
            return null;
        }

        ActionDto actionDto = new ActionDto();

        actionDto.setId(action.getId());
        actionDto.setDescription(action.getDescription());
        actionDto.setDateModification(action.getDateModification());

        if (action.getAuteur() != null) {
            actionDto.setAuteur(action.getAuteur().getCptId());
        }

        if (action.getTacheModifiee() != null) {
            actionDto.setTacheModifiee(action.getTacheModifiee().getTchId());
        }

        return actionDto;
    }

    public Action toEntity(ActionDto actionDto) {
        if (actionDto == null) {
            return null;
        }

        Action action = new Action();

        action.setId(actionDto.getId());
        action.setDescription(actionDto.getDescription());
        action.setDateModification(actionDto.getDateModification());

        // 🔎 Recherche en base du compte
        Compte compte = compteRepository.findById(actionDto.getAuteur())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Le compte avec l'ID %d n'existe pas", actionDto.getAuteur())));

        // 🔎 Recherche en base de la tâche
        Tache tache = tacheRepository.findById(actionDto.getTacheModifiee())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("La tâche avec l'ID %d n'existe pas", actionDto.getTacheModifiee())));

        action.setAuteur(compte);
        action.setTacheModifiee(tache);

        return action;
    }
}