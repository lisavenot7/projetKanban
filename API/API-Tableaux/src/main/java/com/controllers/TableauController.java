package com.controllers;

import com.dtos.DisplayResponseDto;
import com.dtos.TableauDto;
import com.services.TableauService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tableaux")
public class TableauController {

    private final TableauService tableauService;

    public TableauController(TableauService tableauService) {
        this.tableauService = tableauService;
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

}
