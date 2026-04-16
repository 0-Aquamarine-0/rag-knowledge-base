package com.sera.ragknowledgebase.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentChunkRecord {

    private String documentId;
    private String fileName;
    private String contentType;
    private Integer chunkIndex;
    private Integer contentLength;
    private String content;
    private String vectorId;
}
