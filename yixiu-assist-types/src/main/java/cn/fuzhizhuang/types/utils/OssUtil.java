package cn.fuzhizhuang.types.utils;

import cn.fuzhizhuang.types.config.OssProperties;
import cn.hutool.core.io.FileUtil;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * OSS工具
 *
 * @author Fu.zhizhuang
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OssUtil {

    private final MinioClient minioClient;
    private final OssProperties properties;

    /**
     * 上传文件
     *
     * @param folder 文件夹
     * @param file   文件
     * @return 文件url
     */
    public String uploadFile(String folder, MultipartFile file) {
        String fileName = generateFileName(file);
        boolean fileExist = checkFileExist(folder, fileName);
        if (!fileExist) {
            // 上传文件
            String url = upload(fileName, file, folder);
            // 解析url
            return parseUrl(url);
        }
        return getFileUrl(folder, fileName);
    }

    /**
     * 生成文件名
     *
     * @param file 文件
     * @return 文件名
     */
    private String generateFileName(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        // 获取扩展名
        String extName = FileUtil.extName(fileName);
        // 如果没有扩展名，默认png
        if (StringUtils.isBlank(extName)) {
            extName = "png";
        }
        // 重新生成文件名 id.后缀名
        String idStr = UUID.randomUUID().toString();
        return String.join(".", idStr, extName);
    }

    /**
     * 检查文件是否存在
     *
     * @param folder   文件夹
     * @param fileName 文件名
     * @return 是否存在
     */
    private boolean checkFileExist(String folder, String fileName) {
        String bucketName = properties.getBucketName();
        if (checkBucketExist(bucketName)) {
            return checkObjectExist(bucketName, getFolderFileName(folder, fileName));
        }
        return false;
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件url
     */
    private String upload(String fileName, MultipartFile file, String folder) {
        // 上传文件
        uploadFile(fileName, file, folder);
        return getFileUrl(folder, fileName);
    }

    /**
     * 上传文件
     *
     * @param file 文件
     */
    private void uploadFile(String fileName, MultipartFile file, String folder) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(properties.getBucketName())
                    .object(getFolderFileName(folder, fileName))
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
        } catch (Exception e) {
            log.error("上传文件失败", e);
        }
    }

    /**
     * 获取文件url
     *
     * @param fileName 文件名
     * @return 文件url
     */
    private String getFileUrl(String folder, String fileName) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(properties.getBucketName())
                    .object(getFolderFileName(folder, fileName))
                    .build());
        } catch (Exception e) {
            log.error("获取文件url失败", e);
            throw new RuntimeException("获取文件url失败");
        }
    }

    /**
     * 获取bucket列表
     *
     * @return bucket列表
     */
    public List<Bucket> getBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            log.error("获取bucket失败", e);
            throw new RuntimeException("获取bucket失败");
        }
    }


    /**
     * 检查bucket是否存在
     *
     * @param bucketName bucket名称
     * @return 是否存在
     */
    public boolean checkBucketExist(String bucketName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.error("检查bucket是否存在失败", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建bucket
     *
     * @param bucketName bucket名称
     */
    public void createBucket(String bucketName) {
        boolean exist = checkBucketExist(bucketName);
        if (!exist) {
            try {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } catch (Exception e) {
                log.error("创建bucket失败", e);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 删除bucket
     *
     * @param bucketName bucket名称
     */
    public void deleteBucket(String bucketName) {
        boolean exist = checkBucketExist(bucketName);
        if (exist) {
            // 删除存储桶
            try {
                minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
            } catch (Exception e) {
                log.error("删除bucket失败", e);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return 是否存在
     */
    public boolean checkObjectExist(String bucketName, String objectName) {
        // 判断文件是否存在
        try {
            minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 删除文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     */
    public void deleteObject(String bucketName, String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            log.error("删除文件失败", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 拼接文件名
     *
     * @param folder   文件夹
     * @param fileName 文件名
     * @return 完整文件名
     */
    private String getFolderFileName(String folder, String fileName) {
        return StringUtils.isNotBlank(folder) ? String.join("/", folder, fileName) : fileName;
    }

    /**
     * 解析url
     *
     * @param url <a href="http://127.0.0.1:9000/assist/avatar/e29d0ae2-443c-4b9f-94d8-c9037b9dc137.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=yishotech%2F20241019%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241019T123202Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=8323163f788dc9eeacef65bbded1e5a223769efea5e1a442c4c8ddfba76aec59">...</a>
     * @return <a href="http://127.0.0.1:9000/assist/avatar/e29d0ae2-443c-4b9f-94d8-c9037b9dc137.png">...</a>
     */
    private String parseUrl(String url) {
        if (StringUtils.isBlank(url)) return "";
        return url.substring(0, url.indexOf("?"));
    }
}
