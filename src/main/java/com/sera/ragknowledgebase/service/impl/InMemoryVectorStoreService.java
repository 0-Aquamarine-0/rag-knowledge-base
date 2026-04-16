package com.sera.ragknowledgebase.service.impl;

import com.sera.ragknowledgebase.model.ChunkVector;
import com.sera.ragknowledgebase.model.VectorStoreUpsertResult;
import com.sera.ragknowledgebase.service.VectorStoreService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryVectorStoreService implements VectorStoreService {

    private final Map<String, ChunkVector> store = new ConcurrentHashMap<>();

    @Override
    public VectorStoreUpsertResult upsert(String fileName, List<ChunkVector> chunkVectors) {
        List<String> vectorIds = chunkVectors.stream()
                .map(chunkVector -> {
                    String vectorId = buildVectorId(fileName, chunkVector.getChunkIndex());
                    store.put(vectorId, chunkVector);
                    return vectorId;
                })
                .toList();

        return VectorStoreUpsertResult.builder()
                .storeName("in-memory")
                .storedCount(vectorIds.size())
                .vectorIds(vectorIds)
                .build();
    }

    private String buildVectorId(String fileName, Integer chunkIndex) {
        String safeFileName = fileName == null || fileName.isBlank() ? "unknown-file" : fileName;
        String safeChunkIndex = chunkIndex == null ? "0" : chunkIndex.toString();
        return safeFileName + "-" + safeChunkIndex + "-" + UUID.randomUUID();
    }
}
