package com.sera.ragknowledgebase.dto;

import com.sera.ragknowledgebase.model.Chunk;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UploadResponse {

    private String documentId;
    private String fileName;
    private String contentType;
    private Integer textLength;
    private Integer chunkCount;
    private String vectorStore;
    private Integer storedVectorCount;
    private List<String> vectorIds;
    private String chunkRepository;
    private Integer savedChunkMetadataCount;
    private String chunkMetadataFilePath;
    private List<Chunk> chunks;
}
