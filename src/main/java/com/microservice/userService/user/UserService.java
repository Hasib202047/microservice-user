package com.microservice.userService.user;


import com.microservice.userService.exception.AlreadyExistException;
import com.microservice.userService.exception.InvalidException;
import com.microservice.userService.exception.NotFoundException;
import com.microservice.userService.jwt.JwtUtil;
import com.microservice.userService.response.Response;
import com.microservice.userService.response.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public Response RegisterUser(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername());
        if (user != null) {
            throw new AlreadyExistException("User already exist with this username");
        }
        String hashedPassword = passwordEncoder.encode(userDto.getPassword());

        return ResponseBuilder.getSuccessResponse(HttpStatus.OK,"Signup Successful",userRepository.save(User.builder()
                .username(userDto.getUsername())
                .password(hashedPassword)
                .roles(userDto.getRoles())
                .name(userDto.getName())
                .build()));
    }

    public Response login(Map<String,String> userCredential) throws Exception {
        User user = userRepository.findByUsername(userCredential.get("userName"));
        if (user == null) {
            throw new NotFoundException("User not found with this username");
        }
        try {
            System.out.println("authenticating user");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCredential.get("userName"),userCredential.get("password")));
            System.out.println("authentication complete");
        }catch (BadCredentialsException e)
        {
            throw new InvalidException("Invalid password");
        }catch (Exception e) {
            throw new Exception("Authentication error: "+e.getMessage());
        }
        System.out.println("token :"+jwtUtil.generateToken(user));
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK,"Login Successful",jwtUtil.generateToken(user));
    }
    public Response getByUserName(String userName)
    {
        User user = userRepository.findByUsername(userName);
        if (user == null) {
            throw new NotFoundException("User not found with this username");
        }
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK,"User found!",user);
    }

}
