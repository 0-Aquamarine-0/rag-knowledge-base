package com.sera.ragknowledgebase.service.impl;

import com.sera.ragknowledgebase.dto.UploadResponse;
import com.sera.ragknowledgebase.exception.DocumentProcessException;
import com.sera.ragknowledgebase.model.Chunk;
import com.sera.ragknowledgebase.service.DocumentIngestService;
import com.sera.ragknowledgebase.service.TextChunker;
import com.sera.ragknowledgebase.service.TextExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentIngestServiceImpl implements DocumentIngestService {

    private final TextExtractor textExtractor;
    private final TextChunker textChunker;

    @Override
    public UploadResponse ingest(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new DocumentProcessException("Uploaded file must not be empty");
        }

        String text = textExtractor.extractText(file);
        if (text.isBlank()) {
            throw new DocumentProcessException("No readable text could be extracted from the uploaded file");
        }

        List<Chunk> chunks = textChunker.chunk(text);

        return UploadResponse.builder()
                .fileName(file.getOriginalFilename())
                .contentType(file.getContentType())
                .textLength(text.length())
                .chunkCount(chunks.size())
                .chunks(chunks)
                .build();
    }
}
