package com.controllers;

import com.dtos.CommentaireDto;
import com.dtos.DisplayResponseDto;
import com.dtos.TableauDto;
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
    public DisplayResponseDto<CommentaireDto> createCommentaire(@RequestBody CommentaireDto commentaireDto) {
        DisplayResponseDto<CommentaireDto> displayResponseDto = new DisplayResponseDto<>();

        displayResponseDto.setMessage("success");
        displayResponseDto.setType("collection");
        displayResponseDto.setData(commentaireService.saveCommentaire(commentaireDto));

        return displayResponseDto;
    }

    /**
     * Récupérer un commentaire par ID
     */
    @GetMapping("/{id}")
    public DisplayResponseDto<CommentaireDto> getCommentaireById(@PathVariable String id) {
        DisplayResponseDto<CommentaireDto> displayResponseDto = new DisplayResponseDto<>();

        displayResponseDto.setMessage("success");
        displayResponseDto.setType("collection");
        displayResponseDto.setData(commentaireService.getCommentaireById(id));

        return displayResponseDto;
    }

    /**
     * Récupérer tous les commentaires
     */
    @GetMapping
    public DisplayResponseDto<List<CommentaireDto>> getAllCommentaires() {
        DisplayResponseDto<List<CommentaireDto>> displayResponseDto = new DisplayResponseDto<>();

        displayResponseDto.setMessage("success");
        displayResponseDto.setType("collection");
        displayResponseDto.setData(commentaireService.getAllCommentaires());

        return displayResponseDto;
    }

    /**
     * Modifier un commentaire
     */
    @PatchMapping("/{id}")
    public DisplayResponseDto<CommentaireDto> updateCommentaire(
            @PathVariable String id,
            @RequestBody CommentaireDto commentaireDto) {
        DisplayResponseDto<CommentaireDto> displayResponseDto = new DisplayResponseDto<>();

        displayResponseDto.setMessage("success");
        displayResponseDto.setType("collection");
        displayResponseDto.setData(commentaireService.updateCommentaire(id, commentaireDto));

        return displayResponseDto;
    }

    /**
     * Supprimer un commentaire
     */
    @DeleteMapping("/{id}")
    public boolean deleteCommentaire(@PathVariable String id) {
        return commentaireService.deleteCommentaire(id);
    }
}