package com.shuyx.shuyxfile.controller;

import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxfile.service.OSSService;
import com.shuyx.shuyxfile.utils.MinioUtils;
import io.minio.CreateMultipartUploadResponse;
import io.minio.StatObjectResponse;
import io.minio.errors.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/shuyx-file/oss")
public class OSSController {
    @Autowired
    private MinioUtils minioUtils;

    @Autowired
    private OSSService ossService;

    //用户头像文件对象桶
    private String USER_AVATAR_BUCKET = "user-avatar-bucket";
    //媒体剧集文件对象桶
    private String MEDIA_EPISODES_BUCKET = "media-episodes-bucket";
    //媒体封面图片对象桶
    private String MEDIA_COVER_BUCKET = "media-cover-bucket";

    /**
     * 上传文件接口
     *
     * @param file
     */
    @PostMapping("/upload")
    public Object upload(@RequestParam("file") MultipartFile file, String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("/shuyx-file/oss/upload, file,{}", file);
        double size = (double) (file.getSize() / 1024 / 1024) + 1;
        log.info(file.getOriginalFilename() + " 文件大小为 " + size + " MB之内。");
        return ossService.uploadFile(file, bucketName);
    }

    /**
     * 更新文件，先删除旧文件，再添加新文件
     *
     * @param file
     * @param bucketName
     */
    @PostMapping("/updateFile")
    public Object updateFile(@RequestParam("file") MultipartFile file, String bucketName, String oldFileName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("/shuyx-file/oss/updateFile, file {} ,bucketName {},oldFileName {}", file, bucketName, oldFileName);
        return ossService.updateFile(file, bucketName, oldFileName);
    }

    /**
     * 创建分片文件请求等信息
     * 逻辑：
     * 1. minio接收到前端发来的创建分片文件请求。会返回对应的uploadId
     * 2. 然后通过uploadId以及分片数量，创建同等数量的预签名链接。并返回
     *
     * @return
     */
    @PostMapping("/createMultipartUpload")
    public Object createMultipartUpload(String fileName, String bucketName, Integer chunkNum) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, XmlParserException, InvalidResponseException, InternalException {
        log.info("/shuyx-file/oss/createMultipartUpload, bucketName {} , fileName {},chunkNum {}", bucketName, fileName, chunkNum);
        log.info("文件名称为" + fileName);
        log.info("对象桶名称为" + bucketName);
        log.info("分片数量为" + chunkNum);

        // 1. 向minio创建分片上传请求,会返回一个uploadId
        CreateMultipartUploadResponse response = minioUtils.createMultipartUpload(bucketName, null, fileName, null, null);
        // 2. 获取uploadId
        String uploadId = response.result().uploadId();

        // 3. 返回结果信息
        Map<String, Object> result = new HashMap<>();
        result.put("uploadId", uploadId);

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
        result.put("uploadUrlList", uploadUrlList);
        //返回
        return ReturnUtil.success(result);
    }

    /**
     * 合并分片文件
     *
     * @param bucketName
     * @param fileName
     * @param uploadId
     * @return
     */
    @PostMapping("/mergePartFile")
    public Object mergePartFile(String bucketName, String fileName, String uploadId) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, XmlParserException, InvalidResponseException, InternalException {
        return ossService.completeMultipartUpload(bucketName, fileName, uploadId);
    }

    /**
     * 删除文件
     *
     * @param fileName
     */
    @DeleteMapping("/delete")
    public Object delete(@RequestParam("fileName") String fileName, @RequestParam("bucketName") String bucketName) {
        log.info("/shuyx-file/oss/delete, fileName,{},bucketName,{}", fileName, bucketName);
        return minioUtils.removeFile(bucketName, fileName);
    }

    /**
     * 获取文件信息
     *
     * @param fileName
     * @return
     */
    @SneakyThrows
    @GetMapping("/info")
    public Object getFileStatusInfo(@RequestParam("fileName") String fileName, String bucketName) {
        log.info("/shuyx-file/oss/info, fileName,{},bucketName,{}", fileName, bucketName);
        StatObjectResponse fileInfo = minioUtils.getFileInfo(bucketName, fileName);
        return ReturnUtil.success(fileInfo);
    }

    /**
     * 获取文件外链
     *
     * @param fileName
     * @return
     */
    @GetMapping("/url")
    public Object getPresignedObjectUrl(@RequestParam("fileName") String fileName, String bucketName) {
        log.info("/shuyx-file/oss/url,fileName,{},bucketName,{}", fileName, bucketName);
        String presignedObjectUrl = minioUtils.getPresignedObjectUrl(bucketName, fileName);
        return ReturnUtil.success(presignedObjectUrl);
    }

    /**
     * 文件下载
     *
     * @param fileName
     * @param response
     */
    @GetMapping("/download")
    public void download(@RequestParam("fileName") String fileName, @RequestParam("bucketName") String bucketName, HttpServletResponse response) throws IOException {
        log.info("/shuyx-file/oss/download, fileName,{},bucketName,{}", fileName, bucketName);
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            //先查询文件信息
            StatObjectResponse fileInfo = minioUtils.getFileInfo(bucketName, fileName);
            //设置响应请求
            response.setContentType("application/octet-stream");
            response.setContentLengthLong(fileInfo.size());
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "utf8"));

            inputStream = minioUtils.getObject(bucketName, fileName);
            outputStream = response.getOutputStream();
            byte[] buffer = new byte[2048];
            int len = 0;
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("文件下载失败,请查询日志");
        } finally {
            inputStream.close();
            outputStream.close();
        }
    }
}
