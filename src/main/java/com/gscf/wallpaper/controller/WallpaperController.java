package com.gscf.wallpaper.controller;

import com.gscf.wallpaper.dto.ResultDto;
import com.gscf.wallpaper.service.WallpaperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "Wallpaper API")
@RequiredArgsConstructor
public class WallpaperController {

    private final WallpaperService wallpaperService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Endpoint for uploading TXT data file.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "The file has been successfully uploaded, and validated. All the needed calculations done."),
        @ApiResponse(responseCode = "400", description = "The file contains wrong data, or the formatting of the data is not acceptable."),
        @ApiResponse(responseCode = "500", description = "Server might not be running.")})
    public ResponseEntity<ResultDto> uploadFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(wallpaperService.uploadFile(file));
    }
}
