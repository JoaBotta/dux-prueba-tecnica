package com.duxsoftware.dux_prueba_tecnica.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duxsoftware.dux_prueba_tecnica.models.Equipo;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    List<Equipo> findByNombreContaining(String nombre);

    Equipo findByNombreAndLigaAndPais(String nombre, String Liga, String Pais);
}