package com.cms.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ComplaintDTO {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String priority;
    private String status;
    private String reporterName;
    private String reporterEmail;
    private String reporterPhone;
    private String resolutionNotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
