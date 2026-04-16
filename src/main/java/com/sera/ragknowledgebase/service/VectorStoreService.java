package com.sera.ragknowledgebase.service;

import com.sera.ragknowledgebase.model.ChunkVector;
import com.sera.ragknowledgebase.model.VectorStoreUpsertResult;

import java.util.List;

public interface VectorStoreService {

    VectorStoreUpsertResult upsert(String fileName, List<ChunkVector> chunkVectors);
}
