package com.cms.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FreightTest {

  private Freight freight1;
  private Freight freight2;

  @BeforeEach
  void setUp() {
    freight1 =
        Freight.builder()
            .id(1L)
            .origin("Origin")
            .destination("Destination")
            .customer("Customer")
            .status("IN_TRANSIT")
            .departureDate(LocalDate.now())
            .arrivalDate(LocalDate.now())
            .carrier("Carrier")
            .trackingNumber("12345")
            .build();

    freight2 =
        Freight.builder()
            .id(1L)
            .origin("Origin")
            .destination("Destination")
            .customer("Customer")
            .status("IN_TRANSIT")
            .departureDate(freight1.getDepartureDate())
            .arrivalDate(freight1.getArrivalDate())
            .carrier("Carrier")
            .trackingNumber("12345")
            .build();
  }

  @Test
  void testFreightCreationAndGetters() {
    assertNotNull(freight1);
    assertEquals(1L, freight1.getId());
    assertEquals("Origin", freight1.getOrigin());
    assertEquals("Destination", freight1.getDestination());
    assertEquals("Customer", freight1.getCustomer());
    assertEquals("IN_TRANSIT", freight1.getStatus());
    assertNotNull(freight1.getDepartureDate());
    assertNotNull(freight1.getArrivalDate());
    assertEquals("Carrier", freight1.getCarrier());
    assertEquals("12345", freight1.getTrackingNumber());
  }

  @Test
  void testSetters() {
    Freight freight = new Freight();
    freight.setId(2L);
    freight.setOrigin("New Origin");
    freight.setDestination("New Destination");
    freight.setCustomer("New Customer");
    freight.setStatus("DELIVERED");
    LocalDate now = LocalDate.now();
    freight.setDepartureDate(now);
    freight.setArrivalDate(now);
    freight.setCarrier("New Carrier");
    freight.setTrackingNumber("67890");

    assertEquals(2L, freight.getId());
    assertEquals("New Origin", freight.getOrigin());
    assertEquals("New Destination", freight.getDestination());
    assertEquals("New Customer", freight.getCustomer());
    assertEquals("DELIVERED", freight.getStatus());
    assertEquals(now, freight.getDepartureDate());
    assertEquals(now, freight.getArrivalDate());
    assertEquals("New Carrier", freight.getCarrier());
    assertEquals("67890", freight.getTrackingNumber());
  }

  @Test
  void testEqualsAndHashCode() {
    assertEquals(freight1, freight2);
    assertEquals(freight1.hashCode(), freight2.hashCode());

    freight2.setId(2L);
    assertNotEquals(freight1, freight2);
    assertNotEquals(freight1.hashCode(), freight2.hashCode());
  }

  @Test
  void testToString() {
    String freightString = freight1.toString();
    assertTrue(freightString.contains("id=1"));
    assertTrue(freightString.contains("origin=Origin"));
  }

  @Test
  void testNoArgsConstructor() {
    Freight freight = new Freight();
    assertNotNull(freight);
  }
}