package com.controllers;

import com.dtos.*;
import com.dtos.ColonneDto;
import com.services.ColonneService;
import com.services.impl.TacheServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colonnes")
public class ColonneController {

    private final ColonneService colonneService;
    private final TacheServiceImpl tacheService;

    public ColonneController(ColonneService colonneService, TacheServiceImpl tacheService) {
        this.colonneService = colonneService;
        this.tacheService = tacheService;
    }

    @GetMapping
    public DisplayResponseDto<List<ColonneDto>> getColonnes() {
        DisplayResponseDto<List<ColonneDto>> displayResponseDto = new DisplayResponseDto<>();

        displayResponseDto.setMessage("success");
        displayResponseDto.setType("collection");
        displayResponseDto.setData(colonneService.getAllColonnes());

        return displayResponseDto;
    }

    @GetMapping("/{id}")
    public DisplayResponseDto<ColonneDto> getColonne(@PathVariable Long id){
        DisplayResponseDto<ColonneDto> displayResponseDto = new DisplayResponseDto<>();

        displayResponseDto.setMessage("success");
        displayResponseDto.setType("item");
        displayResponseDto.setData(colonneService.getColonneById(id));

        return displayResponseDto;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public DisplayResponseDto<ColonneDto> updateColonne(@PathVariable Long id, final @RequestBody ColonneDto colonneDto) {
        DisplayResponseDto<ColonneDto> displayResponseDto = new DisplayResponseDto<>();

        displayResponseDto.setMessage("success");
        displayResponseDto.setType("item");
        displayResponseDto.setData(colonneService.updateColonne(id, colonneDto));

        return displayResponseDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Boolean deleteColonne(@PathVariable Long id){
        return colonneService.deleteColonne(id);
    }

    @GetMapping("/{id}/taches")
    public DisplayResponseDto<List<TacheDto>> getTachesOneColonne(@PathVariable Long id) {
        DisplayResponseDto<List<TacheDto>> displayResponseDto = new DisplayResponseDto<>();

        displayResponseDto.setMessage("success");
        displayResponseDto.setType("collection");
        displayResponseDto.setData(colonneService.getColonneById(id).getTaches());

        return displayResponseDto;
    }

    @PostMapping("/{id}/taches")
    @ResponseStatus(HttpStatus.CREATED)
    public DisplayResponseDto<TableauDto> saveTache(@PathVariable Long id, final @RequestBody TacheDto tacheDto) {
        DisplayResponseDto<TableauDto> displayResponseDto = new DisplayResponseDto<>();

        displayResponseDto.setMessage("success");
        displayResponseDto.setType("item");
        displayResponseDto.setData(tacheService.createTache(id, tacheDto));

        return displayResponseDto;
    }
}
