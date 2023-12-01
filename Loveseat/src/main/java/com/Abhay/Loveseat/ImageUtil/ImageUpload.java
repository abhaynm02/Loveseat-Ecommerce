package com.Abhay.Loveseat.ImageUtil;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class ImageUpload {
    private String UPLOAD_FOLDER = "C:/Users/hp/Downloads/Loveseat/Loveseat/src/main/resources/static/Product-images";

    public boolean uploadImage(MultipartFile imageProduct) {
        boolean isUpload = false;
        try {
            Path destinationPath = Paths.get(UPLOAD_FOLDER, imageProduct.getOriginalFilename());
            Files.copy(imageProduct.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            isUpload = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpload;
    }
}