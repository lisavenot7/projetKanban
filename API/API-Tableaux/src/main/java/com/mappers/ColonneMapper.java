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
public class ColonneMapper {

    public ColonneDto toDto(Colonne c) {
        var dto = new ColonneDto();
        dto.setClnId(c.getClnId());
        dto.setClnNom(c.getClnNom());
        return dto;
    }

    public Colonne toEntity(ColonneDto dto) {
        var c = new Colonne();
        c.setClnId(dto.getClnId());
        c.setClnNom(dto.getClnNom());
        return c;
    }

} 