package com.cms.controller;

import com.cms.model.Freight;
import com.cms.repository.FreightRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DashboardController.class)
public class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FreightRepository freightRepository;

    @Test
    public void whenHome_thenRedirectToDashboard() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"));
    }

    @Test
    public void whenDashboard_thenReturnDashboardView() throws Exception {
        Freight f1 = Freight.builder().status("IN_TRANSIT").customer("Customer A").build();
        Freight f2 = Freight.builder().status("IN_TRANSIT").customer("Customer B").build();
        Freight f3 = Freight.builder().status("DELIVERED").customer("Customer A").build();
        List<Freight> freights = List.of(f1, f2, f3);

        when(freightRepository.findAll()).thenReturn(freights);

        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andExpect(model().attributeExists("kpis", "statusCounts", "customerCounts", "pageTitle"));
    }

    @Test
    public void whenDashboardWithNoFreights_thenReturnDashboardView() throws Exception {
        when(freightRepository.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andExpect(model().attributeExists("kpis", "statusCounts", "customerCounts"));
    }
}
