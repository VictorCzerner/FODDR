package com.czerner.foddr.adaptadores.apresentação.Requests;

public record RegisterUserRequest(
    String email,
    String password
) {}
