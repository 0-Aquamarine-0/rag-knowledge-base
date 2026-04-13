package com.sera.ragknowledgebase.service;

import org.springframework.web.multipart.MultipartFile;

public interface TextExtractor {

    String extractText(MultipartFile file);
}
