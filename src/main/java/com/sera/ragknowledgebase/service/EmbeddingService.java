package com.sera.ragknowledgebase.service;

import java.util.List;

public interface EmbeddingService {

    List<Double> embed(String text);
}
