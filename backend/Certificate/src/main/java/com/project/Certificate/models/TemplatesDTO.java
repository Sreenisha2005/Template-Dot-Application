package com.project.Certificate.models;

import lombok.Data;

import java.util.List;

@Data
public class TemplatesDTO {
    private String name;

    private String filePath; // MinIO / S3 URL

    private int pageCount;

    private List<TextSlotDTO> textSlots;
}
