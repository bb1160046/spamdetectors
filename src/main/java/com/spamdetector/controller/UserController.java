package com.spamdetector.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spamdetector.entity.SpamReport;
import com.spamdetector.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
    private UserService userService;

    @PostMapping("/mark-spam")
    public ResponseEntity<?> markSpam(@RequestBody SpamReport dto, Principal principal) {
        userService.markSpam(dto, principal.getName());
        return ResponseEntity.ok("Marked as spam");
    }
}
