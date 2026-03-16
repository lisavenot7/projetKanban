package com.entities;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "commentaires")
public class Commentaires {

    @Id
    private String id;

    private String contenu;

    private String datePublication;

    private String cptId;

    private String tchId;
}
