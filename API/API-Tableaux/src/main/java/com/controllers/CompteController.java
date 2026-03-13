package com.controllers;

import com.dtos.CompteDto;
import com.dtos.DisplayResponseDto;
import com.entities.Compte;
import com.services.CompteService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comptes")
public class CompteController {

    private final CompteService compteService;

    public CompteController(CompteService compteService) {
        this.compteService = compteService;
    }

    // ENDPOINT POUR LE COMPTE COURANT
    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Compte getCurrentCompte() {
        return (Compte) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    // GET ALL
    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public DisplayResponseDto<List<CompteDto>> getComptes() {
        DisplayResponseDto<List<CompteDto>> displayResponseDto = new DisplayResponseDto<>();
        displayResponseDto.setMessage("success");
        displayResponseDto.setType("collection");
        displayResponseDto.setData(compteService.getAllComptes());
        return displayResponseDto;
    }

    // GET BY ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public DisplayResponseDto<CompteDto> getCompte(@PathVariable Long id) {
        DisplayResponseDto<CompteDto> displayResponseDto = new DisplayResponseDto<>();
        displayResponseDto.setMessage("success");
        displayResponseDto.setType("item");
        displayResponseDto.setData(compteService.getCompteById(id));
        return displayResponseDto;
    }

    // CREATE
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public DisplayResponseDto<CompteDto> saveCompte(@RequestBody CompteDto compteDto) {
        DisplayResponseDto<CompteDto> displayResponseDto = new DisplayResponseDto<>();
        displayResponseDto.setMessage("success");
        displayResponseDto.setType("item");
        displayResponseDto.setData(compteService.saveCompte(compteDto));
        return displayResponseDto;
    }

    // UPDATE
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public DisplayResponseDto<CompteDto> updateCompte(@PathVariable Long id, @RequestBody CompteDto compteDto) {
        DisplayResponseDto<CompteDto> displayResponseDto = new DisplayResponseDto<>();
        displayResponseDto.setMessage("success");
        displayResponseDto.setType("item");
        displayResponseDto.setData(compteService.updateCompte(id, compteDto));
        return displayResponseDto;
    }

    // DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Boolean deleteCompte(@PathVariable Long id) {
        return compteService.deleteCompte(id);
    }
}