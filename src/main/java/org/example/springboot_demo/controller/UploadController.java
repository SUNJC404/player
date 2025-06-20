package org.example.springboot_demo.controller;

import org.example.springboot_demo.Song;
import org.example.springboot_demo.repository.SongRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID; // To generate unique filenames

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Value("${app.upload.dir}")
    private String uploadDir; // This should be configured in application.properties

    private final SongRepository songRepository;

    public UploadController(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @PostMapping // Handles POST requests to /api/upload
    public ResponseEntity<?> uploadSong(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("artist") String artist) {
        try {
            if (file.isEmpty()) {
                Map<String, String> errorResponse = Map.of(
                        "status", "error",
                        "message", "Please select a file to upload."
                );
                return ResponseEntity.badRequest().body(errorResponse);
            }

            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
            Path filePath = uploadPath.resolve(uniqueFilename);

            Files.copy(file.getInputStream(), filePath);

            Song song = new Song(title, artist, uniqueFilename);

            Song savedSong = songRepository.save(song);

            Map<String, Object> successResponse = new HashMap<>();
            successResponse.put("status", "success");
            successResponse.put("message", "Song uploaded successfully!");

            successResponse.put("song", savedSong);

            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload song: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }
}