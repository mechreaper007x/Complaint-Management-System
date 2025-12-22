package com.cms.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FreightDTOTest {

  private FreightDTO dto1;
  private FreightDTO dto2;

  @BeforeEach
  void setUp() {
    dto1 = new FreightDTO();
    dto1.setId(1L);
    dto1.setOrigin("Origin");
    dto1.setDestination("Destination");
    dto1.setCustomer("Customer");
    dto1.setStatus("IN_TRANSIT");
    dto1.setDepartureDate(LocalDate.now());
    dto1.setArrivalDate(LocalDate.now());
    dto1.setCarrier("Carrier");
    dto1.setTrackingNumber("12345");

    dto2 = new FreightDTO();
    dto2.setId(1L);
    dto2.setOrigin("Origin");
    dto2.setDestination("Destination");
    dto2.setCustomer("Customer");
    dto2.setStatus("IN_TRANSIT");
    dto2.setDepartureDate(dto1.getDepartureDate());
    dto2.setArrivalDate(dto1.getArrivalDate());
    dto2.setCarrier("Carrier");
    dto2.setTrackingNumber("12345");
  }

  @Test
  void testGettersAndSetters() {
    FreightDTO dto = new FreightDTO();
    LocalDate now = LocalDate.now();

    dto.setId(2L);
    dto.setOrigin("New Origin");
    dto.setDestination("New Destination");
    dto.setCustomer("New Customer");
    dto.setStatus("DELIVERED");
    dto.setDepartureDate(now);
    dto.setArrivalDate(now);
    dto.setCarrier("New Carrier");
    dto.setTrackingNumber("67890");

    assertEquals(2L, dto.getId());
    assertEquals("New Origin", dto.getOrigin());
    assertEquals("New Destination", dto.getDestination());
    assertEquals("New Customer", dto.getCustomer());
    assertEquals("DELIVERED", dto.getStatus());
    assertEquals(now, dto.getDepartureDate());
    assertEquals(now, dto.getArrivalDate());
    assertEquals("New Carrier", dto.getCarrier());
    assertEquals("67890", dto.getTrackingNumber());
  }

  @Test
  void testEqualsAndHashCode() {
    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());

    dto2.setId(3L);
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());
  }

  @Test
  void testToString() {
    String dtoString = dto1.toString();
    assertNotNull(dtoString);
    assertTrue(dtoString.contains("id=1"));
    assertTrue(dtoString.contains("origin=Origin"));
  }

  @Test
  void testNoArgsConstructor() {
    FreightDTO dto = new FreightDTO();
    assertNotNull(dto);
  }
}