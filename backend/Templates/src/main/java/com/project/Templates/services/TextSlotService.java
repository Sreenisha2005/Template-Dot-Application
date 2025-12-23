package com.project.Templates.services;

import com.project.Templates.DTOs.TextSlotDTO;
import com.project.Templates.mappers.TextSlotMapper;
import com.project.Templates.models.Templates;
import com.project.Templates.models.TextSlot;
import com.project.Templates.repos.TemplatesRepo;
import com.project.Templates.repos.TextSlotsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TextSlotService {
    private final TemplatesRepo templatesRepo;
    private final TextSlotsRepo slotsRepo;
    private final TextSlotMapper mapper;

    public TextSlot addTextSlots(TextSlotDTO dto){
        Templates template = templatesRepo.findById(dto.getTemplateId()).orElse(null);
        TextSlot.TextSlotBuilder builder = TextSlot.builder()
                .key(dto.getKey())
                .template(template);

        if (dto.getMarginTop() != null) builder.marginTop(dto.getMarginTop());
        if (dto.getMarginBottom() != null) builder.marginBottom(dto.getMarginBottom());
        if (dto.getMarginLeft() != null) builder.marginLeft(dto.getMarginLeft());
        if (dto.getMarginRight() != null) builder.marginRight(dto.getMarginRight());
        if (dto.getFontSize() != null) builder.fontSize(dto.getFontSize());
        if (dto.getFont() != null) builder.font(dto.getFont());
        if (dto.getAlignment() != null) builder.alignment(dto.getAlignment());
        if (dto.getMaxWidth() != null) builder.maxWidth(dto.getMaxWidth());
        if (dto.getRequired() != null) builder.required(dto.getRequired());

        TextSlot slot = builder.build();
        return slotsRepo.save(slot);
    }

    public TextSlotDTO findTextSlot(long id) {
        TextSlot slot = slotsRepo.findById(id).orElse(null);
        TextSlotDTO dto = new TextSlotDTO();

        if (slot != null){
            dto.setKey(slot.getKey());
            dto.setTemplateId(slot.getTemplate().getId());
            if (slot.getMarginTop() != null) dto.setMarginTop(slot.getMarginTop());
            if (slot.getMarginBottom() != null) dto.setMarginBottom(slot.getMarginBottom());
            if (slot.getMarginLeft() != null) dto.setMarginLeft(slot.getMarginLeft());
            if (slot.getMarginRight() != null) dto.setMarginRight(slot.getMarginRight());
            if (slot.getFontSize() != null) dto.setFontSize(slot.getFontSize());
            if (slot.getFont() != null) dto.setFont(slot.getFont());
            if (slot.getAlignment() != null) dto.setAlignment(slot.getAlignment());
            if (slot.getMaxWidth() != null) dto.setMaxWidth(slot.getMaxWidth());
            if (slot.getRequired() != null) dto.setRequired(slot.getRequired());
        }

        return dto;
    }

    public List<TextSlotDTO> allTextSlots(Long templateId){
        List<TextSlot> slots = slotsRepo.findAllByTemplateId(templateId);
        return slots.stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
