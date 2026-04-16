# Project Context

## Goal

This project is a learning-oriented RAG knowledge base demo for interview preparation and vibe coding practice.

The goal is not to build a production-ready system. The goal is to understand the end-to-end flow well enough to explain it clearly and continue implementing it step by step.

## Day 1 Scope

Only focus on this main chain:

`document upload -> document parsing/chunking -> embedding -> vector database write -> user question -> retrieval -> prompt assembly -> LLM answer`

Do not implement these for now:

- long-term memory extraction
- Elasticsearch dual-engine retrieval
- complex frontend
- full evaluation system

## Current Structure

The current Spring Boot project uses these package areas:

- `controller`
- `service`
- `config`
- `dto`
- `exception`
- `model`

## Current Progress

The upload-side minimal flow has been implemented:

- `POST /api/documents/upload`
- upload controller delegates to ingest service
- Tika-based text extraction
- simple text chunking
- placeholder embedding generation
- placeholder vector store upsert
- local JSON file persistence for chunk text and metadata
- unified error response
- minimal MockMvc tests

Main related files:

- `src/main/java/com/sera/ragknowledgebase/controller/DocumentController.java`
- `src/main/java/com/sera/ragknowledgebase/service/impl/DocumentIngestServiceImpl.java`
- `src/main/java/com/sera/ragknowledgebase/service/impl/TikaTextExtractor.java`
- `src/main/java/com/sera/ragknowledgebase/service/impl/SimpleTextChunker.java`
- `src/main/java/com/sera/ragknowledgebase/service/impl/SimpleHashEmbeddingService.java`
- `src/main/java/com/sera/ragknowledgebase/service/impl/InMemoryVectorStoreService.java`
- `src/main/java/com/sera/ragknowledgebase/service/impl/LocalJsonDocumentChunkRepository.java`
- `src/main/java/com/sera/ragknowledgebase/exception/GlobalExceptionHandler.java`
- `src/test/java/com/sera/ragknowledgebase/controller/DocumentControllerTest.java`

## Verified State

`mvn test` passes.

The upload test chain is working for:

- normal text file upload
- empty file rejection
- upload response includes vector store write result
- chunk metadata is written to `target/document-chunks.json`

## Important Notes

- This project currently uses Spring Boot `4.0.5`.
- Spring Boot 4 changed the MockMvc test annotation package.
- `AutoConfigureMockMvc` should be imported from:
  `org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc`
- IDEA showed dependency vulnerability warnings for transitive dependencies. For now, these are acknowledged but intentionally not treated as a blocker because this is a local learning project.

## Suggested Next Step

Continue the main chain by adding a minimal placeholder implementation for:

- real Pinecone-backed vector store implementation
- support verification for `txt`, `md`, and `pdf`
- a retrieval endpoint for the later question-answer flow

The idea is to keep moving toward the complete RAG flow without introducing too much production complexity too early.

## How To Resume Later

If a future chat loses context, ask Codex to:

`Please read PROJECT_CONTEXT.md first, then continue from the current project state.`
