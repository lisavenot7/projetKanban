package com.services;

import com.dtos.ActionDto;
import com.dtos.TacheDto;

import java.util.List;

public interface ActionService {

    /**
     * Crée une nouvelle tâche
     * @param actionDto DTO de la tâche à créer
     * @return DTO de la tâche créée
     */
    ActionDto saveAction(ActionDto actionDto);

    /**
     * Récupère une tâche par son ID
     * @param actionId ID de la tâche
     * @return DTO de la tâche
     */
    ActionDto getActionById(Long actionId);

    /**
     * Supprime une tâche
     * @param actionId ID de la tâche
     * @return true si la suppression a réussi
     */
    boolean deleteAction(Long actionId);

    /**
     * Récupère toutes les tâches
     * @return liste de DTOs de toutes les tâches
     */
    List<ActionDto> getAllActions();

    /**
     * Met à jour une tâche existante
     * @param actionDto ID de la tâche à mettre à jour
     * @param actionId DTO contenant les nouvelles informations
     * @return DTO de la tâche mise à jour
     */
    ActionDto updateAction(Long actionId, ActionDto actionDto);

}