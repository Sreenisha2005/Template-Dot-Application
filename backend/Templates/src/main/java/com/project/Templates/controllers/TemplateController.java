package com.project.Templates.controllers;

import com.project.Templates.models.Templates;
import com.project.Templates.services.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequiredArgsConstructor
@RequestMapping("/templates")
public class TemplateController {
    private final TemplateService service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadTemplate(
            @RequestParam MultipartFile file,
            @RequestParam String name){

        return service.saveTemplate(file, name);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> viewTemplateDetails(@PathVariable long id){
        Templates template = service.findTemplate(id);
        if (template == null){
            return ResponseEntity.status(404).body("Template does not exist!");
        }
        return ResponseEntity.status(200).body(template);
    }

    @GetMapping("/getTemplate/{id}")
    public ResponseEntity<Resource> getTemplate(@PathVariable Long id){
        Templates template = service.findTemplate(id);
        File file = new File(template.getFilePath());
        Resource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                "inline; filename=\"" + file.getName() + "\"")
                .body(resource);
    }

}
