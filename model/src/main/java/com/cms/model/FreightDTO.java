package com.cms.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class FreightDTO {
    private Long id;
    private String origin;
    private String destination;
    private String customer;
    private String status;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private String carrier;
    private String trackingNumber;
}
