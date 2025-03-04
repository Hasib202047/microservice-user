package com.microservice.userService.response;

import org.springframework.http.HttpStatus;

import java.util.Date;

public final class ResponseBuilder {
    private ResponseBuilder() {
    }

    public static Response getSuccessResponse(HttpStatus status, String message, Object content) {
        return Response.builder()
                .message(message)
                .status(status.getReasonPhrase())
                .content(content)
                .statusCode(status.value())
                .timestamp(new Date().getTime()).build();
    }

//    public static Response getSuccessResponse(HttpStatus status, String message, long numberOfElement, long rowCount, Object content) {
//        return Response.builder()
//                .message(message)
//                .status(status.getReasonPhrase())
//                .content(content)
//                .numberOfElement(numberOfElement)
//                .rowCount(rowCount)
//                .statusCode(status.value())
//                .timestamp(new Date().getTime()).build();
//    }
}
