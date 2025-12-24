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

        TextSlot slot = builder.build();
        return slotsRepo.save(slot);
    }

    public TextSlotDTO findTextSlot(long id) {
        TextSlot slot = slotsRepo.findById(id).orElse(null);
        TextSlotDTO dto = new TextSlotDTO();

        if (slot != null){
            dto.setKey(slot.getKey());
            dto.setTemplateId(slot.getTemplate().getId());
        }

        return dto;
    }

    public List<TextSlotDTO> allTextSlots(Long templateId){
        List<TextSlot> slots = slotsRepo.findAllByTemplateId(templateId);
        return slots.stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
