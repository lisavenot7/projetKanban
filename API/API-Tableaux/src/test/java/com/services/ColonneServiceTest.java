package com.services;

import com.dtos.ColonneDto;
import com.entities.Colonne;
import com.entities.Tableau;
import com.mappers.ColonneMapper;
import com.mappers.TableauMapper;
import com.repositories.ColonneRepository;
import com.repositories.TableauRepository;
import com.repositories.TacheRepository;
import com.services.impl.ColonneServiceImpl;
import com.services.TacheService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ColonneServiceTest {

    @Mock private ColonneRepository colonneRepository;
    @Mock private ColonneMapper colonneMapper;
    @Mock private TableauRepository tableauRepository;
    @Mock private TableauMapper tableauMapper;
    @Mock private TacheRepository tacheRepository;
    @Mock private TacheService tacheService;

    @InjectMocks
    private ColonneServiceImpl colonneService;

    // =========================
    // GET BY ID
    // =========================

    @Test
    void shouldReturnColonneById() {

        Colonne colonne = new Colonne();
        ColonneDto dto = new ColonneDto();

        when(colonneRepository.findById(1L))
                .thenReturn(Optional.of(colonne));

        when(colonneMapper.toDto(colonne))
                .thenReturn(dto);

        var result = colonneService.getColonneById(1L);

        assertNotNull(result);
        assertEquals(dto, result);

        verify(colonneRepository).findById(1L);
        verify(colonneMapper).toDto(colonne);
    }

    @Test
    void shouldThrowWhenColonneNotFound() {

        when(colonneRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> colonneService.getColonneById(1L));
    }

    // =========================
    // GET ALL
    // =========================

    @Test
    void shouldReturnAllColonnes() {

        Colonne c1 = new Colonne();
        Colonne c2 = new Colonne();

        when(colonneRepository.findAll())
                .thenReturn(List.of(c1, c2));

        when(colonneMapper.toDto(any()))
                .thenReturn(new ColonneDto());

        var result = colonneService.getAllColonnes();

        assertEquals(2, result.size());

        verify(colonneRepository).findAll();
        verify(colonneMapper, times(2)).toDto(any());
    }

    // =========================
    // CREATE
    // =========================

    @Test
    void shouldCreateColonne() {

        Long tableauId = 1L;

        Tableau tableau = new Tableau();
        ColonneDto dto = new ColonneDto();

        Colonne colonne = new Colonne();
        Colonne savedColonne = new Colonne();

        when(tableauRepository.findById(tableauId))
                .thenReturn(Optional.of(tableau));

        when(colonneMapper.toEntity(dto))
                .thenReturn(colonne);

        when(colonneRepository.save(any(Colonne.class)))
                .thenReturn(savedColonne);

        when(colonneMapper.toDto(savedColonne))
                .thenReturn(new ColonneDto());

        var result = colonneService.createColonne(tableauId, dto);

        assertNotNull(result);

        verify(tableauRepository).findById(tableauId);
        verify(colonneRepository).save(colonne);
        assertEquals(tableau, colonne.getTableau());
    }

    @Test
    void shouldThrowWhenTableauNotFoundOnCreate() {

        when(tableauRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> colonneService.createColonne(1L, new ColonneDto()));
    }

    // =========================
    // UPDATE
    // =========================

    @Test
    void shouldUpdateColonne() {

        Colonne colonne = new Colonne();
        colonne.setClnNom("Ancien nom");

        ColonneDto dto = new ColonneDto();
        dto.setClnNom("Nouveau nom");

        when(colonneRepository.findById(1L))
                .thenReturn(Optional.of(colonne));

        when(colonneRepository.save(any(Colonne.class)))
                .thenReturn(colonne);

        when(colonneMapper.toDto(colonne))
                .thenReturn(new ColonneDto());

        var result = colonneService.updateColonne(1L, dto);

        assertNotNull(result);
        assertEquals("Nouveau nom", colonne.getClnNom());

        verify(colonneRepository).save(colonne);
    }

    @Test
    void shouldThrowWhenUpdateColonneNotFound() {

        when(colonneRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> colonneService.updateColonne(1L, new ColonneDto()));
    }

    // =========================
    // DELETE
    // =========================

    @Test
    void shouldDeleteColonne() {

        Colonne colonne = new Colonne();
        colonne.setClnId(1L);

        when(colonneRepository.findById(1L))
                .thenReturn(Optional.of(colonne));

        boolean result = colonneService.deleteColonne(1L);

        assertTrue(result);

        verify(colonneRepository).deleteById(1L);
    }

    @Test
    void shouldThrowWhenDeleteNotFound() {

        when(colonneRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> colonneService.deleteColonne(1L));
    }
}