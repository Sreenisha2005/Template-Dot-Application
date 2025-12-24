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

import static com.project.Certificate.models.TextSlotDTO.TextAlignment.*;

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



    public Point2D.Float resolve(
            TextSlotDTO.TextAlignment alignment,
            PDPage page,
            float textWidth,
            float textHeight,
            float marginTop,
            float marginBottom,
            float marginLeft,
            float marginRight
    ) {

        float pageWidth = page.getMediaBox().getWidth();
        float pageHeight = page.getMediaBox().getHeight();

        float x = 0, y = 0;

        switch (alignment) {

            // ---------- TOP ----------
            case TOP_LEFT -> {
                x = marginLeft;
                y = pageHeight - marginTop - textHeight;
            }
            case TOP_CENTER -> {
                x = (pageWidth - textWidth) / 2;
                y = pageHeight - marginTop - textHeight;
            }
            case TOP_RIGHT -> {
                x = pageWidth - marginRight - textWidth;
                y = pageHeight - marginTop - textHeight;
            }

            // ---------- MIDDLE ----------
            case MIDDLE_LEFT -> {
                x = marginLeft;
                y = (pageHeight - textHeight) / 2;
            }
            case MIDDLE_CENTER -> {
                x = (pageWidth - textWidth) / 2;
                y = (pageHeight - textHeight) / 2;
            }
            case MIDDLE_RIGHT -> {
                x = pageWidth - marginRight - textWidth;
                y = (pageHeight - textHeight) / 2;
            }

            // ---------- BOTTOM ----------
            case BOTTOM_LEFT -> {
                x = marginLeft;
                y = marginBottom;
            }
            case BOTTOM_CENTER -> {
                x = (pageWidth - textWidth) / 2;
                y = marginBottom;
            }
            case BOTTOM_RIGHT -> {
                x = pageWidth - marginRight - textWidth;
                y = marginBottom;
            }
        }

        return new Point2D.Float(x, y);
    }

    public float getTextWidth(float fontsize, PDFont font, String text) throws IOException {
        return font.getStringWidth(text) / 1000 * fontsize;
    }

    public List<String> wrapText(String text, PDFont font, int fontSize, float maxWidth) throws IOException {
        List<String> lines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();

        for (String word : text.split(" ")) {
            String testLine = currentLine + word + " ";
            float width = getTextWidth(fontSize, font, testLine);

            if (width > maxWidth && !currentLine.isEmpty()) {
                lines.add(currentLine.toString().trim());
                currentLine = new StringBuilder(word).append(" ");
            } else {
                currentLine.append(word).append(" ");
            }
        }

        if (!currentLine.isEmpty()) {
            lines.add(currentLine.toString().trim());
        }

        return lines;
    }
}
