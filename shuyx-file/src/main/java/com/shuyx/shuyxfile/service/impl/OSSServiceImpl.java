package com.shuyx.shuyxfile.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.shuyx.shuyxcommons.utils.ResultCodeEnum;
import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxfile.service.OSSService;
import com.shuyx.shuyxfile.utils.MinioUtils;
import com.shuyx.shuyxfile.utils.MyMinioClient;
import io.minio.ListPartsResponse;
import io.minio.RemoveObjectArgs;
import io.minio.messages.Part;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 86182
 * @version 1.0
 * @description: TODO
 * @date 2024/3/23 13:23
 */
@Service
@Slf4j
public class OSSServiceImpl implements OSSService {

    @Autowired
    private MinioUtils minioUtils;
    @Autowired
    private MyMinioClient myMinioClient;


    /**
     * 上传文件
     * @param file
     * @return
     */
    public Object uploadFile(MultipartFile file, String bucketName){
        try {
            //新文件名
            String newFileName = System.currentTimeMillis() + "." +  file.getOriginalFilename();
            //文件类型
            String contentType = file.getContentType();
            //上传文件
            minioUtils.uploadFile(bucketName, file, newFileName, contentType);
            JSONObject jsonObject = new JSONObject();
            //新文件名就是文件链接
            jsonObject.put("fileUrl",newFileName);
            return ReturnUtil.success(jsonObject);
        } catch (Exception e) {
            log.error("上传文件失败。请查询日志。");
            e.printStackTrace();
            return ReturnUtil.fail(ResultCodeEnum.HTTP_REQUEST_ERROR);
        }
    }

    public Object completeMultipartUpload(String bucketName,String fileName,String uploadId){
        try{
            //先查询minio中具有相同uploadId的分片文件
            ListPartsResponse listPartsResponse = minioUtils.listParts(bucketName,null,
                    fileName,null,null, uploadId,null,null);
            List<Part> parts = listPartsResponse.result().partList();
            //找到这些分片文件之后，开始合并分片文件,并重新命名
            minioUtils.completeMultipartUpload(bucketName, null,
                    fileName, uploadId, parts.toArray(new Part[]{}), null, null);
            //合并成功
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fileUrl",fileName);
            return ReturnUtil.success(jsonObject);
        }catch (Exception e){
            log.error("合并分片文件失败。请查询日志。bucketName {},fileName {}, uploadId {}",bucketName,fileName,uploadId);
            e.printStackTrace();
            //合并失败
            return ReturnUtil.fail(ResultCodeEnum.MINIO_FILE_MERGE_FAIL);
        }
    }

    @Override
    public Object updateFile(MultipartFile file, String bucketName, String oldFileName) {
        try{
            //先删除旧文件
            myMinioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(oldFileName)
                            .build());
            //再上传新文件
            //新文件名
            String newFileName = System.currentTimeMillis() + "." +  file.getOriginalFilename();
            //文件类型
            String contentType = file.getContentType();
            //上传文件
            minioUtils.uploadFile(bucketName, file, newFileName, contentType);
            JSONObject jsonObject = new JSONObject();
            //新文件名就是文件链接
            jsonObject.put("fileUrl",newFileName);
            return ReturnUtil.success(jsonObject);
        }catch(Exception e){
            log.error("文件更新异常，请查询日志");
            e.printStackTrace();
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_UPDATE_FAILED);
        }
    }
}
