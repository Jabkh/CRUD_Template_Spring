package org.example.crud_template.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseResponseDto<T> {
    private String message;
    private T token;

    public BaseResponseDto(String message) {
        this.message = message;
    }
}
