package ta.technikumwien.dmsocr.service.impl;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Service
public class OcrService {
    public String performOCR(InputStream pdfStream) throws Exception {
        // Perform OCR using Tesseract
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("/usr/share/tesseract-ocr/5/tessdata");
        tesseract.setLanguage("eng+deu");

        File tempFile = File.createTempFile("ocr-", ".pdf");
        try {
            Files.copy(pdfStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            StringBuilder recognizedText = new StringBuilder();
            try (PDDocument document = Loader.loadPDF(tempFile)) {
                PDFRenderer renderer = new PDFRenderer(document);
                for (int page = 0; page < document.getNumberOfPages(); page++) {
                    BufferedImage image = renderer.renderImageWithDPI(page, 300, ImageType.RGB);
                    recognizedText.append(tesseract.doOCR(image)).append(System.lineSeparator());
                }
            }

            return recognizedText.toString().trim();

        } finally {
            // Clean up temporary file
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}
