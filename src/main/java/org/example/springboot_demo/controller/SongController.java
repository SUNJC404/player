package org.example.springboot_demo.controller;

import org.example.springboot_demo.Song;
import org.example.springboot_demo.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    @Autowired
    private SongService songService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // 处理文件上传
    public ResponseEntity<Song> uploadSong(@RequestPart("song") Song song,
                                           @RequestPart("file") MultipartFile file) {
        try {
            Song savedSong = songService.saveSong(song, file);
            return ResponseEntity.ok(savedSong);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }
    // 其他端点: GET /{id}, DELETE /{id}, GET /search?title=xxx&artist=yyy
}