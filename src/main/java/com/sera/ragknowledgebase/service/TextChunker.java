package com.sera.ragknowledgebase.service;

import com.sera.ragknowledgebase.model.Chunk;

import java.util.List;

public interface TextChunker {

    List<Chunk> chunk(String text);
}
