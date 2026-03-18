package com.services.impl;

import com.dtos.CommentaireDto;
import com.entities.Commentaire;
import com.mappers.CommentaireMapper;
import com.repositories.CommentaireRepository;
import com.repositories.CompteRepository;
import com.repositories.TacheRepository;
import com.services.CommentaireService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CommentaireServiceImpl implements CommentaireService {

    private final CommentaireRepository commentaireRepository;
    private final TacheRepository tacheRepository;
    private final CompteRepository compteRepository;

    public CommentaireServiceImpl(CommentaireRepository commentaireRepository, TacheRepository tacheRepository, CompteRepository compteRepository) {
        this.commentaireRepository = commentaireRepository;
        this.tacheRepository = tacheRepository;
        this.compteRepository = compteRepository;
    }

    @Override
    public CommentaireDto saveCommentaire(CommentaireDto commentaireDto) {

        Commentaire commentaire = CommentaireMapper.toEntity(commentaireDto);

        Commentaire savedCommentaire = commentaireRepository.save(commentaire);

        return CommentaireMapper.toDto(savedCommentaire);
    }

    @Override
    public CommentaireDto getCommentaireById(String commentaireId) {

        Commentaire commentaire = commentaireRepository.findById(commentaireId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Le commentaire avec l'ID %s n'existe pas", commentaireId)
                ));

        return CommentaireMapper.toDto(commentaire);
    }

    @Override
    public boolean deleteCommentaire(String commentaireId) {

        Commentaire commentaire = commentaireRepository.findById(commentaireId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Le commentaire avec l'ID %s n'existe pas", commentaireId)
                ));

        commentaireRepository.deleteById(commentaire.getId());

        return true;
    }

    @Override
    public List<CommentaireDto> getAllCommentaires() {

        return commentaireRepository.findAll()
                .stream()
                .map(CommentaireMapper::toDto)
                .toList();
    }

    @Override
    public CommentaireDto updateCommentaire(String commentaireId, CommentaireDto commentaireDto) {

        Commentaire commentaire = commentaireRepository.findById(commentaireId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Le commentaire avec l'ID %s n'existe pas", commentaireId)
                ));

        if (commentaireDto.getContenu() != null) {
            commentaire.setContenu(commentaireDto.getContenu());
        }

        if (commentaireDto.getDatePublication() != null) {
            commentaire.setDatePublication(commentaireDto.getDatePublication());
        }

        if (commentaireDto.getCptId() != null) {
            commentaire.setCptId(commentaireDto.getCptId());
        }

        if (commentaireDto.getTchId() != null) {
            commentaire.setTchId(commentaireDto.getTchId());
        }

        Commentaire updatedCommentaire = commentaireRepository.save(commentaire);

        return CommentaireMapper.toDto(updatedCommentaire);
    }

    public List<CommentaireDto> getCommentairesByTache(Long tchId) {

        return commentaireRepository.findByTchId(tchId)
                .stream()
                .map(CommentaireMapper::toDto)
                .toList();
    }

    @Override
    public CommentaireDto createCommentaire(Long tchId, CommentaireDto commentaireDto) {

        // 1. Vérifier que la tâche existe (MariaDB)
        tacheRepository.findById(tchId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "La tâche avec l'ID " + commentaireDto.getTchId() + " n'existe pas"));

        // 2. Vérifier que le compte existe (optionnel mais recommandé)
        if (commentaireDto.getCptId() != null) {
            compteRepository.findById(Long.valueOf(commentaireDto.getCptId()))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Le compte avec l'ID " + commentaireDto.getCptId() + " n'existe pas"));
        }

        // 3. Créer le commentaire (MongoDB)
        Commentaire commentaire = new Commentaire();
        commentaire.setContenu(commentaireDto.getContenu());
        commentaire.setDatePublication(commentaireDto.getDatePublication());
        commentaire.setCptId(commentaireDto.getCptId());
        commentaire.setTchId(tchId.intValue());

        Commentaire saved = commentaireRepository.save(commentaire);

        // 4. Retour DTO
        return CommentaireMapper.toDto(saved);
    }
}