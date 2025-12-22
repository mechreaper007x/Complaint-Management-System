package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.mapper.FreightMapper;
import com.cms.model.Freight;
import com.cms.model.FreightDTO;
import com.cms.repository.FreightRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FreightService {
  private final FreightRepository repo;
  private final FreightMapper mapper = FreightMapper.INSTANCE;

  public List<FreightDTO> listAll(){ return repo.findAllByOrderByDepartureDateDesc().stream().map(mapper::freightToFreightDTO).toList(); }
  
  public List<Freight> listAllEntities(){ return repo.findAllByOrderByDepartureDateDesc(); }

  public List<FreightDTO> listByStatus(String status){ return repo.findByStatusOrderByDepartureDateDesc(status).stream().map(mapper::freightToFreightDTO).toList(); }
  
  public FreightDTO get(Long id){ return repo.findById(id).map(mapper::freightToFreightDTO).orElseThrow(); }
  
  public Freight getEntity(Long id){ return repo.findById(id).orElseThrow(); }

  public FreightDTO create(FreightDTO c){ 
    Freight freight = mapper.freightDTOToFreight(c);
    return mapper.freightToFreightDTO(repo.save(freight)); 
  }

  public Freight create(Freight c){ 
    return repo.save(c);
  }

  public FreightDTO update(Long id, FreightDTO c){
    Freight existing = repo.findById(id).orElseThrow();
    if (c.getOrigin() != null) existing.setOrigin(c.getOrigin());
    if (c.getDestination() != null) existing.setDestination(c.getDestination());
    if (c.getCustomer() != null) existing.setCustomer(c.getCustomer());
    if (c.getStatus() != null) existing.setStatus(c.getStatus());
    return mapper.freightToFreightDTO(repo.save(existing));
  }

  public FreightDTO transition(Long id, String status, String notes){
    Freight c = repo.findById(id).orElseThrow();
    c.setStatus(status);
    return mapper.freightToFreightDTO(repo.save(c));
  }
  
  public void delete(Long id){ repo.deleteById(id); }
}

