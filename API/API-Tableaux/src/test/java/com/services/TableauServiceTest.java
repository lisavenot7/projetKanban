package com.services;

import com.dtos.CompteUserResponse;
import com.dtos.ParticipantsDto;
import com.dtos.TableauDto;
import com.entities.Compte;
import com.entities.Tableau;
import com.mappers.CompteUserMapper;
import com.mappers.TableauMapper;
import com.repositories.ColonneRepository;
import com.repositories.CompteRepository;
import com.repositories.TableauRepository;
import com.services.impl.TableauServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TableauServiceTest {

    @Mock private TableauRepository tableauRepository;
    @Mock private CompteRepository compteRepository;
    @Mock private ColonneRepository colonneRepository;
    @Mock private ColonneService colonneService;
    @Mock private TableauMapper tableauMapper;
    @Mock private CompteUserMapper compteUserMapper;

    @InjectMocks
    private TableauServiceImpl tableauService;

    // =========================
    // GET BY ID
    // =========================

    @Test
    void shouldReturnTableauById() {

        Tableau tableau = new Tableau();
        tableau.setTabId(1L);

        TableauDto dto = new TableauDto();
        dto.setTabId(1L);

        when(tableauRepository.findById(1L))
                .thenReturn(Optional.of(tableau));

        when(tableauMapper.toDto(tableau))
                .thenReturn(dto);

        var result = tableauService.getTableauById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getTabId());

        verify(tableauRepository).findById(1L);
        verify(tableauMapper).toDto(tableau);
    }

    @Test
    void shouldThrowWhenTableauNotFound() {

        when(tableauRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> tableauService.getTableauById(1L));
    }

    // =========================
    // GET ALL
    // =========================

    @Test
    void shouldReturnAllTableaux() {

        Tableau t1 = new Tableau();
        Tableau t2 = new Tableau();

        when(tableauRepository.findAll())
                .thenReturn(List.of(t1, t2));

        when(tableauMapper.toDto(any(Tableau.class)))
                .thenReturn(new TableauDto());

        var result = tableauService.getAllTableaux();

        assertEquals(2, result.size());

        verify(tableauRepository).findAll();
        verify(tableauMapper, times(2)).toDto(any(Tableau.class));
    }

    // =========================
    // DELETE
    // =========================

    @Test
    void shouldDeleteTableau() {

        Tableau tableau = new Tableau();
        tableau.setTabId(1L);

        when(tableauRepository.findById(1L))
                .thenReturn(Optional.of(tableau));

        boolean result = tableauService.deleteTableau(1L);

        assertTrue(result);

        verify(tableauRepository).deleteById(1L);
    }

    @Test
    void shouldThrowWhenDeleteTableauNotFound() {

        when(tableauRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> tableauService.deleteTableau(1L));
    }

    // =========================
    // CREATE TABLEAU
    // =========================

    @Test
    void shouldCreateTableau() {

        Compte compte = new Compte();
        compte.setTableauxCrees(new ArrayList<>());

        Tableau tableau = new Tableau();
        TableauDto dto = new TableauDto();

        when(compteRepository.findById(1L))
                .thenReturn(Optional.of(compte));

        when(tableauMapper.toEntity(dto))
                .thenReturn(tableau);

        when(tableauRepository.save(any(Tableau.class)))
                .thenReturn(tableau);

        when(tableauMapper.toDto(tableau))
                .thenReturn(new TableauDto());

        var result = tableauService.createTableau(1L, dto);

        assertNotNull(result);

        verify(tableauRepository).save(tableau);
        verify(compteRepository).save(compte);
        assertTrue(compte.getTableauxCrees().contains(tableau));
    }

    // =========================
    // UPDATE
    // =========================

    @Test
    void shouldUpdateTableau() {

        Tableau tableau = new Tableau();
        tableau.setTabNom("Ancien nom");

        TableauDto dto = new TableauDto();
        dto.setTabNom("Nouveau nom");

        when(tableauRepository.findById(1L))
                .thenReturn(Optional.of(tableau));

        when(tableauRepository.save(any(Tableau.class)))
                .thenReturn(tableau);

        when(tableauMapper.toDto(any(Tableau.class)))
                .thenReturn(new TableauDto());

        var result = tableauService.updateTableau(1L, dto);

        assertNotNull(result);
        assertEquals("Nouveau nom", tableau.getTabNom());

        verify(tableauRepository).save(tableau);
    }

    // =========================
    // GET PARTICIPANTS
    // =========================

    @Test
    void shouldReturnParticipants() {

        Tableau tableau = new Tableau();

        Compte c1 = new Compte();
        c1.setCptId(1L);

        Compte c2 = new Compte();
        c2.setCptId(2L);

        tableau.setParticipants(Set.of(c1, c2));

        when(tableauRepository.findById(1L))
                .thenReturn(Optional.of(tableau));

        when(compteUserMapper.toDto(any(Compte.class)))
                .thenReturn(new CompteUserResponse());

        var result = tableauService.getParticipants(1L);

        assertEquals(2, result.size());

        verify(compteUserMapper, times(2))
                .toDto(any(Compte.class));
    }

    // =========================
    // SET PARTICIPANTS
    // =========================

    @Test
    void shouldSetParticipants() {

        Tableau tableau = new Tableau();
        tableau.setParticipants(new HashSet<>());

        ParticipantsDto dto = new ParticipantsDto();
        dto.setIds(Set.of(1L, 2L));

        Compte c1 = new Compte();
        c1.setCptId(1L);
        c1.setParticipations(new HashSet<>());

        Compte c2 = new Compte();
        c2.setCptId(2L);
        c2.setParticipations(new HashSet<>());

        when(tableauRepository.findById(1L))
                .thenReturn(Optional.of(tableau));

        when(compteRepository.findAllById(dto.getIds()))
                .thenReturn(List.of(c1, c2));

        var result = tableauService.setParticipants(1L, dto);

        assertNotNull(result);

        verify(compteRepository).findAllById(dto.getIds());
    }

    // =========================
    // CREATEUR
    // =========================

    @Test
    void shouldReturnCreateur() {

        Tableau tableau = new Tableau();
        Compte createur = new Compte();
        tableau.setCreateur(createur);

        when(tableauRepository.findById(1L))
                .thenReturn(Optional.of(tableau));

        when(compteUserMapper.toDto(createur))
                .thenReturn(new CompteUserResponse());

        var result = tableauService.getCreateur(1L);

        assertNotNull(result);

        verify(compteUserMapper).toDto(createur);
    }
}