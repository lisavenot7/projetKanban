package com.services;

import com.dtos.ActionDto;
import com.entities.Action;
import com.entities.Compte;
import com.entities.Tache;
import com.mappers.ActionMapper;
import com.repositories.ActionRepository;
import com.repositories.CompteRepository;
import com.repositories.TacheRepository;
import com.services.impl.ActionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class ActionServiceTest {

    @Mock
    private ActionRepository actionRepository;

    @Mock
    private CompteRepository compteRepository;

    @Mock
    private TacheRepository tacheRepository;

    @Mock
    private ActionMapper actionMapper;

    @InjectMocks
    private ActionServiceImpl actionService;

    private Action action;
    private ActionDto actionDto;

    @BeforeEach
    void setUp() {
        action = new Action();
        action.setId(1L);
        action.setDescription("Test");

        actionDto = new ActionDto();
        actionDto.setId(1L);
        actionDto.setDescription("Test");
        actionDto.setAuteur(1L);
        actionDto.setTacheModifiee(1L);
    }

    // =========================
    // TEST CREATE
    // =========================
    @Test
    void saveAction_shouldReturnDto() {

        when(actionMapper.toEntity(actionDto)).thenReturn(action);
        when(actionRepository.save(action)).thenReturn(action);
        when(actionMapper.toDto(action)).thenReturn(actionDto);

        ActionDto result = actionService.saveAction(actionDto);

        assertNotNull(result);
        verify(actionRepository, times(1)).save(action);
    }

    // =========================
    // TEST GET BY ID
    // =========================
    @Test
    void getActionById_shouldReturnDto() {

        when(actionRepository.findById(1L)).thenReturn(Optional.of(action));
        when(actionMapper.toDto(action)).thenReturn(actionDto);

        ActionDto result = actionService.getActionById(1L);

        assertNotNull(result);
        verify(actionRepository).findById(1L);
    }

    @Test
    void getActionById_shouldThrowException_whenNotFound() {

        when(actionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> actionService.getActionById(1L));
    }

    // =========================
    // TEST DELETE
    // =========================
    @Test
    void deleteAction_shouldDeleteSuccessfully() {

        when(actionRepository.findById(1L)).thenReturn(Optional.of(action));

        boolean result = actionService.deleteAction(1L);

        assertTrue(result);
        verify(actionRepository).delete(action);
    }

    // =========================
    // TEST UPDATE
    // =========================
    @Test
    void updateAction_shouldUpdateSuccessfully() {

        Compte compte = new Compte();
        compte.setCptId(1L);

        Tache tache = new Tache();
        tache.setTchId(1L);

        when(actionRepository.findById(1L)).thenReturn(Optional.of(action));
        when(compteRepository.findById(1L)).thenReturn(Optional.of(compte));
        when(tacheRepository.findById(1L)).thenReturn(Optional.of(tache));
        when(actionRepository.save(any(Action.class))).thenReturn(action);
        when(actionMapper.toDto(action)).thenReturn(actionDto);

        ActionDto result = actionService.updateAction(1L, actionDto);

        assertNotNull(result);
        verify(actionRepository).save(action);
    }
}