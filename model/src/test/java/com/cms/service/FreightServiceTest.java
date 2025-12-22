package com.cms.service;

import com.cms.mapper.FreightMapper;
import com.cms.model.Freight;
import com.cms.model.FreightDTO;
import com.cms.repository.FreightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FreightServiceTest {

    @Mock
    private FreightRepository freightRepository;

    @Spy
    private FreightMapper freightMapper = FreightMapper.INSTANCE;

    @InjectMocks
    private FreightService freightService;

    private Freight freight;
    private FreightDTO freightDTO;

    @BeforeEach
    void setUp() {
        freight = new Freight();
        freight.setId(1L);
        freight.setOrigin("Test Origin");
        freight.setDestination("Test Destination");
        freight.setStatus("IN_TRANSIT");
        freight.setCustomer("Test Customer");

        freightDTO = new FreightDTO();
        freightDTO.setId(1L);
        freightDTO.setOrigin("Test Origin");
        freightDTO.setDestination("Test Destination");
        freightDTO.setStatus("IN_TRANSIT");
        freightDTO.setCustomer("Test Customer");
    }

    @Test
    void listAll() {
        when(freightRepository.findAllByOrderByDepartureDateDesc()).thenReturn(List.of(freight));
        List<FreightDTO> result = freightService.listAll();
        assertEquals(1, result.size());
        assertEquals(freightDTO.getOrigin(), result.get(0).getOrigin());
        verify(freightRepository).findAllByOrderByDepartureDateDesc();
    }

    @Test
    void listAllEntities() {
        when(freightRepository.findAllByOrderByDepartureDateDesc()).thenReturn(List.of(freight));
        List<Freight> result = freightService.listAllEntities();
        assertEquals(1, result.size());
        assertEquals(freight.getOrigin(), result.get(0).getOrigin());
        verify(freightRepository).findAllByOrderByDepartureDateDesc();
    }

    @Test
    void listByStatus() {
        when(freightRepository.findByStatusOrderByDepartureDateDesc("IN_TRANSIT")).thenReturn(List.of(freight));
        List<FreightDTO> result = freightService.listByStatus("IN_TRANSIT");
        assertEquals(1, result.size());
        assertEquals("IN_TRANSIT", result.get(0).getStatus());
        verify(freightRepository).findByStatusOrderByDepartureDateDesc("IN_TRANSIT");
    }

    @Test
    void getById() {
        when(freightRepository.findById(1L)).thenReturn(Optional.of(freight));
        FreightDTO result = freightService.get(1L);
        assertNotNull(result);
        assertEquals(freight.getId(), result.getId());
        verify(freightRepository).findById(1L);
    }

    @Test
    void getById_NotFound() {
        when(freightRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> freightService.get(1L));
    }

    @Test
    void getEntityById_NotFound() {
        when(freightRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> freightService.getEntity(1L));
    }

    @Test
    void createDto() {
        when(freightRepository.save(any(Freight.class))).thenReturn(freight);
        FreightDTO result = freightService.create(freightDTO);
        assertNotNull(result);
        assertEquals(freightDTO.getId(), result.getId());
        verify(freightRepository).save(any(Freight.class));
    }

    @Test
    void createEntity() {
        when(freightRepository.save(any(Freight.class))).thenReturn(freight);
        Freight result = freightService.create(freight);
        assertNotNull(result);
        assertEquals(freight.getId(), result.getId());
        verify(freightRepository).save(any(Freight.class));
    }

    @Test
    void update() {
        when(freightRepository.findById(1L)).thenReturn(Optional.of(freight));
        when(freightRepository.save(any(Freight.class))).thenReturn(freight);

        FreightDTO updatedDto = new FreightDTO();
        updatedDto.setOrigin("Updated Origin");
        updatedDto.setDestination("Updated Destination");

        FreightDTO result = freightService.update(1L, updatedDto);

        assertNotNull(result);
        assertEquals("Updated Origin", result.getOrigin());
        verify(freightRepository).findById(1L);
        verify(freightRepository).save(any(Freight.class));
    }
    
    @Test
    void update_NotFound() {
        when(freightRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> freightService.update(1L, freightDTO));
    }

    @Test
    void transition() {
        when(freightRepository.findById(1L)).thenReturn(Optional.of(freight));
        when(freightRepository.save(any(Freight.class))).thenReturn(freight);

        FreightDTO result = freightService.transition(1L, "DELIVERED", "Notes");

        assertNotNull(result);
        assertEquals("DELIVERED", result.getStatus());
        verify(freightRepository).findById(1L);
        verify(freightRepository).save(any(Freight.class));
    }
    
    @Test
    void transition_NotFound() {
        when(freightRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> freightService.transition(1L, "DELIVERED", "notes"));
    }

    @Test
    void transition_withNullNotes() {
        when(freightRepository.findById(1L)).thenReturn(Optional.of(freight));
        when(freightRepository.save(any(Freight.class))).thenReturn(freight);

        FreightDTO result = freightService.transition(1L, "DELIVERED", null);

        assertNotNull(result);
        assertEquals("DELIVERED", result.getStatus());
        verify(freightRepository).findById(1L);
        verify(freightRepository).save(any(Freight.class));
    }

    @Test
    void delete() {
        doNothing().when(freightRepository).deleteById(1L);
        freightService.delete(1L);
        verify(freightRepository, times(1)).deleteById(1L);
    }
}

