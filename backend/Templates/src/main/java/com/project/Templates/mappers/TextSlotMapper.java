package com.project.Templates.mappers;

import com.project.Templates.DTOs.TextSlotDTO;
import com.project.Templates.models.TextSlot;
import org.springframework.stereotype.Component;

@Component
public class TextSlotMapper {

    public TextSlotDTO toDto(TextSlot slot) {
        if (slot == null){
            return null;
        }

        return TextSlotDTO.builder()
                .id(slot.getId())
                .key(slot.getKey())
                .templateId(slot.getTemplate().getId())
                .build();
    }
}