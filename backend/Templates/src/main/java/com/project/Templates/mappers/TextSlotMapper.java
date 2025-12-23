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
                .marginTop(slot.getMarginTop())
                .marginBottom(slot.getMarginBottom())
                .marginLeft(slot.getMarginLeft())
                .marginRight(slot.getMarginRight())
                .font(slot.getFont())
                .fontSize(slot.getFontSize())
                .alignment(slot.getAlignment())
                .maxWidth(slot.getMaxWidth())
                .required(slot.getRequired())
                .templateId(slot.getTemplate().getId())
                .build();
    }
}