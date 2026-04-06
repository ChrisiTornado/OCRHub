# OCRHub

OCRHub is a full-stack document management system for uploading PDF files, extracting their content with OCR, and making the recognized text searchable. The project was built as a microservice-based application and demonstrates the complete workflow from document upload to OCR processing, Elasticsearch indexing, frontend search, and dashboard-ready analytics data.

## Features

- PDF upload through a modern Angular frontend
- Document metadata management through a Spring Boot backend
- File storage with MinIO
- Asynchronous OCR processing with RabbitMQ
- OCR text extraction with Tesseract and Apache PDFBox
- Full-text search over recognized document content with Elasticsearch
- Document detail view that displays the extracted OCR text
- Kibana-ready Elasticsearch index with document metadata for dashboards
- Nginx reverse proxy for frontend and backend routing
- Docker Compose setup for local end-to-end execution
- GitHub Actions CI pipeline for automated builds, tests, and Docker Compose validation

## Tech Stack

### Frontend

- Angular
- TypeScript
- SCSS
- MDB Angular UI Kit
- ngx-translate

### Backend

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Data Elasticsearch
- PostgreSQL
- RabbitMQ
- MinIO client

### OCR Service

- Java 17
- Spring Boot
- Tesseract OCR
- Apache PDFBox
- RabbitMQ
- MinIO
- Elasticsearch

### Infrastructure and DevOps

- Docker
- Docker Compose
- Nginx
- PostgreSQL
- RabbitMQ Management
- MinIO
- Elasticsearch
- Kibana
- GitHub Actions

## Architecture

1. The user uploads a PDF document in the Angular frontend.
2. The backend receives the upload and stores the file in MinIO.
3. Document metadata is stored in PostgreSQL.
4. The backend publishes an OCR job to RabbitMQ.
5. The OCR service consumes the job, downloads the PDF from MinIO, and extracts text with Tesseract.
6. The recognized text and document metadata are stored in Elasticsearch.
7. The frontend can search documents by OCR content and display the recognized text in a document detail view.
8. Kibana can be used to inspect the Elasticsearch index and build dashboards for document statistics and OCR activity.

## Search and OCR

The main goal of OCRHub is to make documents searchable by their actual content. Instead of searching only by title or metadata, the system extracts text from uploaded PDFs and stores it in Elasticsearch. This allows users to search for terms that appear inside the document itself.

## GitHub Actions CI

The project includes a GitHub Actions workflow that runs automatically on pushes to `main` and `sprint-7`, as well as on pull requests. The pipeline:

- sets up Java 17 and Node.js 22
- runs backend tests with Maven
- runs OCR service tests with Maven
- installs frontend dependencies and builds the Angular application
- validates the Docker Compose configuration
- starts the full Docker Compose stack
- checks that Elasticsearch, the backend API, and the Nginx-served frontend are reachable
- shuts the stack down and removes CI volumes after the run

The workflow uses Docker Compose v2 with the `docker compose` command.

## Video Demo

At the end of the project, I created a video demonstration showing the complete workflow: