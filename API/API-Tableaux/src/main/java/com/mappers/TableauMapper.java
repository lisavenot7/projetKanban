package com.mappers;

import com.dtos.ColonneDto;
import com.dtos.TableauDto;
import com.entities.Colonne;
import com.entities.Tableau;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper responsable de la conversion entre les entités Dog et les DTOs DogDto.
 * Un mapper permet de séparer la couche de persistance de la couche de présentation.
 * Points clés du pattern Mapper :
 * - Conversion bidirectionnelle entre DTO et Entity
 * - Gestion des null-safety
 * - Pas de logique métier, uniquement de la transformation
 */
@Component
public class TableauMapper {

    private final ColonneMapper colonneMapper =  new ColonneMapper();

    public TableauDto toDto(Tableau tableau) {
        if(tableau == null) return null;
        var dto = new TableauDto();
        dto.setTabId(tableau.getTabId());
        dto.setTabNom(tableau.getTabNom());
        dto.setCptId(tableau.getCptId());
        dto.setTabDateCreation(tableau.getTabDateCreation());
        if (tableau.getColonnes() != null) {
            dto.setColonnes(tableau.getColonnes().stream()
                    .map(colonneMapper::toDto)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public Tableau toEntity(TableauDto dto) {
        if(dto == null) return null;
        var t = new Tableau();
        t.setTabId(dto.getTabId());
        t.setTabNom(dto.getTabNom());
        t.setTabDateCreation(dto.getTabDateCreation());
        t.setCptId(dto.getCptId());
        if (dto.getColonnes() != null) {
            List<Colonne> cols = dto.getColonnes().stream()
                    .map(c -> {
                        Colonne col = colonneMapper.toEntity(c);
                        col.setTableau(t); // lier la colonne au tableau
                        return col;
                    })
                    .collect(Collectors.toList());
            t.setColonnes(cols);
        }
        return t;
    }

} 