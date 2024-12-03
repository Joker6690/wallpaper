package com.gscf.wallpaper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResultDto {

    private double totalSurfaceArea;

    private List<ResultRoomDto> cubicRoomList;

    private List<ResultRoomDto> identicalRooms;
}
