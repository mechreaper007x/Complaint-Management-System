package com.cms.model;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Complaint {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank private String title;
  @NotBlank @Column(length = 4000) private String description;
  @NotBlank private String category;
  @NotBlank private String priority;
  @NotBlank @Builder.Default private String status = "OPEN";

  private String reporterName;
  @Email private String reporterEmail;
  private String reporterPhone;

  @Column(length = 2000) private String resolutionNotes;

  @Builder.Default private LocalDateTime createdAt = LocalDateTime.now();
  @Builder.Default private LocalDateTime updatedAt = LocalDateTime.now();

  @PreUpdate
  public void onUpdate(){ this.updatedAt = LocalDateTime.now(); }

  public String getStatus() {
    return status;
  }

  public String getCategory() {
    return category;
  }
}
