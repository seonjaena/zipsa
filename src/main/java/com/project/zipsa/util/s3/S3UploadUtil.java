package com.project.zipsa.util.s3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3UploadUtil {

    private final AWSS3 awss3;
    private final MessageSource messageSource;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.dir-prefix}")
    private String dirPrefix;

    @Value("${local.win.dir-prefix}")
    private String winDirPrefix;

    @Value("${local.linux.dir-prefix}")
    private String linuxDirPrefix;

    @Value("${local.mac.dir-prefix}")
    private String macDirPrefix;

    public String upload(MultipartFile multipartFile) throws IOException {
        String changedFileName = getChangedFileName(multipartFile.getOriginalFilename()).replace(" ", "_");
        String tempFileFullPath = String.format("%s/%s", getLocalFilePath(), changedFileName);
        String s3FileFullPath = String.format("%s/%s", dirPrefix, changedFileName);

        File uploadFile = convert(multipartFile, tempFileFullPath)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException(messageSource.getMessage("error.user.change.profile", null, Locale.KOREA)));

        return upload(uploadFile, s3FileFullPath);
    }

    // S3로 파일 업로드하기
    private String upload(File uploadFile, String s3FileFullPath) {
        String fileURL = putS3(uploadFile, s3FileFullPath);
        removeNewFile(uploadFile);
        return fileURL;
    }

    // S3로 업로드
    private String putS3(File uploadFile, String s3FileFullPath) {
        try {
            awss3.upload(bucket, new FileInputStream(uploadFile), s3FileFullPath, uploadFile.getName(), uploadFile.length());
        }catch(Exception e) {
            log.error("Upload File to S3 Error: {}", e.getMessage());
        }
        return s3FileFullPath;
    }

    // 로컬에 파일 업로드 하기
    private Optional<File> convert(MultipartFile file, String changedFileFullPath) throws IOException {
        File convertFile = new File(changedFileFullPath);
        if (!convertFile.createNewFile()) {
            removeNewFile(convertFile);
            return Optional.empty();
        }

        try (FileOutputStream fos = new FileOutputStream(convertFile)) {
            fos.write(file.getBytes());
        }

        return Optional.of(convertFile);
    }

    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

    private String getLocalFilePath() {
        String os = System.getProperty("os.name").toLowerCase();

        log.info("OS name = {}", os);

        if(os.contains("win")) {
            return winDirPrefix;
        }else if(os.contains("linux")) {
            return linuxDirPrefix;
        }else if(os.contains("mac")) {
            return macDirPrefix;
        }else {
            return null;
        }
    }

    private String getChangedFileName(String fileName) {
        return String.format("%s_%s_%s", System.currentTimeMillis(), UUID.randomUUID(), fileName);
    }

    public String getFileURL(String s3FileFullPath) {
        return awss3.getFileURL(s3FileFullPath);
    }

}
