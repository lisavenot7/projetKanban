package com.services;

import com.dtos.TacheDto;
import com.entities.Colonne;
import com.entities.Compte;
import com.entities.Tache;
import com.mappers.TableauMapper;
import com.mappers.TacheMapper;
import com.repositories.ColonneRepository;
import com.repositories.CompteRepository;
import com.repositories.TableauRepository;
import com.repositories.TacheRepository;
import com.services.impl.TacheServiceImpl;
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
class TacheServiceTest {

    @Mock private TacheRepository tacheRepository;
    @Mock private CompteRepository compteRepository;
    @Mock private ColonneRepository colonneRepository;
    @Mock private TacheMapper tacheMapper;
    @Mock private TableauMapper tableauMapper;
    @Mock private TableauRepository tableauRepository;

    @InjectMocks
    private TacheServiceImpl tacheService;

    // =========================
    // GET BY ID
    // =========================

    @Test
    void shouldReturnTacheById() {

        Tache tache = new Tache();
        TacheDto dto = new TacheDto();

        when(tacheRepository.findById(1L))
                .thenReturn(Optional.of(tache));

        when(tacheMapper.toDto(tache))
                .thenReturn(dto);

        var result = tacheService.getTacheById(1L);

        assertNotNull(result);
        verify(tacheRepository).findById(1L);
        verify(tacheMapper).toDto(tache);
    }

    @Test
    void shouldThrowWhenTacheNotFound() {

        when(tacheRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> tacheService.getTacheById(1L));
    }

    // =========================
    // GET ALL
    // =========================

    @Test
    void shouldReturnAllTaches() {

        when(tacheRepository.findAll())
                .thenReturn(List.of(new Tache(), new Tache()));

        when(tacheMapper.toDto(any()))
                .thenReturn(new TacheDto());

        var result = tacheService.getAllTaches();

        assertEquals(2, result.size());
        verify(tacheRepository).findAll();
        verify(tacheMapper, times(2)).toDto(any());
    }

    // =========================
    // DELETE
    // =========================

    @Test
    void shouldDeleteTache() {

        Tache tache = new Tache();
        tache.setTchId(1L);

        when(tacheRepository.findById(1L))
                .thenReturn(Optional.of(tache));

        boolean result = tacheService.deleteTache(1L);

        assertTrue(result);
        verify(tacheRepository).deleteById(1L);
    }

    @Test
    void shouldThrowWhenDeleteNotFound() {

        when(tacheRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> tacheService.deleteTache(1L));
    }

    // =========================
    // CREATE (via colonneId)
    // =========================

    @Test
    void shouldCreateTache() {

        Long colonneId = 1L;

        Colonne colonne = new Colonne();
        TacheDto dto = new TacheDto();
        Tache tache = new Tache();

        when(colonneRepository.findById(colonneId))
                .thenReturn(Optional.of(colonne));

        when(tacheMapper.toEntity(dto))
                .thenReturn(tache);

        when(tacheRepository.save(any(Tache.class)))
                .thenReturn(tache);

        when(tacheMapper.toDto(tache))
                .thenReturn(new TacheDto());

        var result = tacheService.createTache(colonneId, dto);

        assertNotNull(result);
        assertEquals(colonne, tache.getColonne());

        verify(colonneRepository).findById(colonneId);
        verify(tacheRepository).save(tache);
    }

    @Test
    void shouldThrowWhenCreateWithoutColonne() {

        TacheDto dto = new TacheDto();
        dto.setColonneId(null);

        assertThrows(ResponseStatusException.class,
                () -> tacheService.saveTache(dto));
    }

    // =========================
    // SAVE TACHE
    // =========================

    @Test
    void shouldSaveTacheWithColonneAndCompte() {

        TacheDto dto = new TacheDto();
        dto.setColonneId(1L);
        dto.setCompteId(2L);

        Tache tache = new Tache();
        Colonne colonne = new Colonne();
        Compte compte = new Compte();

        when(tacheMapper.toEntity(dto))
                .thenReturn(tache);

        when(colonneRepository.findById(1L))
                .thenReturn(Optional.of(colonne));

        when(compteRepository.findById(2L))
                .thenReturn(Optional.of(compte));

        when(tacheRepository.save(any()))
                .thenReturn(tache);

        when(tacheMapper.toDto(tache))
                .thenReturn(new TacheDto());

        var result = tacheService.saveTache(dto);

        assertNotNull(result);
        assertEquals(colonne, tache.getColonne());
        assertEquals(compte, tache.getCompteAttribue());

        verify(tacheRepository).save(tache);
    }

    @Test
    void shouldThrowWhenSaveWithoutColonne() {

        TacheDto dto = new TacheDto();
        dto.setColonneId(null);

        assertThrows(ResponseStatusException.class,
                () -> tacheService.saveTache(dto));
    }

    @Test
    void shouldThrowWhenCompteNotFound() {

        TacheDto dto = new TacheDto();
        dto.setColonneId(1L);
        dto.setCompteId(2L);

        Tache tache = new Tache();

        // utilisé
        when(tacheMapper.toEntity(dto))
                .thenReturn(tache);

        when(compteRepository.findById(2L))
                .thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> tacheService.saveTache(dto));

        verify(compteRepository).findById(2L);
    }

    // =========================
    // UPDATE
    // =========================

    @Test
    void shouldUpdateTache() {

        Tache tache = new Tache();
        tache.setTchTitre("Ancien");

        TacheDto dto = new TacheDto();
        dto.setTchTitre("Nouveau");
        dto.setTchPriorite(1);

        when(tacheRepository.findById(1L))
                .thenReturn(Optional.of(tache));

        when(tacheRepository.save(any()))
                .thenReturn(tache);

        when(tacheMapper.toDto(tache))
                .thenReturn(new TacheDto());

        var result = tacheService.updateTache(1L, dto);

        assertNotNull(result);
        assertEquals("Nouveau", tache.getTchTitre());
        assertEquals(1, tache.getTchPriorite());

        verify(tacheRepository).save(tache);
    }
}