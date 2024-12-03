package com.gscf.wallpaper.service.impl;

import com.gscf.wallpaper.dto.UploadedRoomDto;
import com.gscf.wallpaper.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${wallpaper-api.file-directory}")
    private String fileUploadDir;

    public String uploadFile(MultipartFile file) {
        deleteFile();
        createDirectory();
        var path = fileUploadDir.concat(file.getOriginalFilename());
        try (var outputStream = new FileOutputStream(path)) {
            outputStream.write(file.getBytes());
            return path;
        } catch (Exception e) {
            throw new RuntimeException("File upload failed!", e);
        }
    }

    public List<UploadedRoomDto> readFile(String path) {
        try (var reader = Files.newBufferedReader(Path.of(path))) {
            String line;
            int lineCount = 0;
            List<UploadedRoomDto> roomList = new ArrayList<>();
            while((line = reader.readLine()) != null) {
                roomList.add(getRoomFromLine(line, lineCount));
                lineCount++;
            }
            return roomList;
        } catch (IOException e) {
            throw new RuntimeException("File reading failed!", e);
        }
    }

    private UploadedRoomDto getRoomFromLine(String line, int lineCount) {
        if (!validateLine(line)) {
            throw new RuntimeException(String.format("Malformatted input at line: %s!", lineCount));
        }
        var dimensions = Arrays.stream(line.split("x")).map(Double::valueOf).toList();
        return new UploadedRoomDto(lineCount, dimensions.get(0), dimensions.get(1), dimensions.get(2));

    }

    private boolean validateLine(String line) {
        return line.matches("^(\\d*\\.)?\\d+x(\\d*\\.)?\\d+x(\\d*\\.)?\\d+$");
    }

    private void deleteFile() {
        var uploadDir = new File(fileUploadDir);
        if (uploadDir.exists() && uploadDir.isDirectory() && uploadDir.listFiles() != null && uploadDir.listFiles().length != 0) {
            Arrays.stream(uploadDir.listFiles()).forEach(File::delete);
        }
    }

    private void createDirectory() {
        if (Files.notExists(Path.of(fileUploadDir))) {
            new File(fileUploadDir).mkdir();
        }
    }
}
