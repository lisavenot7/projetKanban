package com.mappers;

import com.dtos.ColonneDto;
import com.dtos.CompteUserResponse;
import com.dtos.TableauDto;
import com.entities.Colonne;
import com.entities.Compte;
import com.entities.Tableau;
import com.repositories.CompteRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TableauMapper {

    private final ColonneMapper colonneMapper = new ColonneMapper();
    private final CompteRepository compteRepository;

    public TableauMapper(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    // Entity -> DTO
    public TableauDto toDto(Tableau tableau) {
        if (tableau == null) return null;

        var dto = new TableauDto();
        dto.setTabId(tableau.getTabId());
        dto.setTabNom(tableau.getTabNom());
        dto.setTabDateCreation(tableau.getTabDateCreation());
        dto.setCreateurId(tableau.getCreateur() != null ? tableau.getCreateur().getCptId() : null);

        if (tableau.getColonnes() != null) {
            dto.setColonnes(tableau.getColonnes().stream()
                    .map(colonneMapper::toDto)
                    .collect(Collectors.toList()));
        }

        if (tableau.getParticipants() != null) {
            dto.setComptesIds(tableau.getParticipants().stream()
                    .map(Compte::getCptId)
                    .collect(Collectors.toSet()));
        }

        return dto;
    }

    // DTO -> Entity
    public Tableau toEntity(TableauDto dto) {
        if (dto == null) return null;

        var tableau = new Tableau();
        tableau.setTabId(dto.getTabId());
        tableau.setTabNom(dto.getTabNom());
        tableau.setTabDateCreation(dto.getTabDateCreation());

        if (compteRepository.findById(dto.getCreateurId()).isPresent()) {
            Compte compte = compteRepository.findById(dto.getCreateurId()).get();
            tableau.setCreateur(compte);
        }

        if (dto.getColonnes() != null) {
            List<Colonne> cols = dto.getColonnes().stream()
                    .map(c -> {
                        Colonne col = colonneMapper.toEntity(c);
                        col.setTableau(tableau);
                        return col;
                    })
                    .collect(Collectors.toList());
            tableau.setColonnes(cols);
        }

        // createurId sera géré dans le service (on récupère le compte depuis la DB)
        return tableau;
    }
}