package com.crowdzero.crowdzero_sever.populationApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "결과 조회에 성공하였습니다.", data);
    }

    public static <T> ApiResponse<T> successWithoutData() {
        return new ApiResponse<>(200, "결과 조회에 성공하였습니다.", null);
    }

    public static <T> ApiResponse<T> badRequest(String message) {
        return new ApiResponse<>(400, message, null);
    }

    public static <T> ApiResponse<T> internalServerError() {
        return new ApiResponse<>(500, "서버 내부 오류가 발생하였습니다.", null);
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }
}
