package com.sera.ragknowledgebase.service.impl;

import com.sera.ragknowledgebase.config.EmbeddingProperties;
import com.sera.ragknowledgebase.service.EmbeddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleHashEmbeddingService implements EmbeddingService {

    private final EmbeddingProperties embeddingProperties;

    @Override
    public List<Double> embed(String text) {
        int dimension = Math.max(1, embeddingProperties.getDimension());
        List<Double> vector = new ArrayList<>(dimension);
        for (int i = 0; i < dimension; i++) {
            vector.add(0.0D);
        }

        if (text == null || text.isBlank()) {
            return vector;
        }

        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int bucket = i % dimension;
            double value = vector.get(bucket);
            value += chars[i];
            vector.set(bucket, value);
        }

        double normalizer = chars.length;
        for (int i = 0; i < vector.size(); i++) {
            vector.set(i, Math.round((vector.get(i) / normalizer) * 1000.0D) / 1000.0D);
        }

        return vector;
    }
}
