package at.technikumwien.dmsbackend.service.impl;

import at.technikumwien.dmsbackend.service.dto.OCRResultDTO;
import at.technikumwien.dmsbackend.persistence.DocumentIndex;
import at.technikumwien.dmsbackend.persistence.entity.DocumentEntity;
import at.technikumwien.dmsbackend.persistence.repository.DocumentIndexRepository;
import at.technikumwien.dmsbackend.persistence.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class RabbitMQListenerImpl {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQListenerImpl.class);
    private final DocumentIndexRepository documentIndexRepository;
    private final DocumentRepository documentRepository;

    public void receiveResult(OCRResultDTO resultDTO) {
        logger.info("Received result from RabbitMQ: " + resultDTO.getDocumentId());

        DocumentEntity document = documentRepository.findById(resultDTO.getDocumentId())
                .orElse(null);

        if (document == null) {
            logger.warn("OCR result received for unknown document {}", resultDTO.getDocumentId());
            documentIndexRepository.save(DocumentIndex.builder()
                    .documentId(resultDTO.getDocumentId())
                    .content(resultDTO.getRecognizedText())
                    .ocrProcessedAt(OffsetDateTime.now().toString())
                    .build());
            return;
        }

        documentIndexRepository.save(DocumentIndex.builder()
                .documentId(resultDTO.getDocumentId())
                .title(document.getTitle())
                .description(document.getDescription())
                .type(document.getType())
                .size(document.getSize())
                .uploadDate(document.getUploadDate().toString())
                .fileKey(document.getFileKey())
                .content(resultDTO.getRecognizedText())
                .ocrProcessedAt(OffsetDateTime.now().toString())
                .build());
        logger.info("Stored OCR result for document {}", resultDTO.getDocumentId());
    }
}
