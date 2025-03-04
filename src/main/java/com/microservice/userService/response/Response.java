package com.microservice.userService.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response implements Serializable {
    @JsonInclude()
    private long timestamp;
    @JsonInclude()
    private int statusCode;
    @JsonInclude()
    private String status;
    @JsonInclude()
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object content;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private long numberOfElement;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private long rowCount;
}
