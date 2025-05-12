package com.duxsoftware.dux_prueba_tecnica.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duxsoftware.dux_prueba_tecnica.DTO.EquipoRequest;
import com.duxsoftware.dux_prueba_tecnica.models.Equipo;
import com.duxsoftware.dux_prueba_tecnica.repositories.EquipoRepository;


@Service
public class EquipoService {
    @Autowired EquipoRepository equipoRepository;

    @Transactional(readOnly = true)
    public List<Equipo> findAll(){
        return equipoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Equipo> findById(Long id){
        return equipoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Equipo> findByNombreContaining(String nombre){
        return equipoRepository.findByNombreContaining(nombre);
    }

    @Transactional
    public Equipo createEquipo(EquipoRequest equipoRequest){
        if(equipoRequest.getNombre() == null || equipoRequest.getLiga() == null || equipoRequest.getPais() == null){
            throw new IllegalArgumentException("Los datos del equipo son invalidos");
        }

        Equipo equipoExistente = equipoRepository.findByNombreAndLigaAndPais(
                equipoRequest.getNombre(),
                equipoRequest.getLiga(),
                equipoRequest.getPais()
        );

        if (equipoExistente != null) {
            throw new IllegalArgumentException("Los datos del equipo son invalidos");
        }

        Equipo nuevoEquipo = new Equipo( equipoRequest.getNombre(), equipoRequest.getLiga(), equipoRequest.getPais() );
        return equipoRepository.save(nuevoEquipo);
    }

    @Transactional
    public Equipo updateEquipo(Long id, EquipoRequest equipoRequest){
        Equipo equipoExistente = equipoRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        equipoExistente.setNombre(equipoRequest.getNombre());
        equipoExistente.setLiga(equipoRequest.getLiga());
        equipoExistente.setPais(equipoRequest.getPais());

        return equipoRepository.save(equipoExistente);
    }

    @Transactional
    public void deleteEquipo(Long id){
        Equipo equipoExistente = equipoRepository.findById(id).orElseThrow(NoSuchElementException::new);
        equipoRepository.delete(equipoExistente);
    }
}
