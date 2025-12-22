package com.cms.mapper;

import com.cms.model.Complaint;
import com.cms.model.ComplaintDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ComplaintMapper {
    ComplaintMapper INSTANCE = Mappers.getMapper(ComplaintMapper.class);

    ComplaintDTO complaintToComplaintDTO(Complaint complaint);

    Complaint complaintDTOToComplaint(ComplaintDTO complaintDTO);
}
