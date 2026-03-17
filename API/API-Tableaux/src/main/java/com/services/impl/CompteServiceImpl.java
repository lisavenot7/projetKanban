package com.services.impl;


import com.dtos.CompteDto;
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
import com.services.CompteService;
import com.services.TableauService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service("CompteService")
@Transactional
public class CompteServiceImpl implements CompteService {

    private final CompteRepository compteRepository;
    private final UserRepository userRepository;
    private final TableauRepository tableauRepository;
    private final CompteMapper compteMapper;

    private final TableauService tableauService;
    private final CompteUserMapper compteUserMapper;

    private final PasswordEncoder passwordEncoder;

    public CompteServiceImpl(CompteRepository compteRepository, UserRepository userRepository, TableauRepository tableauRepository, CompteMapper compteMapper, TableauService tableauService, CompteUserMapper compteUserMapper, PasswordEncoder passwordEncoder) {
        this.compteRepository = compteRepository;
        this.userRepository = userRepository;
        this.tableauRepository = tableauRepository;
        this.compteMapper = compteMapper;
        this.tableauService = tableauService;
        this.compteUserMapper = compteUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Compte saveCompte(CompteDto compteDto) {
        Compte compte = compteMapper.toEntity(compteDto);
        return compteRepository.save(compte);
    }

    @Override
    public CompteUserResponse createCompte(CreateCompteUserDto createCompteUserDto) {
        Compte compte = new Compte();
        compte.setCptMail(createCompteUserDto.email());
        compte.setCptMdp(passwordEncoder.encode(createCompteUserDto.password()));
        compte.setCptIsAdmin(createCompteUserDto.isAdmin().intValue());
        compte.setCptIsActive(1);

        User user = new User();
        user.setNom(createCompteUserDto.nom());
        user.setPrenom(createCompteUserDto.prenom());
        userRepository.save(user);

        compte.setUser(user);
        compteRepository.save(compte);

        return compteUserMapper.toDto(compte);
    }

    @Override
    @Transactional(readOnly = true)
    public CompteUserResponse getCompteById(Long compteId) {

        Compte compte = compteRepository.findById(compteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Le compte avec l'ID %d n'existe pas", compteId)));

        return compteUserMapper.toDto(compte);
    }

    @Override
    public boolean deleteCompte(Long compteId) {
        Compte compte = compteRepository.findById(compteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Le compte avec l'ID %d n'existe pas", compteId)));

        User user = compte.getUser();

        userRepository.deleteById(user.getUsrId());
        compteRepository.deleteById(compte.getCptId());
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompteUserResponse> getAllComptes() {
        List<Compte> comptes = compteRepository.findAll();

        return comptes.stream()
                .map(compteUserMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public CompteUserResponse updateCompte(Long compteId, ModifCompteDto dto) {

        Compte compte = compteRepository.findById(compteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Compte introuvable"));

        if (dto.getCptMail() != null) {
            compte.setCptMail(dto.getCptMail());
        }

        if (dto.getCptMdp() != null) {
            compte.setCptMdp(passwordEncoder.encode(dto.getCptMdp()));
        }

        if (dto.getCptIsAdmin() != null) {
            compte.setCptIsAdmin(dto.getCptIsAdmin());
        }

        if (dto.getCptIsActive() != null) {
            compte.setCptIsActive(dto.getCptIsActive());
        }

        if (compte.getUser() != null) {
            if (dto.getNom() != null) {
                compte.getUser().setNom(dto.getNom());
            }

            if (dto.getPrenom() != null) {
                compte.getUser().setPrenom(dto.getPrenom());
            }
        }

        return compteUserMapper.toDto(compteRepository.save(compte));
    }
}