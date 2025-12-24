package com.project.Templates.DTOs;

import com.project.Templates.models.TextSlot;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TextSlotDTO {
    private Long id;
    private String key;
    private Long templateId;
}
