package com.microservice.userService.user;

import com.microservice.userService.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;

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


}
