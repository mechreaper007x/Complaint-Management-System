package com.cms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.model.Freight;

public interface FreightRepository extends JpaRepository<Freight, Long> {
  List<Freight> findByStatusOrderByDepartureDateDesc(String status);
  List<Freight> findAllByOrderByDepartureDateDesc();
}

