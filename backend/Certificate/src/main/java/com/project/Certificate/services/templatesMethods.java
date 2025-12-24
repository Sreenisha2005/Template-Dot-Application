package com.project.Certificate.services;

import com.project.Certificate.feign.TemplatesInterface;
import com.project.Certificate.models.CertificateDTO;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.*;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class templatesMethods {

    private final HelperMethods helpers;
    private final ContentClass content;
    private final TemplatesInterface templatesInterface;

    public byte[] completionCertificate(CertificateDTO certificateDTO){

        Map<String, String> map = content.getContentForCompletionCertificate(certificateDTO);

        String firstLine = map.get("firstLine");

        try {
            Resource templatePdf = templatesInterface.getTemplate(1L).getBody();
            InputStream is = templatePdf.getInputStream();
            PDDocument document = Loader.loadPDF(is.readAllBytes());
            PDPage page = document.getPage(0);

            PDPageContentStream contentStream = new PDPageContentStream(
                    document, page,
                    PDPageContentStream.AppendMode.APPEND,
                    true, true
            );

            PDRectangle mediabox = page.getMediaBox();
            float fontSize = 18;
            float pageWidth = mediabox.getWidth();
            float pageHeight = mediabox.getHeight();
            float leading = fontSize*1.5f;
            float maxWidth = 700;
            float startX;
            float startY;
            PDFont font = helpers.font(document);

            //text-start
            contentStream.beginText();
            float textWidth =
                    font.getStringWidth(firstLine) / 1000 * fontSize;
            startX = (pageWidth - textWidth) / 2;
            startY = pageHeight/2 + 80;
            contentStream.setFont(font, fontSize);
            contentStream.newLineAtOffset(startX, startY);
            contentStream.showText(firstLine);
            contentStream.endText();
            //text-end

            //name-start
            textWidth =
                    font.getStringWidth(certificateDTO.getName()) / 1000 * (fontSize * 2);

            startX = (pageWidth - textWidth) / 2;
            startY = pageHeight/2 + 20;

            contentStream.beginText();
            contentStream.setFont(font, fontSize * 2);
            contentStream.newLineAtOffset(startX, startY);
            contentStream.showText(certificateDTO.getName());
            contentStream.endText();
            //name-end

            //para-start
            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            startY = pageHeight/2 - 30;
            contentStream.newLineAtOffset(0, startY);

            StringBuilder builder = new StringBuilder();
            String certificateContent = map.get("paragraph");
            for (String text : certificateContent.split(" ")){

                String textLine = builder + text + " ";
                float len = font.getStringWidth(textLine) / 1000 * fontSize;

                if (len > maxWidth){
                    float centeredX = (pageWidth -
                            (font.getStringWidth(builder.toString()) / 1000 * fontSize)) / 2;
                    contentStream.newLineAtOffset(centeredX, 0);
                    contentStream.showText(builder.toString().trim());
                    contentStream.newLineAtOffset(-centeredX, -leading);
                    builder = new StringBuilder(text).append(" ");
                }
                else {
                    builder.append(text).append(" ");
                }
            }
            if (!builder.isEmpty()){
                float centeredX = (pageWidth - (
                        font.getStringWidth(builder.toString()) / 1000 * fontSize
                )) / 2;

                contentStream.newLineAtOffset(centeredX, 0);
                contentStream.showText(builder.toString().trim());
            }
            contentStream.endText();
            //para-end

            contentStream.close();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream.toByteArray();
        }

        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }





}
