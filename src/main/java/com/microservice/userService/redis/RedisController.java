package com.microservice.userService.redis;

import com.microservice.userService.response.Response;
import com.microservice.userService.response.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisController {
    private final RedisService redisService;

    @GetMapping("/get")
    public Response getData(@RequestParam String key) {
        Object value = redisService.getFromCache(key);
        if (value == null) {
            return ResponseBuilder.getSuccessResponse(HttpStatus.NOT_FOUND,"Key not found",null);
        }
        return ResponseBuilder.getSuccessResponse(HttpStatus.FOUND,"Key found",value);
    }

    @GetMapping("/getKeys")
    public Response getKeys() {
        Set<String> value = redisService.getAllBlacklistedKeys();
        if (value.isEmpty()) {
            return ResponseBuilder.getSuccessResponse(HttpStatus.NOT_FOUND,"Key not found",null);
        }
        return ResponseBuilder.getSuccessResponse(HttpStatus.FOUND,"Key found",value);
    }

    @DeleteMapping("/delete")
    public Response deleteData(@RequestParam String key) {
        redisService.removeFromCache(key);
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK,"Key deleted from Redis",null);
    }

    @GetMapping("/flushDb")
    public Response flushDb() {
        redisService.flushDB();
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK,"Flushed",null);
    }
}
