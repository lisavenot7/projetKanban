package com.controllers;

import com.dtos.ColonneDto;
import com.dtos.DisplayResponseDto;
import com.dtos.ColonneDto;
import com.services.ColonneService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colonnes")
public class ColonneController {

    private final ColonneService colonneService;

    public ColonneController(ColonneService colonneService) {
        this.colonneService = colonneService;
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
}
