package com.michaelakamihe.ecommercebackend.controller;

import com.michaelakamihe.ecommercebackend.config.JwtUtil;
import com.michaelakamihe.ecommercebackend.model.User;
import com.michaelakamihe.ecommercebackend.model.UserDto;
import com.michaelakamihe.ecommercebackend.repo.UserRepository;
import com.michaelakamihe.ecommercebackend.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
    @Autowired
    private UserRepository repo;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @CrossOrigin
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<User> getCurrentUser (HttpServletRequest request)  {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = repo.findByUsername(((UserDetails) principal).getUsername());
        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser (@RequestBody Map<String, Object> user) throws Exception {
        User savedUser = new User();
        User newUser = new User(
                (String) user.get("username"),
                (String) user.get("password"),
                (String) user.get("email"),
                (String) user.get("name"),
                (String) user.get("address"),
                (String) user.get("phone")
        );

        if (newUser.getUsername() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is missing.");
        }

        if (newUser.getEmail() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is missing.");
        }

        if (newUser.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password is missing.");
        } else if (newUser.getPassword().length() < 8) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password length must be 8+.");
        }

        if (newUser.getName() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name is missing.");
        }

        if (newUser.getAddress() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Address is missing.");
        }

        if (newUser.getPhone() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phone is missing.");
        }

        try {
            savedUser = jwtUserDetailsService.save(newUser);
        } catch (DataIntegrityViolationException e) {
            if (e.getRootCause().getMessage().contains(newUser.getUsername())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is not available.");
            }

            if (e.getRootCause().getMessage().contains(newUser.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is not available.");
            }
        }

        Map<String, Object> tokenResponse = new HashMap<>();

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(savedUser.getUsername());
        final String token = jwtUtil.generateToken(userDetails);

        tokenResponse.put("token", token);
        return ResponseEntity.status(HttpStatus.CREATED).body(tokenResponse);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> authenticateUser (@RequestBody Map<String, String> user) throws Exception {
        authenticate(user.get("username"), user.get("password"));

        Map<String, Object> tokenResponse = new HashMap<>();
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(user.get("username"));
        final String token = jwtUtil.generateToken(userDetails);

        tokenResponse.put("token", token);
        return ResponseEntity.ok(tokenResponse);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("User disabled", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials", e);
        }
    }
}
