package com.controllers;

import com.dtos.CommentaireDto;
import com.services.CommentaireService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commentaires")
public class CommentaireController {

    private final CommentaireService commentaireService;

    public CommentaireController(CommentaireService commentaireService) {
        this.commentaireService = commentaireService;
    }

    /**
     * Créer un commentaire
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentaireDto createCommentaire(@RequestBody CommentaireDto commentaireDto) {
        return commentaireService.saveCommentaire(commentaireDto);
    }

    /**
     * Récupérer un commentaire par ID
     */
    @GetMapping("/{id}")
    public CommentaireDto getCommentaireById(@PathVariable String id) {
        return commentaireService.getCommentaireById(id);
    }

    /**
     * Récupérer tous les commentaires
     */
    @GetMapping
    public List<CommentaireDto> getAllCommentaires() {
        return commentaireService.getAllCommentaires();
    }

    /**
     * Modifier un commentaire
     */
    @PatchMapping("/{id}")
    public CommentaireDto updateCommentaire(
            @PathVariable String id,
            @RequestBody CommentaireDto commentaireDto) {

        return commentaireService.updateCommentaire(id, commentaireDto);
    }

    /**
     * Supprimer un commentaire
     */
    @DeleteMapping("/{id}")
    public boolean deleteCommentaire(@PathVariable String id) {
        return commentaireService.deleteCommentaire(id);
    }
}