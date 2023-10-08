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
    private static final String FOLDER_NAME = "uploads";

    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {

        String uniqueId = UUID.randomUUID().toString();
        String publicId = FOLDER_NAME + "/" + uniqueId;

        // Determine the resource type based on the file's content type
        String resourceType = "auto";

        if (multipartFile.getContentType() != null) {
            if (multipartFile.getContentType().startsWith("image")) {
                resourceType = "image";
            } else if (multipartFile.getContentType().startsWith("video")) {
                resourceType = "video";
            }
        }

        Map<?, ?> uploadResult = cloudinary.uploader()
                .upload(multipartFile.getBytes(), ObjectUtils.asMap(
                        "public_id", publicId,
                        "resource_type", resourceType
                ));

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

    @Override
    public String extractPublicId(String url) {

        Integer firstIndex = url.indexOf(FOLDER_NAME);
        Integer lastIndex = url.lastIndexOf(".");

        if (url != null) {
            return url.substring(firstIndex, lastIndex);
        }

        return null;
    }
}
