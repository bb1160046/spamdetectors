package com.spamdetector.dto;


import lombok.Data;

@Data
public class PersonDetailsDto {
    private String name;
    private String phoneNumber;
    private double spamLikelihood;
    private String email; // may be null if not visible
}
