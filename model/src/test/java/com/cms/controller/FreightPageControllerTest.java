package com.cms.controller;

import com.cms.model.Freight;
import com.cms.service.FreightService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FreightPageController.class)
public class FreightPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FreightService freightService;

    @Test
    void listFreights() throws Exception {
        Freight freight = new Freight();
        freight.setId(1L);
        freight.setOrigin("Test Origin");
        freight.setDestination("Test Destination");
        freight.setCustomer("Test Customer");
        freight.setStatus("IN_TRANSIT");

        when(freightService.listAllEntities()).thenReturn(List.of(freight));

        mockMvc.perform(get("/freights"))
                .andExpect(status().isOk())
                .andExpect(view().name("freights"))
                .andExpect(model().attributeExists("freights"));
    }

    @Test
    void showNewFreightForm() throws Exception {
        mockMvc.perform(get("/freights/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("freight-form"))
                .andExpect(model().attributeExists("freight"));
    }

    @Test
    void listFreightsWithFilters() throws Exception {
        Freight f1 = new Freight();
        f1.setId(1L);
        f1.setOrigin("Origin 1");
        f1.setDestination("Destination 1");
        f1.setCustomer("Customer 1");
        f1.setStatus("IN_TRANSIT");

        Freight f2 = new Freight();
        f2.setId(2L);
        f2.setOrigin("Origin 2");
        f2.setDestination("Destination 2");
        f2.setCustomer("Customer 2");
        f2.setStatus("DELIVERED");

        when(freightService.listAllEntities()).thenReturn(List.of(f1, f2));

        mockMvc.perform(get("/freights").param("status", "IN_TRANSIT"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("freights", hasSize(1)));

        mockMvc.perform(get("/freights").param("q", "Customer 1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("freights", hasSize(1)));
    }

    @Test
    void listFreightsWithNoFilters() throws Exception {
        Freight f1 = new Freight();
        f1.setId(1L);
        f1.setOrigin("Origin 1");
        f1.setDestination("Destination 1");
        f1.setCustomer("Customer 1");
        f1.setStatus("IN_TRANSIT");

        when(freightService.listAllEntities()).thenReturn(List.of(f1));

        mockMvc.perform(get("/freights"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("freights", hasSize(1)));
    }

    @Test
    void listFreightsWithAllFilters() throws Exception {
        Freight f1 = new Freight();
        f1.setId(1L);
        f1.setOrigin("Origin 1");
        f1.setDestination("Destination 1");
        f1.setCustomer("Customer 1");
        f1.setStatus("IN_TRANSIT");

        when(freightService.listAllEntities()).thenReturn(List.of(f1));

        mockMvc.perform(get("/freights")
                        .param("status", "IN_TRANSIT")
                        .param("q", "Customer"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("freights", hasSize(1)));
    }

    @Test
    void createFreight() throws Exception {
        Freight freight = new Freight();
        freight.setId(1L);
        freight.setOrigin("New Origin");
        freight.setDestination("New Destination");
        freight.setCustomer("New Customer");
        freight.setStatus("IN_TRANSIT");

        when(freightService.create(any(Freight.class))).thenReturn(freight);

        mockMvc.perform(post("/freights")
                        .param("origin", "New Origin")
                        .param("destination", "New Destination")
                        .param("customer", "New Customer")
                        .param("status", "IN_TRANSIT"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/freights"));
    }

    @Test
    void createFreight_withErrors() throws Exception {
        mockMvc.perform(post("/freights"))
                .andExpect(status().isOk())
                .andExpect(view().name("freight-form"));
    }

    @Test
    void showFreightDetail() throws Exception {
        Freight freight = new Freight();
        freight.setId(1L);
        freight.setOrigin("Test Origin");

        when(freightService.getEntity(1L)).thenReturn(freight);

        mockMvc.perform(get("/freights/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("freight-detail"))
                .andExpect(model().attributeExists("freight"));
    }

    @Test
    void transitionFreightStatus() throws Exception {
        mockMvc.perform(post("/freights/1/status")
                        .param("status", "DELIVERED"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/freights/1"));

        verify(freightService, times(1)).transition(1L, "DELIVERED", null);
    }

    @Test
    void deleteFreight() throws Exception {
        mockMvc.perform(post("/freights/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/freights"));

        verify(freightService, times(1)).delete(1L);
    }
}
