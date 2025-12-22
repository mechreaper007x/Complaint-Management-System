package com.cms.model;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Freight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String origin;
    @NotBlank
    private String destination;
    @NotBlank
    private String customer;
    private String status; // e.g., IN_TRANSIT, DELIVERED
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private String carrier;
    private String trackingNumber;
}
