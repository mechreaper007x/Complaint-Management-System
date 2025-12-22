package com.cms.controller;

import com.cms.model.FreightDTO;
import com.cms.service.FreightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@WebMvcTest(FreightRestController.class)
public class FreightRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FreightService freightService;

    @Test
    public void whenListFreights_thenReturnFreightsList() throws Exception {
        FreightDTO freight = new FreightDTO();
        freight.setId(1L);
        freight.setOrigin("Test Origin");

        when(freightService.listAll()).thenReturn(List.of(freight));

        mockMvc.perform(get("/api/freights"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].origin", is("Test Origin")));
    }

    @Test
    public void whenGetFreight_thenReturnFreight() throws Exception {
        FreightDTO freight = new FreightDTO();
        freight.setId(1L);
        freight.setOrigin("Test Origin");

        when(freightService.get(1L)).thenReturn(freight);

        mockMvc.perform(get("/api/freights/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.origin", is("Test Origin")));
    }

    @Test
    public void whenCreateFreight_thenReturnCreatedFreight() throws Exception {
        FreightDTO freight = new FreightDTO();
        freight.setId(1L);
        freight.setOrigin("New Origin");

        when(freightService.create(any(FreightDTO.class))).thenReturn(freight);

        mockMvc.perform(post("/api/freights")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(freight)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.origin", is("New Origin")));
    }

    @Test
    public void whenUpdateFreight_thenReturnUpdatedFreight() throws Exception {
        FreightDTO freight = new FreightDTO();
        freight.setId(1L);
        freight.setOrigin("Updated Origin");

        when(freightService.update(eq(1L), any(FreightDTO.class))).thenReturn(freight);

        mockMvc.perform(put("/api/freights/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(freight)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.origin", is("Updated Origin")));
    }

    @Test
    public void whenUpdateStatus_thenReturnUpdatedFreight() throws Exception {
        FreightDTO freight = new FreightDTO();
        freight.setId(1L);
        freight.setStatus("DELIVERED");

        when(freightService.transition(1L, "DELIVERED", "notes")).thenReturn(freight);

        mockMvc.perform(patch("/api/freights/1/status")
                        .param("status", "DELIVERED")
                        .param("notes", "notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("DELIVERED")));
    }

    @Test
    public void whenDeleteFreight_thenStatusOk() throws Exception {
        doNothing().when(freightService).delete(1L);
        mockMvc.perform(delete("/api/freights/1"))
                .andExpect(status().isOk());
    }
}
