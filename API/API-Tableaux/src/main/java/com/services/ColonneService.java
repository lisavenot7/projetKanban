package com.services;

import com.dtos.ColonneDto;
import com.dtos.TableauDto;

import java.util.List;

public interface ColonneService {

    ColonneDto saveColonne(ColonneDto ColonneDto);

    ColonneDto getColonneById(Long ColonneId);

    boolean deleteColonne(Long ColonneId);

    List<ColonneDto> getAllColonnes();

    ColonneDto updateColonne(Long ColonneId, ColonneDto ColonneDto);

    TableauDto createColonne(Long id, ColonneDto colonneDto);
}
