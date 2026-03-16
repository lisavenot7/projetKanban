package com.services;

import com.dtos.TableauDto;
import com.dtos.TacheDto;

import java.util.List;

public interface TacheService {

    /**
     * Crée une nouvelle tâche
     * @param tacheDto DTO de la tâche à créer
     * @return DTO de la tâche créée
     */
    TacheDto saveTache(TacheDto tacheDto);

    /**
     * Récupère une tâche par son ID
     * @param tacheId ID de la tâche
     * @return DTO de la tâche
     */
    TacheDto getTacheById(Long tacheId);

    /**
     * Supprime une tâche
     * @param tacheId ID de la tâche
     * @return true si la suppression a réussi
     */
    boolean deleteTache(Long tacheId);

    /**
     * Récupère toutes les tâches
     * @return liste de DTOs de toutes les tâches
     */
    List<TacheDto> getAllTaches();

    /**
     * Met à jour une tâche existante
     * @param tacheId ID de la tâche à mettre à jour
     * @param tacheDto DTO contenant les nouvelles informations
     * @return DTO de la tâche mise à jour
     */
    TacheDto updateTache(Long tacheId, TacheDto tacheDto);

    TableauDto createTache(Long colonneId, TacheDto tacheDto);
}