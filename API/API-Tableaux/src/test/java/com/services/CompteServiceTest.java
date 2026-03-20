package com.services;

import com.dtos.CompteUserResponse;
import com.dtos.CreateCompteUserDto;
import com.dtos.ModifCompteDto;
import com.entities.Compte;
import com.entities.User;
import com.mappers.CompteMapper;
import com.mappers.CompteUserMapper;
import com.repositories.CompteRepository;
import com.repositories.TableauRepository;
import com.repositories.UserRepository;
import com.services.impl.CompteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompteServiceTest {

    @Mock private CompteRepository compteRepository;
    @Mock private UserRepository userRepository;
    @Mock private TableauRepository tableauRepository;
    @Mock private CompteMapper compteMapper;
    @Mock private TableauService tableauService;
    @Mock private CompteUserMapper compteUserMapper;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CompteServiceImpl compteService;

    // =========================
    // CREATE COMPTE
    // =========================

    @Test
    void shouldCreateCompte() {

        CreateCompteUserDto dto =
                new CreateCompteUserDto("mail@test.com", "password", "Jean", "Dupont", 1);

        when(passwordEncoder.encode("password"))
                .thenReturn("encodedPassword");

        when(compteUserMapper.toDto(any()))
                .thenReturn(new CompteUserResponse());

        ArgumentCaptor<Compte> captor = ArgumentCaptor.forClass(Compte.class);

        compteService.createCompte(dto);

        verify(compteRepository).save(captor.capture());

        Compte savedCompte = captor.getValue();

        assertEquals("mail@test.com", savedCompte.getCptMail());
        assertEquals("encodedPassword", savedCompte.getCptMdp());
        assertEquals(1, savedCompte.getCptIsAdmin());
        assertEquals(1, savedCompte.getCptIsActive());

        assertNotNull(savedCompte.getUser());
        assertEquals("Jean", savedCompte.getUser().getPrenom());
        assertEquals("Dupont", savedCompte.getUser().getNom());

        verify(passwordEncoder).encode("password");
        verify(userRepository).save(any());
    }

    // =========================
    // GET BY ID
    // =========================

    @Test
    void shouldReturnCompteById() {

        Compte compte = new Compte();
        CompteUserResponse dto = new CompteUserResponse();

        when(compteRepository.findById(1L))
                .thenReturn(Optional.of(compte));

        when(compteUserMapper.toDto(compte))
                .thenReturn(dto);

        var result = compteService.getCompteById(1L);

        assertNotNull(result);
        assertEquals(dto, result);

        verify(compteRepository).findById(1L);
        verify(compteUserMapper).toDto(compte);
    }

    @Test
    void shouldThrowWhenCompteNotFound() {

        when(compteRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> compteService.getCompteById(1L));
    }

    // =========================
    // GET ALL
    // =========================

    @Test
    void shouldReturnAllComptes() {

        // Given
        Compte compte1 = new Compte();
        compte1.setCptId(1L);

        Compte compte2 = new Compte();
        compte2.setCptId(2L);

        when(compteRepository.findAll())
                .thenReturn(List.of(compte1, compte2));

        CompteUserResponse dto1 = new CompteUserResponse();
        CompteUserResponse dto2 = new CompteUserResponse();

        when(compteUserMapper.toDto(compte1)).thenReturn(dto1);
        when(compteUserMapper.toDto(compte2)).thenReturn(dto2);

        // When
        var result = compteService.getAllComptes();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));

        verify(compteRepository).findAll();

        // 🔥 correction ici
        verify(compteUserMapper, times(2)).toDto(any());

        verifyNoMoreInteractions(compteRepository, compteUserMapper);
    }

    @Test
    void shouldReturnEmptyListWhenNoComptes() {

        when(compteRepository.findAll())
                .thenReturn(List.of());

        var result = compteService.getAllComptes();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // =========================
    // UPDATE
    // =========================

    @Test
    void shouldUpdateCompte() {

        Compte compte = new Compte();
        User user = new User();
        compte.setUser(user);

        when(compteRepository.findById(1L))
                .thenReturn(Optional.of(compte));

        when(compteRepository.save(any()))
                .thenReturn(compte);

        ModifCompteDto dto = new ModifCompteDto();
        dto.setCptMail("new@mail.com");
        dto.setCptMdp("newpass");
        dto.setCptIsAdmin(1);
        dto.setCptIsActive(1);
        dto.setNom("Dupont");
        dto.setPrenom("Jean");

        when(passwordEncoder.encode("newpass"))
                .thenReturn("encoded");

        when(compteUserMapper.toDto(any()))
                .thenReturn(new CompteUserResponse());

        var result = compteService.updateCompte(1L, dto);

        assertNotNull(result);

        assertEquals("new@mail.com", compte.getCptMail());
        assertEquals("encoded", compte.getCptMdp());
        assertEquals(1, compte.getCptIsAdmin());
        assertEquals(1, compte.getCptIsActive());

        assertEquals("Dupont", compte.getUser().getNom());
        assertEquals("Jean", compte.getUser().getPrenom());

        verify(passwordEncoder).encode("newpass");
        verify(compteRepository).save(compte);
    }

    // =========================
    // DELETE
    // =========================

    @Test
    void shouldDeleteCompte() {

        Compte compte = new Compte();
        User user = new User();

        user.setUsrId(10L);
        compte.setUser(user);
        compte.setCptId(1L); // 🔥 important

        when(compteRepository.findById(1L))
                .thenReturn(Optional.of(compte));

        boolean result = compteService.deleteCompte(1L);

        assertTrue(result);

        verify(userRepository).deleteById(10L);
        verify(compteRepository).deleteById(1L);
    }

    @Test
    void shouldThrowWhenDeletingNonExistingCompte() {

        when(compteRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> compteService.deleteCompte(1L));
    }
}