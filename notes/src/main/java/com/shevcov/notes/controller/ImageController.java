package com.shevcov.notes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final String uploadDir = "src/main/resources/static/uploads";

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            // Ensure upload directory exists
            Files.createDirectories(Paths.get(uploadDir));

            // Generate unique file name
            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);

            // Save file to disk
            Files.copy(file.getInputStream(), filePath);

            // Log the file path for debugging
            System.out.println("File saved to: " + filePath.toString());

            // Generate file download URI
            URI fileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(fileName)
                    .build()
                    .toUri();

            return ResponseEntity.ok().body("{\"imageUrl\": \"" + fileUri.toString() + "\"}");

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error saving image: " + e.getMessage());
        }
    }
}
