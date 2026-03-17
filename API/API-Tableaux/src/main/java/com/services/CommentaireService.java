package com.services;

import com.dtos.TableauDto;
import com.dtos.CommentaireDto;

import java.util.List;

public interface CommentaireService {

    /**
     * Crée une nouvelle tâche
     * @param commentaireDto DTO de la tâche à créer
     * @return DTO de la tâche créée
     */
    CommentaireDto saveCommentaire(CommentaireDto commentaireDto);

    /**
     * Récupère une tâche par son ID
     * @param commentaireId ID de la tâche
     * @return DTO de la tâche
     */
    CommentaireDto getCommentaireById(String commentaireId);

    /**
     * Supprime une tâche
     * @param commentaireId ID de la tâche
     * @return true si la suppression a réussi
     */
    boolean deleteCommentaire(String commentaireId);

    /**
     * Récupère toutes les tâches
     * @return liste de DTOs de toutes les tâches
     */
    List<CommentaireDto> getAllCommentaires();

    /**
     * Met à jour une tâche existante
     * @param commentaireId ID de la tâche à mettre à jour
     * @param commentaireDto DTO contenant les nouvelles informations
     * @return DTO de la tâche mise à jour
     */
    CommentaireDto updateCommentaire(String commentaireId, CommentaireDto commentaireDto);

    List<CommentaireDto> getCommentairesByTache(Long tchId);

    CommentaireDto createCommentaire(Long tchId, CommentaireDto commentaireDto);
}