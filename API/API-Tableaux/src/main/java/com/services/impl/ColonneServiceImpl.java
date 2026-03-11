package com.services.impl;

import com.dtos.ColonneDto;
import com.dtos.TableauDto;
import com.entities.Colonne;
import com.mappers.ColonneMapper;
import com.mappers.TableauMapper;
import com.repositories.ColonneRepository;
import com.repositories.TableauRepository;
import com.services.ColonneService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service("ColonneService")
@Transactional
public class ColonneServiceImpl implements ColonneService {

    private final ColonneRepository colonneRepository;
    private final ColonneMapper colonneMapper;
    private final TableauRepository tableauRepository;
    private final TableauMapper tableauMapper;

    public ColonneServiceImpl(ColonneRepository colonneRepository, ColonneMapper colonneMapper, TableauRepository tableauRepository, TableauMapper tableauMapper) {
        this.colonneRepository = colonneRepository;
        this.colonneMapper = colonneMapper;
        this.tableauRepository = tableauRepository;
        this.tableauMapper = tableauMapper;
    }

    @Override
    public ColonneDto saveColonne(ColonneDto colonneDto) {
        var colonne = colonneMapper.toEntity(colonneDto);
        var savedColonne = colonneRepository.save(colonne);
        return colonneMapper.toDto(savedColonne);
    }

    @Override
    @Transactional(readOnly = true)
    public ColonneDto getColonneById(Long colonneId) {
        var colonne = colonneRepository.findById(colonneId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("La colonne avec l'ID %d n'existe pas", colonneId)));
        return colonneMapper.toDto(colonne);
    }

    @Override
    public boolean deleteColonne(Long colonneId) {
        var colonne = colonneRepository.findById(colonneId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("La colonne avec l'ID %d n'existe pas", colonneId)));
        colonneRepository.deleteById(colonne.getClnId());
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ColonneDto> getAllColonnes() {
        return colonneRepository.findAll().stream()
                .map(colonneMapper::toDto)
                .toList();
    }

    @Override
    public ColonneDto updateColonne(Long colonneId, ColonneDto colonneDto) {
        var colonne = colonneRepository.findById(colonneId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("La colonne avec l'ID %d n'existe pas", colonneId)));

        if (colonneDto.getClnNom() != null) {
            colonne.setClnNom(colonneDto.getClnNom());
        }

        var savedColonne = colonneRepository.save(colonne);

        return colonneMapper.toDto(savedColonne);
    }

    @Override
    public TableauDto createColonne(Long tableauId, ColonneDto colonneDto) {
        var tableau = tableauRepository.findById(tableauId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Le tableau avec l'ID %d n'existe pas", tableauId)));

        Colonne colonne =  colonneMapper.toEntity(colonneDto);

        colonne.setTableau(tableau);
        tableau.getColonnes().add(colonne);

        var colonneCreated = colonneRepository.save(colonne);

        return tableauMapper.toDto(tableau);
    }

}
