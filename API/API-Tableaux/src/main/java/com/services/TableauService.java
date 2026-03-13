package com.services;

import com.dtos.TableauDto;

import java.util.List;

public interface TableauService {

    TableauDto saveTableau(TableauDto TableauDto);

    TableauDto getTableauById(Long TableauId);

    boolean deleteTableau(Long TableauId);

    List<TableauDto> getAllTableaux();

    TableauDto updateTableau(Long TableauId, TableauDto TableauDto);

    TableauDto createTableau(Long cptId, TableauDto tableauDto);
}
