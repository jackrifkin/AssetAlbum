package com.dishianerifkinj.util;

import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;

public class FileUtil {

    public static Blob multiPartFileToBlob(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            return new SerialBlob(bytes);
        } catch (Exception e) {
            throw new RuntimeException("Error while converting file to blob", e);
        }
    }

    public static MultipartFile byteArrayToMultiPartFile(byte[] byteArray, String filename) {
        return new BlobMultipartFile(byteArray, filename);
    }
}
