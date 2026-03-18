package com.controllers;

import com.dtos.ActionDto;
import com.dtos.CommentaireDto;
import com.dtos.DisplayResponseDto;
import com.dtos.TacheDto;
import com.services.ActionService;
import com.services.CommentaireService;
import com.services.TacheService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actions")
public class ActionController {

    private final ActionService actionService;

    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    @GetMapping
    public DisplayResponseDto<List<ActionDto>> getActions() {
        DisplayResponseDto<List<ActionDto>> response = new DisplayResponseDto<>();
        response.setMessage("success");
        response.setType("collection");
        response.setData(actionService.getAllActions());
        return response;
    }

    @GetMapping("/{id}")
    public DisplayResponseDto<ActionDto> getAction(@PathVariable Long id) {
        DisplayResponseDto<ActionDto> response = new DisplayResponseDto<>();
        response.setMessage("success");
        response.setType("item");
        response.setData(actionService.getActionById(id));
        return response;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DisplayResponseDto<ActionDto> saveAction(@RequestBody ActionDto actionDto) {
        DisplayResponseDto<ActionDto> response = new DisplayResponseDto<>();
        response.setMessage("success");
        response.setType("item");
        response.setData(actionService.saveAction(actionDto));
        return response;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public DisplayResponseDto<ActionDto> updateAction(@PathVariable Long id, @RequestBody ActionDto actionDto) {
        DisplayResponseDto<ActionDto> response = new DisplayResponseDto<>();
        response.setMessage("success");
        response.setType("item");
        response.setData(actionService.updateAction(id, actionDto));
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Boolean deleteAction(@PathVariable Long id) {
        return actionService.deleteAction(id);
    }

}