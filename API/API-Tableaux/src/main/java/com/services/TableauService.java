package com.services;

import com.dtos.CompteUserResponse;
import com.dtos.TableauDto;
import com.dtos.ParticipantsDto;

import java.util.List;

public interface TableauService {

    TableauDto saveTableau(TableauDto TableauDto);

    TableauDto getTableauById(Long TableauId);

    boolean deleteTableau(Long TableauId);

    List<TableauDto> getAllTableaux();

    TableauDto updateTableau(Long TableauId, TableauDto TableauDto);

    TableauDto createTableau(Long cptId, TableauDto tableauDto);

    List<TableauDto> getTableauxByCompte(Long id);

    List<TableauDto> getTableauxCreesByCompte(Long id);

    List<TableauDto> getTableauxParticipesByCompte(Long id);

    List<CompteUserResponse> getParticipants(Long id);

    List<CompteUserResponse> setParticipants(Long id, ParticipantsDto participantsDto);

    CompteUserResponse getCreateur(Long id);
}
