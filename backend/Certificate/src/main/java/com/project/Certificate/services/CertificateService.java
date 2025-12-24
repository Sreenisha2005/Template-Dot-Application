package com.project.Certificate.services;

import com.project.Certificate.feign.TemplatesInterface;
import com.project.Certificate.models.TextSlotDTO;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.awt.geom.Point2D;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CertificateService {
    private final TemplatesInterface templatesInterface;
    private final templatesMethods templatesMethods;
    private final HelperMethods helpers;

    public byte[] generateCertificate(Long templateId, Map<String, String> slotContent) throws IOException {

        List<TextSlotDTO> slotList = templatesInterface.allTextSlots(templateId).getBody();
        Resource templatePdf = templatesInterface.getTemplate(templateId).getBody();
        InputStream is = templatePdf.getInputStream();
        PDDocument document = Loader.loadPDF(is.readAllBytes());
        PDPage page = document.getPage(0);

        PDPageContentStream contentStream = new PDPageContentStream(
                document, page,
                PDPageContentStream.AppendMode.APPEND,
                true, true
        );

        PDFont font = helpers.font(document);

        for (TextSlotDTO slot : slotList) {
            if (!slotContent.containsKey(slot.getKey())) continue;

            int fontsize = 14;
            int maxWidth = 800;
            float leading = fontsize + 2;
            String value = slotContent.get(slot.getKey());
            contentStream.setFont(font, fontsize);

            List<String> lines = helpers.wrapText(value, font, fontsize, maxWidth);

            float longestWidth = 0;
            for (String line : lines) {
                float width = helpers.getTextWidth(fontsize, font, line);
                if (width > longestWidth) longestWidth = width;
            }

            float textHeight = lines.size() * leading;


            contentStream.beginText();

            for (String line : lines) {
                contentStream.showText(line);
                contentStream.newLineAtOffset(0, -leading);
            }

            contentStream.endText();
        }

        contentStream.close();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.save(outputStream);
        return outputStream.toByteArray();
    }

}
