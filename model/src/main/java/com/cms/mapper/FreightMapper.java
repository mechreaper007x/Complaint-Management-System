package com.cms.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.cms.model.Freight;
import com.cms.model.FreightDTO;

@Mapper
public interface FreightMapper {
    FreightMapper INSTANCE = Mappers.getMapper(FreightMapper.class);

    FreightDTO freightToFreightDTO(Freight freight);

    Freight freightDTOToFreight(FreightDTO freightDTO);
}
