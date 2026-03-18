package com.mappers;

import com.dtos.TacheDto;
import com.entities.Tache;
import org.springframework.stereotype.Component;

@Component
public class TacheMapper {

    /**
     * Convertit un DTO en entité.
     * Les entités liées (Compte et Colonne) seront assignées dans le service.
     * @param dto le DTO à convertir
     * @return l'entité Tache
     */
    public Tache toEntity(TacheDto dto) {
        if (dto == null) return null;

        Tache tache = new Tache();
        tache.setTchId(dto.getTchId());
        tache.setTchTitre(dto.getTchTitre());
        tache.setTchDescription(dto.getTchDescription());
        tache.setTchDateLimite(dto.getTchDateLimite());
        tache.setTchPriorite(dto.getTchPriorite());
        tache.setTchStatus(dto.getTchStatus());
        // Compte et Colonne seront assignés dans le service
        return tache;
    }

    /**
     * Convertit une entité en DTO.
     * @param entity l'entité Tache à convertir
     * @return le DTO correspondant
     */
    public TacheDto toDto(Tache entity) {
        if (entity == null) return null;

        TacheDto dto = new TacheDto();
        dto.setTchId(entity.getTchId());
        dto.setTchTitre(entity.getTchTitre());
        dto.setTchDescription(entity.getTchDescription());
        dto.setTchDateLimite(entity.getTchDateLimite());
        dto.setTchPriorite(entity.getTchPriorite());
        dto.setTchStatus(entity.getTchStatus());

        if (entity.getCompteAttribue() != null) {
            dto.setCompteId(entity.getCompteAttribue().getCptId());
        }

        if (entity.getColonne() != null) {
            dto.setColonneId(entity.getColonne().getClnId());
        }

        return dto;
    }
}