package com.project.Templates.DTOs;

import com.project.Templates.models.TextSlot;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class TemplatesDTO {
    private String name;

    private String filePath; // MinIO / S3 URL

    private int pageCount;

    private List<TextSlot> textSlots;
}
