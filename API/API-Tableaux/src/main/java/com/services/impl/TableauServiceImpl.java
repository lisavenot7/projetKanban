package com.services.impl;

import com.dtos.TableauDto;
import com.mappers.TableauMapper;
import com.repositories.TableauRepository;
import com.services.TableauService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service("TableauService")
@Transactional
public class TableauServiceImpl implements TableauService {

    private final TableauRepository tableauRepository;
    private final TableauMapper tableauMapper;

    public TableauServiceImpl(TableauRepository tableauRepository, TableauMapper tableauMapper) {
        this.tableauRepository = tableauRepository;
        this.tableauMapper = tableauMapper;
    }

    @Override
    public TableauDto saveTableau(TableauDto tableauDto) {
        var tableau = tableauMapper.toEntity(tableauDto);
        var savedTableau = tableauRepository.save(tableau);
        return tableauMapper.toDto(savedTableau);
    }

    @Override
    @Transactional(readOnly = true)
    public TableauDto getTableauById(Long tableauId) {
        var tableau = tableauRepository.findById(tableauId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("La tableau avec l'ID %d n'existe pas", tableauId)));
        return tableauMapper.toDto(tableau);
    }

    @Override
    public boolean deleteTableau(Long tableauId) {
        var tableau = tableauRepository.findById(tableauId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("La tableau avec l'ID %d n'existe pas", tableauId)));
        tableauRepository.deleteById(tableau.getTabId());
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TableauDto> getAllTableaux() {
        return tableauRepository.findAll().stream()
                .map(tableauMapper::toDto)
                .toList();
    }

    @Override
    public TableauDto updateTableau(Long tableauId, TableauDto tableauDto) {
        var tableau = tableauRepository.findById(tableauId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("La tableau avec l'ID %d n'existe pas", tableauId)));

        var modifiedTableau = tableauMapper.toEntity(tableauDto);


        var savedTableau = tableauRepository.save(tableau);

        return tableauMapper.toDto(savedTableau);
    }
}
