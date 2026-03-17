package com.mappers;

import com.dtos.CompteUserResponse;
import com.dtos.TableauDto;
import com.entities.Compte;
import com.entities.Tableau;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompteUserMapper {

    private final TableauMapper tableauMapper;

    public CompteUserMapper(TableauMapper tableauMapper) {
        this.tableauMapper = tableauMapper;
    }

    public CompteUserResponse toDto(Compte compte) {
        if (compte == null) {
            return null;
        }

        CompteUserResponse dto = new CompteUserResponse();
        dto.setCptId(compte.getCptId());
        dto.setCptMail(compte.getCptMail());
        dto.setCptMdp(compte.getCptMdp());
        dto.setNom(compte.getUser().getNom());
        dto.setPrenom(compte.getUser().getPrenom());
        dto.setCptIsAdmin(compte.getCptIsAdmin());
        dto.setCptIsActive(compte.getCptIsActive());

        if (compte.getTableauxCrees() != null) {
            List<TableauDto> tableauDtos = compte.getTableauxCrees().stream()
                    .map(tableauMapper::toDto)
                    .collect(Collectors.toList());
            dto.setTableauxCrees(tableauDtos);

            dto.setTableauIds(compte.getTableauxCrees().stream()
                    .map(Tableau::getTabId)
                    .collect(Collectors.toSet()));
        }

        return dto;
    }

}