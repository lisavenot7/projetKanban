package com.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableauDto {

    private Long tabId; // pas besoin de @NotNull si utilisé pour la création

    private String tabNom;

    private String tabDateCreation;

    private List<ColonneDto> colonnes;

    private Set<Long> comptesIds; // IDs des participants

    private Long createurId; // ID du compte créateur
}