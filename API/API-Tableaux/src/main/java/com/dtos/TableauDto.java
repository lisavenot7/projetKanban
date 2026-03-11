package com.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableauDto {

    @NotNull
    private Long tabId;

    @NotNull
    private int cptId;

    @NotNull
    private String tabNom;

    private String tabDateCreation;

    private List<ColonneDto> colonnes;
}
