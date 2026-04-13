package com.sera.ragknowledgebase.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.upload")
public class UploadProperties {

    private Integer chunkSize = 500;
    private Integer overlap = 100;
    private Integer maxFileSize = 5;
}
