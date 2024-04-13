package com.shuyx.shuyxmedia.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "shuyx-minio",path = "/shuyx-minio/oss")
public interface MinioFeignService {

    @PostMapping("/upload")
    Object upload(@RequestParam("file") MultipartFile file, @RequestParam("bucketName") String bucketName);

    @DeleteMapping("/delete")
    public Object delete(@RequestParam("fileName") String fileName,@RequestParam("bucketName") String bucketName);
}
