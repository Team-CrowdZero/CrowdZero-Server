package com.crowdzero.crowdzero_sever.common;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

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
}
