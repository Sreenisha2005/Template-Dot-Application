package com.project.Templates.controllers;

import com.project.Templates.DTOs.TextSlotDTO;
import com.project.Templates.models.Templates;
import com.project.Templates.models.TextSlot;
import com.project.Templates.services.TemplateService;
import com.project.Templates.services.TextSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/textSlots")
public class TextSlotController {

    private final TextSlotService slotService;
    private final TemplateService templateService;

    @PostMapping("/upload")
    public ResponseEntity<?> addTextSlots(@RequestBody TextSlotDTO dto){
        if (dto == null){
            return ResponseEntity.badRequest().body("Enter valid Details!");
        }
        TextSlot slot = slotService.addTextSlots(dto);
        return ResponseEntity.ok().body(slot);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> textSlotDetails(@PathVariable long id){
        TextSlotDTO slotDTO = slotService.findTextSlot(id);
        if (slotDTO == null){
            return ResponseEntity.status(404).body("TextSlot does not exist!");
        }
        return ResponseEntity.status(200).body(slotDTO);
    }

    @GetMapping("/allTextSlots")
    public ResponseEntity<List<TextSlotDTO>> allTextSlots(@RequestParam Long templateId){
        Templates template = templateService.findTemplate(templateId);
        if (template == null){
            return ResponseEntity.status(404).body(null);
        }

        return ResponseEntity.ok().body(slotService.allTextSlots(templateId));
    }


}
