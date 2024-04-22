package com.shuyx.shuyxfile.service;

import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface OSSService {

    Object uploadFile(MultipartFile file, String bucketName);

    Object completeMultipartUpload(String bucketName, String fileName, String uploadId) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, XmlParserException, InvalidResponseException, InternalException;

    Object updateFile(MultipartFile file, String bucketName, String oldFileName);

}
