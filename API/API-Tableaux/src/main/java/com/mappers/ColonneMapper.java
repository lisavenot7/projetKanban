package com.mappers;

import com.dtos.ColonneDto;
import com.dtos.TacheDto;
import com.entities.Colonne;
import com.entities.Tableau;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ColonneMapper {

    private final TacheMapper tacheMapper = new TacheMapper();

    /**
     * Convertit une entité Colonne en DTO
     * @param colonne l'entité à convertir
     * @return le DTO correspondant avec les tâches
     */
    public ColonneDto toDto(Colonne colonne) {
        if (colonne == null) return null;

        ColonneDto dto = new ColonneDto();
        dto.setClnId(colonne.getClnId());
        dto.setClnNom(colonne.getClnNom());
        if (colonne.getTableau() != null) {
            dto.setTabId(colonne.getTableau().getTabId());
        }

        // Mapper les tâches si elles existent
        if (colonne.getTaches() != null && !colonne.getTaches().isEmpty()) {
            List<TacheDto> tacheDtos = colonne.getTaches()
                    .stream()
                    .map(tacheMapper::toDto)
                    .collect(Collectors.toList());
            dto.setTaches(tacheDtos);
        }

        return dto;
    }

    /**
     * Convertit un DTO en entité Colonne
     * @param dto DTO à convertir
     * @return entité Colonne
     */
    public Colonne toEntity(ColonneDto dto) {
        if (dto == null) return null;

        Colonne colonne = new Colonne();
        colonne.setClnId(dto.getClnId());
        colonne.setClnNom(dto.getClnNom());

        // Crée un tableau minimal avec seulement l'ID pour le lien
        if (dto.getTabId() != null) {
            Tableau tableau = new Tableau();
            tableau.setTabId(dto.getTabId());
            colonne.setTableau(tableau);
        }

        // On ne mappe pas encore les tâches ici pour garder un seul paramètre
        return colonne;
    }
}