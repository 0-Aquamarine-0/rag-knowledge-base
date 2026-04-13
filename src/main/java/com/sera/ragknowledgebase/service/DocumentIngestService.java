package com.sera.ragknowledgebase.service;

import com.sera.ragknowledgebase.dto.UploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentIngestService {

    UploadResponse ingest(MultipartFile file);
}
