package com.cms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.model.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
  List<Complaint> findByStatusOrderByCreatedAtDesc(String status);
  List<Complaint> findByCategoryOrderByCreatedAtDesc(String category);
  List<Complaint> findAllByOrderByCreatedAtDesc();
}
