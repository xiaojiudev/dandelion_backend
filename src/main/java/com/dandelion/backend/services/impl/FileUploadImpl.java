package com.dandelion.backend.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dandelion.backend.services.FileUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadImpl implements FileUpload {

    private final Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {

        String folderName = "uploads";

        String uniqueId = UUID.randomUUID().toString();
        String publicId = folderName + "/" + uniqueId;

        Map<?, ?> uploadResult = cloudinary.uploader()
                .upload(multipartFile.getBytes(), Map.of("public_id", publicId));

        String url = uploadResult.get("url").toString();

        return url;

    }

    @Override
    public void deleteFile(String publicId) throws IOException {

//        Example publicId: uploads/88205ba1-402d-4bfc-8403-636ab319cdd1

        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
