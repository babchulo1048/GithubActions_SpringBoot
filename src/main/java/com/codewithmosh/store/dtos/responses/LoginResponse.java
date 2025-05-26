package com.codewithmosh.store.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginResponse {
    private String accessToken;
//    private Jwt refreshToken;
}
