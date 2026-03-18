package com.services.impl;

import com.dtos.ActionDto;
import com.entities.Action;
import com.entities.Compte;
import com.entities.Tache;
import com.mappers.ActionMapper;
import com.repositories.ActionRepository;
import com.repositories.CompteRepository;
import com.repositories.TacheRepository;
import com.services.ActionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service("ActionService")
@Transactional
public class ActionServiceImpl implements ActionService {

    private final ActionMapper actionMapper;
    private final ActionRepository actionRepository;
    private final CompteRepository compteRepository;
    private final TacheRepository tacheRepository;

    public ActionServiceImpl(ActionMapper actionMapper, ActionRepository actionRepository, CompteRepository compteRepository, TacheRepository tacheRepository) {
        this.actionMapper = actionMapper;
        this.actionRepository = actionRepository;
        this.compteRepository = compteRepository;
        this.tacheRepository = tacheRepository;
    }

    @Override
    public ActionDto saveAction(ActionDto actionDto) {
        Action action = actionMapper.toEntity(actionDto);
        return actionMapper.toDto(actionRepository.save(action));
    }

    @Override
    public ActionDto getActionById(Long actionId) {
        Action action = actionRepository.findById(actionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("L'action avec l'ID %d n'existe pas", actionId)));

        return actionMapper.toDto(action);
    }

    @Override
    public boolean deleteAction(Long actionId) {
        Action action = actionRepository.findById(actionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("L'action avec l'ID %d n'existe pas", actionId)));
        actionRepository.delete(action);
        return true;
    }

    @Override
    public List<ActionDto> getAllActions() {
        return actionRepository.findAll().stream()
                .map(actionMapper::toDto)
                .toList();
    }

    @Override
    public ActionDto updateAction(Long actionId, ActionDto actionDto) {

        Action existingAction = actionRepository.findById(actionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("L'action avec l'ID %d n'existe pas", actionId)));

        // Mise à jour des champs simples
        existingAction.setDescription(actionDto.getDescription());
        existingAction.setDateModification(actionDto.getDateModification());

        // Vérification + récupération du compte
        Compte compte = compteRepository.findById(actionDto.getAuteur())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Le compte avec l'ID %d n'existe pas", actionDto.getAuteur())));

        // Vérification + récupération de la tâche
        Tache tache = tacheRepository.findById(actionDto.getTacheModifiee())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("La tâche avec l'ID %d n'existe pas", actionDto.getTacheModifiee())));

        // Mise à jour des relations
        existingAction.setAuteur(compte);
        existingAction.setTacheModifiee(tache);

        // Sauvegarde
        Action updatedAction = actionRepository.save(existingAction);

        return actionMapper.toDto(updatedAction);
    }
}
