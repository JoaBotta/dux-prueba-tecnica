package com.duxsoftware.dux_prueba_tecnica.services;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.duxsoftware.dux_prueba_tecnica.DTO.EquipoRequest;
import com.duxsoftware.dux_prueba_tecnica.models.Equipo;
import com.duxsoftware.dux_prueba_tecnica.repositories.EquipoRepository;

public class EquipoServiceTest {

    private EquipoRepository equipoRepository;
    private EquipoService equipoService;

    @BeforeEach
    public void setUp() {
        equipoRepository = mock(EquipoRepository.class);
        equipoService = new EquipoService();
        equipoService.equipoRepository = equipoRepository; // inyectamos el mock
    }

    @Test
    public void testFindAll() {
        List<Equipo> lista = List.of(
                new Equipo("Boca", "Liga Argentina", "Argentina"),
                new Equipo("Barcelona", "La Liga", "España")
        );

        when(equipoRepository.findAll()).thenReturn(lista);

        List<Equipo> resultado = equipoService.findAll();

        assertEquals(2, resultado.size());
        verify(equipoRepository).findAll();
    }

    @Test
    public void testFindById_found() {
        Equipo equipo = new Equipo("River", "Liga Argentina", "Argentina");
        equipo.setId(1L);
        when(equipoRepository.findById(1L)).thenReturn(Optional.of(equipo));

        Optional<Equipo> resultado = equipoService.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("River", resultado.get().getNombre());
    }

    @Test
    public void testFindByNombreContaining() {
        List<Equipo> lista = List.of(new Equipo("Manchester United", "Premier", "Inglaterra"));
        when(equipoRepository.findByNombreContaining("Man")).thenReturn(lista);

        List<Equipo> resultado = equipoService.findByNombreContaining("Man");

        assertEquals(1, resultado.size());
        verify(equipoRepository).findByNombreContaining("Man");
    }

    @Test
    public void testCreateEquipo_success() {
        EquipoRequest request = new EquipoRequest("River", "Liga Argentina", "Argentina");

        when(equipoRepository.findByNombreAndLigaAndPais("River", "Liga Argentina", "Argentina")).thenReturn(null);

        Equipo esperado = new Equipo("River", "Liga Argentina", "Argentina");
        esperado.setId(1L);
        when(equipoRepository.save(any(Equipo.class))).thenReturn(esperado);

        Equipo resultado = equipoService.createEquipo(request);

        assertEquals("River", resultado.getNombre());
        verify(equipoRepository).save(any(Equipo.class));
    }

    @Test
    public void testCreateEquipo_invalidData() {
        EquipoRequest request = new EquipoRequest(); // campos nulos

        assertThrows(IllegalArgumentException.class, () -> equipoService.createEquipo(request));
        verify(equipoRepository, never()).save(any());
    }

    @Test
    public void testCreateEquipo_yaExiste() {
        EquipoRequest request = new EquipoRequest("River", "Liga Argentina", "Argentina");

        when(equipoRepository.findByNombreAndLigaAndPais("River", "Liga Argentina", "Argentina"))
                .thenReturn(new Equipo());

        assertThrows(IllegalArgumentException.class, () -> equipoService.createEquipo(request));
        verify(equipoRepository, never()).save(any());
    }

    @Test
    public void testUpdateEquipo_success() {
        EquipoRequest request = new EquipoRequest("River", "Liga Profesional", "Argentina");

        Equipo existente = new Equipo("River", "Liga A", "Argentina");
        existente.setId(1L);
        when(equipoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(equipoRepository.save(any(Equipo.class))).thenReturn(existente);

        Equipo actualizado = equipoService.updateEquipo(1L, request);

        assertEquals("Liga Profesional", actualizado.getLiga());
        verify(equipoRepository).save(existente);
    }

    @Test
    public void testUpdateEquipo_notFound() {
        EquipoRequest request = new EquipoRequest("River", "Liga", "Argentina");
        when(equipoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> equipoService.updateEquipo(1L, request));
    }

    @Test
    public void testDeleteEquipo_success() {
        Equipo existente = new Equipo("River", "Liga", "Argentina");
        existente.setId(1L);
        when(equipoRepository.findById(1L)).thenReturn(Optional.of(existente));

        equipoService.deleteEquipo(1L);

        verify(equipoRepository).delete(existente);
    }

    @Test
    public void testDeleteEquipo_notFound() {
        when(equipoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> equipoService.deleteEquipo(1L));
        verify(equipoRepository, never()).delete(any());
    }
}
