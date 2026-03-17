package com.entities;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "commentaires")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commentaire {

    @Id
    private String id;

    private String contenu;

    private String datePublication;

    private String cptId;

    private String tchId;
}
