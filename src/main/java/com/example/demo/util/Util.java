package com.example.demo.util;

import com.example.demo.dto.BaseResponse;

public class Util {

    public static <T> BaseResponse<T> generateSuccessResponse(T object) {
        return new BaseResponse<>("success", object, null);
    }

    public static BaseResponse<String> generateErrorResponse(String error) {
        return new BaseResponse<>("error", null, error);
    }

}
