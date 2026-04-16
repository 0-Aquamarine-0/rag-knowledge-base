package com.sera.ragknowledgebase.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentChunkSaveResult {

    private String repositoryName;
    private Integer savedCount;
    private String filePath;
}
