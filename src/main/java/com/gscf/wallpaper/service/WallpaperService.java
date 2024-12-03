package com.gscf.wallpaper.service;

import com.gscf.wallpaper.dto.ResultDto;
import com.gscf.wallpaper.dto.UploadedRoomDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface WallpaperService {

    ResultDto uploadFile(MultipartFile file);

    ResultDto calculateAreas(List<UploadedRoomDto> roomList);
}
