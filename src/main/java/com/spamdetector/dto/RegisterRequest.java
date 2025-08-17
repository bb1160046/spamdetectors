package com.spamdetector.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String phoneNumber;
    private String password;
    private String email; // optional
}
