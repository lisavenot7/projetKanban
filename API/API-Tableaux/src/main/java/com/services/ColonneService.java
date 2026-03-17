package com.services;

import com.dtos.ColonneDto;
import com.dtos.TableauDto;

import java.util.List;

public interface ColonneService {

    ColonneDto getColonneById(Long ColonneId);

    boolean deleteColonne(Long ColonneId);

    List<ColonneDto> getAllColonnes();

    ColonneDto updateColonne(Long ColonneId, ColonneDto ColonneDto);

    ColonneDto createColonne(Long id, ColonneDto colonneDto);
}
