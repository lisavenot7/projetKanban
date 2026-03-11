package com.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColonneDto {

    @NotNull
    private Long clnId;

    @NotNull
    private String clnNom;
    
}
