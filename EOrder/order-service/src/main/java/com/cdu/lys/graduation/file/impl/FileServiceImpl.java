package com.cdu.lys.graduation.file.impl;

import com.cdu.lys.graduation.file.FileService;
import com.cdu.lys.graduation.types.exception.ErrorType;
import com.cdu.lys.graduation.types.exception.FileException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author liyinsong
 * @date 2022/3/28 22:21
 */
@Service
public class FileServiceImpl implements FileService {

    private final Path fileStorageLocation; // 文件在本地存储的地址

    public FileServiceImpl(@Value("${system.file.upload-dir}") String uploadUrl) {
        this.fileStorageLocation = Paths.get(uploadUrl).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            throw new FileException(ErrorType.BIZ_ERROR, "can not create the dir where the upload files will be store", e);
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileException(ErrorType.BIZ_ERROR, "Sorry! Filename contains invalid path sequence " + fileName);
            }
            String encodeName = this.encodeFilename(fileName);

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(encodeName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return encodeName;
        } catch (IOException ex) {
            throw new FileException(ErrorType.BIZ_ERROR, "Could not store file " + fileName + ". Please try again!", ex);
        }

    }

    /**
     * 加载文件
     * @param fileName 文件名
     * @return 文件
     */
    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileException(ErrorType.BIZ_ERROR, "File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileException(ErrorType.BIZ_ERROR, "File not found " + fileName, ex);
        }
    }

    /**
     * 文件名加密
     * @param fileName 文件名
     * @return 加密后的文件名
     */
    private String encodeFilename(String fileName) {
        String name = fileName.substring(0, fileName.lastIndexOf("."));
        String postfix = fileName.substring(fileName.lastIndexOf("."));

        return DigestUtils.md5Hex(name) + postfix;
    }

}
