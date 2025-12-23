package com.project.Certificate.models;

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
    private TextAlignment alignment; // LEFT, CENTER

    private Integer maxWidth; // for paragraph wrapping
    private Boolean required;

    private Long templateId;

    public enum TextAlignment {
        TOP_LEFT,
        TOP_CENTER,
        TOP_RIGHT,
        MIDDLE_LEFT,
        MIDDLE_CENTER,
        MIDDLE_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        BOTTOM_CENTER
    }
}
