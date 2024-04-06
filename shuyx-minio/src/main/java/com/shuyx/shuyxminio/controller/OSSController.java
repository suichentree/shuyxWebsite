package com.shuyx.shuyxminio.controller;

import com.alibaba.fastjson.JSONObject;
import com.shuyx.shuyxcommons.utils.ResultCodeEnum;
import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxminio.service.OSSService;
import com.shuyx.shuyxminio.utils.MinioUtils;
import io.minio.CreateMultipartUploadResponse;
import io.minio.errors.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/shuyx-minio/oss")
public class OSSController {
    @Autowired
    private MinioUtils minioUtils;

    @Autowired
    private OSSService ossService;

    //用户头像文件对象桶
    private String USER_AVATAR_BUCKET = "user-avatar-bucket";
    //媒体剧集文件对象桶
    private String MEDIA_EPISODES_BUCKET = "media-episodes-bucket";

    /**
     * 上传文件接口
     * @param file
     */
    @PostMapping("/upload")
    public Object upload(@RequestParam("file") MultipartFile file,String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("/shuyx-minio/oss/upload, file,{}",file);
        double size = (double) (file.getSize() / 1024 / 1024) + 1;
        log.info(file.getOriginalFilename() +" 文件大小为 "+size+" MB之内。");
        return ossService.uploadFile(file,bucketName);
    }

    /**
     * 创建分片文件请求等信息
     * 逻辑：
     * 1. minio接收到前端发来的创建分片文件请求。会返回对应的uploadId
     * 2. 然后通过uploadId以及分片数量，创建同等数量的预签名链接。并返回
     * @return
     */
    @PostMapping("/createMultipartUpload")
    public Object createMultipartUpload(String fileName,String bucketName,Integer chunkNum) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, XmlParserException, InvalidResponseException, InternalException {
        log.info("/shuyx-minio/oss/createMultipartUpload, bucketName {} , fileName {},chunkNum {}",bucketName,fileName,chunkNum);
        log.info("文件名称为"+fileName);
        log.info("对象桶名称为"+bucketName);
        log.info("分片数量为"+chunkNum);

        // 1. 向minio创建分片上传请求,会返回一个uploadId
        CreateMultipartUploadResponse response = minioUtils.createMultipartUpload(bucketName, null, fileName, null, null);
        // 2. 获取uploadId
        String uploadId = response.result().uploadId();

        // 3. 返回结果信息
        Map<String, Object> result = new HashMap<>();
        result.put("uploadId",uploadId);

        ArrayList<String> uploadUrlList = new ArrayList<>();

        // 4. 请求Minio，获取每个分块，对应的签名上传URL
        Map<String, String> partMap = new HashMap<>();
        partMap.put("uploadId", uploadId);
        // 5. 根据分片数量，循环获取各个分片的预签名链接
        for (int i = 1; i <= chunkNum; i++) {
            partMap.put("partNumber", String.valueOf(i));
            String uploadUrl = minioUtils.getPresignedObjectUrl(bucketName, fileName, partMap);//获取每个分片文件的预签名链接
            uploadUrlList.add(uploadUrl);   //添加预签名链接
        }
        result.put("uploadUrlList",uploadUrlList);
        //返回
        return ReturnUtil.success(result);
    }

    /**
     * 合并分片文件
     * @param bucketName
     * @param fileName
     * @param uploadId
     * @return
     */
    @PostMapping("/mergePartFile")
    public Object mergePartFile(String bucketName,String fileName,String uploadId) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, XmlParserException, InvalidResponseException, InternalException {
        return ossService.completeMultipartUpload(bucketName, fileName, uploadId);
    }

    /**
     * 删除文件
     * @param fileName
     */
    @DeleteMapping("/delete")
    public Object delete(@RequestParam("fileName") String fileName,@RequestParam("bucketName") String bucketName) {
        log.info("/shuyx-minio/oss/delete, fileName,{},bucketName,{}",fileName,bucketName);
        return minioUtils.removeFile(bucketName, fileName);
    }

    /**
     * 获取文件信息
     * @param fileName
     * @return
     */
    @SneakyThrows
    @GetMapping("/info")
    public Object getFileStatusInfo(@RequestParam("fileName") String fileName,String bucketName) {
        log.info("/shuyx-minio/oss/info, fileName,{},bucketName,{}",fileName,bucketName);
        String fileStatusInfo = minioUtils.getFileInfo(bucketName, fileName);
        return ReturnUtil.success(new JSONObject().put("fileInfo",fileStatusInfo));
    }

    /**
     * 获取文件外链
     * @param fileName
     * @return
     */
    @GetMapping("/url")
    public Object getPresignedObjectUrl(@RequestParam("fileName") String fileName,String bucketName) {
        log.info("/shuyx-minio/oss/url,fileName,{},bucketName,{}",fileName,bucketName);
        String presignedObjectUrl = minioUtils.getPresignedObjectUrl(bucketName, fileName);
        return ReturnUtil.success(presignedObjectUrl);
    }

    /**
     * 文件下载
     * @param fileName
     * @param response
     */
    @GetMapping("/download")
    public Object download(@RequestParam("fileName") String fileName,String bucketName, HttpServletResponse response) {
        log.info("/shuyx-minio/oss/episodes/download, fileName,{},bucketName,{}",fileName,bucketName);
        try {
            InputStream fileInputStream = minioUtils.getObject(bucketName, fileName);
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(fileInputStream, response.getOutputStream());
        } catch (Exception e) {
            log.error("文件下载失败,请查询日志");
            e.printStackTrace();
            return ReturnUtil.fail(ResultCodeEnum.HTTP_REQUEST_ERROR);
        }
        return ReturnUtil.success();
    }

}
