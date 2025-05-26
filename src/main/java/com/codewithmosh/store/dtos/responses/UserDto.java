package com.codewithmosh.store.dtos.responses;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;

}