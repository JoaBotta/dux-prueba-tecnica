package com.duxsoftware.dux_prueba_tecnica.DTO;

public class TokenResponse { // DTO para la respuesta del token
    private String token;

    public TokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
