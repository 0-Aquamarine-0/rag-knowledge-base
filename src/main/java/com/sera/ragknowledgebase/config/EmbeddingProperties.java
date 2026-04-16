package com.sera.ragknowledgebase.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.embedding")
public class EmbeddingProperties {

    private Integer dimension = 8;
}
