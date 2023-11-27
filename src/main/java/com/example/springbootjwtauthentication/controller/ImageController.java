package com.example.springbootjwtauthentication.controller;

import com.example.springbootjwtauthentication.controller.service.implementations.ImageService;
import com.example.springbootjwtauthentication.model.Producer;
import com.example.springbootjwtauthentication.model.User;
import com.example.springbootjwtauthentication.repository.ProducerRepository;
import com.example.springbootjwtauthentication.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/img")
@Slf4j
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private JwtUtils jwt;

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(
            HttpServletRequest http,
            @RequestParam("image") MultipartFile img
    ) throws IOException {
        // Aquí puedes procesar la imagen como desees.
        // En este ejemplo, simplemente imprimimos el nombre del archivo.
        String fileName = img.getOriginalFilename();

        String imgName = imageService.saveImage(img);

        String userId = jwt.getUserNameFromJwtToken(http);

        //TODO 1 Buscar el productor por el id
        //TODO 2 Obtener sus
        //TODO
        //TODO
        //TODO


        log.info("S_Imagen recibida : {}", img);
        return ResponseEntity.ok("Imagen recibida: " + fileName);
    }
}
