package com.project.Certificate.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentDTO {
    private Long templateId;
    private Map<String, String> key_content;
}
