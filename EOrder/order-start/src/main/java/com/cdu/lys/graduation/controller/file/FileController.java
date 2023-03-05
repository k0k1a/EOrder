package com.cdu.lys.graduation.controller.file;

import com.cdu.lys.graduation.commons.result.ActionResult;
import com.cdu.lys.graduation.commons.result.UploadFileResponse;
import com.cdu.lys.graduation.file.FileService;
import com.cdu.lys.graduation.types.SystemConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/28 21:59
 */
@Api(tags = "文件接口")
@RestController
@RequestMapping("/eorder/file")
@Slf4j
@Validated
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation("图片上传")
    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<UploadFileResponse> uploadFile(@NotNull(message = "文件不能为空") MultipartFile file) {

        String fileName = fileService.storeFile(file);
//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path(SystemConstant.SYSTEM_DOWNLOAD_PATH)
//                .path(fileName)
//                .toUriString();

        String fileDownloadUri = SystemConstant.SYSTEM_DOWNLOAD_PATH + fileName;
        UploadFileResponse response = new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
        return ActionResult.getSuccessResult(response);
    }

//    @PostMapping("/uploadMultipleFiles")
    public ActionResult<List<UploadFileResponse>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {

        List<UploadFileResponse> responses = new ArrayList<>();

        UriComponentsBuilder path = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(SystemConstant.SYSTEM_DOWNLOAD_PATH);
        Arrays.stream(files).forEach(file->{
            String filename = fileService.storeFile(file);
            UploadFileResponse response = new UploadFileResponse(filename, path.path(filename).toUriString(),
                    file.getContentType(), file.getSize());
            responses.add(response);
        });

        return ActionResult.getSuccessResult(responses);
    }

    @ApiOperation("文件下载")
    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.warn("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
