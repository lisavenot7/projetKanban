package com.controllers;

import com.dtos.*;
import com.entities.Compte;
import com.mappers.CompteUserMapper;
import com.services.CompteService;
import com.services.TableauService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comptes")
public class CompteController {

    private final CompteService compteService;
    private final TableauService tableauService;
    private final CompteUserMapper compteUserMapper;

    public CompteController(CompteService compteService, TableauService tableauService, CompteUserMapper compteUserMapper) {
        this.compteService = compteService;
        this.tableauService = tableauService;
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
    public DisplayResponseDto<CompteUserResponse> saveCompte(@RequestBody CreateCompteUserDto createCompteUserDto) {
        DisplayResponseDto<CompteUserResponse> displayResponseDto = new DisplayResponseDto<>();
        displayResponseDto.setMessage("success");
        displayResponseDto.setType("item");
        displayResponseDto.setData(compteService.createCompte(createCompteUserDto));
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

    @PostMapping("/{id}/tableaux")
    @ResponseStatus(HttpStatus.CREATED)
    public DisplayResponseDto<TableauDto> saveTableau(@PathVariable Long id, @RequestBody TableauDto tableauDto) {
        DisplayResponseDto<TableauDto> displayResponseDto = new DisplayResponseDto<>();

        displayResponseDto.setMessage("success");
        displayResponseDto.setType("item");
        displayResponseDto.setData(tableauService.createTableau(id, tableauDto));

        return displayResponseDto;
    }
}