package com.mappers;

import com.dtos.CompteDto;
import com.dtos.TableauDto;
import com.entities.Compte;
import com.entities.Tableau;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompteMapper {

    private final TableauMapper tableauMapper = new TableauMapper();

    public CompteDto toDto(Compte compte) {
        if (compte == null) return null;

        CompteDto dto = new CompteDto();
        dto.setCptId(compte.getCptId());
        dto.setCptMail(compte.getCptMail());
        dto.setCptPseudo(compte.getCptPseudo());
        dto.setCptMdp(compte.getCptMdp());
        dto.setCptIsAdmin(compte.isCptIsAdmin());

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

    public Compte toEntity(CompteDto dto) {
        if (dto == null) return null;

        Compte compte = new Compte();
        compte.setCptId(dto.getCptId());
        compte.setCptMail(dto.getCptMail());
        compte.setCptPseudo(dto.getCptPseudo());
        compte.setCptMdp(dto.getCptMdp());
        compte.setCptIsAdmin(dto.isCptIsAdmin());

        if (dto.getTableauxCrees() != null) {
            var tableaux = dto.getTableauxCrees().stream()
                    .map(tDto -> {
                        Tableau t = tableauMapper.toEntity(tDto); // mapper sans compte
                        t.setCreateur(compte); // on lie le compte après
                        return t;
                    })
                    .toList(); // Set au lieu de List
            compte.setTableauxCrees(tableaux);
        }

        return compte;
    }
}