package com.sera.ragknowledgebase.service;

import com.sera.ragknowledgebase.model.DocumentChunkRecord;
import com.sera.ragknowledgebase.model.DocumentChunkSaveResult;

import java.util.List;

public interface DocumentChunkRepository {

    DocumentChunkSaveResult saveAll(List<DocumentChunkRecord> chunkRecords);
}
