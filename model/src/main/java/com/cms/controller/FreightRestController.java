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

import com.cms.model.FreightDTO;
import com.cms.service.FreightService;

@RestController
@RequestMapping("/api/freights")
public class FreightRestController {
  private final FreightService service;

  public FreightRestController(FreightService service) {
    this.service = service;
  }

  @GetMapping
  public List<FreightDTO> list() {
    return service.listAll();
  }

  @GetMapping("/{id}")
  public FreightDTO get(@PathVariable Long id) {
    return service.get(id);
  }

  @PostMapping
  public FreightDTO create(@RequestBody FreightDTO c) {
    return service.create(c);
  }

  @PutMapping("/{id}")
  public FreightDTO update(@PathVariable Long id, @RequestBody FreightDTO c) {
    return service.update(id, c);
  }

  @PatchMapping("/{id}/status")
  public FreightDTO updateStatus(@PathVariable Long id,
                                @RequestParam String status,
                                @RequestParam(required = false) String notes) {
    return service.transition(id, status, notes);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }
}

