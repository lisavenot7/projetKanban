package com.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionDto {

    private Long id;

    private String description;

    private String dateModification;

    private Long auteur;

    private Long tacheModifiee;
}
