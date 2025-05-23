package com.duxsoftware.dux_prueba_tecnica.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.duxsoftware.dux_prueba_tecnica.DTO.EquipoRequest;
import com.duxsoftware.dux_prueba_tecnica.models.Equipo;
import com.duxsoftware.dux_prueba_tecnica.services.EquipoService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/equipos")
@PreAuthorize("hasRole('USER')")
@SecurityRequirement(name = "bearerAuth") // Requiere autenticación
public class EquipoController {

        @Autowired
        private EquipoService equipoService;

        @GetMapping
        public List<Equipo> list(){
            return equipoService.findAll();
        }

        @GetMapping(path = "/{id}")
        public ResponseEntity<?> findById(@PathVariable("id") Long id){
            Optional<Equipo> equipoOptional = equipoService.findById(id);
            if(equipoOptional.isPresent()){
                return ResponseEntity.ok(equipoOptional.orElseThrow());
            }
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("mensaje", "Equipo no encontrado");
            body.put("codigo", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        }

    @GetMapping(path = "/buscar")
    public ResponseEntity<?> findByNombre(@RequestParam("nombre") String nombre) {
        List<Equipo> equiposEncontrados = equipoService.findByNombreContaining(nombre);

        if (equiposEncontrados.isEmpty()) {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("mensaje", "Equipo no encontrado");
            body.put("codigo", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        }

        return ResponseEntity.ok(equiposEncontrados);
    }

        @PostMapping
        public ResponseEntity<?> createEquipo(@Valid @RequestBody EquipoRequest equipoRequest, BindingResult result){
            try{
                if(result.hasErrors()){
                    throw new IllegalArgumentException();
                }
                Equipo nuevoEquipo = equipoService.createEquipo(equipoRequest);
                return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEquipo);
            }catch(IllegalArgumentException error){
                Map<String, Object> body = new LinkedHashMap<>();
                body.put("mensaje", "La solicitud es invalida");
                body.put("codigo", HttpStatus.NOT_FOUND.value());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
            }
        }

        @PutMapping( path = "/{id}" )
        public ResponseEntity<?> updateEquipo(@PathVariable("id") Long id, @RequestBody EquipoRequest equipoRequest){
            try{
                Equipo equipoActualizado = equipoService.updateEquipo(id, equipoRequest);
                return ResponseEntity.status(HttpStatus.OK).body(equipoActualizado);
            }catch(NoSuchElementException e){
                Map<String, Object> body= new LinkedHashMap<>();
                body.put("mensaje", "Equipo no encontrado");
                body.put("codigo", HttpStatus.NOT_FOUND.value());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
            }
        }

        @DeleteMapping( path = "/{id}")
        public ResponseEntity<?> deleteEquipo(@PathVariable Long id){
            try{
                equipoService.deleteEquipo(id);
                return ResponseEntity.noContent().build();
            }catch(NoSuchElementException e){
                Map<String, Object> body = new LinkedHashMap<>();
                body.put("mensaje", "Equipo no encontrado");
                body.put("codigo", HttpStatus.NOT_FOUND.value());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
            }
        }
    }