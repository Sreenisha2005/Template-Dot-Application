package com.project.Certificate.services;

import com.project.Certificate.models.TextSlotDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class HelperMethods {
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

    public float getTextWidth(float fontsize, PDFont font, String text) throws IOException {
        return font.getStringWidth(text) / 1000 * fontsize;
    }

    public List<String> wrapText(
            String text,
            PDFont font,
            int fontSize,
            float maxWidth
    ) throws IOException {

        List<String> lines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();

        for (String word : text.split("\\s+")) {

            String testLine = currentLine.length() == 0
                    ? word
                    : currentLine + " " + word;

            float width = getTextWidth(fontSize, font, testLine);

            if (width > maxWidth && currentLine.length() > 0) {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            } else {
                if (currentLine.length() > 0) {
                    currentLine.append(" ");
                }
                currentLine.append(word);
            }
        }

        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines;
    }
}
