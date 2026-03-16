package com.controllers;

import com.dtos.CompteDto;
import com.dtos.CompteUserResponse;
import com.dtos.DisplayResponseDto;
import com.dtos.ModifCompteDto;
import com.entities.Compte;
import com.mappers.CompteUserMapper;
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
    private final CompteUserMapper compteUserMapper;

    public CompteController(CompteService compteService, CompteUserMapper compteUserMapper) {
        this.compteService = compteService;
        this.compteUserMapper = compteUserMapper;
    }

    // ENDPOINT POUR LE COMPTE COURANT
    @GetMapping("/me")
    public Compte getCurrentCompte() {
        return (Compte) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    // GET ALL
    @GetMapping
    public DisplayResponseDto<List<CompteUserResponse>> getComptes() {
        DisplayResponseDto<List<CompteUserResponse>> displayResponseDto = new DisplayResponseDto<>();
        displayResponseDto.setMessage("success");
        displayResponseDto.setType("collection");
        displayResponseDto.setData(compteService.getAllComptes());
        return displayResponseDto;
    }

    // GET BY ID
    @GetMapping("/{id}")
    public DisplayResponseDto<CompteUserResponse> getCompte(@PathVariable Long id) {
        DisplayResponseDto<CompteUserResponse> displayResponseDto = new DisplayResponseDto<>();
        displayResponseDto.setMessage("success");
        displayResponseDto.setType("item");
        CompteUserResponse compteUserResponse = compteService.getCompteById(id);
        displayResponseDto.setData(compteUserResponse);
        return displayResponseDto;
    }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DisplayResponseDto<Compte> saveCompte(@RequestBody CompteDto compteDto) {
        DisplayResponseDto<Compte> displayResponseDto = new DisplayResponseDto<>();
        displayResponseDto.setMessage("success");
        displayResponseDto.setType("item");
        displayResponseDto.setData(compteService.saveCompte(compteDto));
        return displayResponseDto;
    }

    // UPDATE
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public DisplayResponseDto<CompteUserResponse> updateCompte(@PathVariable Long id, @RequestBody ModifCompteDto modifcompteDto) {
        DisplayResponseDto<CompteUserResponse> displayResponseDto = new DisplayResponseDto<>();
        displayResponseDto.setMessage("success");
        displayResponseDto.setType("item");
        displayResponseDto.setData(compteService.updateCompte(id, modifcompteDto));
        return displayResponseDto;
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Boolean deleteCompte(@PathVariable Long id) {

        return compteService.deleteCompte(id);
    }
}