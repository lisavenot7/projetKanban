package com.mappers;

import com.dtos.TableauDto;
import com.entities.Tableau;
import org.springframework.stereotype.Component;

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

    /**
     * Convertit une entité Dog en DTO DogDto
     * Cette méthode est utilisée pour exposer les données aux clients de l'API
     * 
     * @param tableau l'entité à convertir
     * @return le DTO correspondant ou null si l'entité est null
     */
    public TableauDto toDto(Tableau tableau) {
        if (tableau == null) {
            return null;
        }

        TableauDto tableauDto = new TableauDto();
        tableauDto.setTabId(tableau.getTabId());
        tableauDto.setCptId(tableau.getCptId());
        tableauDto.setTabNom(tableau.getTabNom());
        tableauDto.setTabDateCreation(tableau.getTabDateCreation());

        return tableauDto;
    }

    /**
     * Convertit un DTO DogDto en entité Dog
     * Cette méthode est utilisée pour persister les données reçues des clients
     * Note: La date de naissance n'est pas dans le DTO mais est présente dans l'entité
     * 
     * @param tableauDto le DTO à convertir
     * @return l'entité correspondante ou null si le DTO est null
     */
    public Tableau toEntity(TableauDto tableauDto) {
        if (tableauDto == null) {
            return null;
        }
        Tableau tableau = new Tableau();

        tableau.setTabId(tableauDto.getTabId());
        tableau.setCptId(tableauDto.getCptId());
        tableau.setTabNom(tableauDto.getTabNom());
        tableau.setTabDateCreation(tableauDto.getTabDateCreation());

        return tableau;
    }
} 