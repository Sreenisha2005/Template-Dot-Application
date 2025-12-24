package com.project.Certificate.services;

import com.project.Certificate.models.CertificateDTO;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class NotInUse {
    private final HelperMethods helpers;
    private final ContentClass content;

    // not in use
    public void createPdf(CertificateDTO certificateDTO){
        Map<String, String> map = content.getContentForCompletionCertificate(certificateDTO);
        float fontSize = 20;
        PDFont font;
        PDDocument pdDocument;
        try{
            pdDocument = new PDDocument();
            PDPage pdPage = new PDPage();
            pdDocument.addPage(pdPage);


            try (PDPageContentStream contentStream =
                         new PDPageContentStream(pdDocument, pdPage)){
                contentStream.beginText();
                contentStream.setFont(helpers.font(pdDocument), fontSize);
                contentStream.setLeading(fontSize);
                contentStream.newLineAtOffset(100, 250);

                StringBuilder builder = new StringBuilder();
                for (String text : map.get("paragraph").split(" ")){
                    String textLine = builder + text + " ";
                    float len = helpers.font(pdDocument).getStringWidth(textLine) / 1000 * fontSize;

                    if (len > 200){
                        contentStream.showText(builder.toString().trim());
                        contentStream.newLineAtOffset(0, -fontSize-2f);
                        builder = new StringBuilder(text).append(" ");
                    }
                    else {
                        builder.append(text).append(" ");
                    }
                }
                if (!builder.isEmpty()){
                    contentStream.showText(builder.toString().trim());
                }
                contentStream.endText();
            }
            pdDocument.save("pdDocument.pdf");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
