package com.controllers;

import com.dtos.*;
import com.services.TableauService;
import com.services.impl.ColonneServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tableaux")
public class TableauController {

    private final TableauService tableauService;
    private final ColonneServiceImpl colonneService;

    public TableauController(TableauService tableauService, ColonneServiceImpl colonneService) {
        this.tableauService = tableauService;
        this.colonneService = colonneService;
    }

    @GetMapping
    public DisplayResponseDto<List<TableauDto>> getTableaux() {
        DisplayResponseDto<List<TableauDto>> displayResponseDto = new DisplayResponseDto<>();

        displayResponseDto.setMessage("success");
        displayResponseDto.setType("collection");
        displayResponseDto.setData(tableauService.getAllTableaux());

        return displayResponseDto;
    }

    @GetMapping("/{id}")
    public DisplayResponseDto<TableauDto> getTableau(@PathVariable Long id){
        DisplayResponseDto<TableauDto> displayResponseDto = new DisplayResponseDto<>();

        displayResponseDto.setMessage("success");
        displayResponseDto.setType("item");
        displayResponseDto.setData(tableauService.getTableauById(id));

        return displayResponseDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DisplayResponseDto<TableauDto> saveTableau(final @RequestBody TableauDto tableauDto) {
        DisplayResponseDto<TableauDto> displayResponseDto = new DisplayResponseDto<>();

        displayResponseDto.setMessage("success");
        displayResponseDto.setType("item");
        displayResponseDto.setData(tableauService.saveTableau(tableauDto));

        return displayResponseDto;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public DisplayResponseDto<TableauDto> updateTableau(@PathVariable Long id, final @RequestBody TableauDto tableauDto) {
        DisplayResponseDto<TableauDto> displayResponseDto = new DisplayResponseDto<>();

        displayResponseDto.setMessage("success");
        displayResponseDto.setType("item");
        displayResponseDto.setData(tableauService.updateTableau(id, tableauDto));

        return displayResponseDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Boolean deleteTableau(@PathVariable Long id){
        return tableauService.deleteTableau(id);
    }

    @GetMapping("/{id}/colonnes")
    public DisplayResponseDto<List<ColonneDto>> getColonnesOneTableau(@PathVariable Long id) {
        DisplayResponseDto<List<ColonneDto>> displayResponseDto = new DisplayResponseDto<>();

        displayResponseDto.setMessage("success");
        displayResponseDto.setType("collection");
        displayResponseDto.setData(tableauService.getTableauById(id).getColonnes());

        return displayResponseDto;
    }

    @PostMapping("/{id}/colonnes")
    @ResponseStatus(HttpStatus.CREATED)
    public DisplayResponseDto<ColonneDto> saveColonne(@PathVariable Long id, @RequestBody ColonneDto colonneDto) {
        DisplayResponseDto<ColonneDto> displayResponseDto = new DisplayResponseDto<>();

        displayResponseDto.setMessage("success");
        displayResponseDto.setType("item");
        displayResponseDto.setData(colonneService.createColonne(id, colonneDto));

        return displayResponseDto;
    }

    @GetMapping("/{id}/participants")
    public DisplayResponseDto<List<CompteUserResponse>> getParticipants(@PathVariable Long id) {
        DisplayResponseDto<List<CompteUserResponse>> displayResponseDto = new DisplayResponseDto<>();

        displayResponseDto.setMessage("success");
        displayResponseDto.setType("item");
        displayResponseDto.setData(tableauService.getParticipants(id));

        return displayResponseDto;
    }

    @PatchMapping("/{id}/participants")
    @ResponseStatus(HttpStatus.CREATED)
    public DisplayResponseDto<List<CompteUserResponse>> setParticipants(@PathVariable Long id, @RequestBody ParticipantsDto participantsDto) {
        DisplayResponseDto<List<CompteUserResponse>> displayResponseDto = new DisplayResponseDto<>();

        displayResponseDto.setMessage("success");
        displayResponseDto.setType("item");
        displayResponseDto.setData(tableauService.setParticipants(id, participantsDto));

        return displayResponseDto;
    }

    @GetMapping("/{id}/createur")
    public DisplayResponseDto<CompteUserResponse> getCreateur(@PathVariable Long id) {
        DisplayResponseDto<CompteUserResponse> displayResponseDto = new DisplayResponseDto<>();

        displayResponseDto.setMessage("success");
        displayResponseDto.setType("item");
        displayResponseDto.setData(tableauService.getCreateur(id));

        return displayResponseDto;
    }
}
