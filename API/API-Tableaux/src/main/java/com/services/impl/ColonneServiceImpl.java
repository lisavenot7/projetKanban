package com.services.impl;

import com.dtos.ColonneDto;
import com.dtos.TableauDto;
import com.entities.Colonne;
import com.entities.Tache;
import com.mappers.ColonneMapper;
import com.mappers.TableauMapper;
import com.mappers.TacheMapper;
import com.repositories.ColonneRepository;
import com.repositories.TableauRepository;
import com.repositories.TacheRepository;
import com.services.ColonneService;
import com.services.TacheService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service("ColonneService")
@Transactional
public class ColonneServiceImpl implements ColonneService {

    private final ColonneRepository colonneRepository;
    private final ColonneMapper colonneMapper;
    private final TableauRepository tableauRepository;
    private final TableauMapper tableauMapper;
    private final TacheRepository tacheRepository;

    private final TacheService tacheService;

    public ColonneServiceImpl(ColonneRepository colonneRepository, ColonneMapper colonneMapper, TableauRepository tableauRepository, TableauMapper tableauMapper, TacheRepository tacheRepository, TacheService tacheService) {
        this.colonneRepository = colonneRepository;
        this.colonneMapper = colonneMapper;
        this.tableauRepository = tableauRepository;
        this.tableauMapper = tableauMapper;
        this.tacheRepository = tacheRepository;
        this.tacheService = tacheService;
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
    public ColonneDto createColonne(Long tableauId, ColonneDto colonneDto) {

        var tableau = tableauRepository.findById(tableauId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Le tableau avec l'ID %d n'existe pas", tableauId)));

        Colonne colonne = colonneMapper.toEntity(colonneDto);

        colonne.setTableau(tableau);

        // 🔥 1. save colonne pour avoir ID
        Colonne colonneSaved = colonneRepository.save(colonne);

        // 🔥 2. gérer les tâches
        if (colonneDto.getTaches() != null) {
            colonneDto.getTaches().forEach(tacheDto -> {

                if (tacheDto.getTchId() != null) {

                    Optional<Tache> tacheBDD = tacheRepository.findById(tacheDto.getTchId());

                    if (tacheBDD.isPresent()) {
                        tacheService.updateTache(tacheDto.getTchId(), tacheDto);
                        return;
                    }
                }

                // ✅ BON ID ici
                tacheService.createTache(colonneSaved.getClnId(), tacheDto);
            });
        }

        return colonneMapper.toDto(colonneSaved);
    }

}
