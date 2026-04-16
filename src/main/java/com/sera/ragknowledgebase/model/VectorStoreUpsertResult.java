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
public class VectorStoreUpsertResult {

    private String storeName;
    private Integer storedCount;
    private List<String> vectorIds;
}
