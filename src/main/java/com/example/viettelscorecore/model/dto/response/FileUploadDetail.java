package com.example.viettelscorecore.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadDetail {
    private String fileName;
    private String objectName;
    private Long fileSize;
    private String contentType;
    private String status;
    private String message;
}
