package com.sera.ragknowledgebase.service.impl;

import com.sera.ragknowledgebase.config.UploadProperties;
import com.sera.ragknowledgebase.model.Chunk;
import com.sera.ragknowledgebase.service.TextChunker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleTextChunker implements TextChunker {

    private final UploadProperties uploadProperties;

    @Override
    public List<Chunk> chunk(String text) {
        List<Chunk> chunks = new ArrayList<>();
        if (text == null || text.isBlank()) {
            return chunks;
        }

        int chunkSize = uploadProperties.getChunkSize();
        int overlap = uploadProperties.getOverlap();
        int step = Math.max(1, chunkSize - overlap);
        int index = 0;

        for (int start = 0; start < text.length(); start += step) {
            int end = Math.min(start + chunkSize, text.length());
            String content = text.substring(start, end).trim();
            if (!content.isEmpty()) {
                chunks.add(Chunk.builder()
                        .index(index++)
                        .content(content)
                        .build());
            }
            if (end >= text.length()) {
                break;
            }
        }

        return chunks;
    }
}
