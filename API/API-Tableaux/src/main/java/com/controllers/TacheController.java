package com.controllers;

import com.dtos.DisplayResponseDto;
import com.dtos.TacheDto;
import com.services.TacheService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/taches")
public class TacheController {

    private final TacheService tacheService;

    public TacheController(TacheService tacheService) {
        this.tacheService = tacheService;
    }

    @GetMapping
    public DisplayResponseDto<List<TacheDto>> getTaches() {
        DisplayResponseDto<List<TacheDto>> response = new DisplayResponseDto<>();
        response.setMessage("success");
        response.setType("collection");
        response.setData(tacheService.getAllTaches());
        return response;
    }

    @GetMapping("/{id}")
    public DisplayResponseDto<TacheDto> getTache(@PathVariable Long id) {
        DisplayResponseDto<TacheDto> response = new DisplayResponseDto<>();
        response.setMessage("success");
        response.setType("item");
        response.setData(tacheService.getTacheById(id));
        return response;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DisplayResponseDto<TacheDto> createTache(@RequestBody TacheDto tacheDto) {
        DisplayResponseDto<TacheDto> response = new DisplayResponseDto<>();
        response.setMessage("success");
        response.setType("item");
        response.setData(tacheService.saveTache(tacheDto));
        return response;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public DisplayResponseDto<TacheDto> updateTache(@PathVariable Long id, @RequestBody TacheDto tacheDto) {
        DisplayResponseDto<TacheDto> response = new DisplayResponseDto<>();
        response.setMessage("success");
        response.setType("item");
        response.setData(tacheService.updateTache(id, tacheDto));
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Boolean deleteTache(@PathVariable Long id) {
        return tacheService.deleteTache(id);
    }
}