package com.dtos;

import lombok.Data;

@Data
public class DisplayResponseDto<T> {
    private String message;
    private String type;
    private T data;
}
