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

    private Float marginTop;
    private Float marginBottom;
    private Float marginLeft;
    private Float marginRight;

    private Integer fontSize;
    private String font;
    private TextSlot.TextAlignment alignment; // LEFT, CENTER

    private Integer maxWidth; // for paragraph wrapping
    private Boolean required;

    private Long templateId;
}
