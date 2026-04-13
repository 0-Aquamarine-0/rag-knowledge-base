package com.sera.ragknowledgebase.service.impl;

import com.sera.ragknowledgebase.exception.DocumentProcessException;
import com.sera.ragknowledgebase.service.TextExtractor;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class TikaTextExtractor implements TextExtractor {

    private final Tika tika = new Tika();

    @Override
    public String extractText(MultipartFile file) {
        try {
            String text = tika.parseToString(file.getInputStream(), new Metadata());
            return text == null ? "" : text.trim();
        } catch (IOException e) {
            throw new DocumentProcessException("Failed to read uploaded file", e);
        } catch (Exception e) {
            throw new DocumentProcessException("Failed to extract text from file", e);
        }
    }
}
