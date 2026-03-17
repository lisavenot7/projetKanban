package com.dtos;

public record CreateCompteUserDto(
        String email,
        String password,
        String prenom,
        String nom,
        Integer isAdmin
        ) {
}