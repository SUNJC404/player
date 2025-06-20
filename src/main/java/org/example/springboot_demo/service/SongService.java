package org.example.springboot_demo.service;

import org.example.springboot_demo.Song;
import org.example.springboot_demo.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;


@Service
public class SongService {
    @Value("${app.upload.dir}")
    private String uploadDir;

    @Autowired
    private SongRepository songRepository;

    public Song saveSong(Song song, MultipartFile file) throws IOException {
        // 1. 处理文件上传
        if (!file.isEmpty()) {
            // 生成唯一文件名 (避免覆盖)
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            // 构造目标文件路径
            Path filePath = Paths.get(uploadDir, fileName);
            // 保存文件
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            // 设置 song 的文件路径 (存储相对路径或文件名)
            song.setFilePath(fileName); // 或者 "/uploads/" + fileName
        }
        // 2. 保存歌曲信息到数据库
        return songRepository.save(song);
    }

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }
}