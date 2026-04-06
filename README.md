<div align="center">

# 📄 OCRHub

### Searchable document intelligence powered by OCR, microservices, and modern DevOps

<p align="center">
  <img src="https://readme-typing-svg.herokuapp.com?font=Fira+Code&weight=600&size=22&pause=1000&color=2563EB&center=true&vCenter=true&width=900&lines=Upload+PDFs+and+extract+text+with+OCR;Search+documents+by+their+real+content;Built+with+Angular%2C+Spring+Boot%2C+Elasticsearch+and+RabbitMQ;Microservice-based+document+processing+pipeline" alt="Typing SVG" />
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Angular-Frontend-DD0031?style=flat-square&logo=angular&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Boot-Backend-6DB33F?style=flat-square&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/Elasticsearch-Search-005571?style=flat-square&logo=elasticsearch&logoColor=white" />
  <img src="https://img.shields.io/badge/RabbitMQ-Async-FF6600?style=flat-square&logo=rabbitmq&logoColor=white" />
  <img src="https://img.shields.io/badge/Docker-Compose-2496ED?style=flat-square&logo=docker&logoColor=white" />
</p>

</div>

---

## ✨ Vision

**OCRHub** is a full-stack document management platform that turns uploaded PDF files into **searchable knowledge**.  
It demonstrates the complete workflow from **document upload** and **OCR processing** to **Elasticsearch indexing**, **frontend search**, and **dashboard-ready analytics**.

The goal is simple: make document content accessible, searchable, and usable through a modern microservice architecture.

---

## 🎥 Video Demo

https://github.com/user-attachments/assets/0175a6de-f596-4c85-b1fd-0a79016afeb6

---

## 🚀 Features

### 📤 Document Upload & Management
- Upload PDF documents through a modern Angular frontend
- Manage document metadata through a Spring Boot backend
- Store uploaded files in MinIO object storage

### 🔎 OCR & Search
- Asynchronous OCR processing with RabbitMQ
- Text extraction using Tesseract and Apache PDFBox
- Full-text search over recognized document content with Elasticsearch
- Document detail view with extracted OCR text

### 📊 Analytics & Infrastructure
- Kibana-ready Elasticsearch index for analytics and dashboards
- Nginx reverse proxy for frontend and backend routing
- Docker Compose setup for local end-to-end execution
- GitHub Actions CI pipeline for automated build and validation

---

## 🧰 Tech Stack

### 🎨 Frontend
- Angular
- TypeScript
- SCSS
- MDB Angular UI Kit
- ngx-translate

### ⚙️ Backend
- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Data Elasticsearch
- PostgreSQL
- RabbitMQ
- MinIO Client

### 🧠 OCR Service
- Java 17
- Spring Boot
- Tesseract OCR
- Apache PDFBox
- RabbitMQ
- MinIO
- Elasticsearch

### 🛠️ Infrastructure & DevOps
- Docker
- Docker Compose
- Nginx
- PostgreSQL
- RabbitMQ Management
- MinIO
- Elasticsearch
- Kibana
- GitHub Actions

---

## 🏗️ Architecture

```text
ocrhub
├── frontend
│   └── Angular application
├── backend
│   └── Spring Boot document management service
├── ocr-service
│   └── Spring Boot OCR processing service
├── nginx
│   └── Reverse proxy configuration
└── docker-compose.yml
