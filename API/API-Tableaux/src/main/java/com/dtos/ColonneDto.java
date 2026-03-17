package com.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColonneDto {

    private Long clnId; // optionnel à la création

    private String clnNom;

    private Long tabId; // ID du tableau auquel la colonne appartient

    // Optionnel : inclure les tâches
    private List<TacheDto> taches;
}