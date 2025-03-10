package com.microservice.userService.user;

import com.microservice.userService.jwt.JwtUtil;
import com.microservice.userService.jwt.TokenBlacklistService;
import com.microservice.userService.response.Response;
import com.microservice.userService.response.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;
    private final TokenBlacklistService tokenBlacklistService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public Response login(@RequestBody Map<String,String> user) throws Exception {
        return userService.login(user);
    }

    @PostMapping("/signUp")
    public Response signUp(@Validated @RequestBody UserDto userDto) {
        return userService.RegisterUser(userDto);
    }
    @GetMapping("/get-by-user-name/{userName}")
    public Response getByUserName(@PathVariable("userName") String userName)
    {
        return userService.getByUserName(userName);
    }

    @PostMapping("/logout")
    public Response logout(@RequestHeader("Authorization") String token) {
        String jwt = token.substring(7); // Remove "Bearer " prefix
        long expiry = jwtUtil.getJwtExpiry(jwt); // Extract JWT expiry time
        tokenBlacklistService.blacklistToken(jwt, expiry);
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK,"Logged out successfully",null);
    }

}
