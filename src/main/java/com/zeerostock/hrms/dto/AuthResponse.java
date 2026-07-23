package com.zeerostock.hrms.dto;

import com.zeerostock.hrms.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private User user;
}