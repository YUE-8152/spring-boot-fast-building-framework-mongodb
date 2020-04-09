package com.company.project.service;

import com.company.project.model.File;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    File uploadFileToMongo(MultipartFile file);

    String getFileStringByFileId(String fileId);

    boolean deleteFileByFileId(String fileId);

    GridFsResource downloadFileById(String fileId);
}
