package com.company.project.model;

import org.bson.types.Binary;

import java.util.Date;

public class File {
    private String id;
    private String fileId;
    private String fileName; // 文件名称
    private String contentType; // 文件类型
    private long fileSize;
    private Date uploadDate;
    private Binary content; // 文件内容
    private String path; // 文件路径
    private String md5;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Binary getContent() {
        return content;
    }

    public void setContent(Binary content) {
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public File() {
    }

    public File(String id, String fileId, String fileName, String contentType, long fileSize, Date uploadDate, Binary content, String path, String md5) {
        this.id = id;
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.uploadDate = uploadDate;
        this.content = content;
        this.path = path;
        this.md5 = md5;
    }
}
