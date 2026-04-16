package com.sera.ragknowledgebase.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldUploadTextFileAndReturnChunks() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "sample.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "This is a simple RAG upload test document.".getBytes()
        );

        mockMvc.perform(multipart("/api/documents/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.documentId").isNotEmpty())
                .andExpect(jsonPath("$.fileName").value("sample.txt"))
                .andExpect(jsonPath("$.chunkCount").value(1))
                .andExpect(jsonPath("$.vectorStore").value("in-memory"))
                .andExpect(jsonPath("$.storedVectorCount").value(1))
                .andExpect(jsonPath("$.vectorIds[0]").isNotEmpty())
                .andExpect(jsonPath("$.chunkRepository").value("local-json-file"))
                .andExpect(jsonPath("$.savedChunkMetadataCount").value(1))
                .andExpect(jsonPath("$.chunkMetadataFilePath").isNotEmpty())
                .andExpect(jsonPath("$.chunks[0].documentId").isNotEmpty())
                .andExpect(jsonPath("$.chunks[0].fileName").value("sample.txt"))
                .andExpect(jsonPath("$.chunks[0].content").isNotEmpty());

        Path metadataFilePath = Path.of("target/document-chunks.json");
        assertTrue(Files.exists(metadataFilePath));
    }

    @Test
    void shouldRejectEmptyFileUpload() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "empty.txt",
                MediaType.TEXT_PLAIN_VALUE,
                new byte[0]
        );

        mockMvc.perform(multipart("/api/documents/upload").file(file))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Uploaded file must not be empty"))
                .andExpect(jsonPath("$.path").value("/api/documents/upload"));
    }
}
