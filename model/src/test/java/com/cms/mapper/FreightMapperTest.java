package com.cms.mapper;

import com.cms.model.Freight;
import com.cms.model.FreightDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FreightMapperTest {

    private final FreightMapper mapper = FreightMapper.INSTANCE;

    @Test
    void testFreightToFreightDTO() {
        Freight freight = new Freight();
        freight.setId(1L);
        freight.setOrigin("Origin");
        freight.setDestination("Destination");
        freight.setCustomer("Customer");
        freight.setStatus("IN_TRANSIT");

        FreightDTO dto = mapper.freightToFreightDTO(freight);

        assertEquals(freight.getId(), dto.getId());
        assertEquals(freight.getOrigin(), dto.getOrigin());
        assertEquals(freight.getDestination(), dto.getDestination());
        assertEquals(freight.getCustomer(), dto.getCustomer());
        assertEquals(freight.getStatus(), dto.getStatus());
    }

    @Test
    void testFreightDTOToFreight() {
        FreightDTO dto = new FreightDTO();
        dto.setId(1L);
        dto.setOrigin("Origin");
        dto.setDestination("Destination");
        dto.setCustomer("Customer");
        dto.setStatus("IN_TRANSIT");

        Freight freight = mapper.freightDTOToFreight(dto);

        assertEquals(dto.getId(), freight.getId());
        assertEquals(dto.getOrigin(), freight.getOrigin());
        assertEquals(dto.getDestination(), freight.getDestination());
        assertEquals(dto.getCustomer(), freight.getCustomer());
        assertEquals(dto.getStatus(), freight.getStatus());
    }

    @Test
    void testFreightToFreightDTONull() {
        assertNull(mapper.freightToFreightDTO(null));
    }

    @Test
    void testFreightDTOToFreightNull() {
        assertNull(mapper.freightDTOToFreight(null));
    }
}

