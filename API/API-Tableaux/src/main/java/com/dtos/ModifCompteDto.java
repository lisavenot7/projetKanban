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
public class ModifCompteDto {

    private Long cptId; // optionnel pour création

    private String cptMail;

    private String cptMdp;

    private String nom;
    private String prenom;

    private int cptIsActive;

    private int cptIsAdmin; // pas besoin de @NotNull, boolean primitif

    private List<TableauDto> tableauxCrees; // tableaux créés par ce compte

    private Set<Long> tableauIds; // IDs des tableaux auxquels le compte participe
}