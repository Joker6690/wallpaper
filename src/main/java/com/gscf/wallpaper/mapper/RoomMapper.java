package com.gscf.wallpaper.mapper;

import com.gscf.wallpaper.dto.ResultRoomDto;
import com.gscf.wallpaper.dto.UploadedRoomDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {

    @Mapping(source = "dto.rowNum", target = "roomNo")
    @Mapping(source = "dto.length", target = "length")
    @Mapping(source = "dto.width", target = "width")
    @Mapping(source = "dto.height", target = "height")
    @Mapping(source = "totalSurfaceArea", target = "totalSurfaceArea")
    ResultRoomDto toResultRoom(UploadedRoomDto dto, double totalSurfaceArea);
}
