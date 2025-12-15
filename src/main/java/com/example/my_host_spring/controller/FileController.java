package com.example.my_host_spring.controller;


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

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    // 配置照片和音乐目录路径
// 使用绝对路径
    @Value("${app.photo-dir:/data/data/com.termux/files/home/storage/dcim/Camera}")
    private String photoDir;

    @Value("${app.music-dir:/data/data/com.termux/files/home/storage/music}")
    private String musicDir;

    // 获取照片列表
    @GetMapping("/photos")
    public ResponseEntity<List<FileInfo>> getPhotoList() {
        List<FileInfo> photos = getFilesFromDirectory(photoDir,
                new String[]{"jpg", "jpeg", "png", "gif"});
        return ResponseEntity.ok(photos);
    }

    // 获取音乐列表
    @GetMapping("/musics")
    public ResponseEntity<List<FileInfo>> getMusicList() {
        List<FileInfo> musics = getFilesFromDirectory(musicDir,
                new String[]{"mp3", "wav", "ogg", "m4a"});
        return ResponseEntity.ok(musics);
    }

    // 提供照片文件流
    @GetMapping("/photo/{filename:.+}")
    public ResponseEntity<Resource> getPhotoFile(@PathVariable String filename) {
        return serveFile(photoDir, filename);
    }

    // 提供音乐文件流
    @GetMapping("/music/{filename:.+}")
    public ResponseEntity<Resource> getMusicFile(@PathVariable String filename) {
        return serveFile(musicDir, filename);
    }

    private List<FileInfo> getFilesFromDirectory(String directoryPath, String[] extensions) {
        List<FileInfo> files = new ArrayList<>();
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] fileList = directory.listFiles((dir, name) -> {
                for (String ext : extensions) {
                    if (name.toLowerCase().endsWith("." + ext)) {
                        return true;
                    }
                }
                return false;
            });

            if (fileList != null) {
                for (File file : fileList) {
                    files.add(new FileInfo(file.getName(), file.getAbsolutePath(), file.length()));
                }
            }
        }
        return files;
    }

    private ResponseEntity<Resource> serveFile(String directory, String filename) {
        try {
            Path filePath = Paths.get(directory).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = determineContentType(filename);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String determineContentType(String filename) {
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        switch (extension) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "mp3":
                return "audio/mpeg";
            case "wav":
                return "audio/wav";
            default:
                return "application/octet-stream";
        }
    }

    // 文件信息DTO
    public static class FileInfo {
        private String name;
        private String path;
        private long size;

        public FileInfo(String name, String path, long size) {
            this.name = name;
            this.path = path;
            this.size = size;
        }

        // getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getPath() { return path; }
        public void setPath(String path) { this.path = path; }

        public long getSize() { return size; }
        public void setSize(long size) { this.size = size; }
    }
}