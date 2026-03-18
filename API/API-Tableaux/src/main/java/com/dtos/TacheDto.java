package com.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TacheDto {

    private Long tchId;          // optionnel pour création
    private String tchTitre;
    private String tchDescription;
    private String tchDateLimite; // format String pour simplifier
    private int tchPriorite;
    private String tchStatus;

    private Long compteId;       // compte auquel la tâche est attribuée
    private Long colonneId;      // colonne à laquelle la tâche appartient
}