package com.services;

import com.dtos.LoginUserDto;
import com.dtos.RegisterUserDto;
import com.entities.Compte;
import com.repositories.CompteRepository;
import com.repositories.UserRepository;
import com.services.impl.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private CompteRepository compteRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    // =========================
    // TEST SIGNUP
    // =========================

    @Test
    void shouldSignupUserSuccessfully() {

        // Given
        RegisterUserDto dto = new RegisterUserDto(
                "test@mail.com",
                "password",
                "Jean",
                "Dupont"
        );

        when(passwordEncoder.encode("password"))
                .thenReturn("encodedPassword");

        when(compteRepository.save(any(Compte.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Compte result = authenticationService.signup(dto);

        // Then
        assertNotNull(result);
        assertEquals("test@mail.com", result.getCptMail());
        assertEquals("encodedPassword", result.getCptMdp());
        assertEquals(1, result.getCptIsActive());

        verify(passwordEncoder).encode("password");
        verify(userRepository).save(any());
        verify(compteRepository).save(any(Compte.class));
    }

    // =========================
    // TEST AUTHENTICATE SUCCESS
    // =========================

    @Test
    void shouldAuthenticateUserSuccessfully() {

        // Given
        LoginUserDto dto = new LoginUserDto(
                "test@mail.com",
                "password"
        );

        Compte compte = new Compte();
        compte.setCptMail("test@mail.com");

        when(compteRepository.findByCptMail("test@mail.com"))
                .thenReturn(Optional.of(compte));

        // When
        UserDetails result = authenticationService.authenticate(dto);

        // Then
        assertNotNull(result);

        verify(authenticationManager).authenticate(
                any(UsernamePasswordAuthenticationToken.class)
        );

        verify(compteRepository).findByCptMail("test@mail.com");
    }

    // =========================
    // TEST AUTHENTICATE FAILURE
    // =========================

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        // Given
        LoginUserDto dto = new LoginUserDto(
                "unknown@mail.com",
                "password"
        );

        when(compteRepository.findByCptMail("unknown@mail.com"))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(Exception.class,
                () -> authenticationService.authenticate(dto));

        verify(authenticationManager).authenticate(
                any(UsernamePasswordAuthenticationToken.class)
        );

        verify(compteRepository).findByCptMail("unknown@mail.com");
    }

    @Test
    void shouldCreateActiveAccount() {

        // Given
        RegisterUserDto dto = new RegisterUserDto(
                "active@mail.com",
                "password",
                "Jean",
                "Dupont"
        );

        when(passwordEncoder.encode("password"))
                .thenReturn("encodedPassword");

        when(compteRepository.save(any(Compte.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Compte result = authenticationService.signup(dto);

        // Then
        assertEquals(1, result.getCptIsActive());
        assertEquals("encodedPassword", result.getCptMdp());

        verify(compteRepository).save(any(Compte.class));
    }


}