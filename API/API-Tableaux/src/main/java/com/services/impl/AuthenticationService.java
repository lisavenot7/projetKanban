package com.services.impl;

import com.dtos.LoginUserDto;
import com.dtos.RegisterUserDto;
import com.entities.Compte;
import com.entities.User;
import com.repositories.CompteRepository;
import com.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service de gestion de l'authentification.
 * Ce service gère :
 * <ul>
 *     <li>L'inscription des nouveaux utilisateurs</li>
 *     <li>L'authentification des utilisateurs</li>
 *     <li>Le hachage des mots de passe</li>
 * </ul>
 */
@Service
public class AuthenticationService {

    private final CompteRepository compteRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    /**
     * Constructeur du service d'authentification.
     *
     * @param compteRepository Le repository de gestion des utilisateurs
     * @param passwordEncoder L'encodeur de mots de passe
     * @param authenticationManager Le gestionnaire d'authentification
     */
    public AuthenticationService(CompteRepository compteRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.compteRepository = compteRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Inscrit un nouvel utilisateur.
     * Cette méthode :
     * <ul>
     *     <li>Crée un nouvel utilisateur</li>
     *     <li>Encode le mot de passe</li>
     *     <li>Sauvegarde l'utilisateur</li>
     * </ul>
     *
     * @param input Les informations d'inscription
     * @return L'utilisateur créé
     */
    public Compte signup(RegisterUserDto input) {

        Compte compte = new Compte();
        compte.setCptMail(input.email());
        compte.setCptMdp(passwordEncoder.encode(input.password()));

        return compteRepository.save(compte);
    }

    /**
     * Authentifie un utilisateur.
     * Cette méthode :
     * <ul>
     *     <li>Vérifie les identifiants de connexion</li>
     *     <li>Authentifie l'utilisateur via le gestionnaire d'authentification</li>
     *     <li>Récupère les détails de l'utilisateur</li>
     * </ul>
     *
     * @param input Les informations de connexion
     * @return Les détails de l'utilisateur authentifié
     * @throws RuntimeException Si l'authentification échoue ou si l'utilisateur n'est pas trouvé
     */
    public UserDetails authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.email(),
                        input.password()
                )
        );

        return compteRepository.findByCptMail(input.email())
                .orElseThrow();
    }
}