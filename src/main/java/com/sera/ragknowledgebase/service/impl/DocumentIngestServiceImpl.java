package com.sera.ragknowledgebase.service.impl;

import com.sera.ragknowledgebase.dto.UploadResponse;
import com.sera.ragknowledgebase.exception.DocumentProcessException;
import com.sera.ragknowledgebase.model.Chunk;
import com.sera.ragknowledgebase.model.ChunkVector;
import com.sera.ragknowledgebase.model.DocumentChunkRecord;
import com.sera.ragknowledgebase.model.DocumentChunkSaveResult;
import com.sera.ragknowledgebase.model.VectorStoreUpsertResult;
import com.sera.ragknowledgebase.service.DocumentChunkRepository;
import com.sera.ragknowledgebase.service.DocumentIngestService;
import com.sera.ragknowledgebase.service.EmbeddingService;
import com.sera.ragknowledgebase.service.TextChunker;
import com.sera.ragknowledgebase.service.TextExtractor;
import com.sera.ragknowledgebase.service.VectorStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentIngestServiceImpl implements DocumentIngestService {

    private final TextExtractor textExtractor;
    private final TextChunker textChunker;
    private final EmbeddingService embeddingService;
    private final VectorStoreService vectorStoreService;
    private final DocumentChunkRepository documentChunkRepository;

    @Override
    public UploadResponse ingest(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new DocumentProcessException("Uploaded file must not be empty");
        }

        String text = textExtractor.extractText(file);
        if (text.isBlank()) {
            throw new DocumentProcessException("No readable text could be extracted from the uploaded file");
        }

        String documentId = UUID.randomUUID().toString();
        List<Chunk> chunks = textChunker.chunk(text).stream()
                .map(chunk -> Chunk.builder()
                        .documentId(documentId)
                        .fileName(file.getOriginalFilename())
                        .index(chunk.getIndex())
                        .content(chunk.getContent())
                        .build())
                .toList();
        List<ChunkVector> chunkVectors = chunks.stream()
                .map(chunk -> ChunkVector.builder()
                        .chunkIndex(chunk.getIndex())
                        .content(chunk.getContent())
                        .embedding(embeddingService.embed(chunk.getContent()))
                        .build())
                .toList();
        VectorStoreUpsertResult vectorStoreUpsertResult =
                vectorStoreService.upsert(file.getOriginalFilename(), chunkVectors);
        List<DocumentChunkRecord> chunkRecords = buildChunkRecords(
                documentId,
                file.getOriginalFilename(),
                file.getContentType(),
                chunks,
                vectorStoreUpsertResult.getVectorIds()
        );
        DocumentChunkSaveResult documentChunkSaveResult = documentChunkRepository.saveAll(chunkRecords);

        return UploadResponse.builder()
                .documentId(documentId)
                .fileName(file.getOriginalFilename())
                .contentType(file.getContentType())
                .textLength(text.length())
                .chunkCount(chunks.size())
                .vectorStore(vectorStoreUpsertResult.getStoreName())
                .storedVectorCount(vectorStoreUpsertResult.getStoredCount())
                .vectorIds(vectorStoreUpsertResult.getVectorIds())
                .chunkRepository(documentChunkSaveResult.getRepositoryName())
                .savedChunkMetadataCount(documentChunkSaveResult.getSavedCount())
                .chunkMetadataFilePath(documentChunkSaveResult.getFilePath())
                .chunks(chunks)
                .build();
    }

    private List<DocumentChunkRecord> buildChunkRecords(
            String documentId,
            String fileName,
            String contentType,
            List<Chunk> chunks,
            List<String> vectorIds
    ) {
        return chunks.stream()
                .map(chunk -> {
                    int chunkIndex = chunk.getIndex();
                    String vectorId = chunkIndex < vectorIds.size() ? vectorIds.get(chunkIndex) : null;
                    return DocumentChunkRecord.builder()
                            .documentId(documentId)
                            .fileName(fileName)
                            .contentType(contentType)
                            .chunkIndex(chunkIndex)
                            .contentLength(chunk.getContent().length())
                            .content(chunk.getContent())
                            .vectorId(vectorId)
                            .build();
                })
                .toList();
    }
}
