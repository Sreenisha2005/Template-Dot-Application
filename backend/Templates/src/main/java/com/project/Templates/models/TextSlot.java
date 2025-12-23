package com.project.Templates.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "text_slots")
public class TextSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String key;

    @Builder.Default
    private Float marginTop = 10f;

    @Builder.Default
    private Float marginBottom = 10f;

    @Builder.Default
    private Float marginLeft = 10f;

    @Builder.Default
    private Float marginRight = 10f;

    @Builder.Default
    private Integer fontSize = 14;

    @Builder.Default
    private String font = "Montserrat";

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TextAlignment alignment = TextAlignment.MIDDLE_CENTER; // LEFT, CENTER

    @Builder.Default
    private Integer maxWidth = 500;  // for paragraph wrapping

    @Builder.Default
    private Boolean required = true;

    @ManyToOne
    @JoinColumn(name = "template_id")
    @JsonIgnore
    private Templates template;

    public enum TextAlignment {
        TOP_LEFT,
        TOP_CENTER,
        TOP_RIGHT,
        MIDDLE_LEFT,
        MIDDLE_CENTER,
        MIDDLE_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        TextAlignment, BOTTOM_CENTER
    }

}