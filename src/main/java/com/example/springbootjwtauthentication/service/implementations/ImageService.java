package com.example.springbootjwtauthentication.service.implementations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class ImageService {

    @Value("${upload.path}")
    private String uploadPath;  // Ruta donde se guardarán las imágenes

    public String saveImage(MultipartFile image) throws IOException {
        // Genera un nombre único para la imagen
        String fileName = generateFileName(Objects.requireNonNull(image.getOriginalFilename()));

        // Guarda la imagen en el sistema de archivos
        saveImageToFile(image, fileName);
        return fileName;
    }

    public byte[] loadImage(String fileName) throws IOException {
        // Lee la imagen del sistema de archivos
        Path imagePath = Paths.get(uploadPath).resolve(fileName);
        return Files.readAllBytes(imagePath);
    }

    private void saveImageToFile(MultipartFile image, String fileName) throws IOException {
        // Crea el directorio si no existe
        Path uploadPath = Paths.get(this.uploadPath);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Guarda la imagen en el sistema de archivos
        Path filePath = uploadPath.resolve(fileName);
        try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
            FileCopyUtils.copy(image.getInputStream(), fos);
        }
    }

    private String generateFileName(String originalFileName) {
        // Genera un nombre único para la imagen basado en el nombre original y la marca de tiempo actual
        return System.currentTimeMillis() + "_" + originalFileName;
    }
}
