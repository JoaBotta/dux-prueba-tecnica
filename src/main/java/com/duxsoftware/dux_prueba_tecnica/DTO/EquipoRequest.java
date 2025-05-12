package com.duxsoftware.dux_prueba_tecnica.DTO;
import jakarta.validation.constraints.NotBlank;

public class EquipoRequest {
    @NotBlank
    private String nombre;
    @NotBlank
    private String liga;
    @NotBlank
    private String pais;

    public EquipoRequest() {}
    public EquipoRequest(String nombre, String liga, String pais) {
    this.nombre = nombre;
    this.liga = liga;
    this.pais = pais;
}
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLiga() {
        return liga;
    }

    public void setLiga(String liga) {
        this.liga = liga;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}