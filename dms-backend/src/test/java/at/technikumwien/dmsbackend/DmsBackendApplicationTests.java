package at.technikumwien.dmsbackend;

import at.technikumwien.dmsbackend.persistence.DocumentIndex;
import at.technikumwien.dmsbackend.persistence.entity.DocumentEntity;
import at.technikumwien.dmsbackend.persistence.repository.DocumentIndexRepository;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;

import at.technikumwien.dmsbackend.persistence.repository.DocumentRepository;
import at.technikumwien.dmsbackend.service.MinioService;
import at.technikumwien.dmsbackend.service.dto.DocumentDTO;
import at.technikumwien.dmsbackend.service.dto.OCRJobDTO;
import at.technikumwien.dmsbackend.service.mapper.DocumentMapper;
import at.technikumwien.dmsbackend.service.impl.DocumentServiceImpl;
import at.technikumwien.dmsbackend.exception.DocumentUploadException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DmsBackendApplicationTests {

	@Mock
	private DocumentRepository documentRepository;

	@Mock
	private DocumentMapper documentMapper;

	@Mock
	private RabbitTemplate rabbitTemplate;

	@Mock
	private MinioService minioService;

	@Mock
	private ObjectMapper objectMapper;

	@Mock
	private ElasticsearchClient elasticsearchClient;

	@Mock
	private DocumentIndexRepository documentIndexRepository;

	@InjectMocks
	private DocumentServiceImpl documentService;

	private DocumentDTO documentDTO;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		documentDTO = new DocumentDTO();
		documentDTO.setTitle("Test Document");
		documentDTO.setDescription("Test Description");
		documentDTO.setType("PDF");
		documentDTO.setSize(1024L);
		documentDTO.setUploadDate("2024-12-23");
		documentDTO.setFileData("pdf".getBytes());
	}

	@Test
	void uploadDocument_shouldReturnDocumentDTO_whenDocumentIsUploaded() {
		// Arrange
		DocumentEntity documentEntity = new DocumentEntity();
		documentEntity.setId(1L);
		documentEntity.setFileKey("document-1");

		when(documentRepository.save(any(DocumentEntity.class))).thenReturn(documentEntity);
		when(documentMapper.mapToDto(any(DocumentEntity.class))).thenReturn(documentDTO);

		// Act
		DocumentDTO result = documentService.uploadDocument(documentDTO);

		// Assert
		assertNotNull(result);
		assertEquals("Test Document", result.getTitle());

		try {
			verify(minioService, times(1)).uploadFile(any(), any(), anyLong(), anyString());
			verify(rabbitTemplate, times(1)).convertAndSend(anyString(), any(OCRJobDTO.class), any(MessagePostProcessor.class));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	void uploadDocument_shouldThrowException_whenUploadFails() {
		// Arrange
		when(documentRepository.save(any(DocumentEntity.class))).thenThrow(new RuntimeException("DB error"));

		// Act & Assert
		assertThrows(RuntimeException.class, () -> documentService.uploadDocument(documentDTO));
	}

	@Test
	void getDocumentById_shouldReturnDocumentDTO_whenDocumentExists() {
		// Arrange
		DocumentEntity documentEntity = new DocumentEntity();
		documentEntity.setId(1L);
		documentEntity.setFileKey("document-1");

		when(documentRepository.findById(1L)).thenReturn(Optional.of(documentEntity));
		when(documentMapper.mapToDto(documentEntity)).thenReturn(documentDTO);

		// Act
		DocumentDTO result = documentService.getDocumentById(1L);

		// Assert
		assertNotNull(result);
		assertEquals("Test Document", result.getTitle());
	}

	@Test
	void getDocumentById_shouldThrowException_whenDocumentDoesNotExist() {
		// Arrange
		when(documentRepository.findById(1L)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(EntityNotFoundException.class, () -> documentService.getDocumentById(1L));
	}

	@Test
	void getAllDocuments_shouldReturnListOfDocuments() {
		// Arrange
		List<DocumentEntity> documentEntities = new ArrayList<>();
		DocumentEntity documentEntity = new DocumentEntity();
		documentEntity.setId(1L);
		documentEntities.add(documentEntity);

		when(documentRepository.findAll()).thenReturn(documentEntities);
		when(documentMapper.mapToDto(any(DocumentEntity.class))).thenReturn(documentDTO);

		// Act
		List<DocumentDTO> result = documentService.getAllDocuments();

		// Assert
		assertNotNull(result);
		assertEquals(1, result.size());
	}

	@Test
	void updateDocument_shouldReturnUpdatedDocumentDTO() {
		// Arrange
		DocumentEntity existingDocument = new DocumentEntity();
		existingDocument.setId(1L);

		when(documentRepository.findById(1L)).thenReturn(Optional.of(existingDocument));
		when(documentMapper.mapToDto(existingDocument)).thenReturn(documentDTO);

		// Act
		DocumentDTO result = documentService.updateDocument(1L, documentDTO);

		// Assert
		assertNotNull(result);
		verify(documentRepository, times(1)).save(any(DocumentEntity.class));
	}

	@Test
	void deleteDocument_shouldDeleteDocument() {
		// Arrange
		DocumentEntity documentEntity = new DocumentEntity();
		documentEntity.setId(1L);
		documentEntity.setFileKey("document-1");

		when(documentRepository.findById(1L)).thenReturn(Optional.of(documentEntity));

		// Act
		documentService.deleteDocument(1L);

		// Assert
		verify(documentRepository, times(1)).deleteById(1L);
		try {
			verify(minioService, times(1)).deleteFile(anyString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	void searchDocuments_shouldReturnListOfDocuments() {
		// Arrange
		List<DocumentEntity> documentEntities = new ArrayList<>();
		DocumentEntity documentEntity = new DocumentEntity();
		documentEntity.setId(1L);
		documentEntities.add(documentEntity);

		when(documentRepository.searchByQuery(anyString())).thenReturn(documentEntities);
		when(documentMapper.mapToDto(any(DocumentEntity.class))).thenReturn(documentDTO);

		// Act
		List<DocumentDTO> result = documentService.searchDocuments("Test");

		// Assert
		assertNotNull(result);
		assertEquals(1, result.size());
	}

//	@Test
//	void searchDocumentsInContent_shouldReturnListOfDocuments() {
//		// Arrange
//		List<Hit<DocumentIndex>> hits = new ArrayList<>();
//		DocumentEntity documentEntity = new DocumentEntity();
//		documentEntity.setId(1L);
//
//		try {
//			when(elasticsearchClient.search((SearchRequest) any(), eq(DocumentIndex.class))).thenReturn(new SearchResponse<>(hits));
//			when(documentRepository.findById(1L)).thenReturn(Optional.of(documentEntity));
//			when(documentMapper.mapToDto(any(DocumentEntity.class))).thenReturn(documentDTO);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//
//		// Act
//		List<DocumentDTO> result = documentService.searchDocumentsInContent("searchTerm");
//
//		// Assert
//		assertNotNull(result);
//		assertEquals(1, result.size());
//	}

}
