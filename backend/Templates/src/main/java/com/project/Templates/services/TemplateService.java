package com.project.Templates.services;

import com.project.Templates.repos.TemplatesRepo;
import com.project.Templates.models.Templates;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class TemplateService {
    private final TemplatesRepo repo;

    public ResponseEntity<String> saveTemplate(MultipartFile file, String name) {
        if (!file.getContentType().equals("application/pdf")){
            return ResponseEntity.badRequest().body("Only PDFs are allowed!");
        }
        try {
            String dirPath = "templates/";
            Files.createDirectory(Paths.get(dirPath));

            String filePath = dirPath + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), Paths.get(filePath));

            PDDocument document = Loader.loadPDF(new File(filePath));
            int pgs = document.getNumberOfPages();
            document.close();

            Templates template = new Templates();
            template.setFilePath(filePath);
            template.setName(name);
            template.setPageCount(pgs);

            repo.save(template);

            return ResponseEntity.ok().body("Template uploaded Successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Templates findTemplate(long id){
        return repo.findById(id).orElse(null);
    }
}
