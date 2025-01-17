package com.henrique.springsecurity.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}
