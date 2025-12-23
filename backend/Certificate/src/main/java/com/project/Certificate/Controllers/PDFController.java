package com.project.Certificate.Controllers;

import com.project.Certificate.services.PDFService;
import com.project.Certificate.models.RequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PDFController {
    private final PDFService pdfService;
    @Autowired
    public PDFController(PDFService pdfService){
        this.pdfService = pdfService;
    }

    @GetMapping("/get")
    public ResponseEntity<?> printPdf(@RequestBody RequestDTO requestDTO){
        RequestDTO request = RequestDTO.builder()
                .name(requestDTO.getName())
                .role(requestDTO.getRole())
                .startDate(requestDTO.getStartDate())
                .endDate(requestDTO.getEndDate())
                .build();
        try {
            byte[] op = pdfService.modifyPdf(request);
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
