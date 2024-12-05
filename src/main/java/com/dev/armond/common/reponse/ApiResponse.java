package com.dev.armond.common.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse <T> {
    private String status;
    private String message;
    private T data;
    private String error;

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>("success", message, data, null);
    }
    public static <T> ApiResponse<T> fail(String message, String error) {
        return new ApiResponse<>("fail", message, null, error);
    }
    public static <T> ApiResponse<T> error(String message, String error) {
        return new ApiResponse<>("error", message, null, error);
    }
}
