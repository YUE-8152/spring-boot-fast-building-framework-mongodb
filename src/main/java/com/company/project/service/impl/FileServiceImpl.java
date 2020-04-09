package com.company.project.service.impl;

import com.company.project.common.core.ServiceException;
import com.company.project.model.File;
import com.company.project.service.FileService;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import static com.sun.xml.internal.messaging.saaj.packaging.mime.util.ASCIIUtility.getBytes;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired(required = false)
    private GridFSBucket gridFSBucket;

    @Override
    public File uploadFileToMongo(MultipartFile multipartFile) {
        //获取上传文件名,包含后缀
        String filename = multipartFile.getOriginalFilename();
        //获取出入流
        InputStream inputStream = null;
        ObjectId objectId = null;
        try {
            inputStream = multipartFile.getInputStream();
            objectId = gridFsTemplate.store(inputStream, filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (objectId == null) {
            new ServiceException("文件上传失败！");
        }
        File file = new File();
        file.setId(objectId.toString());
        file.setFileId(objectId.toString());
        file.setFileName(filename);
        file.setUploadDate(new Date());
        file.setContentType(multipartFile.getContentType());
        file.setFileSize(multipartFile.getSize());
        return file;
    }

    @Override
    public String getFileStringByFileId(String fileId) {
        //根据id查询文件
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        if (gridFSFile == null) {
            new ServiceException("文件不存在！");
        }
        //打开下载流对象
        GridFSDownloadStream downloadStream =
                gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //创建gridFsResource，用于获取流对象
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, downloadStream);
        String fileString = null;
        try {
            //获取流中的数据
            fileString = IOUtils.toString(gridFsResource.getInputStream(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            downloadStream.close();
        }
        return fileString;
    }

    @Override
    public GridFsResource downloadFileById(String fileId) {
        //根据id查询文件
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        if (gridFSFile == null) {
            new ServiceException("文件不存在！");
        }
        //打开下载流对象
        GridFSDownloadStream downloadStream =
                gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //创建gridFsResource，用于获取流对象
        return new GridFsResource(gridFSFile, downloadStream);
    }

    @Override
    public boolean deleteFileByFileId(String fileId) {
        if (StringUtils.isNotBlank(fileId)) {
            //根据文件id删除fs.files和fs.chunks中的记录
            gridFsTemplate.delete(Query.query(Criteria.where("_id").is(fileId)));
        }
        return true;
    }
}
