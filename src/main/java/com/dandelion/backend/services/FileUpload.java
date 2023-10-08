package com.dandelion.backend.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUpload {
    String uploadFile(MultipartFile multipartFile) throws IOException;

    void deleteFile(String publicId) throws IOException;
}
