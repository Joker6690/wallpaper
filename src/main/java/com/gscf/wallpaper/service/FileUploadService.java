package com.gscf.wallpaper.service;

import com.gscf.wallpaper.dto.UploadedRoomDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadService {

    String uploadFile(MultipartFile file);

    List<UploadedRoomDto> readFile(String path);

}
