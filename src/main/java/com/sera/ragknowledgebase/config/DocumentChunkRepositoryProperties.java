package com.sera.ragknowledgebase.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.document-chunk-repository")
public class DocumentChunkRepositoryProperties {

    private String filePath = "target/document-chunks.json";
    private String repositoryName = "local-json-file";
}
