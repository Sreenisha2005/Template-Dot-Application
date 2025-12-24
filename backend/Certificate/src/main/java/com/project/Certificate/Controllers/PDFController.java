package com.project.Certificate.Controllers;

import com.project.Certificate.services.templatesMethods;
import com.project.Certificate.models.CertificateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PDFController {
    private final templatesMethods templatesMethods;
    @Autowired
    public PDFController(templatesMethods templatesMethods){
        this.templatesMethods = templatesMethods;
    }

    @GetMapping("/get")
    public ResponseEntity<?> printPdf(@RequestBody CertificateDTO certificateDTO){
        CertificateDTO request = certificateDTO.builder()
                .name(certificateDTO.getName())
                .role(certificateDTO.getRole())
                .startDate(certificateDTO.getStartDate())
                .endDate(certificateDTO.getEndDate())
                .build();
        try {
            byte[] op = templatesMethods.completionCertificate(request);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename = competion_certificate.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(op);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
