package com.dtos;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class CommentaireDto {

    @Id
    private String id;

    private String contenu;

    private String datePublication;

    private Integer cptId;

    private Integer tchId;
}
