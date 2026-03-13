package com.controllers;

import com.dtos.LoginResponse;
import com.dtos.LoginUserDto;
import com.dtos.RegisterUserDto;
import com.entities.Compte;
import com.entities.User;
import com.security.JwtService;
import com.services.impl.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    /**
     * Constructeur du contrôleur d'authentification.
     *
     * @param jwtService Le service de gestion des JWT
     * @param authenticationService Le service d'authentification
     */
    public AuthController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    /**
     * Endpoint d'inscription d'un nouvel utilisateur.
     * Cette méthode :
     * <ul>
     *     <li>Reçoit les informations d'inscription</li>
     *     <li>Crée un nouvel utilisateur</li>
     *     <li>Retourne l'utilisateur créé</li>
     * </ul>
     *
     * @param registerUserDto Les informations d'inscription
     * @return L'utilisateur créé
     */
    @PostMapping("/signup")
    public ResponseEntity<Compte> register(@RequestBody RegisterUserDto registerUserDto) {
        Compte registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    /**
     * Endpoint de connexion utilisateur.
     * Cette méthode :
     * <ul>
     *     <li>Authentifie l'utilisateur</li>
     *     <li>Génère un token JWT</li>
     *     <li>Retourne le token et sa durée de validité</li>
     * </ul>
     *
     * @param loginUserDto Les informations de connexion
     * @return Le token JWT et sa durée de validité
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {

        var authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}