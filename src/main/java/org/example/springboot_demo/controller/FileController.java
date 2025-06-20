package org.example.springboot_demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Value("${app.upload.dir}")
    private String uploadDir;

    @GetMapping("/{filename:.+}") // :.+ 确保能匹配带后缀的文件名
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            // 1. 根据文件名构造文件路径
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            // 2. 检查文件是否存在且可读
            if (!Files.exists(filePath) || !Files.isReadable(filePath)) {
                return ResponseEntity.notFound().build();
            }
            // 3. 创建 Resource 对象
            Resource resource = new UrlResource(filePath.toUri());
            // 4. 确定 MIME 类型 (可选，但推荐)
            String contentType = determineContentType(filePath);
            // 5. 构建响应
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"") // inline 表示浏览器尝试直接播放
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String determineContentType(Path filePath) throws IOException {
        // 简单实现：根据文件扩展名猜测。更准确可用 Files.probeContentType
        String fileName = filePath.getFileName().toString().toLowerCase();
        if (fileName.endsWith(".mp3")) return "audio/mpeg";
        else if (fileName.endsWith(".wav")) return "audio/wav";
        else if (fileName.endsWith(".ogg")) return "audio/ogg";
        // ... 添加其他支持的格式
        return MediaType.APPLICATION_OCTET_STREAM_VALUE; // 默认二进制流
    }
}
