package com.shuyx.shuyxminio.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.shuyx.shuyxcommons.utils.ResultCodeEnum;
import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxminio.service.OSSService;
import com.shuyx.shuyxminio.utils.MinioUtils;
import io.minio.ListPartsResponse;
import io.minio.ObjectWriteResponse;
import io.minio.errors.*;
import io.minio.messages.Part;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
            jsonObject.put("fileName",newFileName);
            return ReturnUtil.success(jsonObject);
        } catch (Exception e) {
            log.error("上传文件失败。请查询日志。");
            e.printStackTrace();
            return ReturnUtil.fail(ResultCodeEnum.HTTP_REQUEST_ERROR);
        }
    }

    public void completeMultipartUpload(String bucketName,String fileName,String uploadId) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, XmlParserException, InvalidResponseException, InternalException {
        //先查询minio中具有相同uploadId的分片文件
        ListPartsResponse listPartsResponse = minioUtils.listParts(bucketName,null,
                fileName,null,null, uploadId,null,null);
        List<Part> parts = listPartsResponse.result().partList();
        //找到这些分片文件之后，开始合并分片文件
        minioUtils.completeMultipartUpload(bucketName, null,
                fileName, uploadId, parts.toArray(new Part[]{}), null, null);
    }
}
