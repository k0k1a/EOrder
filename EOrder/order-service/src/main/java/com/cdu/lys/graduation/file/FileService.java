package com.cdu.lys.graduation.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author liyinsong
 * @date 2022/3/28 22:21
 */
public interface FileService {

    /**
     * 存储文件
     * @param file file
     * @return filename
     */
    public String storeFile(MultipartFile file);

    Resource loadFileAsResource(String fileName);
}
