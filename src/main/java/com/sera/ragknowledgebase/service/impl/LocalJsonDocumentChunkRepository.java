package com.sera.ragknowledgebase.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sera.ragknowledgebase.config.DocumentChunkRepositoryProperties;
import com.sera.ragknowledgebase.exception.DocumentProcessException;
import com.sera.ragknowledgebase.model.DocumentChunkRecord;
import com.sera.ragknowledgebase.model.DocumentChunkSaveResult;
import com.sera.ragknowledgebase.service.DocumentChunkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LocalJsonDocumentChunkRepository implements DocumentChunkRepository {

    private final DocumentChunkRepositoryProperties properties;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public synchronized DocumentChunkSaveResult saveAll(List<DocumentChunkRecord> chunkRecords) {
        Path filePath = Path.of(properties.getFilePath()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(filePath.getParent());

            List<DocumentChunkRecord> existingRecords = new ArrayList<>();
            if (Files.exists(filePath) && Files.size(filePath) > 0) {
                existingRecords.addAll(objectMapper.readValue(
                        filePath.toFile(),
                        new TypeReference<List<DocumentChunkRecord>>() {
                        }
                ));
            }

            existingRecords.addAll(chunkRecords);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), existingRecords);

            return DocumentChunkSaveResult.builder()
                    .repositoryName(properties.getRepositoryName())
                    .savedCount(chunkRecords.size())
                    .filePath(filePath.toString())
                    .build();
        } catch (IOException e) {
            throw new DocumentProcessException("Failed to persist chunk metadata locally", e);
        }
    }
}
