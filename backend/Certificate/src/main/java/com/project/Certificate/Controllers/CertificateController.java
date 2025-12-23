package com.project.Certificate.Controllers;

import com.project.Certificate.feign.TemplatesInterface;
import com.project.Certificate.models.ContentDTO;
import com.project.Certificate.services.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@RestController
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;
    private final TemplatesInterface templatesInterface;

    @PostMapping("/content")
    public ResponseEntity<?> getCertificate(@RequestBody ContentDTO dto){

        if (dto.getKey_content() == null || dto.getTemplateId() == null){
            return ResponseEntity.badRequest().body("Enter valid data!");
        }

        Map<String, String> slotContent = dto.getKey_content();

        try {
            byte[] docBytes = certificateService.generateCertificate(dto.getTemplateId(), slotContent);
            Resource template = templatesInterface.getTemplate(dto.getTemplateId()).getBody();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename = " + template.getFilename())
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(docBytes);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
