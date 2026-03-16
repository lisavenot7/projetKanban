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
public class CompteDto {

    private Long cptId; // optionnel pour création

    @NotNull
    private String cptMail;

    @NotNull
    private String cptMdp;

    private int cptIsAdmin; // pas besoin de @NotNull, boolean primitif

    private int cptIsActive;

    private List<TableauDto> tableauxCrees; // tableaux créés par ce compte

    private Set<Long> tableauIds; // IDs des tableaux auxquels le compte participe
}