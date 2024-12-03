package com.gscf.wallpaper.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ResultRoomDto {

    @EqualsAndHashCode.Exclude
    private int roomNo;

    private double length;

    private double width;

    private double height;

    private double totalSurfaceArea;

    @JsonIgnore
    public boolean isCubic() {
        return length == width && width == height;
    }
}
