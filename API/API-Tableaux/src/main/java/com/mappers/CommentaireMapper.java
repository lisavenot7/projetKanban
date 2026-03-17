package com.mappers;

import com.dtos.CommentaireDto;
import com.entities.Commentaire;

public class CommentaireMapper {

    public static CommentaireDto toDto(Commentaire commentaire) {
        CommentaireDto dto = new CommentaireDto();

        dto.setId(commentaire.getId());
        dto.setContenu(commentaire.getContenu());
        dto.setDatePublication(commentaire.getDatePublication());
        dto.setCptId(commentaire.getCptId());
        dto.setTchId(commentaire.getTchId());

        return dto;
    }

    public static Commentaire toEntity(CommentaireDto dto) {
        Commentaire commentaire = new Commentaire();

        commentaire.setId(dto.getId());
        commentaire.setContenu(dto.getContenu());
        commentaire.setDatePublication(dto.getDatePublication());
        commentaire.setCptId(dto.getCptId());
        commentaire.setTchId(dto.getTchId());

        return commentaire;
    }
}