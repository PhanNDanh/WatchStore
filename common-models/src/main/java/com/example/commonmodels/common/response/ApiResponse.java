package com.example.commonmodels.common.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private String code;

    private String message;

    private T result;

    public static <T> ApiResponse<T> success(T result) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(CodeResponse.SUCCESS);
        response.setMessage(MessageResponse.SUCCESS);
        response.setResult(result);
        return response;
    }

    public static <T> ApiResponse<T> success() {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(CodeResponse.SUCCESS);
        response.setMessage(MessageResponse.SUCCESS);
        return response;
    }

    public static <T> ApiResponse<T> error(T result) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(CodeResponse.ERROR);
        response.setMessage(MessageResponse.ERROR);
        response.setResult(result);
        return response;
    }
}
