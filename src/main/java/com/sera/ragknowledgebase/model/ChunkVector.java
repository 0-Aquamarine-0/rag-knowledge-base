package com.sera.ragknowledgebase.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChunkVector {

    private Integer chunkIndex;
    private String content;
    private List<Double> embedding;
}
