package com.cms.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cms.model.Complaint;
import com.cms.service.ComplaintService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
public class ComplaintRestController {
  private final ComplaintService service = null;

  @GetMapping
  public List<Complaint> list() {
    return service.listAll();
  }

  @GetMapping("/{id}")
  public Complaint get(@PathVariable Long id) {
    return service.get(id);
  }

  @PostMapping
  public Complaint create(@RequestBody Complaint c) {
    return service.create(c);
  }

  @PutMapping("/{id}")
  public Complaint update(@PathVariable Long id, @RequestBody Complaint c) {
    return service.update(id, c);
  }

  @PatchMapping("/{id}/status")
  public Complaint updateStatus(@PathVariable Long id,
                                @RequestParam String status,
                                @RequestParam(required = false) String notes) {
    return service.transition(id, status, notes);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }
}
