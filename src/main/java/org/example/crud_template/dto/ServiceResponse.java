package org.example.crud_template.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public ServiceResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ServiceResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

}