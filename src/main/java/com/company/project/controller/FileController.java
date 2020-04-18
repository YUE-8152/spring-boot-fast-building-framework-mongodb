package com.company.project.controller;

import com.company.project.common.core.Result;
import com.company.project.common.core.ResultGenerator;
import com.company.project.model.File;
import com.company.project.service.FileService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/uploadFile")
    public Result<File> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        File file = fileService.uploadFileToMongo(multipartFile);
        return ResultGenerator.success(file);
    }

    /**
     * 下载
     *
     * @param fileId 文件id
     * @return
     */
    @GetMapping(value = "/downloadFile/string")
    public Result<String> downloadFile(@RequestParam("fileId") String fileId) {
        String fileString = fileService.getFileStringByFileId(fileId);
        return ResultGenerator.success(fileString);
    }

    /**
     * 下载
     *
     * @param fileId 文件id
     * @return
     */
    @GetMapping(value = "/downloadFile")
    public void downloadFile(@RequestParam("fileId") String fileId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        GridFsResource gridFsResource = fileService.downloadFileById(fileId);
        String fileName = gridFsResource.getFilename().replace(",", "");
        //处理中文文件名乱码
        if (request.getHeader("User-Agent").toUpperCase().contains("MSIE") ||
                request.getHeader("User-Agent").toUpperCase().contains("TRIDENT")
                || request.getHeader("User-Agent").toUpperCase().contains("EDGE")) {
            fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
        } else {
            //非IE浏览器的处理：
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }
        // 通知浏览器进行文件下载
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
        IOUtils.copy(gridFsResource.getInputStream(), response.getOutputStream());
    }

    @GetMapping("/deleteFile")
    public Result deleteFileByFileId(@RequestParam("fileId") String fileId) {
        boolean result = fileService.deleteFileByFileId(fileId);
        return result ? ResultGenerator.success() : ResultGenerator.fail("删除失败！");
    }
}
