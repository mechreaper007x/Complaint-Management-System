package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.model.Complaint;
import com.cms.repository.ComplaintRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComplaintService {
  private final ComplaintRepository repo;

  public List<Complaint> listAll(){ return repo.findAllByOrderByCreatedAtDesc(); }
  public List<Complaint> listByStatus(String status){ return repo.findByStatusOrderByCreatedAtDesc(status); }
  public Complaint get(Long id){ return repo.findById(id).orElseThrow(); }
  public Complaint create(Complaint c){ return repo.save(c); }
  public Complaint update(Long id, Complaint c){
    Complaint existing = get(id);
    existing.setTitle(c.getTitle());
    existing.setDescription(c.getDescription());
    existing.setCategory(c.getCategory());
    existing.setPriority(c.getPriority());
    existing.setReporterName(c.getReporterName());
    existing.setReporterEmail(c.getReporterEmail());
    existing.setReporterPhone(c.getReporterPhone());
    return repo.save(existing);
  }
  public Complaint transition(Long id, String status, String notes){
    Complaint c = get(id);
    c.setStatus(status);
    if (notes != null && !notes.isBlank()) c.setResolutionNotes(notes);
    return repo.save(c);
  }
  public void delete(Long id){ repo.deleteById(id); }
}
