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
public class BatchFileUploadResponse {
    private Long userId;
    private Integer totalFiles;
    private Integer successCount;
    private Integer failureCount;
    private List<FileUploadDetail> files;
    private String message;
}
