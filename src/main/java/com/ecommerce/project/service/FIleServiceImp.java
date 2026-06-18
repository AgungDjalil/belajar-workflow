package com.ecommerce.project.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FIleServiceImp implements FileService {
    
    @Override
    public String uploadImage(String path, MultipartFile imageFile) throws IOException {
        String originalFileName = imageFile.getOriginalFilename();
        String randomID = UUID.randomUUID().toString();
        String fileName = randomID.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        String filePath = path + File.separator + fileName;

        File folder = new File(path);
        if(!folder.exists())
            folder.mkdirs();

        Files.copy(imageFile.getInputStream(), Paths.get(filePath));
        return fileName;
    }
}
