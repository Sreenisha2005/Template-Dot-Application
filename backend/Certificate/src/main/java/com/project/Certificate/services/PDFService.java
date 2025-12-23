package com.project.Certificate.services;

import com.project.Certificate.models.RequestDTO;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class PDFService {

    protected PDFont font(PDDocument pdDocument){
        PDFont font;
        final ClassPathResource resource =
                new ClassPathResource("fonts/Montserrat-SemiBold_72e0d276db1e5baa6f2b4caddfef5b93.ttf");
        try {
            InputStream fontstream = resource.getInputStream();
            font = PDType0Font.load(pdDocument, fontstream, true);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return font;
    }

    public byte[] modifyPdf(RequestDTO requestDTO){
        String firstLine = "This is to certify that";
        File file = new File
                ("D:/java/Certificate/src/main/resources/templates/Completion_Certificate.pdf");

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PDDocument pdDocument = Loader.loadPDF(file);
            PDPage pdPage = pdDocument.getPage(0);
            PDPageContentStream contentStream = new PDPageContentStream(
                    pdDocument,
                    pdPage,
                    PDPageContentStream.AppendMode.APPEND,
                    true,
                    true
            );

            PDRectangle mediabox = pdPage.getMediaBox();
            float fontSize = 18;
            float pageWidth = mediabox.getWidth();
            float pageHeight = mediabox.getHeight();
            float leading = fontSize*1.5f;
            float maxWidth = 700;
            float startX;
            float startY;
            PDFont font = font(pdDocument);

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
                    font.getStringWidth(requestDTO.getName()) / 1000 * (fontSize * 2);

            startX = (pageWidth - textWidth) / 2;
            startY = pageHeight/2 + 20;

            contentStream.beginText();
            contentStream.setFont(font, fontSize * 2);
            contentStream.newLineAtOffset(startX, startY);
            contentStream.showText(requestDTO.getName());
            contentStream.endText();
            //name-end

            //para-start
            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            startY = pageHeight/2 - 30;
            contentStream.newLineAtOffset(0, startY);

            StringBuilder builder = new StringBuilder();
            for (String text : getContent(requestDTO).split(" ")){

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

            pdDocument.save(outputStream);
            return outputStream.toByteArray();
        }

        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

// not in use
    public void createPdf(RequestDTO requestDTO){
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
                contentStream.setFont(font(pdDocument), fontSize);
                contentStream.setLeading(fontSize);
                contentStream.newLineAtOffset(100, 250);

                StringBuilder builder = new StringBuilder();
                for (String text : getContent(requestDTO).split(" ")){
                    String textLine = builder + text + " ";
                    float len = font(pdDocument).getStringWidth(textLine) / 1000 * fontSize;

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

    public String getContent(RequestDTO requestDTO){
        return "has successfully completed his role as " +
                requestDTO.getRole() + " at NeuroStack from "+
                requestDTO.getStartDate() + " to " + requestDTO.getEndDate() +
                ", during which he demonstrated strong technical skills and consistently delivered quality work. " +
                "His performance met our expectations and we wish him continued success in his future endeavors.";
    }

}
