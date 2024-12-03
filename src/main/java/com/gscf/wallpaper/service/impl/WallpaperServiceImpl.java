package com.gscf.wallpaper.service.impl;

import com.gscf.wallpaper.dto.ResultDto;
import com.gscf.wallpaper.dto.ResultRoomDto;
import com.gscf.wallpaper.dto.UploadedRoomDto;
import com.gscf.wallpaper.mapper.RoomMapper;
import com.gscf.wallpaper.service.FileUploadService;
import com.gscf.wallpaper.service.WallpaperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WallpaperServiceImpl implements WallpaperService {

    private final FileUploadService fileUploadService;

    private final RoomMapper roomMapper;

    private List<UploadedRoomDto> roomList;

    @Override
    public ResultDto uploadFile(MultipartFile file) {
        String tmp = fileUploadService.uploadFile(file);
        this.roomList = fileUploadService.readFile(tmp);
        return calculateAreas(this.roomList);
    }

    @Override
    public ResultDto calculateAreas(List<UploadedRoomDto> roomList) {
        double totalWallpaperNeeded = 0D;
        List<ResultRoomDto> resultRoomDtos = new ArrayList<>();
        ResultDto result = new ResultDto();
        for(var room : roomList) {
            totalWallpaperNeeded += getTotalSurfaceArea(room, resultRoomDtos);
        }
        result.setCubicRoomList(resultRoomDtos.stream()
                .filter(ResultRoomDto::isCubic)
                .sorted(Comparator.comparingDouble(ResultRoomDto::getTotalSurfaceArea))
                .toList());
        result.setIdenticalRooms(resultRoomDtos.stream()
                .filter(room -> Collections.frequency(resultRoomDtos, room) > 1)
                .sorted(Comparator.comparingDouble(ResultRoomDto::getLength))
                .sorted(Comparator.comparingDouble(ResultRoomDto::getWidth))
                .sorted(Comparator.comparingDouble(ResultRoomDto::getHeight))
                .toList());
        result.setTotalSurfaceArea(totalWallpaperNeeded);
        return result;

    }

    private double getTotalSurfaceArea(UploadedRoomDto room, List<ResultRoomDto> resultRoomDtos) {
        List<Double> surfaceAreas = getSurfaceAreas(room);
        var smallestWall = surfaceAreas.stream().mapToDouble(x -> x).min().getAsDouble();
        var totalSurfaceArea = smallestWall + surfaceAreas.stream().mapToDouble(wall -> wall*2).sum();
        resultRoomDtos.add(roomMapper.toResultRoom(room, totalSurfaceArea));
        return totalSurfaceArea;
    }

    private List<Double> getSurfaceAreas(UploadedRoomDto room) {
        List<Double> surfaceAreas = new ArrayList<>();
        surfaceAreas.add(room.getHeight() * room.getWidth());
        surfaceAreas.add(room.getWidth() * room.getLength());
        surfaceAreas.add(room.getLength() * room.getHeight());
        return surfaceAreas;
    }

}
