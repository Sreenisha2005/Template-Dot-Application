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
    private Long templateId;

}
